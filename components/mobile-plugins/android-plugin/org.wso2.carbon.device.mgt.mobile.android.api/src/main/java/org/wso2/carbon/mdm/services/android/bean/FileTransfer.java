/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
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
*/
package org.wso2.carbon.mdm.services.android.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.wso2.carbon.mdm.services.android.bean.AndroidOperation;

import java.io.Serializable;

/**
 * This class represents the information of file transfer operation payload.
 */
@ApiModel(value = "FileTransfer",
        description = "This class carries all information related to file transfer operation.")
public class FileTransfer extends AndroidOperation implements Serializable {

    @ApiModelProperty(name = "fileURL", value = "File URL", required = true)
    private String fileURL;

    @ApiModelProperty(name = "userName", value = "User Name", required = true)
    private String userName;

    @ApiModelProperty(name = "ftpPassword", value = "FTP password", required = true)
    private String ftpPassword;

    @ApiModelProperty(name = "fileLocation", value = "fileLocation", required = true)
    private String fileLocation;

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
