package grupo4.dds.repositorios;

import grupo4.dds.monitores.asincronicos.tareas.TareaPendiente;

public class RepositorioDeTareas extends Repositorio<TareaPendiente> {
	
	private static RepositorioDeTareas self = new RepositorioDeTareas();

	public static RepositorioDeTareas instance() {
		return self;
	}
	
	public RepositorioDeTareas() {
		elementType = TareaPendiente.class;
	}
	
	public void ejecutarTodas() {
		list().forEach(TareaPendiente::ejecutar);
	}

}
