package grupo4.dds.command;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;


public class CommandMailSender implements Command {

  private List<Receta> consulta = new ArrayList<>();
  private List<Filtro> filtros = new ArrayList<>();


	public void ejecutar(Usuario usuario)  {
		
		Mail mail = new Mail(usuario, consulta, filtros);
		MailSender mailSender = new MailSenderPosta(mail);
		mailSender.enviarMail(mail);
	}

}
