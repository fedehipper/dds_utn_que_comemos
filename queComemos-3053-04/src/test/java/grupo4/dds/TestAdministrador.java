package grupo4.dds;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.gestionDePerfiles.RepositorioDeSolicitudes;
import grupo4.dds.usuario.gestionDePerfiles.RepositorioDeUsuarios;
import grupo4.dds.usuario.gestionDePerfiles.SolicitudAltaUsuario;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class TestAdministrador implements WithGlobalEntityManager {

	private Usuario usuario;
	
	@Before
	public void setUp() {
		entityManager().getTransaction().begin();
		usuario = Usuario.crearPerfil("USUARIO");
	}
	
	@After
	public void tierDown() {
		entityManager().getTransaction().rollback();
		RepositorioDeUsuarios.get().vaciar();
	}
	
	@Test
	public void testAprobarSolicitud() {
		SolicitudAltaUsuario solicitud = new SolicitudAltaUsuario(usuario);
		
		RepositorioDeSolicitudes.get().aprobar(solicitud);
		assertTrue(solicitud.estado());
		assertNotNull(RepositorioDeUsuarios.get().get(usuario));
	}

	@Test
	public void testRechazarSolicitud() {
		SolicitudAltaUsuario solicitud = new SolicitudAltaUsuario(usuario);
		
		RepositorioDeSolicitudes.get().rechazar(solicitud, "por mockoso");
		assertFalse(solicitud.estado());
		assertNull(RepositorioDeUsuarios.get().get(usuario));
	}
	
	@Test
	public void testVerSolicitudesPendientes() {
		Usuario usuario1 = Usuario.crearPerfil("usuario1");
		Usuario usuario2 = Usuario.crearPerfil("usuario2");
		Usuario usuario3 = Usuario.crearPerfil("usuario3");
		
		List<Usuario> expected = Arrays.asList(usuario, usuario1, usuario2, usuario3);
		List<Usuario> solicitudesPendientes = 
				RepositorioDeSolicitudes.get().solicitudesPendientes().stream().
				map(s -> s.getUsuario()).collect(Collectors.toList());
		assertTrue(solicitudesPendientes.containsAll(expected) && solicitudesPendientes.size() == 4);
	}
}
