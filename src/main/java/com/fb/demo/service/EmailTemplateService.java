package com.fb.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.fb.demo.entity.EmailTemplate;
import com.fb.demo.model.request.EmailTemplateCreateRequest;
import com.fb.demo.model.request.EmailTemplateUpdateRequest;
import com.fb.demo.model.response.EmailTemplateResponse;

@Service
public interface EmailTemplateService {

    public EmailTemplateResponse createEmailTemplate(
                    EmailTemplateCreateRequest emailTemplateCreateRequest) throws Exception;

    public EmailTemplate getEmailTemplate(Integer id) throws Exception;

    public List<EmailTemplate> getAllEmailTemplate();

    public void deleteEmailTemplate(Integer id) throws Exception;

    public void updateEmailTemplate(EmailTemplateUpdateRequest emailTemplateUpdateRequest,
                    Integer id)
                    throws Exception;
}
