package grupo4.dds;

import static grupo4.dds.usuario.Alimento.*;
import static grupo4.dds.usuario.Rutina.*;
import static grupo4.dds.usuario.Sexo.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.NoSePuedeAgregarLaReceta;
import grupo4.dds.receta.NoSePuedeModificarLaReceta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.*;

import java.time.LocalDate;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestUsuario {

	private Usuario usuario;
	private Receta receta;
	private Usuario fecheSena;
	private Usuario arielFolino;
	private Usuario matiasMartino;
	private Usuario federicoHipper;
	private Usuario cristianMaldonado;
	
	@Rule public ExpectedException expectedExcetption = ExpectedException.none();
	
	@Before
	public void setUp() {
		fecheSena = new Usuario("Feche Sena", null, 1.70f, 65.0f, null);
		arielFolino = new Usuario("Ariel Folino", null, 1.69f, 96.0f, null);
		matiasMartino = new Usuario("Matías Martino", null, 1.74f, 79.0f, null);
	}
	
	/* Test: @indiceDeMasaCorporal/0 */

	@Test
	public void testIMCConPeso65YAltura170() {
		assertEquals(fecheSena.indiceDeMasaCorporal(), 22.491, 0.001);
	}

	@Test
	public void testIMCConPeso102YAltura191() {
		federicoHipper = new Usuario("Federico Hipper", null, 1.91f, 102.0f, null);
		assertEquals(federicoHipper.indiceDeMasaCorporal(), 27.959, 0.001);
	}

	@Test
	public void testIMCConPeso96YAltura169() {
		assertEquals(arielFolino.indiceDeMasaCorporal(), 33.612, 0.001);
	}

	@Test
	public void testIMCConPeso87YAltura181() {
		cristianMaldonado = new Usuario("Cristian Maldonado", null, 1.81f, 87.0f, null);
		assertEquals(cristianMaldonado.indiceDeMasaCorporal(), 26.555, 0.001);
	}

	@Test
	public void testIMCConPeso79YAltura174() {
		assertEquals(matiasMartino.indiceDeMasaCorporal(), 26.093, 0.001);
	}
	
	/* Test: @esValido/0 */

	@Test
	public void testNoEsValidoUnUsuarioConNombreMenorA4Caracteres() {
		usuario = new Usuario("Ari", LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL);
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testNoEsValidoUnUsuarioConLaFechaActualComoDiaDeNacimiento() {
		usuario = new Usuario("Ariel", LocalDate.now(), 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL);
		assertFalse(usuario.esValido());
	}
	
	public void testNoEsValidoUnUsuarioSinCamposObligatorios() {
		usuario = new Usuario("Ariel", null, 0, 0, null);
		assertFalse(usuario.esValido());
		
		usuario = new Usuario(null, LocalDate.MIN, 0, 0, null);
		assertFalse(usuario.esValido());

		usuario = new Usuario(null, null, 1.7f, 0, null);
		assertFalse(usuario.esValido());
		
		usuario = new Usuario(null, null, 0, 75, null);
		assertFalse(usuario.esValido());
		
		usuario = new Usuario(null, null, 0, 0, ACTIVA_EJERCICIO_ADICIONAL);
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testEsValidoUnUsuarioSinCondiciones() {
		usuario = new Usuario("Ariel", LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL);
		assertTrue(usuario.esValido());
	}

	@Test
	public void testEsValidoUnUsuarioConCondicionesValidas() {
		usuario = new Usuario("Ariel", MASCULINO, LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Diabetico());
		usuario.agregarPreferenciaAlimenticia(CHIVITO);
		
		assertTrue(usuario.esValido());
	}
	
	@Test
	public void testNoEsValidoUnUsuarioConAlgunaCondicionInvalida() {
		usuario = new Usuario("Ariel", LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
		usuario.agregarPreferenciaAlimenticia(CHIVITO);
		
		assertFalse(usuario.esValido());
	}

	/* Test: @sigueRutinaSaludable/0 */
	
	@Test
	public void testSigueRutinaSaludableUnUsuarioSinCondicionesConIMCEntre18Y30() {
		usuario = new Usuario("Ariel", LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL);
		assertTrue(usuario.esValido());
	}
	
	@Test
	public void testNoSigueRutinaSaludableUnUsuarioConCondicionesSinSubsanar() {
		usuario = new Usuario("Ariel", LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
		
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testSigueRutinaSaludableUnUsuarioConCondicionesSubsanadas() {
		usuario = new Usuario("Ariel", LocalDate.MIN, 1.7f, 70, SEDENTARIA_CON_EJERCICIO);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
		usuario.agregarPreferenciaAlimenticia(FRUTAS);
		
		assertTrue(usuario.esValido());
	}
	
	/* Test: @leGusta/1 */
	
	@Test
	public void testLeGustaLaCarneAUnUsuario() {
		usuario = new Usuario();
				
		usuario.agregarPreferenciaAlimenticia(CARNE);
		assertTrue(usuario.leGusta(CARNE));
	}
	
	@Test
	public void testNoLeGustanLasFrutasAUnUsuario() {
		usuario = new Usuario();
				
		usuario.agregarPreferenciaAlimenticia(CARNE);
		assertFalse(usuario.leGusta(FRUTAS));
	}
	
	/* Test: @esAdecuada/1 */
	
	@Test
	public void testNoEsAdecuadaUnaRecetaParaUnUsuarioSiEsInvalida() {
		usuario = new Usuario();
		receta = new Receta();
		
		assertFalse(usuario.esAdecuada(receta));
	}
	
	@Test
	public void testEsAdecuadaUnaRecetaParaUnUsuarioSinCondiciones() {
		usuario = new Usuario();
		
		receta = new Receta();
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);	
		
		assertTrue(usuario.esAdecuada(receta));
	}
	
	@Test
	public void testEsAdecuadaUnaRecetaParaUnUsuarioSiEsRecomendableParaTodasSusCondiciones() {
		usuario = new Usuario();
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
		
		receta = new Receta();
		receta.setTotalCalorias(4500);
		receta.agregarCondimento("azucar", 99.9f);		
		receta.agregarIngrediente("frutas", 0f);
		receta.agregarIngrediente("melon", 0f);
		receta.agregarIngrediente("pescado", 0f);
		
		assertTrue(usuario.esAdecuada(receta));
	}
	
	@Test
	public void testNoEsAdecuadaUnaRecetaParaUnUsuarioSiNoEsRecomendableParaAlgunaDeSusCondiciones() {
		usuario = new Usuario();
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
		
		receta = new Receta();
		receta.setTotalCalorias(4500);
		receta.agregarCondimento("azucar", 99.9f);		
		receta.agregarIngrediente("frutas", 0f);
		receta.agregarIngrediente("melon", 0f);
		receta.agregarIngrediente("chivito", 0f);
		
		assertFalse(usuario.esAdecuada(receta));
	}
	
	/* Test: @puedeVer/1 */
	
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaSiLePertenece() {
		receta = new Receta(fecheSena, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		assertTrue(fecheSena.puedeVer(receta));
	}
	
	@Test
	public void testUnUsuarioNoPuedeVerUnaRecetaSiNoLePertenece() {
		receta = new Receta(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		assertFalse(arielFolino.puedeVer(receta));
	}
	
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaPublica() {
		RecetaPublica recetaPublica = new RecetaPublica();
		assertTrue(arielFolino.puedeVer(recetaPublica));
	}
	
	/* Test: @puedeModificar/1 */
	
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaSiLePertenece() {
		receta = new Receta(fecheSena, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		assertTrue(fecheSena.puedeModificar(receta));
	}
	
	@Test
	public void testUnUsuarioNoPuedeModificarUnaRecetaSiNoLePertenece() {
		receta = new Receta(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		assertFalse(arielFolino.puedeModificar(receta));
	}
	
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaPublica() {
		RecetaPublica recetaPublica = new RecetaPublica();
		assertTrue(arielFolino.puedeModificar(recetaPublica));
	}
	
	/* Test: @agregarReceta/1 */
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaInadecuadaParaEl() throws NoSePuedeAgregarLaReceta {
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		receta = new Receta(fecheSena, null, null);
		assertFalse(fecheSena.esAdecuada(receta));
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaQueNoLePertenece() throws NoSePuedeAgregarLaReceta {
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		receta = new Receta(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaPublica() throws NoSePuedeAgregarLaReceta {
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		receta = new RecetaPublica();
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioPuedeAgregarUnaRecetaValidaPropia() throws NoSePuedeAgregarLaReceta {
	
		receta = new Receta(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		matiasMartino.agregarReceta(receta);
	}
	
	/* Test: @modificarReceta/6 */
	
	@Test
	public void testUnUsuarioModificaUnaRecetaQuePuedeModificar() throws NoSePuedeModificarLaReceta {
		
		receta = new Receta(fecheSena, null, "Preparacion antes de modificar");
		
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setTotalCalorias(4500);
		
		HashMap<String, Float> ingredientes = new HashMap<String, Float>();
		ingredientes.put("frutas", 0f);
		
		fecheSena.modificarReceta(receta, encabezado, ingredientes, null, "Preparacion despues de modificar", null);
		
		assertTrue(receta.getPreparacion().equals("Preparacion despues de modificar"));
	}
	
	@Test
	public void testUnUsuarioNoModificaUnaRecetaQueNoPuedeModificar() throws NoSePuedeModificarLaReceta {
		expectedExcetption.expect(NoSePuedeModificarLaReceta.class);
		
		receta = new Receta(fecheSena, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		matiasMartino.modificarReceta(receta, null, null, null, "", null);
	}
	
	@Test
	public void testUnUsuarioModificaUnaRecetaPublicaPeroSoloElVeLosCambios() throws NoSePuedeModificarLaReceta {
		RecetaPublica recetaPublica = new RecetaPublica(null, "Preparacion antes de modificar");
		
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setTotalCalorias(4500);
		
		HashMap<String, Float> ingredientes = new HashMap<String, Float>();
		ingredientes.put("frutas", 0f);

		fecheSena.modificarReceta(recetaPublica, encabezado, ingredientes, null, "Preparacion despues de modificar", null);

		assertTrue(recetaPublica.getPreparacion().equals("Preparacion antes de modificar"));
		assertTrue(fecheSena.recetaMasReciente().getPreparacion().equals("Preparacion despues de modificar"));
	}
	
}
