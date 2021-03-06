/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.sso.saml.cache;

import org.wso2.carbon.identity.application.authentication.framework.store.SessionDataStore;
import org.wso2.carbon.identity.application.common.cache.BaseCache;

public class SAMLSSOSessionIndexCache extends BaseCache<SAMLSSOSessionIndexCacheKey, SAMLSSOSessionIndexCacheEntry> {

    private static final String CACHE_NAME = "SAMLSSOSessionIndexCache";
    private static volatile SAMLSSOSessionIndexCache instance;

    private SAMLSSOSessionIndexCache() {
        super(CACHE_NAME);
    }

    public static SAMLSSOSessionIndexCache getInstance() {
        if (instance == null) {
            synchronized (SAMLSSOSessionIndexCache.class) {
                if (instance == null) {
                    instance = new SAMLSSOSessionIndexCache();
                }
            }
        }
        return instance;
    }

    public void addToCache(SAMLSSOSessionIndexCacheKey key, SAMLSSOSessionIndexCacheEntry entry) {
        super.addToCache(key, entry);
        SessionDataStore.getInstance().storeSessionData(key.getTokenId(), CACHE_NAME, entry);
    }

    public SAMLSSOSessionIndexCacheEntry getValueFromCache(SAMLSSOSessionIndexCacheKey key) {
        SAMLSSOSessionIndexCacheEntry cacheEntry = super.getValueFromCache(key);
        if (cacheEntry == null) {
            cacheEntry = (SAMLSSOSessionIndexCacheEntry) SessionDataStore.getInstance().getSessionData(key.getTokenId(),
                    CACHE_NAME);
            super.addToCache(key, cacheEntry);
        }
        return cacheEntry;
    }

    public void clearCacheEntry(SAMLSSOSessionIndexCacheKey key) {
        super.clearCacheEntry(key);
        SessionDataStore.getInstance().clearSessionData(key.getTokenId(), CACHE_NAME);
    }
}
