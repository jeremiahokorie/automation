package com.automation.core.global.service.ServiceImpl;

import com.automation.core.global.model.MailModel;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final Configuration templateConfiguration;

    private final SimpleMailMessage simpleMail;
    private final JavaMailSender javaMailSender;

    public void sendEmail(MailModel model) {
        try {
            if (model.isHasAttachment()) {
                sendMailWithAttachment(model);
            } else if (model.isUseTemplate()) {
                sendHtmlEmail(model);
            } else {
                sendPlainEmail(model);
            }
        } catch (org.springframework.mail.MailSendException e) {
            log.error("Error occurred sending mail with plain message:: {}", e.getMessage());
        }
    }

    private void sendPlainEmail(MailModel model) {
        try {
            simpleMail.setFrom(model.getFrom());
            simpleMail.setTo(model.getTo());
            simpleMail.setBcc(model.getBcc());
            simpleMail.setCc(model.getCc());
            simpleMail.setSubject(model.getSubject());
            if (model.isUseTemplate()) {
                simpleMail.setText(FreeMarkerTemplateUtils.processTemplateIntoString(templateConfiguration
                        .getTemplate(model.getTemplateName(), model.getLocale()), model.getMessageMap()));
            } else {
                simpleMail.setText(model.getMessage());
            }
            simpleMail.setSentDate(new Date());
            javaMailSender.send(simpleMail);
            log.info("Plain Email Sent !");
        } catch (IOException | TemplateException e) {
            log.error("Error occurred sending mail with plain message:: {}", e.getMessage());
        }
    }

    private void sendHtmlEmail(MailModel model) {
        try {
            final MimeMessage mime = javaMailSender.createMimeMessage();
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mime, true);
            messageHelper.setSubject(model.getSubject());
            messageHelper.setFrom(model.getFrom());
            messageHelper.setTo(model.getTo());
            messageHelper.setBcc(model.getBcc());
            messageHelper.setCc(model.getCc());
            templateConfiguration.setClassForTemplateLoading(this.getClass(), "/");
            final String htmlContext = FreeMarkerTemplateUtils.processTemplateIntoString(templateConfiguration
                    .getTemplate("/templates/" + model.getTemplateName(), model.getLocale()), model.getMessageMap());
            messageHelper.setText(htmlContext, true);
            javaMailSender.send(mime);
            log.info("Html Email Sent !");
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("Error occurred sending mail with HTML message:: {}", e.getMessage());
        }
    }

    private void sendMailWithAttachment(MailModel mailModel) {
        try {
            final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(mailModel.getFrom());
            messageHelper.setSubject(mailModel.getSubject());
            messageHelper.setBcc(mailModel.getBcc());
            messageHelper.setTo(mailModel.getTo());
            messageHelper.setCc(mailModel.getCc());
            templateConfiguration.setClassForTemplateLoading(this.getClass(), "/");
            final String htmlContext = FreeMarkerTemplateUtils.processTemplateIntoString(templateConfiguration
                            .getTemplate("/templates/" + mailModel.getTemplateName(), mailModel.getLocale()),
                    mailModel.getMessageMap());
            messageHelper.setText(htmlContext, true);

            if (mailModel.getHasUrlLocation().equals(Boolean.TRUE)) {
                for (int i = 0; i < mailModel.getAttachmentBytes().size(); i++) {
                    URL url = new URL(mailModel.getUrls().get(i));
                    UrlResource urlResource = new UrlResource(url);
                    messageHelper.addAttachment(mailModel.getAttachmentFileNames().get(i),
                            urlResource,
                            mailModel.getAttachmentContentTypes().get(i));
                }
            } else {
                for (int i = 0; i < mailModel.getAttachmentBytes().size(); i++) {
                    final InputStreamSource attachmentSource = new ByteArrayResource(
                            mailModel.getAttachmentBytes().get(i),
                            mailModel.getDescriptions().get(i));
                    messageHelper.addAttachment(mailModel.getAttachmentFileNames().get(i),
                            attachmentSource,
                            mailModel.getAttachmentContentTypes().get(i));
                }
            }
            javaMailSender.send(mimeMessage);
            log.info("Html Email with attachment Sent !");
        } catch (IOException | TemplateException | MessagingException e) {
            log.error("error occurred sending HTML with Attachment email to client: {}", e.getMessage(), e);
        }
    }
    }
