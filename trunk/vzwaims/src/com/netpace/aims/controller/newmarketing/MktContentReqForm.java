package com.netpace.aims.controller.newmarketing;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="MktContentReqForm"
 * @author Ahson Imtiaz.
 */

public class MktContentReqForm extends BaseValidatorForm
{

    /** identifier field */
    private java.lang.Long crequestId;

    /** nullable persistent field */
    private java.lang.String programName;

    /** nullable persistent field */
    private java.lang.String vehicleComm;

    /** nullable persistent field */
    private java.lang.String projectStartDate;

    /** nullable persistent field */
    private java.lang.String areaContactName;

    /** nullable persistent field */
    private java.lang.String areaContactTel;

    /** nullable persistent field */
    private java.lang.String creativeAgency;

    /** nullable persistent field */
    private java.lang.String areaTargeted;

    /** nullable persistent field */
    private java.lang.String exposure;

    /** nullable persistent field */
    private java.lang.String timelineExposure;

    /** nullable persistent field */
    private java.lang.String objectiveOfProgram;

    /** nullable persistent field */
    private java.lang.String placementDisclaimer;

    /** nullable persistent field */
    private java.lang.String promotionLength;

    /** nullable persistent field */
    private java.lang.String expProgramTakeRate;

    /** nullable persistent field */
    private java.lang.String programDistributionDate;

    /** nullable persistent field */
    private java.lang.String programHighlights;

    /** nullable persistent field */
    private java.lang.String status;

    /** nullable persistent field */
    private java.util.Date createdDate;

    /** persistent field */
    private java.lang.Long userId;

    /** nullable persistent field */
    private java.util.Date submittedDate;

    /** nullable persistent field */
    private java.util.Date expiryDate;

    /** nullable persistent field */
    private java.util.Date approvalDate;

    /** nullable persistent field */
    private java.lang.Long approvedBy;

    /** nullable persistent field */
    private Long fileId;

    /** nullable persistent field */
    private Long[] AppsIds;

    /** nullable persistent field */
    private String task;

    /** nullable persistent field */
    private FormFile uploadDocument;

    private Long fileType;

    private String comments;

    /**
     * @return Returns the comments.
     */
    public String getComments()
    {
        return comments;
    }
    /**
     * @param comments The comments to set.
     */
    public void setComments(String comments)
    {
        this.comments = comments;
    }
    /**
     * @return Returns the fileType.
     */
    public Long getFileType()
    {
        return fileType;
    }
    /**
     * @param fileType The fileType to set.
     */
    public void setFileType(Long fileType)
    {
        this.fileType = fileType;
    }
    /**
     * @return Returns the fileId.
     */
    public Long getFileId()
    {
        return fileId;
    }
    /**
     * @param fileId The fileId to set.
     */
    public void setFileId(Long fileId)
    {
        this.fileId = fileId;
    }
    /**
     * @return Returns the uploadDocument.
     */
    public FormFile getUploadDocument()
    {
        return uploadDocument;
    }
    /**
     * @param uploadDocument The uploadDocument to set.
     */
    public void setUploadDocument(FormFile uploadDocument)
    {
        this.uploadDocument = uploadDocument;
    }
    /**
     * @return Returns the task.
     */
    public String getTask()
    {
        return task;
    }
    /**
     * @param task The task to set.
     */
    public void setTask(String task)
    {
        this.task = task;
    }

