/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*
*/
package org.wso2.carbon.device.mgt.extensions.remote.session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.base.MultitenantConstants;
import org.wso2.carbon.device.mgt.common.DeviceIdentifier;
import org.wso2.carbon.device.mgt.common.DeviceManagementException;
import org.wso2.carbon.device.mgt.common.InvalidDeviceException;
import org.wso2.carbon.device.mgt.common.authorization.DeviceAccessAuthorizationException;
import org.wso2.carbon.device.mgt.common.authorization.DeviceAccessAuthorizationService;
import org.wso2.carbon.device.mgt.common.operation.mgt.Activity;
import org.wso2.carbon.device.mgt.common.operation.mgt.OperationManagementException;
import org.wso2.carbon.device.mgt.core.DeviceManagementConstants;
import org.wso2.carbon.device.mgt.core.service.DeviceManagementProviderService;
import org.wso2.carbon.device.mgt.extensions.remote.session.authentication.AuthenticationInfo;
import org.wso2.carbon.device.mgt.extensions.remote.session.authentication.OAuthAuthenticator;
import org.wso2.carbon.device.mgt.extensions.remote.session.common.BaseRemoteSessionTest;
import org.wso2.carbon.device.mgt.extensions.remote.session.common.RemoteSessionTestConstants;
import org.wso2.carbon.device.mgt.extensions.remote.session.common.ServiceHolder;
import org.wso2.carbon.device.mgt.extensions.remote.session.exception.RemoteSessionManagementException;
import org.wso2.carbon.device.mgt.extensions.remote.session.internal.RemoteSessionManagementDataHolder;

import java.io.IOException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import static org.mockito.Matchers.anyList;

/**
 * /**
 * This class includes unit tests for testing the functionality of
 * {@link org.wso2.carbon.device.mgt.extensions.remote.session.RemoteSessionManagementServiceImpl}
 */
public class RemoteSessionManagementServiceTest extends BaseRemoteSessionTest {

    private static final Log log = LogFactory.getLog(RemoteSessionManagementServiceTest.class);

    private OAuthAuthenticator oAuthAuthenticator;
    private Session clientSession, deviceSession;

    @BeforeClass
    public void init() throws DeviceAccessAuthorizationException, OperationManagementException, InvalidDeviceException,
            IOException, DeviceManagementException {

        super.init();
        initMocks();
        RemoteSessionManagementDataHolder.getInstance().setEnabled(true);
        RemoteSessionManagementDataHolder.getInstance().setOauthAuthenticator(oAuthAuthenticator);
        RemoteSessionManagementDataHolder.getInstance().setServerUrl("wss://localhost:9443");
    }

    @Test(description = "Client initiate the remote session",
          priority = 1)
    public void initiateRemoteClientSessionTest()
            throws DeviceAccessAuthorizationException, OperationManagementException, InvalidDeviceException,
            RemoteSessionManagementException {
        Mockito.when(clientSession.isOpen()).thenReturn(true);
        log.info("Client initiate remote session for device type: " + RemoteSessionTestConstants.DEVICE_TYPE + " and "
                + "device id: " + RemoteSessionTestConstants.DEVICE_ID);
        ServiceHolder.getInstance().getRemoteSessionManagementService()
                .initializeSession(clientSession, RemoteSessionTestConstants.DEVICE_TYPE,
                        RemoteSessionTestConstants.DEVICE_ID);
        Assert.assertEquals(RemoteSessionManagementDataHolder.getInstance().getSessionMap().size(), 1,
                "The Client Device session should be 1");
    }

    @Test(description = "Device connect to remote session",
          priority = 2)
    public void DeviceConnectToRemoteSessionTest()
            throws DeviceAccessAuthorizationException, OperationManagementException, InvalidDeviceException,
            RemoteSessionManagementException {

        Mockito.when(deviceSession.isOpen()).thenReturn(true);
        ServiceHolder.getInstance().getRemoteSessionManagementService()
                .initializeSession(deviceSession, RemoteSessionTestConstants.DEVICE_TYPE,
                        RemoteSessionTestConstants.DEVICE_ID, RemoteSessionTestConstants.OPERATION_ID);
        Assert.assertEquals(RemoteSessionManagementDataHolder.getInstance().getSessionMap().size(), 2,
                "The Client Device session should be 2");
        log.info("Remote device with  device type: " + RemoteSessionTestConstants.DEVICE_TYPE + " and device id: "
                + RemoteSessionTestConstants.DEVICE_ID + " connected to client's remote session");
    }

    @Test(description = "Send remote control message to Device",
          priority = 3)
    public void SendRemoteMessageToDeviceTest() throws RemoteSessionManagementException {

        ServiceHolder.getInstance().getRemoteSessionManagementService()
                .sendMessageToPeer(clientSession, RemoteSessionTestConstants.MESSAGE);
        log.info("Client sent remote string message :" + RemoteSessionTestConstants.MESSAGE + " to device with type: "
                + RemoteSessionTestConstants.DEVICE_TYPE + " and device id: " + RemoteSessionTestConstants.DEVICE_ID);
    }

