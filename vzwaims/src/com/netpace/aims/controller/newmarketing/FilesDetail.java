package com.netpace.aims.controller.newmarketing;

import java.io.Serializable;

public class FilesDetail implements Serializable
{

    private Long fileId;
    private int fileType;
    private String fileName;
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
     * @return Returns the fileName.
     */
    public String getFileName()
    {
        return fileName;
    }
    /**
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    /**
     * @return Returns the fileType.
     */
    public int getFileType()
    {
        return fileType;
    }
    /**
     * @param fileType The fileType to set.
     */
    public void setFileType(int fileType)
    {
        this.fileType = fileType;
    }

}
