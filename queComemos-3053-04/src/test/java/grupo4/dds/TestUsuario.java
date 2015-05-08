package grupo4.dds;

import static grupo4.dds.usuario.Alimento.*;
import static grupo4.dds.usuario.Rutina.*;
import static grupo4.dds.usuario.Sexo.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.NoTienePermisoParaAgregarReceta;
import grupo4.dds.receta.NoTienePermisoParaModificarReceta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.*;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestUsuario {

	private Usuario usuario;
	private Receta receta;
	private Usuario fecheSena = new Usuario("Feche Sena", null, 1.70f, 65.0f, null);
	private Usuario federicoHipper = new Usuario("Federico Hipper", null, 1.91f, 102.0f, null);
	private Usuario arielFolino = new Usuario("Ariel Folino", null, 1.69f, 96.0f, null);
	private Usuario cristianMaldonado = new Usuario("Cristian Maldonado", null, 1.81f, 87.0f, null);
	private Usuario matiasMartino = new Usuario("Matías Martino", null, 1.74f, 79.0f, null);
	
	@Rule public ExpectedException expectedExcetption = ExpectedException.none();
	
	/* Test: @indiceDeMasaCorporal/0 */

	@Test
	public void testIMCConPeso65YAltura170() {
		assertEquals(fecheSena.indiceDeMasaCorporal(), 22.491, 0.001);
	}

	@Test
	public void testIMCConPeso102YAltura191() {
		assertEquals(federicoHipper.indiceDeMasaCorporal(), 27.959, 0.001);
	}

	@Test
	public void testIMCConPeso96YAltura169() {
		assertEquals(arielFolino.indiceDeMasaCorporal(), 33.612, 0.001);
	}

	@Test
	public void testIMCConPeso87YAltura181() {
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
	public void testUnUsuarioNoPuedeAgregarUnaRecetaInadecuadaParaEl() throws NoTienePermisoParaAgregarReceta {
		expectedExcetption.expect(NoTienePermisoParaAgregarReceta.class);
		
		receta = new Receta(fecheSena, null, null);
		assertFalse(fecheSena.esAdecuada(receta));
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaQueNoPuedeVer() throws NoTienePermisoParaAgregarReceta {
		expectedExcetption.expect(NoTienePermisoParaAgregarReceta.class);
		
		receta = new Receta(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioPuedeAgregarUnaRecetaValidaPropia() throws NoTienePermisoParaAgregarReceta {
	
		receta = new Receta(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		matiasMartino.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioPuedeAgregarUnaRecetaPublicaValida() throws NoTienePermisoParaAgregarReceta {
		
		receta = new RecetaPublica();
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		fecheSena.agregarReceta(receta);
	}
	
	/* Test: @modificarReceta/1 */
	
	@Test
	public void testUnUsuarioModificaUnaRecetaQuePuedeModificar() throws NoTienePermisoParaModificarReceta {
		
		receta = new Receta(fecheSena, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		fecheSena.modificarReceta(receta, null, null, null, "", null);
	}
	
	@Test
	public void testUnUsuarioNoModificaUnaRecetaQueNoPuedeModificar() throws NoTienePermisoParaModificarReceta {
		expectedExcetption.expect(NoTienePermisoParaModificarReceta.class);
		
		receta = new Receta(fecheSena, null, null);
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente("frutas", 0f);
		
		matiasMartino.modificarReceta(receta, null, null, null, "", null);
	}
	
	@Test
	public void testUnUsuarioModificaUnaRecetaPublicaPeroSoloElVeLosCambios() throws NoTienePermisoParaModificarReceta {
	//TODO: terminar este test
//		receta = new RecetaPublica(null, "Preparación antes de modificar");
//		receta.setTotalCalorias(4500);
//		receta.agregarIngrediente("frutas", 0f);
//		
//		fecheSena.modificarReceta(receta, null, null, null, "Preparación después de modificar", null);
//		
//		Receta[] a = null;
//		a = fecheSena.getRecetas().toArray(a);
//		a[a.length].equals("Preparación después de modificar");
//		
//		assertTrue(receta.getPreparacion().equals("Preparación antes de modificar"));
	}
	
}
