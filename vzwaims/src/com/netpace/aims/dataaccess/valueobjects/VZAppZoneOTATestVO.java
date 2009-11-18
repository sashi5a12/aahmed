package com.netpace.aims.dataaccess.valueobjects;

import org.apache.struts.upload.FormFile;

/**
 * This class defines ValueObject ( read-only cache object ) for VZAppZone OTA Test.
 * Maps AimsVZAppBinaryFirmware object ota fields, data for devices stored in this object first and then in database
 *
 * @author    Sajjad Raza
 * @version   1.0
 */

public final class VZAppZoneOTATestVO {

    private Long binaryFirmwareId;

    private String otaTestedDate;
    private String otaTestStatus;

    private String otaComments;

    private FormFile otaResultsFile;
    private String otaResultsFileFileName;
    private java.lang.Long otaResultsFileTempFileId;   //non persistent field
    private java.lang.String otaResultsFileContentType;


    private java.lang.String moveToOTATesting;
    private java.lang.String inOTATesting;

    private java.lang.Boolean disableMoveToOTATesting;
    private java.lang.Boolean disableOTATest;

    public Long getBinaryFirmwareId() {
        return binaryFirmwareId;
    }

    public void setBinaryFirmwareId(Long binaryFirmwareId) {
        this.binaryFirmwareId = binaryFirmwareId;
    }

    public String getOtaTestedDate() {
        return otaTestedDate;
    }

    public void setOtaTestedDate(String otaTestedDate) {
        this.otaTestedDate = otaTestedDate;
    }

    public String getOtaTestStatus() {
        return otaTestStatus;
    }

    public void setOtaTestStatus(String otaTestStatus) {
        this.otaTestStatus = otaTestStatus;
    }

    public String getOtaComments() {
        return otaComments;
    }

    public void setOtaComments(String otaComments) {
        this.otaComments = otaComments;
    }

    public FormFile getOtaResultsFile() {
        return otaResultsFile;
    }

    public void setOtaResultsFile(FormFile otaResultsFile) {
        this.otaResultsFile = otaResultsFile;
    }

    public String getOtaResultsFileFileName() {
        return otaResultsFileFileName;
    }

    public void setOtaResultsFileFileName(String otaResultsFileFileName) {
        this.otaResultsFileFileName = otaResultsFileFileName;
    }

    public Long getOtaResultsFileTempFileId() {
        return otaResultsFileTempFileId;
    }

    public void setOtaResultsFileTempFileId(Long otaResultsFileTempFileId) {
        this.otaResultsFileTempFileId = otaResultsFileTempFileId;
    }

    public String getOtaResultsFileContentType() {
        return otaResultsFileContentType;
    }

    public void setOtaResultsFileContentType(String otaResultsFileContentType) {
        this.otaResultsFileContentType = otaResultsFileContentType;
    }

    public String getMoveToOTATesting() {
        return moveToOTATesting;
    }

    public void setMoveToOTATesting(String moveToOTATesting) {
        this.moveToOTATesting = moveToOTATesting;
    }

    public String getInOTATesting() {
        return inOTATesting;
    }

    public void setInOTATesting(String inOTATesting) {
        this.inOTATesting = inOTATesting;
    }

    public Boolean getDisableMoveToOTATesting() {
        return disableMoveToOTATesting;
    }

    public void setDisableMoveToOTATesting(Boolean disableMoveToOTATesting) {
        this.disableMoveToOTATesting = disableMoveToOTATesting;
    }

    public Boolean getDisableOTATest() {
        return disableOTATest;
    }

    public void setDisableOTATest(Boolean disableOTATest) {
        this.disableOTATest = disableOTATest;
    }

}
