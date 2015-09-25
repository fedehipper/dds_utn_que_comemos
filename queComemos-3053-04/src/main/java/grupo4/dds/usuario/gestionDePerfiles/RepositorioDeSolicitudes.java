package grupo4.dds.usuario.gestionDePerfiles;

import grupo4.dds.misc.CoberturaIgnore;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.function.Consumer;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioDeSolicitudes implements WithGlobalEntityManager {

	private static final RepositorioDeSolicitudes self = new RepositorioDeSolicitudes();
	
	public static RepositorioDeSolicitudes get() {
		return self;
	}
	
	public void solicitarIncorporación(Usuario usuario) {
		SolicitudAltaUsuario solicitud = new SolicitudAltaUsuario(usuario);
		
		entityManager().getTransaction().begin();
		entityManager().persist(solicitud);
		entityManager().refresh(solicitud);
		entityManager().getTransaction().commit();
	}
	
	public void aprobar(SolicitudAltaUsuario solicitud) {
		RepositorioDeUsuarios.get().add(solicitud.getUsuario());
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
		for (SolicitudAltaUsuario solicitud : solicitudesPendientes()) {
			solicitudesPendientes().remove(solicitud);	
			procesador.accept(solicitud);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SolicitudAltaUsuario> solicitudesPendientes() {
		return (List<SolicitudAltaUsuario>) entityManager().createQuery("from SolicitudAltaUsuario").getResultList();
	}

}
