package com.netpace.aims.controller.tools;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.tools.AimsContractManager;
import com.netpace.aims.bo.tools.AimsToolsManager;
import com.netpace.aims.model.core.AimsContract;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.tools.AimsTool;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.TempFile;

/**
 * @struts.form name="ToolsForm"
 * @author Ahson Imtiaz.
 */
public class ToolsForm extends com.netpace.aims.controller.BaseValidatorForm
{

    static Logger log = Logger.getLogger(ToolsForm.class.getName());

    private String toolName;
    private java.lang.Long toolId;
    
    private java.util.Collection allAppCategories;
    private java.lang.Long[] categoriesIds;
    
    private java.util.Collection allPlatforms;
    private Long[] platformIds;
        
    private java.util.Collection allContracts;
    private java.util.Collection addedContracts;
    private Long[] contractList;
    
    private String docType;
    private String displayFilename;
    private Long displayFilenameId;
    private String downloadURL;
    private String description;

    private String taskPerform;
    private org.apache.struts.upload.FormFile toolFile;
    private String toolFileContentType;
    
    private String shareType;   

    /* Public Getter and Setter Functions Starts*/

    /*  */
    public void setToolName(String strInput)
    {
        this.toolName = StringUtils.trim(strInput);
    }

    public String getToolName()
    {
        return this.toolName;
    }

    /*  */
    public void setTaskPerform(String strInput)
    {
        this.taskPerform = strInput;
    }

    public String getTaskPerform()
    {
        return this.taskPerform;
    }

    /* */
    public void setToolId(java.lang.Long lngInput)
    {
        this.toolId = lngInput;
    }

    public java.lang.Long getToolId()
    {
        return this.toolId;
    }
   
    /*  */
    public void setDisplayFilename(String strInput)
    {
        this.displayFilename = strInput;
    }

    public String getDisplayFilename()
    {
        return this.displayFilename;
    }

    /*  */
    public void setDownloadURL(String strInput)
    {
        this.downloadURL = StringUtils.trim(strInput);
    }

    public String getDownloadURL()
    {
        return this.downloadURL;
    }

    /*  */
    public void setDescription(String strInput)
    {
        this.description = StringUtils.trim(strInput);
    }

    public String getDescription()
    {
        return this.description;
    }

    /* */
    public void setCategoriesIds(java.lang.Long[] lngInput)
    {
        this.categoriesIds = lngInput;
    }

    public java.lang.Long[] getCategoriesIds()
    {
        return this.categoriesIds;
    }

    /*  */
    public void setAllAppCategories(java.util.Collection col)
    {
        this.allAppCategories = col;
    }

    public java.util.Collection getAllAppCategories()
    {
        return this.allAppCategories;
    }

    /*  */
    public void setAllPlatforms(java.util.Collection col)
    {
        this.allPlatforms = col;
    }

    public java.util.Collection getAllPlatforms()
    {
        return this.allPlatforms;
    }

    /*  */
    public void setAllContracts(java.util.Collection col)
    {
        this.allContracts = col;
    }

    public java.util.Collection getAllContracts()
    {
        return this.allContracts;
    }

    /*  */
    public void setAddedContracts(java.util.Collection col)
    {
        this.addedContracts = col;
    }

    public java.util.Collection getAddedContracts()
    {
        return this.addedContracts;
    }

