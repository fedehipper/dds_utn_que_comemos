package grupo4.dds;

import static org.junit.Assert.*;
import grupo4.dds.repositorios.RepositorioDeGrupos;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestRepositorioDeGrupos extends BaseTest {
	
	private GrupoUsuarios grupo1;
	private GrupoUsuarios grupo2;
	private RepositorioDeGrupos repositorio = RepositorioDeGrupos.get();
	
	@Before
	public void setUp() {
		
		grupo1 = GrupoUsuarios.crearGrupo("grupo1");
		grupo2 = GrupoUsuarios.crearGrupo("grupo2");
	}
	
	@Test
	public void testGetGrupo() {
		assertEquals(grupo1.getId(), repositorio.get(grupo1).getId());
	}

	@Test
	public void testListarGrupos() {
		List<GrupoUsuarios> expected = Arrays.asList(grupo1, grupo2);
		assertEqualsList(expected, repositorio.list());
	}
	
	@Test
	public void testActualizarGrupo() {
		entityManager().clear();
		
		Usuario usuario = Usuario.crearPerfil("");
		grupo1.agregarUsuario(usuario);
		
		repositorio.update(grupo1);
		
		assertTrue(repositorio.get(grupo1).esMiembro(usuario));
	}

	@Test
	public void testEliminarGrupo() {
		repositorio.remove(grupo1);
		assertNull(repositorio.get(grupo1));
	}
	
	private void assertEqualsList(Collection<GrupoUsuarios> l1, Collection<GrupoUsuarios> l2) {
		assertTrue(l1.size() == l2.size() && l1.containsAll(l2));
	}
	
}
