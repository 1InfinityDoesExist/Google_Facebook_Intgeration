package com.fb.demo.mail;

import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MailService {

    @Autowired
    private GmailProvider gmailProvider;

    @Autowired
    private YahooProvider yahooProvider;

    @Autowired
    private RabbitholeProvider rabbitholeProvider;

    @Value("${reset.password.email.host}")
    private String host;
    @Value("${reset.password.email.port}")
    private int port;
    @Value("${reset.password.email.id}")
    private String emailId;
    @Value("${reset.password.email.pwd}")
    private String password;
    @Value("${reset.password.email.provider}")
    private String provider;
    @Value("${reset.password.email.subject}")
    private String subject;

    public void sendMail(ModelMap modelMap) {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(host);
        javaMailSenderImpl.setPort(port);
        javaMailSenderImpl.setUsername(emailId);
        javaMailSenderImpl.setPassword(password);
        javaMailSenderImpl.setJavaMailProperties(getMailProperties(provider));
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            log.info("::::::Setting toEmail, subject, emailBody:::::");
            helper.setSubject((String) modelMap.get("subject"));
            helper.setText((String) modelMap.get("mailBody"));
            if (modelMap.get("to") instanceof List) {
                log.info(":::::SendTo = String[]:::::");
                helper.setTo((String[]) modelMap.get("to"));
            } else {
                log.info("::::SendTo = String:::::");
                helper.setTo((String) modelMap.get("to"));
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        log.info(":::::About to send email:::::");
        javaMailSenderImpl.send(mimeMessage);
        log.info("::::::Mail Sent:::::");
    }

    private Properties getMailProperties(String provider) {
        Properties properties = new Properties();
        if (provider.equalsIgnoreCase("GMAIL")) {
            getGMailProperties(properties);
        } else if (provider.equalsIgnoreCase("YAHOO")) {
            getYahooProperties(properties);
        } else {
            getRabbitholeProperties(properties);
        }
        return properties;
    }

    private void getRabbitholeProperties(Properties properties) {
        rabbitholeProvider.getRabbitHoleProperties().forEach((key, value) -> {
            properties.setProperty(key, value);
        });
    }

    private void getYahooProperties(Properties properties) {
        yahooProvider.getYahooProperties().forEach((key, value) -> {
            properties.setProperty(key, value);
        });
    }

    private void getGMailProperties(Properties properties) {
        gmailProvider.getGmailProerties().forEach((key, value) -> {
            properties.setProperty(key, value);
        });

    }
}

