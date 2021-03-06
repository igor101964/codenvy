/*
 *  [2012] - [2017] Codenvy, S.A.
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.api.workspace.server.filters;


import com.codenvy.api.machine.server.recipe.RecipeDomain;
import com.codenvy.api.permission.server.PermissionsManager;
import com.codenvy.api.permission.server.SystemDomain;
import com.codenvy.api.permission.server.filter.check.DefaultRemovePermissionsChecker;
import com.codenvy.api.workspace.server.stack.StackDomain;
import com.codenvy.api.workspace.server.stack.StackPermissionsImpl;

import org.eclipse.che.api.core.ForbiddenException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.commons.env.EnvironmentContext;
import org.eclipse.che.commons.subject.Subject;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codenvy.api.permission.server.SystemDomain.MANAGE_SYSTEM_ACTION;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests {@link PublicPermissionsRemoveChecker}.
 *
 * @author Anton Korneta
 */
@Listeners(MockitoTestNGListener.class)
public class PublicPermissionsRemoveCheckerTest {

    private static final String USER     = "user123";
    private static final String INSTANCE = "instance123";

    @Mock
    private Subject                         subj;
    @Mock
    private PermissionsManager              manager;
    @Mock
    private DefaultRemovePermissionsChecker defaultChecker;

    private PublicPermissionsRemoveChecker publicPermissionsRemoveChecker;

    @BeforeMethod
    public void setup() throws Exception {
        publicPermissionsRemoveChecker = new PublicPermissionsRemoveChecker(defaultChecker, manager);
        final EnvironmentContext ctx = new EnvironmentContext();
        ctx.setSubject(subj);
        EnvironmentContext.setCurrent(ctx);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        EnvironmentContext.reset();
    }

    @Test
    public void permitsRemoveNonPublicPermissionsWhenDefaultCheckPassed() throws Exception {
        doNothing().when(defaultChecker).check(USER, StackDomain.DOMAIN_ID, INSTANCE);

        publicPermissionsRemoveChecker.check(USER, StackDomain.DOMAIN_ID, INSTANCE);

        verify(defaultChecker, times(1)).check(USER, StackDomain.DOMAIN_ID, INSTANCE);
        verify(manager, never()).get(USER, StackDomain.DOMAIN_ID, INSTANCE);
    }

    @Test(expectedExceptions = ForbiddenException.class)
    public void throwsForbiddenExceptionWhenRemoveNonPublicPermissionsAndDefaultCheckFailed() throws Exception {
        doThrow(ForbiddenException.class).when(defaultChecker).check(USER, StackDomain.DOMAIN_ID, INSTANCE);

        publicPermissionsRemoveChecker.check(USER, StackDomain.DOMAIN_ID, INSTANCE);

        verify(defaultChecker, times(1)).check(USER, StackDomain.DOMAIN_ID, INSTANCE);
        verify(manager, never()).get(USER, StackDomain.DOMAIN_ID, INSTANCE);
    }

    @Test(expectedExceptions = ForbiddenException.class)
    public void throwsForbiddenExceptionWhenFailedToGetActionRemovingPermissionByAdmin() throws Exception {
        doThrow(ServerException.class).when(manager).get("*", StackDomain.DOMAIN_ID, INSTANCE);
        doThrow(ForbiddenException.class).when(defaultChecker).check("*", StackDomain.DOMAIN_ID, INSTANCE);
        when(subj.hasPermission(SystemDomain.DOMAIN_ID, null, MANAGE_SYSTEM_ACTION)).thenReturn(true);

        publicPermissionsRemoveChecker.check("*", StackDomain.DOMAIN_ID, INSTANCE);
    }

    @Test
    public void permitsRemoveStackPermissionsWhenAdminUserPassedDefaultCheck() throws Exception {
        when(manager.get("*", StackDomain.DOMAIN_ID, INSTANCE))
                .thenReturn(new StackPermissionsImpl("*", INSTANCE, singletonList(StackDomain.SEARCH)));
        when(subj.hasPermission(SystemDomain.DOMAIN_ID, null, MANAGE_SYSTEM_ACTION)).thenReturn(true);

        publicPermissionsRemoveChecker.check("*", StackDomain.DOMAIN_ID, INSTANCE);

        verify(manager, times(1)).get("*", StackDomain.DOMAIN_ID, INSTANCE);
        verify(subj, times(1)).hasPermission(SystemDomain.DOMAIN_ID, null, MANAGE_SYSTEM_ACTION);
        verify(defaultChecker, never()).check("*", StackDomain.DOMAIN_ID, INSTANCE);
    }

    @Test
    public void permitsRemoveRecipePermissionsWhenAdminUserPassedDefaultCheck() throws Exception {
        when(manager.get("*", RecipeDomain.DOMAIN_ID, INSTANCE))
                .thenReturn(new StackPermissionsImpl("*", INSTANCE, singletonList(StackDomain.SEARCH)));
        when(subj.hasPermission(SystemDomain.DOMAIN_ID, null, MANAGE_SYSTEM_ACTION)).thenReturn(true);

        publicPermissionsRemoveChecker.check("*", RecipeDomain.DOMAIN_ID, INSTANCE);

        verify(manager, times(1)).get("*", RecipeDomain.DOMAIN_ID, INSTANCE);
        verify(subj, times(1)).hasPermission(SystemDomain.DOMAIN_ID, null, MANAGE_SYSTEM_ACTION);
        verify(defaultChecker, never()).check("*", RecipeDomain.DOMAIN_ID, INSTANCE);
    }

}
