package grupo4.dds;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestReceta {
	
	private Receta receta = new Receta();
	

	@Before
	public void setUp() {
		receta.getIngredientes().add("morron");
		receta.setCalorias(10);
	}

	@Test
	public void testRecetaEsValida() {
		assertTrue(receta.esValida());
	}

}
