package grupo4.dds.repositorios;

import grupo4.dds.misc.CoberturaIgnore;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.gestionDePerfiles.SolicitudAltaUsuario;

import java.util.function.Consumer;

public class RepositorioDeSolicitudes extends Repositorio<SolicitudAltaUsuario> {

	private static final RepositorioDeSolicitudes self = new RepositorioDeSolicitudes();
	
	public static RepositorioDeSolicitudes instance() {
		return self;
	}
	
	public RepositorioDeSolicitudes() {
		elementType = SolicitudAltaUsuario.class;
	}

	public void solicitarIncorporación(Usuario usuario) {
		add(new SolicitudAltaUsuario(usuario));
	}
	
	public void aprobar(SolicitudAltaUsuario solicitud) {
		solicitud.aceptada();
	}
	
	public void rechazar(SolicitudAltaUsuario solicitud, String motivo) {
		solicitud.rechazada(motivo);
	}
	
	@CoberturaIgnore
	public void aprobarTodas() {
		procesarTodas(s -> aprobar(s));
	}
	
	@CoberturaIgnore
	public void rechazarTodas(String motivo) {
		procesarTodas(s -> rechazar(s, motivo));
	}
	
	@CoberturaIgnore
	private void procesarTodas(Consumer<SolicitudAltaUsuario> procesador) {
		for (SolicitudAltaUsuario solicitud : list()) {
			list().remove(solicitud);	
			procesador.accept(solicitud);
		}
	}
	
}
