package grupo4.dds.command;


import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;


import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class Mail {

	private  String localMail = "queComemos4@gmail.com";
	private  String pass = "ESCUADRONSUICIDA";

	public void enviarMail(Usuario usuario, List<Receta> consulta,
			List<Filtro> filtros) {

		try {
			// Propiedades de la conexión
			Properties propiedades = configurarConexion(usuario);

			// Inicializacion de la sesion
			Session session = Session.getDefaultInstance(propiedades);

			// Construcción del mensaje
			MimeMessage mensajeCompleto = crearMensaje(session, usuario, consulta,filtros);

			// Envío de mensaje.
			Transport t = session.getTransport("smtp");
			t.connect(localMail, pass);
			t.sendMessage(mensajeCompleto, mensajeCompleto.getAllRecipients());

			
			
			t.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public MimeMessage crearMensaje(Session session,Usuario usuario, List<Receta> consulta,
			List<Filtro> filtros) throws MessagingException {
		
		MimeMessage mensaje = new MimeMessage(session);
		mensaje.setFrom(new InternetAddress(localMail));
		mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getMail()));

		mensaje.setSubject("QueComemos Consultas");

		String consultaTexto = consulta.stream().map(c -> c.getNombreDelPlato()).collect(Collectors.toList()).toString();
		String consultaFiltro = filtros.stream().map(f -> f.getNombre()).collect(Collectors.toList()).toString();
		
		mensaje.setText("Filtros Seleccionados: " + consultaFiltro + "\nConsultas: " + consultaTexto + "\n");
		return mensaje;
	}

	public Properties configurarConexion(Usuario usuario) {
		
		Properties propiedades = new Properties();
		propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedades.setProperty("mail.smtp.starttls.enable", "true");
		propiedades.setProperty("mail.smtp.port", "587");
		propiedades.setProperty("mail.smtp.user", usuario.getMail());
		propiedades.setProperty("mail.smtp.auth", "true");

		return propiedades;
	}
	
	
}