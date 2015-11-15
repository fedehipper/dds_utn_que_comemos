package grupo4.dds;


import static org.junit.Assert.*;
import grupo4.dds.controller.RecetaController;
import grupo4.dds.repositorios.RepositorioDeRecetas;

import org.junit.Test;

public class TestControladorReceta extends BaseTest {
	
	
	@Test
	public void testUnaRecetaEsMostrada(){
		assertEquals(RepositorioDeRecetas.instance().buscar(1), new RecetaController().recetaMostrada(1));
	}
	

}
