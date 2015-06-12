package grupo4.dds;

import static org.junit.Assert.*;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioRecetasExterno;

import org.junit.Before;
import org.junit.Test;

public class TestRepositorioRecetasExterno {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetRecetasDevuelveUnaListaDeRecetasPrivadas() {
		assertTrue(RepositorioRecetasExterno.get().getRecetas().stream().allMatch(r -> r.getClass().equals(Receta.class)));
	}

}
