/*
 *
 * CODENVY CONFIDENTIAL
 * ________________
 *
 * [2012] - [2013] Codenvy, S.A.
 * All Rights Reserved.
 * NOTICE: All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.analytics.metrics.sessions;

import com.codenvy.analytics.metrics.AbstractListValueResulted;
import com.codenvy.analytics.metrics.MetricType;

/** @author <a href="mailto:abazko@codenvy.com">Anatoliy Bazko</a> */
public class ProductUsageSessionsList extends AbstractListValueResulted {

    public static final String WS           = "ws";
    public static final String USER         = "user";
    public static final String DOMAIN       = "domain";
    public static final String USER_COMPANY = "user_company";
    public static final String TIME         = "time";
    public static final String START_TIME   = "start_time";
    public static final String END_TIME     = "end_time";
    public static final String SESSION_ID   = "session_id";

    public ProductUsageSessionsList() {
        super(MetricType.PRODUCT_USAGE_SESSIONS_LIST);
    }

    @Override
    public String[] getTrackedFields() {
        return new String[]{WS,
                            USER,
                            USER_COMPANY,
                            DOMAIN,
                            TIME,
                            START_TIME,
                            END_TIME,
                            SESSION_ID,
                            DATE};
    }

    @Override
    public String getStorageCollectionName() {
        return super.getStorageCollectionName();
    }

    @Override
    public String getDescription() {
        return "Users' sessions";
    }
}
