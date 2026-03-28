package dev.forgepack.library.api.service;

/**
 * @author	Marcelo Ribeiro Gadelha
 * Website:	www.gadelha.eti.br
 **/

public interface ServiceInterfaceEmail {

    void sendSimpleMessage(String to, String subject, String text);
    void sendHtmlMessageWithAttachment(String to, String subject, String htmlContent,
                                       byte[] attachmentData, String attachmentName, String mimeType);
    String buildWelcomeEmailContent(String username, String password, String secret);
}
