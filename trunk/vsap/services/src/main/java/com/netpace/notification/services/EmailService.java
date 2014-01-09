package com.netpace.notification.services;

import com.netpace.notification.vo.EmailMessageVO;

public interface EmailService {

    public void sendEmail(EmailMessageVO emailMessageVO) throws Exception;
}
