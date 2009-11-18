  
package com.netpace.aims.dataaccess.valueobjects;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import com.netpace.aims.bo.content.AimsFAQsManager;
import com.netpace.aims.model.core.AimsPlatform;
import com.netpace.aims.bo.masters.AimsProgLangManager;
import java.util.Collection;
import com.netpace.aims.bo.core.AimsException;
import java.util.Iterator;
import com.netpace.aims.bo.system.AimsAppCategoryManager;
import com.netpace.aims.model.application.AimsAppCategory;
import com.netpace.aims.bo.system.AimsSystemManager;
import com.netpace.aims.model.masters.AimsRegion;
import com.netpace.aims.bo.system.AimsPlatformManager;
import com.netpace.aims.model.reports.AimsReportObject;
import com.netpace.aims.model.reports.AimsReportObjectColumn;
import com.netpace.aims.bo.reports.AimsUserDefReportManager;
import com.netpace.aims.model.application.AimsLifecyclePhase;


/**
 * This class provides cache mechanism for lookup tables of the AIMS system
 *
 * @author    Fawad Sikandar
 * @version   1.0
 */

public final class VOLookupFactory {

    private static VOLookupFactory instance;
    private Map aimsPlatformList = new HashMap();
    private Map aimsAppCategoryList = new HashMap();
    private Map aimsRegionList = new HashMap();
    private Map aimsReportObjectList = new HashMap();
    private Map aimsReportObjectColumnList = new HashMap();
    private Map aimsLifecycleObjectList = new HashMap();

    /**
     * Constructor for the VOLookupFactory object
     */
    private VOLookupFactory()
    {
      try
      {
        populateAimsPlatformList(AimsPlatformManager.getPlatforms());
        populateAimsAppCategoryList(AimsAppCategoryManager.getAppCategorys());
        populateAimsRegionList(AimsSystemManager.getRegionsList());
        populateAimsReportObjectList(AimsUserDefReportManager.getReportObjectList());
        populateAimsReportObjectColumnList(AimsUserDefReportManager.getReportObjectColumnList());
        populateAimsLifecycleList(com.netpace.aims.bo.application.AimsApplicationsManager.getPhases());

      }catch(Exception e) {
        e.printStackTrace(System.out);
      }
    }

    /**
     * This method finds a value object by name
     *
     * @param hmap  - list of cached value objects
     * @param name  - name of the required value object
     * @return      - required value object
     */
    private ValueObject findByName(Map hmap, String name)
    {
	ValueObject	   vo = null;
	ValueObject	   tempVO = null;
	java.util.Iterator it = hmap.values().iterator();

	while (it.hasNext()) {
	    tempVO = (ValueObject) it.next();

	    if (name.equals(tempVO.getValue())) {
		vo = tempVO;
	    }
	}

	return vo;
    }

    /**
     * Gets the aimsPlatform attribute of the VOLookupFactory object
     *
     * @param id  id of the required value object
     * @return    The aimsPlatform value
     */
    public final AimsPlatformVO getAimsPlatform(Long id)
    {
	return (AimsPlatformVO) aimsPlatformList.get(id);
    }

    /**
     * Gets the aimsAppCategory attribute of the VOLookupFactory object
     *
     * @param id  id of the required value object
     * @return    The aimsAppCategory value
     */
    public final AimsAppCategoryVO getAimsAppCategory(Long id)
    {
      return (AimsAppCategoryVO) aimsAppCategoryList.get(id);
    }

    /**
     * Gets the aimsRegion attribute of the VOLookupFactory object
     *
     * @param id  id of the required value object
     * @return    The aimsRegion value
     */
    public final AimsRegionVO getAimsRegion(Long id)
    {
      return (AimsRegionVO) aimsRegionList.get(id);
    }

    /**
     * Gets the aimsPlatform attribute of the VOLookupFactory object
     *
     * @param id  id of the required value object
     * @return    The aimsPlatform value
     */
    public final AimsReportObjectVO getReportObject(Long id)
    {
      return (AimsReportObjectVO) aimsReportObjectList.get(id);
    }

