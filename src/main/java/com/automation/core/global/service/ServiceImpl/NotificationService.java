package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.model.MailModel;
import com.automation.events.EmailNotificationEvent;
import com.automation.events.SmsNotificationEvent;
import com.automation.util.integration.InfoBipSendSmsDestinationRequest;
import com.automation.util.integration.InfoBipSendSmsMessageRequest;
import com.automation.util.integration.InfoBipSendSmsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailSenderService emailService;

//    @Autowired
//    private final InfoBipIntegration infoBip;

    @Value("${spring.mail.sender}")
    String emailFrom;

    @Async
    @EventListener(condition = "#event.type eq 'welcome'")
    public void sendNewUserNotificationEmail(EmailNotificationEvent event) {
        Map<String, String> mailInfo = event.getMessage();
        MailModel model = new MailModel();
        model.setFrom(emailFrom);
        model.setSubject("Welcome to Bauchi Mda Portal");
        model.setUseTemplate(true);
        model.setTemplateName("SignUpWelcomePage.ftl");
        model.setTo(new String[]{mailInfo.get("recipient")});
        Map<String, String> mailMap = new HashMap<>();
        mailMap.put("name", StringUtils.capitalize(mailInfo.get("name")));
        mailMap.put("url", StringUtils.capitalize(mailInfo.get("url")));

        model.setMessageMap(mailMap);
        emailService.sendEmail(model);
    }

    @Async
    @EventListener(condition = "#event.type eq 'password'")
    public void sendUserResetTokenNotificationEmail(EmailNotificationEvent event) {
        Map<String, String> mailInfo = event.getMessage();
        MailModel model = new MailModel();
        model.setFrom(emailFrom);
        model.setSubject("Welcome to Bauchi Mda Portal");
        model.setUseTemplate(true);
        model.setTemplateName("welcomePage.ftl");
        model.setTo(new String[]{mailInfo.get("recipient")});
        Map<String, String> mailMap = new HashMap<>();
        mailMap.put("name", StringUtils.capitalize(mailInfo.get("name")));
        mailMap.put("url", StringUtils.capitalize(mailInfo.get("url")));

        model.setMessageMap(mailMap);
        emailService.sendEmail(model);
    }

    @Async
    @EventListener(condition = "#event.type eq 'welcome-admin'")
    public void sendNewAdminUserNotificationEmail(EmailNotificationEvent event) {
        Map<String, String> mailInfo = event.getMessage();
        MailModel model = new MailModel();
        model.setFrom(emailFrom);
        model.setSubject("Welcome to Hi'Bees");
        model.setUseTemplate(true);
        model.setTemplateName("welcomePage.ftl");
        model.setTo(new String[]{mailInfo.get("recipient")});
        Map<String, String> mailMap = new HashMap<>();
        mailMap.put("name", StringUtils.capitalize(mailInfo.get("name")));
        mailMap.put("username", StringUtils.capitalize(mailInfo.get("username")));
        mailMap.put("cypher", StringUtils.capitalize(mailInfo.get("cypher")));

        model.setMessageMap(mailMap);
        emailService.sendEmail(model);
    }

//    @Async
//    @EventListener()
//    public void smsNotificationHandler(SmsNotificationEvent event) {
//        infoBip.sendSms(InfoBipSendSmsRequest.builder()
//                .messages(Collections.singletonList(InfoBipSendSmsMessageRequest.builder()
//                        .destinations(Collections.singletonList(InfoBipSendSmsDestinationRequest.builder().to(event.getMessage().get("recipient")).build()))
//                        .text(event.getMessage().get("message"))
//                        .build()
//                ))
//                .build());
//
//    }
}
