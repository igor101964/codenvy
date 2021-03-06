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
package com.codenvy.api.permission.shared.event;

import com.codenvy.api.permission.shared.model.Permissions;

import org.eclipse.che.commons.annotation.Nullable;

/**
 * The base interface for all events related to permissions.
 *
 * @author Anton Korneta
 */
public interface PermissionsEvent {

    /**
     * Returns the permissions related to this event.
     */
    Permissions getPermissions();

    /**
     * Returns concrete event type, see {@link EventType}.
     */
    EventType getType();

    /**
     * Returns name of user who acted with permission or null if user is undefined.
     */
    @Nullable
    String getInitiator();
}
