package grupo4.dds;

import static grupo4.dds.usuario.Rutina.ACTIVA_EJERCICIO_ADICIONAL;
import static grupo4.dds.usuario.Rutina.ACTIVA_SIN_EJERCICIO_ADICIONAL;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Condicion;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Test;

public class TestCondiciones extends BaseTest {

	private Condicion celiaco = Celiaco.instance();
	private Condicion vegano = Vegano.instance();
	private Condicion diabetico = Diabetico.instance();
	private Condicion hipertenso = Hipertenso.instance();
	
	private Ingrediente chivito = Ingrediente.nuevaComida("chivito");
	private Ingrediente carne = Ingrediente.nuevaComida("carne");
	private Ingrediente fruta = Ingrediente.nuevaComida("fruta");
	private Ingrediente melon = Ingrediente.nuevaComida("melon");
	private Ingrediente pescado = Ingrediente.nuevaComida("pescado");
	
	/* Test: @esValidoCon/1 */
	@Test
	public void testCeliacoSiempreEsCondicionValida() {
		assertTrue(celiaco.esValidaCon(null));
	}
	
	@Test
	public void testDiabeticoEsValidaSiElUsuarioIndicaSexoYAlgunaPreferenciaAlimenticia() {
		fecheSena.agregarPreferenciaAlimenticia(chivito);
		assertTrue(diabetico.esValidaCon(fecheSena));
	}
	
	@Test 
	public void testEsCarne() {
		assertTrue(carne.esCarne());
	}
	
	@Test
	public void noEsCarne() {
		assertFalse(fruta.esCarne());
	}
	
	@Test
	public void testHipertensoEsValidaSiElUsuarioIndicaAlgunaPreferenciaAlimenticia() {
		fecheSena.agregarPreferenciaAlimenticia(carne);
		assertTrue(hipertenso.esValidaCon(fecheSena));
	}
	
	@Test
	public void testVeganoEsValidaSiElUsuarioNoTieneCarnesEnSusPreferenciasAlimenticias() {
		
		arielFolino.agregarPreferenciaAlimenticia(fruta);
		arielFolino.agregarPreferenciaAlimenticia(melon);
		arielFolino.agregarPreferenciaAlimenticia(pescado);
		assertTrue(vegano.esValidaCon(arielFolino));

		arielFolino.agregarPreferenciaAlimenticia(chivito);
		assertFalse(vegano.esValidaCon(arielFolino));
	}

	/* Test: @subsanaCondicion/1 */
	@Test
	public void testCeliacoSiempreSubsanaCondicion() {
		assertTrue(celiaco.subsanaCondicion(null));
	}

	@Test
	public void testVeganoSubsanaCondicionSiAlUsuarioLeGustanLasFrutas() {
		matiasMartino.agregarPreferenciaAlimenticia(chivito);
		assertFalse(vegano.subsanaCondicion(matiasMartino));
	
		matiasMartino.agregarPreferenciaAlimenticia(fruta);
		assertTrue(vegano.subsanaCondicion(matiasMartino));
	}

	@Test
	public void testHipertensoSubsanaCondicionSiElUsuarioTieneRuinaActivaIntensaConEjercicioAdicional() {
		matiasMartino.setRutina(ACTIVA_EJERCICIO_ADICIONAL);
		assertTrue(hipertenso.subsanaCondicion(matiasMartino));
	}

	@Test
	public void testDiabeticoSubsanaCondicionSiElUsuarioTieneRutinaActivaONoPesaMasDe70() {
		assertTrue(diabetico.subsanaCondicion(fecheSena));
		assertTrue(diabetico.subsanaCondicion(fecheSena.setRutina(ACTIVA_EJERCICIO_ADICIONAL)));
		assertTrue(diabetico.subsanaCondicion(fecheSena.setRutina(ACTIVA_SIN_EJERCICIO_ADICIONAL)));
		assertTrue(diabetico.subsanaCondicion(federicoHipper.setRutina(ACTIVA_EJERCICIO_ADICIONAL)));
		assertTrue(diabetico.subsanaCondicion(fecheSena.setRutina(ACTIVA_SIN_EJERCICIO_ADICIONAL)));
	}
	
	@Test
	public void testDiabeticoNoSubsanaCondicionSiElUsuarioNoTieneRutinaActivaYPesaMasDe70() {
		assertFalse(diabetico.subsanaCondicion(federicoHipper));
	}
	
	/* Test: @esRecomendable/1 */
	@Test
	public void testCeliacoSiempreEsRecomendable() {
		assertTrue(celiaco.esRecomendable(null));
	}
	
	@Test
	public void testHipertensoEsRecomendableSiLaRecetaNoContieneSalNiCaldo() {
		Ingrediente pimienta = Ingrediente.nuevoCondimento("pimienta", 0f);
		Ingrediente oregano = Ingrediente.nuevoCondimento("oregano", 0f);
		
		milanesa.agregarCondimento(pimienta);
		milanesa.agregarCondimento(oregano);
		
		assertTrue(hipertenso.esRecomendable(milanesa));
	}
	
	@Test
	public void testHipertensoNoEsRecomendableSiLaRecetaContieneSaloCaldo() {
		Ingrediente sal = Ingrediente.nuevoCondimento("sal", 0f);
		milanesa.agregarCondimento(sal);
		assertFalse(hipertenso.esRecomendable(milanesa));
		
		Ingrediente caldo = Ingrediente.nuevoCondimento("caldo", 0f);
		pollo.agregarCondimento(caldo);
		assertFalse(hipertenso.esRecomendable(pollo));
	}
	
	@Test
	public void testVeganoEsRecomendableSiLaRecetaNoTieneCarne() {

		milanesa.agregarIngrediente(fruta);
		milanesa.agregarIngrediente(melon);
		milanesa.agregarIngrediente(pescado);
		assertTrue(vegano.esRecomendable(milanesa));
		
		milanesa.agregarIngrediente(chivito);
		assertFalse(vegano.esRecomendable(milanesa));
	}
	
	@Test
	public void testDiabeticoEsRecomendableSiLaRecetaNoTieneMasDe100DeAzucar() {
		Ingrediente azucar1 = Ingrediente.nuevoCondimento("azucar", 100.1f);
		
		milanesa.agregarCondimento(azucar1);
		assertFalse(diabetico.esRecomendable(milanesa));
		
		Ingrediente azucar2 = Ingrediente.nuevoCondimento("azucar", 99.9f);
		
		pollo.agregarCondimento(azucar2);
		assertTrue(diabetico.esRecomendable(pollo));
	}
}
