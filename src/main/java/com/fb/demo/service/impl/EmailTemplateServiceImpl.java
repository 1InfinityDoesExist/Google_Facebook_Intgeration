package com.fb.demo.service.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import com.fb.demo.entity.EmailTemplate;
import com.fb.demo.model.request.EmailTemplateCreateRequest;
import com.fb.demo.model.request.EmailTemplateUpdateRequest;
import com.fb.demo.model.response.EmailTemplateResponse;
import com.fb.demo.service.EmailTemplateService;

@Component
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Override
    public EmailTemplateResponse createEmailTemplate(
                    EmailTemplateCreateRequest emailTemplateCreateRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EmailTemplate getEmailTemplate(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<EmailTemplate> getAllEmailTemplate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteEmailTemplate(Integer id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateEmailTemplate(EmailTemplateUpdateRequest emailTemplateUpdateRequest) {
        // TODO Auto-generated method stub

    }

}
