package grupo4.dds;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import grupo4.dds.repositorios.RepositorioDeSolicitudes;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.gestionDePerfiles.SolicitudAltaUsuario;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class TestAdministrador extends BaseTest {

	@Test
	public void testAprobarSolicitud() {
		SolicitudAltaUsuario solicitud = new SolicitudAltaUsuario(fecheSena);
		
		RepositorioDeSolicitudes.instance().aprobar(solicitud);
		assertTrue(solicitud.estado());
		assertNotNull(RepositorioDeUsuarios.instance().get(fecheSena));
	}

	@Test
	public void testRechazarSolicitud() {
		SolicitudAltaUsuario solicitud = new SolicitudAltaUsuario(arielFolino);
		
		RepositorioDeSolicitudes.instance().rechazar(solicitud, "por mockoso");
		assertFalse(solicitud.estado());
	}
	
	@Test
	public void testVerSolicitudesPendientes() {
		List<Usuario> expected = Arrays.asList(maria, fecheSena, arielFolino, matiasMartino, federicoHipper, cristianMaldonado);
		List<Usuario> solicitudesPendientes = 
				RepositorioDeSolicitudes.instance().list().stream().
				map(s -> s.getUsuario()).collect(Collectors.toList());
		
		assertTrue(solicitudesPendientes.containsAll(expected) && solicitudesPendientes.size() == expected.size());
	}
}
