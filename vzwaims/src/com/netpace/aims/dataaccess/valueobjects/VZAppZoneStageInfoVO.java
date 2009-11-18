package com.netpace.aims.dataaccess.valueobjects;

/**
 * This class defines ValueObject ( read-only cache object ) for VZAppZone Stage Info.
 * Maps AimsVZAppBinaryFirmware object stage fields, data for devices stored in this object first and then in database
 *
 * @author    Sajjad Raza
 * @version   1.0
 */

public final class VZAppZoneStageInfoVO {

    private Long binaryFirmwareId;

    private java.lang.Boolean disableMoveToStaging;

	private java.lang.String moveToStaging;
    private java.lang.String inStaging;
	private java.lang.String stageMovedDate;

    public Long getBinaryFirmwareId() {
        return binaryFirmwareId;
    }

    public void setBinaryFirmwareId(Long binaryFirmwareId) {
        this.binaryFirmwareId = binaryFirmwareId;
    }

    public Boolean getDisableMoveToStaging() {
        return disableMoveToStaging;
    }

    public void setDisableMoveToStaging(Boolean disableMoveToStaging) {
        this.disableMoveToStaging = disableMoveToStaging;
    }

    public String getMoveToStaging() {
        return moveToStaging;
    }

    public void setMoveToStaging(String moveToStaging) {
        this.moveToStaging = moveToStaging;
    }

    public String getInStaging() {
        return inStaging;
    }

    public void setInStaging(String inStaging) {
        this.inStaging = inStaging;
    }

    public String getStageMovedDate() {
        return stageMovedDate;
    }

    public void setStageMovedDate(String stageMovedDate) {
        this.stageMovedDate = stageMovedDate;
    }
}
