package grupo4.dds.monitores.asincronicos.tareas;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import grupo4.dds.monitores.asincronicos.EnvioPorMail;
import grupo4.dds.monitores.asincronicos.mail.Mail;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

@Entity
@DiscriminatorValue("mail")
public class TareaEnvioPorMail extends TareaPendiente {

	public TareaEnvioPorMail() {
	}

	public TareaEnvioPorMail(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		super(usuario, resultadoConsulta, parametros);
	}

	@Override
	public void ejecutar() {
		if(EnvioPorMail.instance().suscriptos.contains(usuario))
			new Mail(usuario, resultadoConsulta, parametros).enviarMail();
	}

}
