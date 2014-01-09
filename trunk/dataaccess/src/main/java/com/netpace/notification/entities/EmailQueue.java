package com.netpace.notification.entities;

import com.netpace.device.entities.BaseEntity;
import com.netpace.device.utils.enums.MessageQueueStatus;
import com.netpace.notification.vo.EmailMessageVO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "vap_email_queue")
@NamedQueries({
    @NamedQuery(name = "findQueuedEmails", query = "from EmailQueue emailQueue where emailQueue.active = '1' order by createdDate asc")
})
public class EmailQueue extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;
    private Integer id;
    private String title;
    private String toAddresses;
    private MessageQueueStatus status;
    private EmailMessageVO emailMessage;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "to_addresses")
    public String getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(String toAddresses) {
        this.toAddresses = toAddresses;
    }

    @Lob
    @Column(name = "email_message")
    public EmailMessageVO getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(EmailMessageVO emailMessage) {
        this.emailMessage = emailMessage;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public MessageQueueStatus getStatus() {
        return status;
    }

    public void setStatus(MessageQueueStatus status) {
        this.status = status;
    }
    
}