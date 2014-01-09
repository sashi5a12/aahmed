package com.netpace.notification.vo;

import com.netpace.commons.vo.Record;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.validation.groups.PatternGroup;
import com.netpace.device.vo.validation.groups.RequiredGroup;
import com.netpace.device.vo.validation.groups.SizeGroup;
import java.util.List;
import javax.validation.GroupSequence;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@GroupSequence(value = {NotificationVO.class, RequiredGroup.class, PatternGroup.class, SizeGroup.class})
public class NotificationVO extends Record {

    private String title;
    private String description;
    private Integer eventId;
    private String emailContent;
    private String emailPlainText;
    private boolean enabled;
    private List<Integer> targetSystemRoles;
    private String recipientEmailAddresses;

    @NotBlank(groups = RequiredGroup.class)
    @Length(min = 8, max = 130, groups = SizeGroup.class)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Length(min = 8, max = 200, groups = SizeGroup.class)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Range(min = 1, groups = RequiredGroup.class)
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @NotBlank(groups = RequiredGroup.class)
    @Length(min = 8, max = 4000, groups = SizeGroup.class)
    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getEmailPlainText() {
        return emailPlainText;
    }

    public void setEmailPlainText(String emailPlainText) {
        this.emailPlainText = emailPlainText;
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Pattern(regexp = VAPConstants.REGEX_STD_EMAIL_COMMA_SEPARATED, groups = PatternGroup.class)
    public String getRecipientEmailAddresses() {
        return recipientEmailAddresses;
    }

    public void setRecipientEmailAddresses(String recipientEmailAddresses) {
        this.recipientEmailAddresses = recipientEmailAddresses;
    }

    public List<Integer> getTargetSystemRoles() {
        return targetSystemRoles;
    }

    public void setTargetSystemRoles(List<Integer> targetSystemRoles) {
        this.targetSystemRoles = targetSystemRoles;
    }
}
