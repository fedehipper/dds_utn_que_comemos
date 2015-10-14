package grupo4.dds.repositorios;

import grupo4.dds.monitores.asincronicos.TareaPendiente;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioDeTareas implements WithGlobalEntityManager {
	
	private static RepositorioDeTareas self = new RepositorioDeTareas();

	public static RepositorioDeTareas instance() {
		return self;
	}
	
	public List<TareaPendiente> getTareasPendientes() {
		return entityManager().createQuery("from TareaPendiente", TareaPendiente.class).getResultList();
	}

	public void agregarTarea(TareaPendiente tarea) {
		entityManager().persist(tarea);
	}
	
	public void ejecutarTodas() {
		getTareasPendientes().forEach(TareaPendiente::ejecutar);
	}

}