    /**
     * Gets the aimsPlatform attribute of the VOLookupFactory object
     *
     * @param id  id of the required value object
     * @return    The aimsPlatform value
     */
    public final AimsReportObjectColumnVO getReportObjectColumn(Long id)
    {
      return (AimsReportObjectColumnVO) aimsReportObjectColumnList.get(id);
    }
    
    /**
     * Gets the aimsLifecycle attribute of the VOLookupFactory object
     *
     * @param id  id of the required value object
     * @return    The aimsLifecycle value
     */
    public final AimsLifecycleVO getLifecycle(Long id)
    {
      return (AimsLifecycleVO) aimsLifecycleObjectList.get(id);
    }

    /**
     * Gets the aimsPlatform attribute of the VOLookupFactory object
     *
     * @param name  name of the required value object
     * @return      The aimsPlatform value
     */
    public final AimsPlatformVO getAimsPlatform(String name)
    {
      return (AimsPlatformVO) findByName(aimsPlatformList, name);
    }


    /**
     * Gets the aimsAppCategory attribute of the VOLookupFactory object
     *
     * @param name  name of the required value object
     * @return    The aimsAppCategory value
     */
    public final AimsAppCategoryVO getAimsAppCategory(String name)
    {
        return (AimsAppCategoryVO) findByName(aimsAppCategoryList, name);
    }

    /**
     * Gets the aimsRegion attribute of the VOLookupFactory object
     *
     * @param name  name of the required value object
     * @return    The aimsRegion value
     */

    public final AimsRegionVO getAimsRegion(String name)
    {
        return (AimsRegionVO) findByName(aimsRegionList, name);
    }

    /**
     * Gets the aimsLifecycle attribute of the VOLookupFactory object
     *
     * @param name  name of the required value object
     * @return    The AimsLifecycleVO value
     */

    public final AimsLifecycleVO getAimsLifecycle(String name)
    {
        return (AimsLifecycleVO) findByName(aimsLifecycleObjectList, name);
    }

      /**
       * Gets the platformList attribute of the VOLookupFactory object
       *
       * @return   The platformList value
       */
      public final AimsPlatformVO[] getAimsPlatformList()
      {

        AimsPlatformVO[] aimsPlatformArray =
            (AimsPlatformVO[]) aimsPlatformList.values()
            .toArray(new AimsPlatformVO[0]);

        sortValueObjects(aimsPlatformArray);

        return aimsPlatformArray;
      }

      /**
       * Gets the appCategoryList attribute of the VOLookupFactory object
       *
       * @return   The aimsAppCategoryList value
       */

      public final AimsAppCategoryVO[] getAimsAppCategoryList()
      {

        AimsAppCategoryVO[] aimsAppCategoryArray =
              (AimsAppCategoryVO[]) aimsAppCategoryList.values()
                 .toArray(new AimsAppCategoryVO[0]);

          sortValueObjects(aimsAppCategoryArray);

          return aimsAppCategoryArray ;
      }

        /**
         * Gets the regionList attribute of the VOLookupFactory object
         *
         * @return   The aimsRegionList value
         */

        public final AimsRegionVO[] getAimsRegionList()
        {

          AimsRegionVO[] aimsRegionArray =
                (AimsRegionVO[]) aimsRegionList.values()
                   .toArray(new AimsRegionVO[0]);

            sortValueObjects(aimsRegionArray);

            return aimsRegionArray ;
        }

        /**
         * Gets the platformList attribute of the VOLookupFactory object
         *
         * @return   The platformList value
         */
        public final AimsReportObjectVO[] getAimsReportObjectList()
        {

          AimsReportObjectVO[] aimsReportObjectArray =
              (AimsReportObjectVO[]) aimsReportObjectList.values()
              .toArray(new AimsReportObjectVO[0]);

          sortValueObjects(aimsReportObjectArray);

          return aimsReportObjectArray;
        }



