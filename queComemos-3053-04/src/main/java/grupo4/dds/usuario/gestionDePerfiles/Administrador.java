package grupo4.dds.usuario.gestionDePerfiles;

import grupo4.dds.usuario.Usuario;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Administrador {

	private static final Administrador self = new Administrador();
	private Set<Usuario> pendientesAprobacion = new HashSet<>();
	
	public static Administrador get() {
		return self;
	}
	
	public void solicitarIncorporación(Usuario usuario) {
		pendientesAprobacion.add(usuario);		
	}
	
	public void aprobar(Usuario usuario) {
		RepositorioDeUsuarios.get().add(usuario);
		usuario.solicitudAceptada();
	}
	
	public void rechazar(Usuario usuario, String motivo) {
		usuario.solicitudRechazada(motivo);
	}
	
	public void aprobarTodas() {
		procesarTodas(u -> aprobar(u));
	}
	
	public void rechazarTodas(String motivo) {
		procesarTodas(u -> rechazar(u, motivo));
	}
	
	private void procesarTodas(Consumer<Usuario> procesador) {
		
		for (Usuario usuario : pendientesAprobacion) {
			pendientesAprobacion.remove(usuario);	
			procesador.accept(usuario);
		}
	}
	
	public void limpiarSolicitudes() {
		pendientesAprobacion.clear();
	}
	
	public Set<Usuario> verSolicitudesPendientes() {
		return Collections.unmodifiableSet(pendientesAprobacion);
	}
	
}
