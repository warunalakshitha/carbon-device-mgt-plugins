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
package org.wso2.carbon.device.mgt.extensions.remote.session.common;

/**
 * This holds the constants related to remote session tests
 */
public class RemoteSessionTestConstants {

    public static final String DEVICE_TYPE = "android";
    public static final String DEVICE_ID = "android_test_device_1";
    public static final String OPERATION_ID = "123";
    public static final String MESSAGE = "{\"message\": \"hello\"}";
    public static final String MESSAGE_RESPONSE = "{\"message\": \"helloResponse\"}";
    public static final String CLIENT_SESSION_ID = "1";
    public static final String DEVICE_SESSION_ID = "2";

    private RemoteSessionTestConstants() {
    }
}
