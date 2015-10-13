package grupo4.dds.monitores.asincronicos;

import java.util.function.Consumer;

import grupo4.dds.usuario.Usuario;

public class TareaPendiente {
	
	private Usuario usuario;
	private Consumer<Usuario> operacion;
	
	public TareaPendiente(Usuario usuario, Consumer<Usuario> operacion) {
		this.usuario = usuario;
		this.operacion = operacion;
	}
	
	public void ejecutar() {
		operacion.accept(usuario);
	}
	
}
