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
		receta.getIngredientes().put("morron", 80.0);
		receta.getIngredientes().put("sal", 90.0);
		receta.getIngredientes().put("caldo", 8.0);
		receta.getIngredientes().put("carne", 90.0);
		receta.getCondimentos().put("azucar", 100.0);
		receta.setCalorias(10);	
	}

	@Test
	public void testRecetaEsValida() {
		assertTrue(receta.esValida());
	}
	
	@Test
	public void esRecetaInadecuadaEnHipertensos() {
		assertTrue(!(hipertenso.esRecomendable(receta)));
	}
	
	@Test
	public void esRecetaInadecuadaEnVeganos() {
		assertTrue(!(vegano.esRecomendable(receta)));
	}
	
	@Test
	public void esRecetaAdecuadaEnCeliacos() {
		assertTrue((celiaco.esRecomendable(receta))); 
	}
	
	@Test
	public void esRecetaAdecuadaEnDiabeticos() {
		assertTrue(diabetico.esRecomendable(receta));
	}
		
	@Test 
	public void testEsRecetaInadecuadaParaUnUsuario() {
		pedro.agregarCondicion(hipertenso);
		pedro.agregarCondicion(vegano);
		pedro.agregarCondicion(celiaco);
		pedro.agregarCondicion(diabetico);
		pedro.agregarReceta(receta);
		assertTrue(!(pedro.esRecetaAdecuada(receta)));
	}
	
	
}

