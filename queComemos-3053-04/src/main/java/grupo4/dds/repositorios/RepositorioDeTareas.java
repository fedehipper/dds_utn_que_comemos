package grupo4.dds.repositorios;

import grupo4.dds.monitores.asincronicos.TareaPendiente;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDeTareas {
	
	private static RepositorioDeTareas self = new RepositorioDeTareas();
	private List<TareaPendiente> tareasPendientes = new ArrayList<>();

	public static RepositorioDeTareas instance() {
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
