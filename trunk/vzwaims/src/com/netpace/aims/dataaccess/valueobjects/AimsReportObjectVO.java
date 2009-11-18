
package com.netpace.aims.dataaccess.valueobjects;

/**
 * This class defines ValueObject ( read-only cache object ) for aims platform.
 *
 * @author    Fawad Sikandar
 * @version   1.0
 */

public final class AimsReportObjectVO implements ValueObject {

    private Long reportObjectId;
    private String tableName;
    private String tableDisplayName;


    /**
     * @param id      Description of Parameter
     * @param name    Description of Parameter
     * @param desc    Description of Parameter
     */
    public  AimsReportObjectVO(Long id, String name, String displayName) {
	reportObjectId = id;
	tableName = name;
	tableDisplayName = displayName;
    }


    public Object getSortFieldValue() {
      return this.tableDisplayName;
    }


    /**
     * Gets the platformDesc attribute of the AimsPlateformVO object
     *
     * @return   The platformDesc value
     */
    public final String getTableDisplayName() {
       return tableDisplayName;
    }

    /**
     * Gets the platformName attribute of the AimsPlatformVO object
     *
     * @return   The platformName value
     */
    public final String getTableName() {
       return tableName;
    }

    /**
     * Gets the platformId attribute of the AimsPlatformVO object
     *
     * @return   The platformId value
     */
    public final Long getReportObjectId() {
       return reportObjectId;
    }

    /**
     * Gets the value attribute of the AimsPlatformVO object
     *
     * @return   The value value
     */
    public final String getValue() {
        return this.tableDisplayName;
    }

     /**
     * Gets the id attribute of the AimsPlatformVO object
     *
     * @return   The id value
     */
    public final String getID() {
	return String.valueOf(this.reportObjectId);
    }

}

