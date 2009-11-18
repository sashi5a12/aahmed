package com.netpace.aims.dataaccess.valueobjects;

/**
 * This class defines ValueObject ( read-only cache object ) for VZAppZone Prod Info.
 * Maps AimsVZAppBinaryFirmware object prod fields, data for devices stored in this object first and then in database
 *
 * @author    Sajjad Raza
 * @version   1.0
 */

public final class VZAppZoneProdInfoVO  {

    private Long binaryFirmwareId;

    private java.lang.Boolean disableMoveToProd;

	private java.lang.String moveToProd;
    private java.lang.String inProd;
	private java.lang.String prodMovedDate;

    public Long getBinaryFirmwareId() {
        return binaryFirmwareId;
    }

    public void setBinaryFirmwareId(Long binaryFirmwareId) {
        this.binaryFirmwareId = binaryFirmwareId;
    }

    public Boolean getDisableMoveToProd() {
        return disableMoveToProd;
    }

    public void setDisableMoveToProd(Boolean disableMoveToProd) {
        this.disableMoveToProd = disableMoveToProd;
    }

    public String getMoveToProd() {
        return moveToProd;
    }

    public void setMoveToProd(String moveToProd) {
        this.moveToProd = moveToProd;
    }

    public String getInProd() {
        return inProd;
    }

    public void setInProd(String inProd) {
        this.inProd = inProd;
    }

    public String getProdMovedDate() {
        return prodMovedDate;
    }

    public void setProdMovedDate(String prodMovedDate) {
        this.prodMovedDate = prodMovedDate;
    }
}
