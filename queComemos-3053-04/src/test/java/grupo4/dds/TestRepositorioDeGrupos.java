package grupo4.dds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import grupo4.dds.repositorios.RepositorioDeGrupos;
import grupo4.dds.usuario.GrupoUsuarios;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestRepositorioDeGrupos extends BaseTest {
	
	private RepositorioDeGrupos repositorio = RepositorioDeGrupos.instance();
	
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
		grupo1.agregarUsuario(matiasMartino);
		
		repositorio.update(grupo1);
		
		assertTrue(repositorio.get(grupo1).esMiembro(matiasMartino));
	}

	@Test
	public void testEliminarGrupo() {
		repositorio.remove(grupo1);
		assertNull(repositorio.get(grupo1));
	}
	
}
