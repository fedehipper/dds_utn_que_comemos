package grupo4.dds.monitores.asincronicos;

import grupo4.dds.monitores.asincronicos.tareas.TareaEnvioPorMail;
import grupo4.dds.monitores.asincronicos.tareas.TareaPendiente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("mail")
public class EnvioPorMail extends MonitorAsincronico {
	
	private static EnvioPorMail self = new EnvioPorMail();
	
	@OneToMany
	public List<Usuario> suscriptos = new ArrayList<Usuario>();
	
	public static EnvioPorMail instance() {
		return self;
	}

	private EnvioPorMail() {}

	public void suscribir(Usuario usuario) {
		suscriptos.add(usuario);
	}

	public void desuscribir(Usuario usuario) {
		suscriptos.remove(usuario);
	}

	@Override
	public TareaPendiente nuevaTarea(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		return new TareaEnvioPorMail(usuario, resultadoConsulta, parametros);
	}

}

