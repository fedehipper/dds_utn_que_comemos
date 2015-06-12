package grupo4.dds;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.gestionDePerfiles.Administrador;
import grupo4.dds.usuario.gestionDePerfiles.RepositorioDeUsuarios;

import org.junit.Before;
import org.junit.Test;

public class TestAdministrador {

	private boolean aceptada;
	private Usuario mockUsuario = new Usuario() {
		
		{
			this.nombre = "mockUsuario";
		}
		
		public void solicitudAceptada() {
			aceptada = true;
		}
		
		public void solicitudRechazada(String motivo) {
			aceptada = false;
		}

	};
	
	@Before
	public void setUp() {
		Administrador.get().limpiarSolicitudes();
		Administrador.get().solicitarIncorporación(mockUsuario);
	}
	
	@Test
	public void testAprobarSolicitud() {
		aceptada = false;
		Administrador.get().aprobar(mockUsuario);
		assertTrue(aceptada);
		assertTrue(RepositorioDeUsuarios.get().get(mockUsuario) != null);
	}

	@Test
	public void testRechazarSolicitud() {
		aceptada = true;
		Administrador.get().rechazar(mockUsuario, "por mockoso");
		assertFalse(aceptada);
	}
	
	@Test
	public void testVerSolicitudesPendientes() {
		Usuario usuario1 = Usuario.crearPerfil("usuario1");
		Usuario usuario2 = Usuario.crearPerfil("usuario2");
		Usuario usuario3 = Usuario.crearPerfil("usuario3");
		
		List<Usuario> expected = Arrays.asList(mockUsuario, usuario1, usuario2, usuario3);
		Set<Usuario> solicitudesPendientes = Administrador.get().verSolicitudesPendientes();
		
		assertTrue(solicitudesPendientes.containsAll(expected) && solicitudesPendientes.size() == 4);
	}
}
