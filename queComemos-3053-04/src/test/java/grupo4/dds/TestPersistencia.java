package grupo4.dds;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import grupo4.dds.usuario.GrupoUsuarios;

import org.junit.Test;

public class TestPersistencia extends BaseTest {

	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void testAlCrearUnUsuarioEsPersistido() {
		
		assertTrue(entityManager().contains(arielFolino));
		assertTrue(entityManager().contains(maria));
	}
	
	@Test
	public void testAlCrearUnaRecetaEsPersistida() {
		
		assertTrue(entityManager().contains(pollo));
		assertTrue(entityManager().contains(milanesa));
	}

	@Test
	public void testAlCrearUnGrupoEsPersistido() {
		assertTrue(entityManager().contains(GrupoUsuarios.crearGrupo("")));
	}
	
}