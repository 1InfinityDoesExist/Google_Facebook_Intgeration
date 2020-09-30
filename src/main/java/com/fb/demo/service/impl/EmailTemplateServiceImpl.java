package com.fb.demo.service.impl;

import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fb.demo.entity.EmailTemplate;
import com.fb.demo.exception.EmailTemplateNotFound;
import com.fb.demo.model.request.EmailTemplateCreateRequest;
import com.fb.demo.model.request.EmailTemplateUpdateRequest;
import com.fb.demo.model.response.EmailTemplateResponse;
import com.fb.demo.repository.EmailTemplateRepository;
import com.fb.demo.service.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Override
    public EmailTemplateResponse createEmailTemplate(
                    EmailTemplateCreateRequest emailTemplateCreateRequest) throws Exception {
        log.info(":::::EmailTemplateServiceImpl Class, createEmailTemplate method:::::");
        if (emailTemplateCreateRequest != null) {
            EmailTemplateResponse emailTemplateCreateResponse = new EmailTemplateResponse();
            EmailTemplate emailTemplate = new EmailTemplate();
            emailTemplate.setEmailProp(emailTemplateCreateRequest.getEmailProp());
            emailTemplate.setFormat(emailTemplateCreateRequest.getFormat());
            emailTemplate.setName(emailTemplateCreateRequest.getName());
            emailTemplate.setSubject(emailTemplate.getSubject());
            emailTemplateRepository.save(emailTemplate);
            emailTemplateCreateResponse.setId(emailTemplate.getId());
            emailTemplateCreateResponse.setMsg("Successfully created");
            return emailTemplateCreateResponse;
        } else {
            throw new InvalidInputException("Invalid input");
        }
    }

    @Override
    public EmailTemplate getEmailTemplate(Integer id) throws Exception {
        if (id != null) {
            EmailTemplate emailTemplate = emailTemplateRepository.getEmailTemplateById(id);
            if (emailTemplate != null) {
                return emailTemplate;
            } else {
                throw new EmailTemplateNotFound("EmailTemplate not found for given id :" + id);
            }
        } else {
            throw new InvalidInputException("Invalid input");
        }
    }

    @Override
    public List<EmailTemplate> getAllEmailTemplate() {
        List<EmailTemplate> listOfEmailTemplate = emailTemplateRepository.findAll();
        return listOfEmailTemplate;
    }

    @Override
    public void deleteEmailTemplate(Integer id) throws Exception {
        if (id != null) {
            EmailTemplate emailTemplate = emailTemplateRepository.getEmailTemplateById(id);
            if (emailTemplate != null) {
                emailTemplateRepository.delete(emailTemplate);
            } else {
                throw new EmailTemplateNotFound("EmailTemplate not found for given id :" + id);
            }
        } else {
            throw new InvalidInputException("Invalid input");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateEmailTemplate(EmailTemplateUpdateRequest emailTemplateUpdateRequest,
                    Integer id) throws Exception {
        log.info(":::::Update EmailTemplate ::::");
        if (id != null && emailTemplateUpdateRequest != null) {
            EmailTemplate emailTemplate = emailTemplateRepository.getEmailTemplateById(id);
            if (emailTemplate != null) {
                JSONObject dbObject = (JSONObject) new JSONParser()
                                .parse(new ObjectMapper().writeValueAsString(emailTemplate));
                JSONObject payloadObject = (JSONObject) new JSONParser().parse(
                                new ObjectMapper().writeValueAsString(emailTemplateUpdateRequest));
                for (Object object : payloadObject.keySet()) {
                    String param = (String) object;
                    dbObject.put(param, payloadObject.get(param));
                }
                emailTemplateRepository.save(new ObjectMapper().readValue(dbObject.toJSONString(),
                                EmailTemplate.class));
            } else {
                throw new EmailTemplateNotFound("EmailTemplate not found for given id :" + id);
            }
        } else {
            throw new InvalidInputException("Invalid input");
        }
    }

}
