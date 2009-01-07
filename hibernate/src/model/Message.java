package model;

import java.util.Date;


/**
 * Message entity. @author MyEclipse Persistence Tools
 */

public class Message  implements java.io.Serializable {


	private static final long serialVersionUID = -8614051923119723288L;
	private Integer id;
	private Message nextMessage;
	private String text;
	private Date dateModified;


    // Constructors

    /** default constructor */
    public Message() {
    }

    
    /** full constructor */
    public Message(Message nextMessage, String text, Date dateModified) {
        this.nextMessage = nextMessage;
        this.text = text;
        this.dateModified = dateModified;
    }

   
    // Property accessors

    public Message(String text) {
    	this.text=text;
	}

	public Message(String text, Date dateModified) {
		this.text = text;
		this.dateModified = dateModified;
	}

	public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Message getNextMessage() {
        return this.nextMessage;
    }
    
    public void setNextMessage(Message nextMessage) {
        this.nextMessage = nextMessage;
    }

    public String getText() {
        return this.text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public Date getDateModified() {
        return this.dateModified;
    }
    
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
   








}