package grupo4.dds.monitores.asincronicos.mail;

public class EMailer {
	
	private static MailSender mailSender;
	
	public static void setMailSender(MailSender newSender) {
		mailSender = newSender;
	}

	public static void enviarMail(Mail mail) {
		mailSender.enviarMail(mail);
	}
}
