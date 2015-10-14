package grupo4.dds.monitores.asincronicos;

import grupo4.dds.monitores.asincronicos.mail.Mail;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("mail")
public class EnvioPorMail extends MonitorAsincronico {
	
	@Transient
	public List<Usuario> suscriptos = new ArrayList<Usuario>();
	
	public void suscribir(Usuario usuario) {
		suscriptos.add(usuario);
	}

	public void desuscribir(Usuario usuario) {
		suscriptos.remove(usuario);
	}

	@Override
	public Consumer<Usuario> operacion(List<Receta> resultadoConsulta, List<Filtro> parametros) {
		return (Usuario u) -> {		
			if(suscriptos.contains(u))
				new Mail(u, resultadoConsulta, parametros).enviarMail();
		};
	}
}

