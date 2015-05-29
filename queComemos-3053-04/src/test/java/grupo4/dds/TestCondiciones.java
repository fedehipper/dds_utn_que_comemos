package grupo4.dds;

import static grupo4.dds.usuario.Rutina.*;
import static grupo4.dds.usuario.Sexo.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Ingrediente;
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
	@Test
	public void testCeliacoSiempreEsCondicionValida() {
		assertTrue(celiaco.esValidaCon(null));
	}
	
	@Test
	public void testDiabeticoEsValidaSiElUsuarioIndicaSexoYAlgunaPreferenciaAlimenticia() {
		usuario = new Usuario(null, MASCULINO, null, 0, 0, null);
		
		Ingrediente chivito = new Ingrediente("chivito", 0f);

		usuario.agregarPreferenciaAlimenticia(chivito);
		assertTrue(diabetico.esValidaCon(usuario));
	}
	
	@Test 
	public void testEsCarne() {
		Ingrediente carne = new Ingrediente("carne", 0f);
		assertTrue(carne.esCarne());
	}
	
	@Test
	public void noEsCarne() {
		Ingrediente fruta = new Ingrediente("fruta", 0f);
		assertFalse(fruta.esCarne());
	}
	
	@Test
	public void testHipertensoEsValidaSiElUsuarioIndicaAlgunaPreferenciaAlimenticia() {
		usuario = new Usuario();
		
		Ingrediente carne = new Ingrediente("carne", 0f);
		usuario.agregarPreferenciaAlimenticia(carne);
		assertTrue(hipertenso.esValidaCon(usuario));
	}
	
	@Test
	public void testVeganoEsValidaSiElUsuarioNoTieneCarnesEnSusPreferenciasAlimenticias() {
		usuario = new Usuario();
		
		Ingrediente fruta = new Ingrediente("fruta", 0f);
		Ingrediente melon = new Ingrediente("melon", 0f);
		Ingrediente pescado = new Ingrediente("pescado", 0f);
		
		usuario.agregarPreferenciaAlimenticia(fruta);
		usuario.agregarPreferenciaAlimenticia(melon);
		usuario.agregarPreferenciaAlimenticia(pescado);
		assertTrue(vegano.esValidaCon(usuario));
		
		Ingrediente chivito = new Ingrediente("chivito", 0f);

		usuario.agregarPreferenciaAlimenticia(chivito);
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
		
		Ingrediente mondongo = new Ingrediente("mondongo", 0f);		
		usuario.agregarPreferenciaAlimenticia(mondongo);
		
		assertFalse(vegano.subsanaCondicion(usuario));
	
		Ingrediente fruta = new Ingrediente("fruta", 0f);
		usuario.agregarPreferenciaAlimenticia(fruta);
		
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
		
		Ingrediente pimienta = new Ingrediente("pimienta", 0f);
		Ingrediente oregano = new Ingrediente("oregano", 0f);
		
		receta.agregarCondimento(pimienta);
		receta.agregarCondimento(oregano);
		assertTrue(hipertenso.esRecomendable(receta));
	}
	
	@Test
	public void testHipertensoNoEsRecomendableSiLaRecetaContieneSaloCaldo() {
		receta = new Receta();
		Ingrediente sal = new Ingrediente("sal", 0f);
		receta.agregarCondimento(sal);
		assertFalse(hipertenso.esRecomendable(receta));
		
		receta = new Receta();
		Ingrediente caldo = new Ingrediente("caldo", 0f);
		receta.agregarCondimento(caldo);
		assertFalse(hipertenso.esRecomendable(receta));
	}
	
	@Test
	public void testVeganoEsRecomendableSiLaRecetaNoTieneCarne() {
		receta = new Receta();
		
		Ingrediente fruta = new Ingrediente("fruta", 0f);
		Ingrediente melon = new Ingrediente("melon", 0f);
		Ingrediente pescado = new Ingrediente("pescado", 0f);
		
		
		receta.agregarIngrediente(fruta);
		receta.agregarIngrediente(melon);
		receta.agregarIngrediente(pescado);

		assertTrue(vegano.esRecomendable(receta));
		
		Ingrediente chivito = new Ingrediente("chivito", 0f);
		
		receta.agregarIngrediente(chivito);
		assertFalse(vegano.esRecomendable(receta));
	}
	
	@Test
	public void testDiabeticoEsRecomendableSiLaRecetaNoTieneMasDe100DeAzucar() {
		receta = new Receta();
		Ingrediente azucar1 = new Ingrediente("azucar", 100.1f);
		
		receta.agregarCondimento(azucar1);
		assertFalse(diabetico.esRecomendable(receta));
		
		receta = new Receta();
		Ingrediente azucar2 = new Ingrediente("azucar", 99.9f);
		
		receta.agregarCondimento(azucar2);
		assertTrue(diabetico.esRecomendable(receta));
	}
}
