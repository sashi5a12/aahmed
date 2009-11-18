package com.netpace.aims.dataaccess.valueobjects;

import org.apache.struts.upload.FormFile;

public class VZAppBaseTestVO {

    private Long binaryFirmwareId;

    private String baseTestedDate;
    private String baseTestStatus;

    private String baseComments;

    private FormFile baseResultsFile;
    private String baseResultsFileFileName;
    private java.lang.Long baseResultsFileTempFileId;   //non persistent field
    private java.lang.String baseResultsFileContentType;

    private Boolean disableBaseTest;

    public Long getBinaryFirmwareId() {
        return binaryFirmwareId;
    }

    public void setBinaryFirmwareId(Long binaryFirmwareId) {
        this.binaryFirmwareId = binaryFirmwareId;
    }

    public String getBaseTestedDate() {
        return baseTestedDate;
    }

    public void setBaseTestedDate(String baseTestedDate) {
        this.baseTestedDate = baseTestedDate;
    }

    public String getBaseTestStatus() {
        return baseTestStatus;
    }

    public void setBaseTestStatus(String baseTestStatus) {
        this.baseTestStatus = baseTestStatus;
    }

    public FormFile getBaseResultsFile() {
        return baseResultsFile;
    }

    public void setBaseResultsFile(FormFile baseResultsFile) {
        this.baseResultsFile = baseResultsFile;
    }

    public String getBaseResultsFileFileName() {
        return baseResultsFileFileName;
    }

    public void setBaseResultsFileFileName(String baseResultsFileFileName) {
        this.baseResultsFileFileName = baseResultsFileFileName;
    }

    public Long getBaseResultsFileTempFileId() {
        return baseResultsFileTempFileId;
    }

    public void setBaseResultsFileTempFileId(Long baseResultsFileTempFileId) {
        this.baseResultsFileTempFileId = baseResultsFileTempFileId;
    }

    public String getBaseResultsFileContentType() {
        return baseResultsFileContentType;
    }

    public void setBaseResultsFileContentType(String baseResultsFileContentType) {
        this.baseResultsFileContentType = baseResultsFileContentType;
    }

    public String getBaseComments() {
        return baseComments;
    }

    public void setBaseComments(String baseComments) {
        this.baseComments = baseComments;
    }

    public Boolean getDisableBaseTest() {
        return disableBaseTest;
    }

    public void setDisableBaseTest(Boolean disableBaseTest) {
        this.disableBaseTest = disableBaseTest;
    }
}
