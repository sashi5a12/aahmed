
package com.netpace.aims.dataaccess.valueobjects;

import java.util.*;

/**
 * This class defines ValueObject ( read-only cache object ) for aims platform.
 *
 * @author    Fawad Sikandar
 * @version   1.0
 */

public final class AimsReportObjectColumnVO implements ValueObject {

    private Long reportObjectColumnId;
    private String columnName;
    private String columnDisplayName;
    private Long reportObjectId;



    /**
     * @param id      Description of Parameter
     * @param name    Description of Parameter
     * @param desc    Description of Parameter
     */
    public  AimsReportObjectColumnVO(Long id, Long reportObjectId,String name, String displayName) {

        this.reportObjectColumnId = id;
	this.reportObjectId = reportObjectId;
	this.columnName = name;
	this.columnDisplayName = displayName;

    }

    public Object getSortFieldValue() {
      return this.columnDisplayName;
    }


    /**
     * Gets the platformDesc attribute of the AimsPlateformVO object
     *
     * @return   The platformDesc value
     */
    public final String getColumnDisplayName() {
       return columnDisplayName;
    }

    /**
     * Gets the platformName attribute of the AimsPlatformVO object
     *
     * @return   The platformName value
     */
    public final String getColumnName() {
       return columnName;
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
     * Gets the platformId attribute of the AimsPlatformVO object
     *
     * @return   The platformId value
     */
    public final Long getReportObjectColumnId() {
       return reportObjectColumnId;
    }


    /**
     * Gets the value attribute of the AimsPlatformVO object
     *
     * @return   The value value
     */
    public final String getValue() {
        return this.columnDisplayName;
    }

     /**
     * Gets the id attribute of the AimsPlatformVO object
     *
     * @return   The id value
     */
    public final String getID() {
	return String.valueOf(this.reportObjectColumnId);
    }

}

