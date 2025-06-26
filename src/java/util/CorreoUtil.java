package util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class CorreoUtil {
    public static void enviarCorreo(String destino, String asunto, String mensaje) {
        final String remitente = "obotecare@gmail.com"; // ✅ Correo emisor
        final String clave = "muos hmof edmg lrqh"; // ⚠️ Usa aquí tu contraseña de aplicación real

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session sesion = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);

            // ✅ Confirmación por consola
            System.out.println("✅ Correo enviado exitosamente a: " + destino);
        } catch (MessagingException e) {
            // ❌ Mensaje de error por consola
            System.err.println("❌ Error al enviar el correo a: " + destino);
            e.printStackTrace();
        }
    }
}
