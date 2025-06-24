package com.automation.core.global.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MailModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String subject;

    private String from;

    @Builder.Default
    private String[] to = new String[0];

    @Builder.Default
    private String[] bcc = new String[0];

    @Builder.Default
    private String[] cc = new String[0];

    private String message;

    @Builder.Default
    private String type = "html";

    private Map<String, Object> attachedFiles;

    @Builder.Default
    private Locale locale = Locale.ENGLISH;

    private String templateName;

    @Builder.Default
    private boolean hasAttachment = false;

    @Builder.Default
    private boolean useTemplate = false;

    @Builder.Default
    private Boolean hasUrlLocation = false;

    private Map<String, String> messageMap;

    private List<byte[]> attachmentBytes;

    private List<String> attachmentContentTypes;

    private List<String> attachmentFileNames;

    private List<String> descriptions;

    private List<String> urls;

}