    /**
     * @return Returns the approvalDate.
     */
    public java.util.Date getApprovalDate()
    {
        return approvalDate;
    }
    /**
     * @param approvalDate The approvalDate to set.
     */
    public void setApprovalDate(java.util.Date approvalDate)
    {
        this.approvalDate = approvalDate;
    }
    /**
     * @return Returns the approvedBy.
     */
    public java.lang.Long getApprovedBy()
    {
        return approvedBy;
    }
    /**
     * @param approvedBy The approvedBy to set.
     */
    public void setApprovedBy(java.lang.Long approvedBy)
    {
        this.approvedBy = approvedBy;
    }
    /**
     * @return Returns the appsIds.
     */
    public Long[] getAppsIds()
    {
        return AppsIds;
    }
    /**
     * @param appsIds The appsIds to set.
     */
    public void setAppsIds(Long[] appsIds)
    {
        AppsIds = appsIds;
    }
    /**
     * @return Returns the areaContactName.
     */
    public java.lang.String getAreaContactName()
    {
        return areaContactName;
    }
    /**
     * @param areaContactName The areaContactName to set.
     */
    public void setAreaContactName(java.lang.String areaContactName)
    {
        this.areaContactName = areaContactName;
    }
    /**
     * @return Returns the areaContactTel.
     */
    public java.lang.String getAreaContactTel()
    {
        return areaContactTel;
    }
    /**
     * @param areaContactTel The areaContactTel to set.
     */
    public void setAreaContactTel(java.lang.String areaContactTel)
    {
        this.areaContactTel = areaContactTel;
    }
    /**
     * @return Returns the areaTargeted.
     */
    public java.lang.String getAreaTargeted()
    {
        return areaTargeted;
    }
    /**
     * @param areaTargeted The areaTargeted to set.
     */
    public void setAreaTargeted(java.lang.String areaTargeted)
    {
        this.areaTargeted = areaTargeted;
    }
    /**
     * @return Returns the createdDate.
     */
    public java.util.Date getCreatedDate()
    {
        return createdDate;
    }
    /**
     * @param createdDate The createdDate to set.
     */
    public void setCreatedDate(java.util.Date createdDate)
    {
        this.createdDate = createdDate;
    }
    /**
     * @return Returns the creativeAgency.
     */
    public java.lang.String getCreativeAgency()
    {
        return creativeAgency;
    }
    /**
     * @param creativeAgency The creativeAgency to set.
     */
    public void setCreativeAgency(java.lang.String creativeAgency)
    {
        this.creativeAgency = creativeAgency;
    }
    /**
     * @return Returns the crequestId.
     */
    public java.lang.Long getCrequestId()
    {
        return crequestId;
    }
    /**
     * @param crequestId The crequestId to set.
     */
    public void setCrequestId(java.lang.Long crequestId)
    {
        this.crequestId = crequestId;
    }
    /**
     * @return Returns the expiryDate.
     */
    public java.util.Date getExpiryDate()
    {
        return expiryDate;
    }
    /**
     * @param expiryDate The expiryDate to set.
     */
    public void setExpiryDate(java.util.Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }
    /**
     * @return Returns the exposure.
     */
    public java.lang.String getExposure()
    {
        return exposure;
    }
    /**
     * @param exposure The exposure to set.
     */
    public void setExposure(java.lang.String exposure)
    {
        this.exposure = exposure;
    }
    /**
     * @return Returns the expProgramTakeRate.
     */
    public java.lang.String getExpProgramTakeRate()
    {
        return expProgramTakeRate;
    }
    /**
     * @param expProgramTakeRate The expProgramTakeRate to set.
     */
    public void setExpProgramTakeRate(java.lang.String expProgramTakeRate)
    {
        this.expProgramTakeRate = expProgramTakeRate;
    }

