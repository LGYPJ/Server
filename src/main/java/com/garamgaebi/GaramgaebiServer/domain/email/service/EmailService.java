package com.garamgaebi.GaramgaebiServer.domain.email.service;

import com.garamgaebi.GaramgaebiServer.domain.email.dto.EmailReq;
import com.garamgaebi.GaramgaebiServer.domain.email.dto.EmailRes;

public interface EmailService {
    EmailRes sendEmail(EmailReq emailReq) throws Exception;
}
