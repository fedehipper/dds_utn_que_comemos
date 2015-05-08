package grupo4.dds;

import static grupo4.dds.usuario.Alimento.*;
import static grupo4.dds.usuario.Rutina.*;
import static grupo4.dds.usuario.Sexo.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.*;

import org.junit.Test;

public class TestCondiciones {

	private Usuario usuario;
	private Receta receta;
	private Condicion celiaco = new Celiaco();
	private Condicion vegano = new Vegano();
	private Condicion diabetico = new Diabetico();
	private Condicion hipertenso = new Hipertenso();
	
	/* Test: @esValidoCon/1 */
	
	public void testCeliacoSiempreEsCondicionValida() {
		assertTrue(celiaco.esValidaCon(null));
	}
	
	public void testDiabeticoEsValidaSiElUsuarioIndicaSexoYAlgunaPreferenciaAlimenticia() {
		usuario = new Usuario(null, MASCULINO, null, 0, 0, null);
		
		usuario.agregarPreferenciaAlimenticia(CHIVITO);
		assertTrue(diabetico.esValidaCon(usuario));
	}
	
	public void testHipertensoEsValidaSiElUsuarioIndicaAlgunaPreferenciaAlimenticia() {
		usuario = new Usuario();
		
		usuario.agregarPreferenciaAlimenticia(CARNE);
		assertTrue(hipertenso.esValidaCon(usuario));
	}
	
	public void testVeganoEsValidaSiElUsuarioNoTieneCarnesEnSusPreferenciasAlimenticias() {
		usuario = new Usuario();
		
		usuario.agregarPreferenciaAlimenticia(FRUTAS);
		usuario.agregarPreferenciaAlimenticia(MELON);
		usuario.agregarPreferenciaAlimenticia(PESCADO);
		assertTrue(vegano.esValidaCon(usuario));
		
		usuario.agregarPreferenciaAlimenticia(CHIVITO);
		assertFalse(vegano.esValidaCon(usuario));
	}

	/* Test: @subsanaCondicion/1 */
	
	@Test
	public void testCeliacoSiempreSubsanaCondicion() {
		assertTrue(celiaco.subsanaCondicion(null));
	}

	@Test
	public void testVeganoSubsanaCondicionSiAlUsuarioLeGustanLasFrutas() {
		usuario = new Usuario();
		
		usuario.agregarPreferenciaAlimenticia(MONDONGO);
		assertFalse(vegano.subsanaCondicion(usuario));
		
		usuario.agregarPreferenciaAlimenticia(FRUTAS);
		assertTrue(vegano.subsanaCondicion(usuario));
	}

	@Test
	public void testHipertensoSubsanaCondicionSiElUsuarioTieneRuinaActivaIntensaConEjercicioAdicional() {
		usuario = new Usuario(null, null, 0, 0, ACTIVA_EJERCICIO_ADICIONAL);
		assertTrue(hipertenso.subsanaCondicion(usuario));
	}

	@Test
	public void testDiabeticoSubsanaCondicionSiElUsuarioTieneRutinaActivaONoPesaMasDe70() {
		assertTrue(diabetico.subsanaCondicion(new Usuario(null, null, 0, 69.9f, null)));
		assertTrue(diabetico.subsanaCondicion(new Usuario(null, null, 0, 0, ACTIVA_EJERCICIO_ADICIONAL)));
		assertTrue(diabetico.subsanaCondicion(new Usuario(null, null, 0, 0, ACTIVA_SIN_EJERCICIO_ADICIONAL)));
	}
	
	/* Test: @esRecomendable/1 */
	
	@Test
	public void testCeliacoSiempreEsRecomendable() {
		assertTrue(celiaco.esRecomendable(null));
	}
	
	@Test
	public void testHipertensoEsRecomendableSiLaRecetaNoContieneSalNiCaldo() {
		receta = new Receta();
		
		receta.agregarCondimento("pimienta", 0f);
		receta.agregarCondimento("oregano", 0f);
		assertTrue(hipertenso.esRecomendable(receta));
	}
	
	public void testHipertensoNoEsRecomendableSiLaRecetaContieneSaloCaldo() {
		receta = new Receta();
		receta.agregarCondimento("sal", 0f);
		assertFalse(hipertenso.esRecomendable(receta));
		
		receta = new Receta();
		receta.agregarCondimento("caldo", 0f);
		assertFalse(hipertenso.esRecomendable(receta));
	}
	
	@Test
	public void testVeganoEsRecomendableSiLaRecetaNoTieneCarne() {
		receta = new Receta();
		
		receta.agregarIngrediente("frutas", 0f);
		receta.agregarIngrediente("melon", 0f);
		receta.agregarIngrediente("pescado", 0f);

		assertTrue(vegano.esRecomendable(receta));
		
		receta.agregarIngrediente("chivito", 0f);
		assertFalse(vegano.esRecomendable(receta));
	}
	
	@Test
	public void testDiabeticoEsRecomendableSiLaRecetaNoTieneMasDe100DeAzucar() {
		receta = new Receta();
		receta.agregarCondimento("azucar", 100.1f);
		assertFalse(diabetico.esRecomendable(receta));
		
		receta = new Receta();
		receta.agregarCondimento("azucar", 99.9f);
		assertTrue(diabetico.esRecomendable(receta));
	}
}
