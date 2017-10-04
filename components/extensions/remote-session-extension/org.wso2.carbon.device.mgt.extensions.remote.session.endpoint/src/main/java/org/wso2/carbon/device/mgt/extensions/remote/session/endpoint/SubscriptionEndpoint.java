/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * deviceId 2.0 (the "License"); you may not use this file except
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

package org.wso2.carbon.device.mgt.extensions.remote.session.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.device.mgt.extensions.remote.session.endpoint.utils.ServiceHolder;
import org.wso2.carbon.device.mgt.extensions.remote.session.exception.RemoteSessionManagementException;

import javax.websocket.CloseReason;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.io.IOException;

/**
 * This class represents common web socket endpoint to manage Remote Sessions
 */
public class SubscriptionEndpoint {

    private static final Log log = LogFactory.getLog(SubscriptionEndpoint.class);

    /**
     * Web socket onMessage - When client sends a message
     *
     * @param session    - Registered  session.
     * @param deviceType - DeviceType
     * @param message    - String Message which needs to send to peer
     */
    public void onMessage(Session session, String message, @PathParam("deviceType") String deviceType, @PathParam
            ("deviceId") String deviceId) {
        if (log.isDebugEnabled()) {
            log.debug("Received message from client for RemoteSession id: " + session.getId() + " device type: " +
                    deviceType + " device id: " + deviceId);
        }
        try {
            ServiceHolder.getInstance().getRemoteSessionManagementService().sendMessageToPeer(session, message);
        } catch (RemoteSessionManagementException e) {
            if (log.isDebugEnabled()) {
                log.error("Error occurred while send message to peer session ", e);
            }
            try {
                session.close(e.getCloseReason());
            } catch (IOException ex) {
                if (log.isDebugEnabled()) {
                    log.error("Failed to disconnect the client.", ex);
                }
            }
        }
    }

    /**
     * Web socket onMessage use When client sends a message
     *
     * @param session    - Registered  session.
     * @param deviceType - DeviceType
     * @param deviceId   - Device Identifier
     * @param message    - Byte Message which needs to send to peer
     */
    public void onMessage(Session session, byte[] message, @PathParam("deviceType") String deviceType, @PathParam
            ("deviceId") String deviceId) {
        if (log.isDebugEnabled()) {
            log.debug("Received message from client for RemoteSession id: " + session.getId() + " device type: " +
                    deviceType + " device id: " + deviceId);
        }
        try {
            ServiceHolder.getInstance().getRemoteSessionManagementService().sendMessageToPeer(session, message);
        } catch (RemoteSessionManagementException e) {
            if (log.isDebugEnabled()) {
                log.error("Error occurred while send message to peer session ", e);
            }
            try {
                session.close(e.getCloseReason());
            } catch (IOException ex) {
                if (log.isDebugEnabled()) {
                    log.error("Failed to disconnect the client.", ex);
                }
            }
        }
    }

    /**
     * Web socket onClose use to handle  socket connection close
     *
     * @param session    - Registered  session.
     * @param deviceType - DeviceType
     * @param deviceId   - Device Identifier
     * @param reason     - Status code for web-socket close.
     */
    public void onClose(Session session, CloseReason reason, @PathParam("deviceType") String deviceType, @PathParam
            ("deviceId") String deviceId) {

        ServiceHolder.getInstance().getRemoteSessionManagementService().endSession(session, "Remote session closed");
        if (log.isDebugEnabled()) {
            log.debug("websocket closed due to " + reason.getReasonPhrase() + ", for session ID:" + session.getId
                    () + ", for request URI - " + session.getRequestURI() + " device type: " + deviceType + " device " +
                    "id: " + deviceId);
        }

    }

    /**
     * Web socket onError use to handle  socket connection error
     *
     * @param session    - Registered  session.
     * @param throwable  - Web socket exception
     * @param deviceType - DeviceType
     * @param deviceId   - Device Identifier
     */
    public void onError(Session session, Throwable throwable, @PathParam("deviceType") String deviceType, @PathParam
            ("deviceId") String deviceId) {

        if (throwable instanceof IOException) {
            if (log.isDebugEnabled()) {
                log.error("Error occurred in session ID: " + session.getId() + " device type: " + deviceType +
                        "device id: " + deviceId + ", for request URI - " + session.getRequestURI() +
                        ", " + throwable.getMessage(), throwable);
            }
        } else {
            log.error("Error occurred in session ID: " + session.getId() + " device type: " + deviceType + " device " +
                    "id: " + deviceId + ", for request URI - " + session.getRequestURI() + ", " + throwable.getMessage
                    (), throwable);
        }
        try {
            ServiceHolder.getInstance().getRemoteSessionManagementService().endSession(session, "Remote session closed");
            if (session.isOpen()) {
                session.close(new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR, "Unexpected Error Occurred"));
            }
        } catch (IOException ex) {
            if (log.isDebugEnabled()) {
                log.error("Failed to disconnect the client.", ex);
            }
        }
    }
}