package grupo4.dds.command;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;


public class CommandMailSender implements Command {




	public void ejecutar()  {
		Mail mail = new Mail();
		//mail.enviarMail(usuario, consulta, filtros);
	}

}
