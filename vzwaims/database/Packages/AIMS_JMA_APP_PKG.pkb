CREATE OR REPLACE Package Body AIMS_JMA_APP_PKG
IS

/* -------------------------------------------------------------------------------------------------------------  */


    PROCEDURE publish_jma_app
         (
           p_solution_id            IN number,
           p_is_published           IN char,
           p_is_mobile_professional IN char,
	   p_is_soho                IN char,
	   p_is_sme                 IN char,
           p_is_enterprise          IN char,
           p_is_featured            IN char
         )
    IS

    /*
    || Overview:        is_published = FALSE
    ||				Then in BDS (BDS_SOLUTION_MARKET_SEGMENTS table):
    ||					1.    Set is_public = FALSE for all Market Segments
    ||                                  2.    Set is_excluded = TRUE for all Market Segments
    ||
    ||			is_published = TRUE
    ||                         Then in BDS (BDS_SOLUTION_MARKET_SEGMENTS table):
    ||                                 1.     Set is_public = FALSE for all Market Segments
    ||                                 2.     Set is_public = TRUE for those Market Segments that have been checked in AIMS
    ||                                 3.     Set is_excluded = TRUE for all Market Segments
    ||                                 4.     Set is_excluded = FALSE for those Market Segments that have been checked in AIMS			
    ||
    ||
    ||
    || Dependencies:
    ||
    || Modification History:
    || When             Who             What
    ||---------------------------------------
    || 30-05-2009       akureshi        Created
    || 26-05-2009       akureshi        Updated, added script for isFeatured
    ||
    ||
    ||
    */


   BEGIN
    
    if(p_is_published = 'N') then
   	
   	  UPDATE bds_solution_market_segments
   	  SET
   		IS_PUBLIC = 'N',
   		IS_EXCLUDED ='Y'	
   	  WHERE SOLUTION_ID = p_solution_id; 			   
   	  				   
    end if;
    
    if(p_is_published = 'Y') then
        
    	UPDATE bds_solution_market_segments 
        SET
       	    IS_PUBLIC = 'N',
       	    IS_EXCLUDED ='Y'	
        WHERE SOLUTION_ID = p_solution_id;
    		  
     	if(p_is_mobile_professional = 'Y') then
     		
     		UPDATE bds_solution_market_segments 
		SET
		     IS_PUBLIC = 'Y',
		     IS_EXCLUDED ='N'	
        	WHERE SOLUTION_ID = p_solution_id and MARKET_SEGMENT_ID = 9;
     	end if;
     	
     	if(p_is_soho = 'Y') then
	     		
	     UPDATE bds_solution_market_segments 
	     SET
		IS_PUBLIC = 'Y',
		IS_EXCLUDED ='N'	
	    WHERE SOLUTION_ID = p_solution_id and MARKET_SEGMENT_ID = 10;
     	end if;
        
        if(p_is_sme = 'Y') then
		     		
	       UPDATE bds_solution_market_segments 
	       SET
	       	   IS_PUBLIC = 'Y',
		   IS_EXCLUDED ='N'	
	      WHERE SOLUTION_ID = p_solution_id and MARKET_SEGMENT_ID = 11;
     	end if;
     	
     	 if(p_is_enterprise = 'Y') then
			     		
		UPDATE bds_solution_market_segments 
		SET
			IS_PUBLIC = 'Y',
			IS_EXCLUDED ='N'	
		WHERE SOLUTION_ID = p_solution_id and MARKET_SEGMENT_ID = 12;
     	end if;
     	
    end if;
    
    /*script for isFeatured*/
     
     if(p_is_featured = 'Y') then
       	
       	  UPDATE bds_solution_market_segments
       	  SET
       		IS_FEATURED = 'Y'	
       	  WHERE SOLUTION_ID = p_solution_id; 			   
       	  				   
    end if;
    
     if(p_is_featured = 'N') then
           	
          UPDATE bds_solution_market_segments
          SET
           	IS_FEATURED = 'N'	
          WHERE SOLUTION_ID = p_solution_id; 			   
           	  				   
    end if;
   	  

   END publish_jma_app;

/* -------------------------------------------------------------------------------------------------------------  */

END AIMS_JMA_APP_PKG; -- Package Body END AIMS_JMA_APP_PKG
/