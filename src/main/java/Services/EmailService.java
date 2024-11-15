package Services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    private final String username = "conta.teste.inovadora@gmail.com";  
    private final String password = "bnjc ogrp gaby ngcn";  // Senha de app gerada pelo Gmail

    public void enviarEmailComToken(String destinatario, String token) {
        // Configurações de e-mail (usando SSL)
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Criando uma sessão com autenticação
        Session session = Session.getInstance(prop, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Redefinição de Senha");

            String conteudo = "Você solicitou a redefinição de senha.\n\n"
                    + "Use o seguinte token para redefinir sua senha:\n\n" + token
                    + "\n\nSe você não solicitou esta ação, por favor, ignore este e-mail.";

            message.setText(conteudo);

            Transport.send(message);

            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException e) {
            System.out.println(e);
        }
    }
}
