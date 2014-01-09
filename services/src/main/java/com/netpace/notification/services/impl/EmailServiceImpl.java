package com.netpace.notification.services.impl;

import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.notification.services.EmailService;
import com.netpace.notification.vo.EmailMessageVO;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private final static Log log = LogFactory.getLog(EmailServiceImpl.class);
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    private JavaMailSender javaMailSender;

    @PostConstruct
    public void initialize() {
        JavaMailSenderImpl javaMailSenderImpl;
        Properties javaMailProperties;
        String host;

        host = applicationPropertiesService.defaultEmailHost();
        javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(host);

        javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", "false");
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailSenderImpl.setJavaMailProperties(javaMailProperties);

        this.javaMailSender = javaMailSenderImpl;
    }

    @Override
    public void sendEmail(EmailMessageVO emailMessageVO) throws Exception {
        MimeMessage mimeMessage;
        MimeMessageHelper messageHelper;
        String env;
        String sendToInCaseOfDevEnv;

        try {
            env = applicationPropertiesService.defaultNotificationEnvironment();
            sendToInCaseOfDevEnv = applicationPropertiesService.defaultNotificationEnvironmentDevSendTo();

            mimeMessage = javaMailSender.createMimeMessage();
            messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(emailMessageVO.getFromAddress());
            messageHelper.setSubject(emailMessageVO.getSubject());

            // if dev environment, do not send to actual users
            String htmlContent, plainText, preText;
            String[] toAddresses;
            
            if (env.equalsIgnoreCase("DEV")) {
                preText = "In production environment this mail would have been sent to :[" + emailMessageVO.getToAddresses() + "]"
                            + "<br/><br/><br/>" + "**************   ORIGNAL MESSAGE BEGINS BELOW   ***************" + "<br/>";
                toAddresses = StringUtils.split(sendToInCaseOfDevEnv.trim(), ',');
                htmlContent = emailMessageVO.getContent();
                plainText = emailMessageVO.getPlainText();
                
            } else {
                preText = "";
                toAddresses = emailMessageVO.getToAddresses().toArray(new String[emailMessageVO.getToAddresses().size()]);
                htmlContent = emailMessageVO.getContent();
                plainText = emailMessageVO.getPlainText();
            }
            
            if( StringUtils.isNotEmpty(htmlContent) && StringUtils.isNotEmpty(plainText) ){
                messageHelper.setText(preText + plainText, preText + htmlContent);
            }else if(StringUtils.isNotEmpty(htmlContent)){
                messageHelper.setText( preText + htmlContent, true);
            }else if(StringUtils.isNotEmpty(plainText)){
                messageHelper.setText( preText + plainText, false);
            }
            
            if (env.equalsIgnoreCase("DEV")) {
                messageHelper.setTo(toAddresses);
                javaMailSender.send(mimeMessage);
            }else{
                for (String toAddress : toAddresses) {
                    messageHelper.setTo(toAddress);
                    javaMailSender.send(mimeMessage);
                }
            }
        } catch (MessagingException e) {
            log.error("Error Sending Email: ", e);
            throw e;
        } catch (Exception e) {
            log.error("Error Sending Email: ", e);
            throw e;
        }

    }
}
