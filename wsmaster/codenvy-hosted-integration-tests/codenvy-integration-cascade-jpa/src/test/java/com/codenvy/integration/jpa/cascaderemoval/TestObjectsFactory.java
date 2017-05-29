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
package com.codenvy.integration.jpa.cascaderemoval;

import com.codenvy.api.workspace.server.model.impl.WorkerImpl;
import com.codenvy.resource.spi.impl.FreeResourcesLimitImpl;
import com.codenvy.resource.spi.impl.ResourceImpl;
import com.google.common.collect.ImmutableMap;

import org.eclipse.che.account.shared.model.Account;
import org.eclipse.che.account.spi.AccountImpl;
import org.eclipse.che.api.factory.server.model.impl.AuthorImpl;
import org.eclipse.che.api.factory.server.model.impl.FactoryImpl;
import org.eclipse.che.api.machine.server.model.impl.SnapshotImpl;
import org.eclipse.che.api.machine.server.recipe.RecipeImpl;
import org.eclipse.che.api.ssh.server.model.impl.SshPairImpl;
import org.eclipse.che.api.user.server.model.impl.ProfileImpl;
import org.eclipse.che.api.user.server.model.impl.UserImpl;
import org.eclipse.che.api.workspace.server.model.impl.WorkspaceConfigImpl;
import org.eclipse.che.api.workspace.server.model.impl.WorkspaceImpl;
import org.eclipse.che.api.workspace.server.model.impl.stack.StackComponentImpl;
import org.eclipse.che.api.workspace.server.model.impl.stack.StackImpl;
import org.eclipse.che.api.workspace.server.model.impl.stack.StackSourceImpl;
import org.eclipse.che.api.workspace.server.stack.image.StackIcon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Defines method for creating tests object instances.
 *
 * @author Yevhenii Voevodin
 */
public final class TestObjectsFactory {

    public static AccountImpl createAccount(String id) {
        return new AccountImpl(id, id + "_name", "test");
    }

    public static UserImpl createUser(String id) {
        return new UserImpl(id,
                            id + "@eclipse.org",
                            id + "_name",
                            "password",
                            asList(id + "_alias1", id + "_alias2"));
    }

    public static ProfileImpl createProfile(String userId) {
        return new ProfileImpl(userId, new HashMap<>(ImmutableMap.of("attribute1", "value1",
                                                                     "attribute2", "value2",
                                                                     "attribute3", "value3")));
    }

    public static Map<String, String> createPreferences() {
        return new HashMap<>(ImmutableMap.of("preference1", "value1",
                                             "preference2", "value2",
                                             "preference3", "value3"));
    }

    public static WorkspaceConfigImpl createWorkspaceConfig(String id) {
        return new WorkspaceConfigImpl(id + "_name",
                                       id + "description",
                                       "default-env",
                                       null,
                                       null,
                                       null);
    }

    public static WorkspaceImpl createWorkspace(String id, Account account) {
        return new WorkspaceImpl(id, account, createWorkspaceConfig(id));
    }

    public static SshPairImpl createSshPair(String owner, String service, String name) {
        return new SshPairImpl(owner, service, name, "public-key", "private-key");
    }

    public static FactoryImpl createFactory(String id, String creator) {
        return new FactoryImpl(id,
                               id + "-name",
                               "4.0",
                               createWorkspaceConfig(id),
                               new AuthorImpl(creator, System.currentTimeMillis()),
                               null,
                               null,
                               null);
    }

    public static SnapshotImpl createSnapshot(String snapshotId, String workspaceId) {
        return new SnapshotImpl(snapshotId,
                                "type",
                                null,
                                System.currentTimeMillis(),
                                workspaceId,
                                snapshotId + "_description",
                                true,
                                "dev-machine",
                                snapshotId + "env-name");
    }

    public static RecipeImpl createRecipe(String id) {
        return new RecipeImpl(id,
                              "recipe-name-" + id,
                              "recipe-creator",
                              "recipe-type",
                              "recipe-script",
                              asList("recipe-tag1", "recipe-tag2"),
                              "recipe-description");
    }

    public static StackImpl createStack(String id, String name) {
        return StackImpl.builder()
                        .setId(id)
                        .setName(name)
                        .setCreator("user123")
                        .setDescription(id + "-description")
                        .setScope(id + "-scope")
                        .setWorkspaceConfig(createWorkspaceConfig(id + "test"))
                        .setTags(asList(id + "-tag1", id + "-tag2"))
                        .setComponents(asList(new StackComponentImpl(id + "-component1", id + "-component1-version"),
                                              new StackComponentImpl(id + "-component2", id + "-component2-version")))
                        .setSource(new StackSourceImpl(id + "-type", id + "-origin"))
                        .setStackIcon(new StackIcon(id + "-icon",
                                                    id + "-media-type",
                                                    "0x1234567890abcdef".getBytes()))
                        .build();
    }


    public static WorkerImpl createWorker(String userId, String workspaceId) {
        return new WorkerImpl(workspaceId, userId, Arrays.asList("read", "write", "run"));
    }

    public static FreeResourcesLimitImpl createFreeResourcesLimit(String accountId) {
        return new FreeResourcesLimitImpl(accountId,
                                          Arrays.asList(new ResourceImpl("test1", 123, "mb"),
                                                        new ResourceImpl("test2", 234, "h")));
    }

    private TestObjectsFactory() {}
}