    @Test(description = "Send remote control message response to Client",
          priority = 4)
    public void SendRemoteMessageResponseToClientTest() throws RemoteSessionManagementException {
        ServiceHolder.getInstance().getRemoteSessionManagementService()
                .sendMessageToPeer(deviceSession, RemoteSessionTestConstants.MESSAGE);
        log.info("Device sent remote message :" + RemoteSessionTestConstants.MESSAGE_RESPONSE + " to client with client"
                + " session id: " + clientSession.getId());
    }

    @Test(description = "Send remote control message to Device",
          priority = 3)
    public void SendRemoteByteMessageToDeviceTest() throws RemoteSessionManagementException {

        ServiceHolder.getInstance().getRemoteSessionManagementService()
                .sendMessageToPeer(clientSession, RemoteSessionTestConstants.MESSAGE.getBytes());
        log.info("Client sent remote byte message :" + RemoteSessionTestConstants.MESSAGE + " to device with type: "
                + RemoteSessionTestConstants.DEVICE_TYPE + " and device id: " + RemoteSessionTestConstants.DEVICE_ID);
    }

    @Test(description = "Send remote control message response to Client",
          priority = 4)
    public void SendRemoteByteMessageResponseToClientTest() throws RemoteSessionManagementException {
        ServiceHolder.getInstance().getRemoteSessionManagementService()
                .sendMessageToPeer(deviceSession, RemoteSessionTestConstants.MESSAGE.getBytes());
        log.info("Device sent remote byte message :" + RemoteSessionTestConstants.MESSAGE_RESPONSE + " to client with "
                + "client session id: " + clientSession.getId());
    }

    @Test(description = "End the remote session",
          priority = 5)
    public void EndRemoteSesssionTest() throws RemoteSessionManagementException {
        ServiceHolder.getInstance().getRemoteSessionManagementService()
                .endSession(clientSession, "Client closed the " + "" + "session");
        log.info("Client closed the session");
        Assert.assertEquals(RemoteSessionManagementDataHolder.getInstance().getSessionMap().size(), 0,
                "The session count should be 0 after closing sessions");
    }

    /**
     * Init Mock objects and assign to related data holder
     *
     * @throws OperationManagementException       throws if an error occur while adding an operation
     * @throws InvalidDeviceException             throws error when passing invalid device data
     * @throws DeviceAccessAuthorizationException throws when unauthorised user access the device
     * @throws IOException                        throws when error occur with session
     */
    private void initMocks()
            throws OperationManagementException, InvalidDeviceException, DeviceAccessAuthorizationException,
            IOException {

        AuthenticationInfo authenticationInfo = new AuthenticationInfo();
        authenticationInfo.setTenantDomain(MultitenantConstants.SUPER_TENANT_DOMAIN_NAME);
        authenticationInfo.setAuthenticated(true);
        Activity activity = new Activity();
        activity.setActivityId(
                DeviceManagementConstants.OperationAttributes.ACTIVITY.concat(RemoteSessionTestConstants.OPERATION_ID));
        this.oAuthAuthenticator = Mockito.mock(OAuthAuthenticator.class);
        Mockito.when(oAuthAuthenticator.isAuthenticated(Mockito.anyMap())).thenReturn(authenticationInfo);
        this.deviceSession = Mockito.mock(Session.class);
        this.clientSession = Mockito.mock(Session.class);
        RemoteEndpoint.Basic basicRemote = Mockito.mock(RemoteEndpoint.Basic.class);
        DeviceAccessAuthorizationService deviceAccessAuthorizationService = Mockito
                .mock(DeviceAccessAuthorizationService.class);
        DeviceManagementProviderService deviceManagementProviderService = Mockito
                .mock(DeviceManagementProviderService.class);
        Mockito.when(deviceAccessAuthorizationService
                .isUserAuthorized((DeviceIdentifier) Mockito.anyObject(), Mockito.anyString())).thenReturn(true);
        Mockito.when(deviceManagementProviderService.addOperation(Mockito.anyString(), Mockito.anyObject(), anyList()))
                .thenReturn(activity);
        Mockito.when(deviceSession.getBasicRemote()).thenReturn(basicRemote);
        Mockito.when(clientSession.getBasicRemote()).thenReturn(basicRemote);
        Mockito.when(clientSession.getId()).thenReturn(RemoteSessionTestConstants.CLIENT_SESSION_ID);
        Mockito.when(deviceSession.getId()).thenReturn(RemoteSessionTestConstants.DEVICE_SESSION_ID);
        Mockito.doNothing().when(basicRemote).sendText(Mockito.anyString());
        RemoteSessionManagementDataHolder.getInstance()
                .setDeviceAccessAuthorizationService(deviceAccessAuthorizationService);
        RemoteSessionManagementDataHolder.getInstance()
                .setDeviceManagementProviderService(deviceManagementProviderService);
    }
}
