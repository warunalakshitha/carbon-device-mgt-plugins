/*
 *   Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */
package org.wso2.carbon.device.mgt.extensions.remote.session;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.device.mgt.common.DeviceManagementException;
import org.wso2.carbon.device.mgt.common.InvalidDeviceException;
import org.wso2.carbon.device.mgt.common.authorization.DeviceAccessAuthorizationException;
import org.wso2.carbon.device.mgt.common.operation.mgt.OperationManagementException;
import org.wso2.carbon.device.mgt.core.config.DeviceConfigurationManager;
import org.wso2.carbon.device.mgt.extensions.remote.session.common.BaseRemoteSessionTest;
import org.wso2.carbon.device.mgt.extensions.remote.session.exception.RemoteSessionManagementException;
import org.wso2.carbon.device.mgt.extensions.remote.session.internal.RemoteSessionManagementDataHolder;
import org.wso2.carbon.device.mgt.extensions.remote.session.listener.RemoteSessionManagerStartupListener;

import java.io.IOException;

/**
 * /**
 * This class includes unit tests for testing the functionality of
 * {@link org.wso2.carbon.device.mgt.extensions.remote.session.listener.RemoteSessionManagerStartupListener}
 */
public class RemoteSessionManagerStartupListenerTest extends BaseRemoteSessionTest {

    RemoteSessionManagerStartupListener remoteSessionManagerStartupListener;

    @BeforeClass
    public void init() throws DeviceAccessAuthorizationException, OperationManagementException, InvalidDeviceException,
            IOException, DeviceManagementException {

        super.init();
        DeviceConfigurationManager.getInstance().initConfig();
        remoteSessionManagerStartupListener = new RemoteSessionManagerStartupListener();
    }

    @Test(description = "Start Remote Session Manager Startup Listener",
          priority = 1)
    public void startRemoteSessionManagerStartupListenerTest()
            throws DeviceAccessAuthorizationException, OperationManagementException, InvalidDeviceException,
            RemoteSessionManagementException {
        remoteSessionManagerStartupListener.completedServerStartup();
        Assert.assertEquals(RemoteSessionManagementDataHolder.getInstance().isEnabled(), true);
    }
}
