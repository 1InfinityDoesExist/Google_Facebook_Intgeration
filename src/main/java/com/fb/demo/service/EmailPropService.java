package com.fb.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.fb.demo.entity.EmailProp;
import com.fb.demo.model.request.EmailPropCreateRequest;
import com.fb.demo.model.request.EmailPropUpdateRequest;
import com.fb.demo.model.response.EmailPropResponse;

@Service
public interface EmailPropService {

    public EmailPropResponse createEmailProp(EmailPropCreateRequest emailPropRequest)
                    throws Exception;

    public EmailProp getEmailProp(Integer id) throws Exception;

    public List<EmailProp> getAllEmailProp();

    public void deleteEmilProp(Integer id) throws Exception;

    public void updateEmailProp(EmailPropUpdateRequest emailPropUpdateRequest, Integer id)
                    throws Exception;
}
