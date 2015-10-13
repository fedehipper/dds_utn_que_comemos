package grupo4.dds.monitores.asincronicos.mail;

public interface MailSender {
	
	public void enviarMail(Mail mail);
	public Mail ultimoMail();
	
}