    /* RESET FUNCTION */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {

        Long currentUserAllianceId = ((AimsUser) (request.getSession().getAttribute(AimsConstants.AIMS_USER))).getAimsAllianc();

        try
        {
            log.debug("In RESET Entry Tools Form");

            this.addedContracts = new java.util.HashSet();
            this.allAppCategories = AimsToolsManager.getCategories();
            this.allPlatforms = AimsEntAppsManager.getAimsPlatforms();
            this.allContracts = AimsContractManager.getAllContracts();

            log.debug("In RESET Tools Out");
        }
        catch (Exception ex)
        {
            log.debug("Exception in RESET: " + ex);
        }

    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();
        
        AimsTool atool = null;        
        boolean fileCheck = false;
        String currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String fileSize = (String) request.getSession().getAttribute(AimsConstants.FILE_SIZE);
        String sessionId = session.getId();
        
        saveTempFiles(currUserId, sessionId, fileSize, errors);
        
        if (this.toolId != null && this.toolId.intValue() != 0)
        {
            try
            {
                atool = AimsToolsManager.getAimsTool(this.toolId);
                this.addedContracts = atool.getAimsContracts();
            }
            catch (Exception ex)
            {
                log.debug("Exception " + ex);
            }
        }

        if (this.isBlankString(this.toolName))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.toolName"));
        else if (this.toolName.length() > 50)
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.length.toolName"));

        if (this.isBlankString(this.description))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.toolDescription"));
        else if (this.description.length() > 500)
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.length.description"));

        if (isBlankString(this.docType)){
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.docType"));
        }
        else if (docType.equals("1") && (this.isBlankString(this.displayFilename))){
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.fileName"));
        }
        else if (docType.equals("2") && isBlankString(this.downloadURL)){
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.url"));
        }
        else if (docType.equals("2") && !isValidURL(this.downloadURL)){
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.invalid.url"));
        }
        else if (docType.equals("2") &&  this.downloadURL.length() > 50){
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.length.downloadURL"));
        }
        
        if (this.categoriesIds == null || this.categoriesIds.length <= 0)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.category"));
        
        if (this.isBlankString(this.shareType)){
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.notShareWithContract"));
        }
        else if (shareType.equals("2") && (this.platformIds == null || this.platformIds.length <= 0))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.vzwPlatform"));
        else if (shareType.equals("3") && (this.contractList == null || "-1".equals(this.contractList[0].toString())))
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.noContractSelected"));

        if (!(this.contractList == null || this.contractList.length < 1 || "-1".equals(this.contractList[0].toString())))
        {

            Collection collection = new ArrayList();
            AimsContract aimsContract = null;
            AimsContract contracts[] = null;

            try
            {

                contracts = (AimsContract[]) AimsContractManager.getAimsContracts(contractList).toArray(new AimsContract[0]);
            }
            catch (HibernateException he)
            {
                log.debug("Exception in Getting Contracts : " + he.getMessage());
            }

            for (int i = 0; contracts.length > i; i++)
            {

                aimsContract = new AimsContract();
                aimsContract.setContractId(contracts[i].getContractId());
                aimsContract.setContractTitle(contracts[i].getContractTitle());
                collection.add(aimsContract);
            }
            setAddedContracts(collection);

        }
        
        if (!this.isBlankString(this.shareType)){
        	if (this.shareType.equals("1")){
        		this.platformIds=null;
        		this.contractList=null;
        	}
        	else if (this.shareType.equals("2")){
        		this.contractList=null;
        	}
        	else if (this.shareType.equals("3")){
        		this.platformIds=null;
        	}
        }
        
        return errors;

    }
    public void saveTempFiles(String currUserId, String sessionId, String fileSize, ActionErrors errors) {
		TempFile tempFile = null;
		
		try {
			
			if ((this.toolFile != null) && !(this.isBlankString(toolFile.getFileName())) && (this.toolFile.getFileName().trim().length() > 150)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.length.toolFileName"));
				this.toolFile.destroy();
			} else if ((this.toolFile != null) && (this.toolFile.getFileSize() > 0) && !(this.isValidFileSize(fileSize, this.toolFile.getFileSize()))) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.ToolsForm.fileSize.exceeded"));
				this.toolFile.destroy();
			} else {
				tempFile = TempFilesManager.saveTempFile(this.toolFile, sessionId, currUserId);
				if (tempFile != null) {
					this.displayFilenameId = tempFile.getFileId();
					this.displayFilename = tempFile.getFileName();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} 

	}
    public org.apache.struts.upload.FormFile getToolFile()
    {
        return toolFile;
    }
    public void setToolFile(org.apache.struts.upload.FormFile toolFile)
    {
        this.toolFile = toolFile;
    }
    public String getToolFileContentType()
    {
        return toolFileContentType;
    }
    public void setToolFileContentType(String toolFileContentType)
    {
        this.toolFileContentType = toolFileContentType;
    }    
    public Long[] getPlatformIds() {
		return platformIds;
	}
	public void setPlatformIds(Long[] platformIds) {
		this.platformIds = platformIds;
	}
	public Long[] getContractList()
    {
        return contractList;
    }
    public void setContractList(Long[] contractList)
    {
        this.contractList = contractList;
    }
	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public Long getDisplayFilenameId() {
		return displayFilenameId;
	}

	public void setDisplayFilenameId(Long displayFilenameId) {
		this.displayFilenameId = displayFilenameId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}    	
}
