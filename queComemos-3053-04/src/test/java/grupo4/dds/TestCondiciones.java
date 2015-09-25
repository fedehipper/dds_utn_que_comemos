package grupo4.dds;

import static grupo4.dds.usuario.Rutina.*;
import static grupo4.dds.usuario.Sexo.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import grupo4.dds.receta.Ingrediente;
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
	@Test
	public void testCeliacoSiempreEsCondicionValida() {
		assertTrue(celiaco.esValidaCon(null));
	}
	
	@Test
	public void testDiabeticoEsValidaSiElUsuarioIndicaSexoYAlgunaPreferenciaAlimenticia() {
		usuario = Usuario.crearPerfil(null, MASCULINO, null, 0, 0, null, false, null);
		
		Ingrediente chivito = Ingrediente.nuevoIngrediente("chivito", 0f);

		usuario.agregarPreferenciaAlimenticia(chivito);
		assertTrue(diabetico.esValidaCon(usuario));
	}
	
	@Test 
	public void testEsCarne() {
		Ingrediente carne = Ingrediente.nuevoIngrediente("carne", 0f);
		assertTrue(carne.esCarne());
	}
	
	@Test
	public void noEsCarne() {
		Ingrediente fruta = Ingrediente.nuevoIngrediente("fruta", 0f);
		assertFalse(fruta.esCarne());
	}
	
	@Test
	public void testHipertensoEsValidaSiElUsuarioIndicaAlgunaPreferenciaAlimenticia() {
		usuario = Usuario.crearPerfil(null);
		
		Ingrediente carne = Ingrediente.nuevoIngrediente("carne", 0f);
		usuario.agregarPreferenciaAlimenticia(carne);
		assertTrue(hipertenso.esValidaCon(usuario));
	}
	
	@Test
	public void testVeganoEsValidaSiElUsuarioNoTieneCarnesEnSusPreferenciasAlimenticias() {
		usuario = Usuario.crearPerfil(null);
		
		Ingrediente fruta = Ingrediente.nuevoIngrediente("fruta", 0f);
		Ingrediente melon = Ingrediente.nuevoIngrediente("melon", 0f);
		Ingrediente pescado = Ingrediente.nuevoIngrediente("pescado", 0f);
		
		usuario.agregarPreferenciaAlimenticia(fruta);
		usuario.agregarPreferenciaAlimenticia(melon);
		usuario.agregarPreferenciaAlimenticia(pescado);
		assertTrue(vegano.esValidaCon(usuario));
		
		Ingrediente chivito = Ingrediente.nuevoIngrediente("chivito", 0f);

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
		usuario = Usuario.crearPerfil(null);
		
		Ingrediente mondongo = Ingrediente.nuevoIngrediente("mondongo", 0f);		
		usuario.agregarPreferenciaAlimenticia(mondongo);
		
		assertFalse(vegano.subsanaCondicion(usuario));
	
		Ingrediente fruta = Ingrediente.nuevoIngrediente("fruta", 0f);
		usuario.agregarPreferenciaAlimenticia(fruta);
		
		assertTrue(vegano.subsanaCondicion(usuario));
	}

	@Test
	public void testHipertensoSubsanaCondicionSiElUsuarioTieneRuinaActivaIntensaConEjercicioAdicional() {
		usuario = Usuario.crearPerfil(null, null, null, 0, 0, ACTIVA_EJERCICIO_ADICIONAL, false, null);
		assertTrue(hipertenso.subsanaCondicion(usuario));
	}

	@Test
	public void testDiabeticoSubsanaCondicionSiElUsuarioTieneRutinaActivaONoPesaMasDe70() {
		assertTrue(diabetico.subsanaCondicion(Usuario.crearPerfil(null, null, null, 0, 69.9f, null, false, null)));
		assertTrue(diabetico.subsanaCondicion(Usuario.crearPerfil(null, null, null, 0, 0, ACTIVA_EJERCICIO_ADICIONAL, false, null)));
		assertTrue(diabetico.subsanaCondicion(Usuario.crearPerfil(null, null, null, 0, 0, ACTIVA_SIN_EJERCICIO_ADICIONAL, false, null)));
		assertTrue(diabetico.subsanaCondicion(Usuario.crearPerfil(null, null, null, 0, 71, ACTIVA_EJERCICIO_ADICIONAL, false, null)));
		assertTrue(diabetico.subsanaCondicion(Usuario.crearPerfil(null, null, null, 0, 71, ACTIVA_SIN_EJERCICIO_ADICIONAL, false, null)));
	}
	
	@Test
	public void testDiabeticoNoSubsanaCondicionSiElUsuarioNoTieneRutinaActivaYPesaMasDe70() {
		assertFalse(diabetico.subsanaCondicion(Usuario.crearPerfil(null, null, null, 0, 71, null, false, null)));
	}
	
	/* Test: @esRecomendable/1 */
	@Test
	public void testCeliacoSiempreEsRecomendable() {
		assertTrue(celiaco.esRecomendable(null));
	}
	
	@Test
	public void testHipertensoEsRecomendableSiLaRecetaNoContieneSalNiCaldo() {
		receta = Receta.crearNueva();
		
		Ingrediente pimienta = Ingrediente.nuevoIngrediente("pimienta", 0f);
		Ingrediente oregano = Ingrediente.nuevoIngrediente("oregano", 0f);
		
		receta.agregarCondimento(pimienta);
		receta.agregarCondimento(oregano);
		assertTrue(hipertenso.esRecomendable(receta));
	}
	
	@Test
	public void testHipertensoNoEsRecomendableSiLaRecetaContieneSaloCaldo() {
		receta = Receta.crearNueva();
		Ingrediente sal = Ingrediente.nuevoIngrediente("sal", 0f);
		receta.agregarCondimento(sal);
		assertFalse(hipertenso.esRecomendable(receta));
		
		receta = Receta.crearNueva();
		Ingrediente caldo = Ingrediente.nuevoIngrediente("caldo", 0f);
		receta.agregarCondimento(caldo);
		assertFalse(hipertenso.esRecomendable(receta));
	}
	
	@Test
	public void testVeganoEsRecomendableSiLaRecetaNoTieneCarne() {
		receta = Receta.crearNueva();
		
		Ingrediente fruta = Ingrediente.nuevoIngrediente("fruta", 0f);
		Ingrediente melon = Ingrediente.nuevoIngrediente("melon", 0f);
		Ingrediente pescado = Ingrediente.nuevoIngrediente("pescado", 0f);
		
		
		receta.agregarIngrediente(fruta);
		receta.agregarIngrediente(melon);
		receta.agregarIngrediente(pescado);

		assertTrue(vegano.esRecomendable(receta));
		
		Ingrediente chivito = Ingrediente.nuevoIngrediente("chivito", 0f);
		
		receta.agregarIngrediente(chivito);
		assertFalse(vegano.esRecomendable(receta));
	}
	
	@Test
	public void testDiabeticoEsRecomendableSiLaRecetaNoTieneMasDe100DeAzucar() {
		receta = Receta.crearNueva();
		Ingrediente azucar1 = Ingrediente.nuevoIngrediente("azucar", 100.1f);
		
		receta.agregarCondimento(azucar1);
		assertFalse(diabetico.esRecomendable(receta));
		
		receta = Receta.crearNueva();
		Ingrediente azucar2 = Ingrediente.nuevoIngrediente("azucar", 99.9f);
		
		receta.agregarCondimento(azucar2);
		assertTrue(diabetico.esRecomendable(receta));
	}
}
