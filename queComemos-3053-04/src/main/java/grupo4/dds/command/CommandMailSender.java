package grupo4.dds.command;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;


public class MailSender implements Command {

	private Usuario usuario;
	private List<Receta> consulta;
	private List<Filtro> filtros;

	public MailSender(Usuario usuario, List<Receta> consulta,
			List<Filtro> filtros) {

		this.usuario = usuario;
		this.consulta = consulta;
		this.filtros = filtros;

	}

	public void ejecutar()  {
		Mail mail = new Mail();
		mail.enviarMail(usuario, consulta, filtros);
	}

}