        /**
         * Gets the platformList attribute of the VOLookupFactory object
         *
         * @return   The platformList value
         */
        public final AimsReportObjectColumnVO[] getAimsReportObjectColumnList()
        {

          AimsReportObjectColumnVO[] aimsReportObjectColumnArray =
              (AimsReportObjectColumnVO[]) aimsReportObjectColumnList.values()
              .toArray(new AimsReportObjectColumnVO[0]);

          sortValueObjects(aimsReportObjectColumnArray);

          return aimsReportObjectColumnArray;
        }


        /**
         * Gets the lifecycleList attribute of the VOLookupFactory object
         *
         * @return   The lifecycleList value
         */
        public final AimsLifecycleVO[] getAimsLifecycleList()
        {

          AimsLifecycleVO[] aimsLifecycleArray =
              (AimsLifecycleVO[]) aimsLifecycleObjectList.values()
              .toArray(new AimsLifecycleVO[0]);

          sortValueObjects(aimsLifecycleArray);

          return aimsLifecycleArray;
        }


    /**
     * This method populates VO list with the list of entities in the database.
     * In doing so it removes all previous VO entries
     *
     * @param aimsPlatforms list of entities
     */
    private void populateAimsPlatformList(Collection aimsPlatforms)
    {
     if ( aimsPlatforms != null )
     {
      Iterator it = aimsPlatforms.iterator();

       while ( it.hasNext() )
       {
            AimsPlatform aimsPlatform = (AimsPlatform)it.next();
            updateAimsPlatformVO(aimsPlatform);
        }
      }
     }

     /**
      * Update the aimsPlatform attribute of the VOLookupFactory object
      *
      * @param aimsPlatform
      */

     public void updateAimsPlatformVO(AimsPlatform aimsPlatform)
     {
            AimsPlatformVO vo =
                new AimsPlatformVO(aimsPlatform.getPlatformId(),
                                  aimsPlatform.getPlatformName(),
                                  aimsPlatform.getPlatformDesc(),
                                  null
                                  );
            aimsPlatformList.put(aimsPlatform.getPlatformId(), vo);
     }

     /**
      * Deletes the aimsPlatform attribute of the VOLookupFactory object
      * @param id
      */

     public void deleteAimsPlatformVO(Long id)
     {
             aimsPlatformList.remove(id);
     }

     /**
      * This method populates VO list with the list of entities in the database.
      * In doing so it removes all previous VO entries
      *
      * @param aimsAppCategories list of entities
      */

     private void populateAimsAppCategoryList(Collection aimsAppCategories)
     {
      if ( aimsAppCategories != null )
      {
       Iterator it = aimsAppCategories.iterator();

        while ( it.hasNext() )
        {
             AimsAppCategory aimsAppCategory = (AimsAppCategory)it.next();
             AimsAppCategoryVO vo =
                 new AimsAppCategoryVO(aimsAppCategory.getCategoryId(),
                                   aimsAppCategory.getCategoryName(),
                                   aimsAppCategory.getCategoryDesc());
             aimsAppCategoryList.put(aimsAppCategory.getCategoryId(), vo);
         }
       }
      }


      /**
       * This method populates VO list with the list of entities in the database.
       * In doing so it removes all previous VO entries
       *
       * @param aimsRegions list of entities
       */

   private void populateAimsRegionList(Collection aimsRegions)
   {

     if( aimsRegions != null ) {

       Iterator it  = aimsRegions.iterator();

       while ( it.hasNext() )
       {
         AimsRegion aimsRegion = (AimsRegion) it.next();
         AimsRegionVO vo =
            new AimsRegionVO(aimsRegion.getRegionId(),
                             aimsRegion.getRegionName(),
                             aimsRegion.getRegionDescription());
         aimsRegionList.put(aimsRegion.getRegionId(),vo);
       }

       }
     }

