package grupo4.dds;

import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioRecetasExterno;

import org.junit.Test;

public class TestRepositorioRecetasExterno {

	@Test
	public void testGetRecetasDevuelveUnaListaDeRecetasPrivadas() {
		assertTrue(RepositorioRecetasExterno.get().getRecetas().stream().allMatch(r -> r.getClass().equals(Receta.class)));
	}

}