    /**
     * @return Returns the objectiveOfProgram.
     */
    public java.lang.String getObjectiveOfProgram()
    {
        return objectiveOfProgram;
    }
    /**
     * @param objectiveOfProgram The objectiveOfProgram to set.
     */
    public void setObjectiveOfProgram(java.lang.String objectiveOfProgram)
    {
        this.objectiveOfProgram = objectiveOfProgram;
    }
    /**
     * @return Returns the placementDisclaimer.
     */
    public java.lang.String getPlacementDisclaimer()
    {
        return placementDisclaimer;
    }
    /**
     * @param placementDisclaimer The placementDisclaimer to set.
     */
    public void setPlacementDisclaimer(java.lang.String placementDisclaimer)
    {
        this.placementDisclaimer = placementDisclaimer;
    }
    /**
     * @return Returns the programDistributionDate.
     */
    public java.lang.String getProgramDistributionDate()
    {
        return programDistributionDate;
    }
    /**
     * @param programDistributionDate The programDistributionDate to set.
     */
    public void setProgramDistributionDate(java.lang.String programDistributionDate)
    {
        this.programDistributionDate = programDistributionDate;
    }
    /**
     * @return Returns the programHighlights.
     */
    public java.lang.String getProgramHighlights()
    {
        return programHighlights;
    }
    /**
     * @param programHighlights The programHighlights to set.
     */
    public void setProgramHighlights(java.lang.String programHighlights)
    {
        this.programHighlights = programHighlights;
    }
    /**
     * @return Returns the programName.
     */
    public java.lang.String getProgramName()
    {
        return programName;
    }
    /**
     * @param programName The programName to set.
     */
    public void setProgramName(java.lang.String programName)
    {
        this.programName = programName;
    }
    /**
     * @return Returns the projectStartDate.
     */
    public java.lang.String getProjectStartDate()
    {
        return projectStartDate;
    }
    /**
     * @param projectStartDate The projectStartDate to set.
     */
    public void setProjectStartDate(java.lang.String projectStartDate)
    {
        this.projectStartDate = projectStartDate;
    }
    /**
     * @return Returns the promotionLength.
     */
    public java.lang.String getPromotionLength()
    {
        return promotionLength;
    }
    /**
     * @param promotionLength The promotionLength to set.
     */
    public void setPromotionLength(java.lang.String promotionLength)
    {
        this.promotionLength = promotionLength;
    }
    /**
     * @return Returns the status.
     */
    public java.lang.String getStatus()
    {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(java.lang.String status)
    {
        this.status = status;
    }
    /**
     * @return Returns the submittedDate.
     */
    public java.util.Date getSubmittedDate()
    {
        return submittedDate;
    }
    /**
     * @param submittedDate The submittedDate to set.
     */
    public void setSubmittedDate(java.util.Date submittedDate)
    {
        this.submittedDate = submittedDate;
    }
    /**
     * @return Returns the timelineExposure.
     */
    public java.lang.String getTimelineExposure()
    {
        return timelineExposure;
    }
    /**
     * @param timelineExposure The timelineExposure to set.
     */
    public void setTimelineExposure(java.lang.String timelineExposure)
    {
        this.timelineExposure = timelineExposure;
    }
    /**
     * @return Returns the userId.
     */
    public java.lang.Long getUserId()
    {
        return userId;
    }
    /**
     * @param userId The userId to set.
     */
    public void setUserId(java.lang.Long userId)
    {
        this.userId = userId;
    }
    /**
     * @return Returns the vehicleComm.
     */
    public java.lang.String getVehicleComm()
    {
        return vehicleComm;
    }
    /**
     * @param vehicleComm The vehicleComm to set.
     */
    public void setVehicleComm(java.lang.String vehicleComm)
    {
        this.vehicleComm = vehicleComm;
    }

    /**
     * Function perform validation related to mnarketing content request submit action.
     * If task is submit the validation is performed otherwise content is saved and date and
     * date validitiy is conformed
     * */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
        ActionErrors errors = new ActionErrors();

        if (task != null)
        {
            /*Error Checking if form going to be submitted*/
            if (task.equals("submit"))
            {
                if (this.isBlankString(programName))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.programName"));
                }
                if (this.isBlankString(vehicleComm))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.vehicleComm"));
                }
                if (this.isBlankString(projectStartDate))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.projectStartDate"));
                }
                else if (!isDate(projectStartDate))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.invalid.programStart"));
                }
                if (this.isBlankString(creativeAgency))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.agencyDev"));
                }
                if (this.isBlankString(areaContactName))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.areaContact"));
                }
                if (this.isBlankString(areaContactTel))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.areaContactTel"));
                }
                if (this.isBlankString(areaTargeted))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.areaTargeted"));
                }
                if (this.isBlankString(exposure))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.reachExposure"));
                }
                if (this.isBlankString(programHighlights))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.programHighlights"));
                }
                if (this.isBlankString(objectiveOfProgram))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.objectiveProgram"));
                }
                if (this.isBlankString(placementDisclaimer))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.placementDisclaimer"));
                }
                if (this.isBlankString(promotionLength))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.lengthPromotion"));
                }
                if (this.isBlankString(expProgramTakeRate))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.acticipatedTakeRate"));
                }
                if (this.isBlankString(programDistributionDate))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.programDistribution"));
                }
                else if (!isDate(programDistributionDate))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.invalid.programDistribution"));
                }
                if (this.isBlankString(timelineExposure))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.timelineExposure"));
                }

            } // if task is submit ends :: Task Other option check
            else if (task.equals("save"))
            {
                if (this.isBlankString(programName))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.programName"));
                }
                if (!isBlankString(projectStartDate) && !isDate(projectStartDate))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.invalid.programStart"));
                }
                if (!isBlankString(programDistributionDate) && !isDate(programDistributionDate))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.invalid.programDistribution"));
                }
            } // if task is upload then

            else if (task.equals("upload"))
            {
                if (uploadDocument == null || uploadDocument.getFileSize() <= 0)
                {
                    System.out.println(uploadDocument.getContentType() + " +++++++++++++++++++++++");
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.file"));
                }
                else if (!uploadDocument.getFileName().toUpperCase().endsWith("PDF"))
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.contentRequest.required.file.invalid"));
                }

            }
        } // if task != null ends

        return errors;
    }
    /* Validation function ends */
}