     /**
      * This method populates VO list with the list of entities in the database.
      * In doing so it removes all previous VO entries
      *
      * @param aimsPlatforms list of entities
      */
     private void populateAimsReportObjectList(Collection aimsReportObjects)
     {
       if (aimsReportObjects != null)
       {
         Iterator it = aimsReportObjects.iterator();

         while (it.hasNext())
         {
           AimsReportObject aimsReportObject = (AimsReportObject) it.next();
           updateAimsReportObjectVO(aimsReportObject);
         }
       }
     }
     
     
     /**
      * This method populates VO list with the list of entities in the database.
      *
      * @param aimsLifecycle list of entities
      */
     private void populateAimsLifecycleList(Collection aimsLifecycleObjects)
     {
       if (aimsLifecycleObjects != null)
       {
         Iterator it = aimsLifecycleObjects.iterator();

         while (it.hasNext())
         {
           AimsLifecyclePhase aimsLifecyclePhase = (AimsLifecyclePhase) it.next();
           AimsLifecycleVO vo = new AimsLifecycleVO(aimsLifecyclePhase.getPhaseId(),aimsLifecyclePhase.getPhaseName());
           aimsLifecycleObjectList.put(aimsLifecyclePhase.getPhaseId(),vo);
         }
       }
     }

     /**
      * Update the aimsPlatform attribute of the VOLookupFactory object
      *
      * @param aimsPlatform
      */

     public void updateAimsReportObjectVO(AimsReportObject aimsReportObject)
     {
       AimsReportObjectVO vo =
           new AimsReportObjectVO(aimsReportObject.getReportObjectId(),
                                  aimsReportObject.getTableName(),
                                  aimsReportObject.getTableDisplayName());
       aimsReportObjectList.put(aimsReportObject.getReportObjectId(), vo);
     }

     /**
      * This method populates VO list with the list of entities in the database.
      * In doing so it removes all previous VO entries
      *
      * @param aimsPlatforms list of entities
      */
     private void populateAimsReportObjectColumnList(Collection
                                                     aimsReportObjectColumns)
     {
       if (aimsReportObjectColumns != null)
       {
         Iterator it = aimsReportObjectColumns.iterator();

         while (it.hasNext())
         {
           AimsReportObjectColumn aimsReportObjectColumn = (AimsReportObjectColumn)
               it.next();
           updateAimsReportObjectColumnVO(aimsReportObjectColumn);
         }
       }
     }

     /**
      * Update the aimsPlatform attribute of the VOLookupFactory object
      *
      * @param aimsPlatform
      */

     public void updateAimsReportObjectColumnVO(AimsReportObjectColumn
                                                aimsReportObjectColumn)
     {
       AimsReportObjectColumnVO vo =
           new AimsReportObjectColumnVO(aimsReportObjectColumn.
                                        getReportObjectColumnId(),
                                        aimsReportObjectColumn.getReportObjectId(),
                                        aimsReportObjectColumn.getColumnName(),
                                        aimsReportObjectColumn.
                                        getColumnDisplayName());
       aimsReportObjectColumnList.put(aimsReportObjectColumn.
                                      getReportObjectColumnId(), vo);
     }

     /**
  /**
   * This method sorts list of value object
   *
   * @param objects  Description of Parameter
   */
  private void sortValueObjects(ValueObject[] objects)
  {
      Arrays.sort(objects, new Comparator() {

          /**
           * Method declaration
           *
           * @param o1
           * @param o2
           * @return
           */
          public int compare(Object o1, Object o2) {
              Object value1 = ((ValueObject) o1).getSortFieldValue();
              Object value2 = ((ValueObject) o2).getSortFieldValue();

              if (value1 instanceof Long) {
                  return ((Long) value1).compareTo((Long) value2);
              } else {
                  return ((String) value1).compareTo((String) value2);
              }
          }

      });
  }

  /**
   * Gets the instance attribute of the VOLookupFactory class
   *
   * @return   The instance value
   */
  public final static VOLookupFactory getInstance()
  {

    if (instance == null)
    {
      synchronized (VOLookupFactory.class)
      {
        if (instance == null)
        {
          instance = new VOLookupFactory();
        }
      }
    }
    return instance;
  }

}


