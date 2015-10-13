package grupo4.dds.monitores.asincronicos;

import java.util.ArrayList;
import java.util.List;

public class RepositorioTareas {
	
	private static RepositorioTareas self = new RepositorioTareas();
	private List<TareaPendiente> tareasPendientes = new ArrayList<>();

	public static RepositorioTareas instance() {
		return self;
	}
	
	public List<TareaPendiente> getTareasPendientes() {
		return tareasPendientes;
	}

	public void agregarTarea(TareaPendiente operacion) {
		tareasPendientes.add(operacion);
	}
	
	public void ejecutarTodas() {
		tareasPendientes.forEach(TareaPendiente::ejecutar);
	}

	public void vaciar() {
		tareasPendientes.clear();
	}
	
}
