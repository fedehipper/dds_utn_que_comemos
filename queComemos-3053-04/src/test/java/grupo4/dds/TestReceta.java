package grupo4.dds;

import static grupo4.dds.Rutina.ACTIVA_EJERCICIO_ADICIONAL;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class TestReceta {
	
	private Receta receta = new Receta();
	private Usuario pedro = new Usuario("pedro", 'M',
			LocalDate.of(2015, 04, 23), 1.50, 90.0, ACTIVA_EJERCICIO_ADICIONAL);
	private Celiaco celiaco = new Celiaco();
	private Vegano vegano = new Vegano();
	private Diabetico diabetico = new Diabetico();
	private Hipertenso hipertenso = new Hipertenso();

	
	

	@Before
	public void setUp() {
		receta.getIngredientes().add("morron");
		receta.getIngredientes().add("sal");
		receta.getIngredientes().add("caldo");
		receta.setCalorias(10);
	
	}

	@Test
	public void testRecetaEsValida() {
		assertTrue(receta.esValida());
	}
	
	@Test
	public void esRecetaInhadecuadaEnHipertensos() {
		Hipertenso hipertenso = new Hipertenso();
		assertTrue(hipertenso.noEsRecomendable(receta));
	}
	
		
	@Test 
	public void testEsRecetaInhadecuadaParaUnUsuario() {
		pedro.agregarCondicion(hipertenso);
		pedro.agregarReceta(receta);
		assertTrue(pedro.esRecetaInhadecuada(receta));
	}
	
	
}

