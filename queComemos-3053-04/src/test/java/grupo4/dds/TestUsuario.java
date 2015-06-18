package grupo4.dds;

import static grupo4.dds.usuario.Rutina.ACTIVA_EJERCICIO_ADICIONAL;
import static grupo4.dds.usuario.Rutina.SEDENTARIA_CON_EJERCICIO;
import static grupo4.dds.usuario.Sexo.MASCULINO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.excepciones.NoSePuedeAgregarLaReceta;
import grupo4.dds.excepciones.NoSePuedeGuardarLaRecetaEnElHistorial;
import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null, false);
		arielFolino = Usuario.crearPerfil("Ariel Folino", null, null, 1.69f, 96.0f, null, false);
		matiasMartino = Usuario.crearPerfil("Matías Martino", null, null, 1.74f, 79.0f, null, false);
		federicoHipper = Usuario.crearPerfil("Federico Hipperdinger", null, null, 1.91f, 99.0f, null, false);
		cristianMaldonado = Usuario.crearPerfil("Cristian Maldonado", null, null, 1.34f, 87.0f, null, false);
	}
	
	/* Test: @indiceDeMasaCorporal/0 */
	@Test
	public void testIMCConPeso65YAltura170() {
		assertEquals(fecheSena.indiceDeMasaCorporal(), 22.491, 0.001);
	}

	@Test
	public void testIMCConPeso102YAltura191() {
		federicoHipper = Usuario.crearPerfil("Federico Hipper", null, null, 1.91f, 102.0f, null, false);
		assertEquals(federicoHipper.indiceDeMasaCorporal(), 27.959, 0.001);
	}

	@Test
	public void testIMCConPeso96YAltura169() {
		assertEquals(arielFolino.indiceDeMasaCorporal(), 33.612, 0.001);
	}

	@Test
	public void testIMCConPeso87YAltura181() {
		cristianMaldonado = Usuario.crearPerfil("Cristian Maldonado", null, null, 1.81f, 87.0f, null, false);
		assertEquals(cristianMaldonado.indiceDeMasaCorporal(), 26.555, 0.001);
	}

	@Test
	public void testIMCConPeso79YAltura174() {
		assertEquals(matiasMartino.indiceDeMasaCorporal(), 26.093, 0.001);
	}
	
	/* Test: @esValido/0 */
	@Test
	public void testNoEsValidoUnUsuarioConNombreMenorA4Caracteres() {
		usuario = Usuario.crearPerfil("Ari", null, LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL, false);
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testNoEsValidoUnUsuarioConLaFechaActualComoDiaDeNacimiento() {
		usuario = Usuario.crearPerfil("Ariel", null, LocalDate.now(), 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL, false);
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testNoEsValidoUnUsuarioSinCamposObligatorios() {
		usuario = Usuario.crearPerfil("Ariel", null, null, 0, 0, null, false);
		assertFalse(usuario.esValido());
		
		usuario = Usuario.crearPerfil(null, null, LocalDate.MIN, 0, 0, null, false);
		assertFalse(usuario.esValido());

		usuario = Usuario.crearPerfil(null, null, null, 1.7f, 0, null, false);
		assertFalse(usuario.esValido());
		
		usuario = Usuario.crearPerfil(null, null, null, 0, 75, null, false);
		assertFalse(usuario.esValido());
		
		usuario = Usuario.crearPerfil(null, null, null, 0, 0, ACTIVA_EJERCICIO_ADICIONAL, false);
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testEsValidoUnUsuarioSinCondiciones() {
		usuario = Usuario.crearPerfil("Ariel", null, LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL, false);
		assertTrue(usuario.esValido());
	}

	@Test
	public void testEsValidoUnUsuarioConCondicionesValidas() {
		usuario = Usuario.crearPerfil("Ariel", MASCULINO, LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL, false);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Diabetico());
		
		Ingrediente chivito = new Ingrediente("chivito", 0f);

		usuario.agregarPreferenciaAlimenticia(chivito);	
		assertTrue(usuario.esValido());
	}
	
	@Test
	public void testNoEsValidoUnUsuarioConAlgunaCondicionInvalida() {
		usuario = Usuario.crearPerfil("Ariel", null, LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL, false);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
		
		Ingrediente chivito = new Ingrediente("chivito", 0f);
		usuario.agregarPreferenciaAlimenticia(chivito);
		
		assertFalse(usuario.esValido());
	}

	/* Test: @sigueRutinaSaludable/0 */
	@Test
	public void testSigueRutinaSaludableUnUsuarioSinCondicionesConIMCEntre18Y30() {
		usuario = Usuario.crearPerfil("Ariel", null, LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL, false);
		assertTrue(usuario.esValido());
	}
	
	@Test
	public void testNoSigueRutinaSaludableUnUsuarioConCondicionesSinSubsanar() {
		usuario = Usuario.crearPerfil("Ariel", null, LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL, false);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
		
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testSigueRutinaSaludableUnUsuarioConCondicionesSubsanadas() {
		usuario = Usuario.crearPerfil("Ariel", null, LocalDate.MIN, 1.7f, 70, SEDENTARIA_CON_EJERCICIO, false);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		usuario.agregarPreferenciaAlimenticia(frutas);
	
		assertTrue(usuario.esValido());
	}
	
	/* Test: @leGusta/1 */
	@Test
	public void testLeGustaLaCarneAUnUsuario() {
		usuario = Usuario.crearPerfil(null);
		Ingrediente carne = new Ingrediente("carne", 0f);
		
		usuario.agregarPreferenciaAlimenticia(carne);
		assertTrue(usuario.leGusta("carne"));
	}
	
	@Test
	public void testNoLeGustanLasFrutasAUnUsuario() {
		usuario = Usuario.crearPerfil(null);
		
		Ingrediente carne = new Ingrediente("carne", 0f);
				
		usuario.agregarPreferenciaAlimenticia(carne);
		assertFalse(usuario.leGusta("fruta"));
	}
	
	/* Test: @esAdecuada/1 */
	@Test
	public void testNoEsAdecuadaUnaRecetaParaUnUsuarioSiEsInvalida() {
		usuario = Usuario.crearPerfil(null);
		receta = Receta.crearNueva();
		
		assertFalse(usuario.esAdecuada(receta));
	}
	
	@Test
	public void testEsAdecuadaUnaRecetaParaUnUsuarioSinCondiciones() {
		usuario = Usuario.crearPerfil(null);
		
		receta = Receta.crearNueva();
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);	
		
		assertTrue(usuario.esAdecuada(receta));
	}
	
	@Test
	public void testEsAdecuadaUnaRecetaParaUnUsuarioSiEsRecomendableParaTodasSusCondiciones() {
		usuario = Usuario.crearPerfil(null);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
		
		receta = Receta.crearNueva();
		receta.setTotalCalorias(4500);
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		Ingrediente azucar = new Ingrediente("azucar", 99.9f);
		Ingrediente melon = new Ingrediente("melon", 0f);
		Ingrediente pescado = new Ingrediente("pescado", 0f);
		
		receta.agregarCondimento(azucar);		
		receta.agregarIngrediente(frutas);
		receta.agregarIngrediente(melon);
		receta.agregarIngrediente(pescado);
		
		assertTrue(usuario.esAdecuada(receta));
	}
	
	@Test
	public void testNoEsAdecuadaUnaRecetaParaUnUsuarioSiNoEsRecomendableParaAlgunaDeSusCondiciones() {
		usuario = Usuario.crearPerfil(null);
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
			
		receta = Receta.crearNueva();
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		Ingrediente azucar = new Ingrediente("azucar", 99.9f);
		Ingrediente melon = new Ingrediente("melon", 0f);
		Ingrediente chivito = new Ingrediente("chivito", 0f);
		
		
		receta.agregarCondimento(azucar);		
		receta.agregarIngrediente(frutas);
		receta.agregarIngrediente(melon);
		receta.agregarIngrediente(chivito);
		
		assertFalse(usuario.esAdecuada(receta));
	}
	
	/* Test: @puedeVer/1 */
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaSiLePertenece() {
		receta = Receta.crearNueva(fecheSena, null, null);
		assertTrue(fecheSena.puedeVer(receta));
	}
	
	@Test
	public void testUnUsuarioNoPuedeVerUnaRecetaSiNoLePerteneceAElNiANingunMiembroDeSusGrupos() {
		receta = Receta.crearNueva(matiasMartino, null, null);		
		
		matiasMartino.agregarGrupo(new GrupoUsuarios("grupo1"));
		arielFolino.agregarGrupo(new GrupoUsuarios("grupo2"));
			
		assertFalse(arielFolino.puedeVer(receta));
	}
	
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaPublica() {
		RecetaPublica recetaPublica = RecetaPublica.crearNueva(null, null);
		assertTrue(arielFolino.puedeVer(recetaPublica));
	}
	
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaSiLePerteneceAAlgunMiembroDeSusGrupos() {
				
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		
		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);

		receta = Receta.crearNueva(arielFolino, null, null);
		
		assertTrue(fecheSena.puedeVer(receta));
		assertTrue(matiasMartino.puedeVer(receta));
	}
		
	/* Test: @puedeModificar/1 */
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaSiLePertenece() {
		receta = Receta.crearNueva(fecheSena, null, null);
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		assertTrue(fecheSena.puedeModificar(receta));
	}
	
	@Test
	public void testUnUsuarioNoPuedeModificarUnaRecetaSiNoLePerteneceNiAAlgunMiembroDeSusGrupos() {
		receta = Receta.crearNueva(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		matiasMartino.agregarGrupo(new GrupoUsuarios("grupo1"));
		arielFolino.agregarGrupo(new GrupoUsuarios("grupo2"));
		
		assertFalse(arielFolino.puedeModificar(receta));
	}
	
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaPublica() {
		RecetaPublica recetaPublica = RecetaPublica.crearNueva(null, null);
		assertTrue(arielFolino.puedeModificar(recetaPublica));
	}
	
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaSiLePerteneceAAlgunMiembroDeSusGrupos() {
		receta = Receta.crearNueva(fecheSena, null, null);
		
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		
		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);

		assertTrue(arielFolino.puedeModificar(receta));
	}

	/* Test: @agregarReceta/1 */
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaInadecuadaParaEl(){
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		receta = Receta.crearNueva(fecheSena, null, null);
		assertFalse(fecheSena.esAdecuada(receta));
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaQueNoLePertenece(){
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		receta = Receta.crearNueva(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaPublica(){
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		receta = RecetaPublica.crearNueva(null, null);
		receta.setTotalCalorias(4500);
		Ingrediente frutas = new Ingrediente("frutas", 0f);
	
		receta.agregarIngrediente(frutas);
		fecheSena.agregarReceta(receta);
	}
	
	
	@Test
	public void testUnUsuarioPuedeAgregarUnaRecetaValidaPropia(){
		receta = Receta.crearNueva(matiasMartino, null, null);
		receta.setTotalCalorias(4500);

		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		matiasMartino.agregarReceta(receta);
	}

	/* Test: @modificarReceta/6 */
	@Test
	public void testUnUsuarioModificaUnaRecetaQuePuedeModificar(){
		
		receta = Receta.crearNueva(fecheSena, null, "Preparacion antes de modificar");
		
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setTotalCalorias(4500);
		
		List<Ingrediente> ingredientes = new ArrayList<>();
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		ingredientes.add(frutas);
		
		fecheSena.modificarReceta(receta, encabezado, ingredientes, null, "Preparacion despues de modificar", null);
		assertTrue(receta.getPreparacion().equals("Preparacion despues de modificar"));
	}
	
	@Test
	public void testUnUsuarioNoModificaUnaRecetaQueNoPuedeModificar(){
		expectedExcetption.expect(NoSePuedeModificarLaReceta.class);
		
		receta = Receta.crearNueva(fecheSena, null, null);
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		matiasMartino.modificarReceta(receta, null, null, null, "", null);
	}
	
	@Test
	public void testUnUsuarioModificaUnaRecetaPublicaPeroSoloElVeLosCambios(){

		RecetaPublica recetaPublica = RecetaPublica.crearNueva(null, "Preparacion antes de modificar");
		
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setTotalCalorias(4500);
		
		List<Ingrediente> ingredientes = new ArrayList<>();
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		
		ingredientes.add(frutas);

		fecheSena.modificarReceta(recetaPublica, encabezado, ingredientes, null, "Preparacion despues de modificar", null);

		assertTrue(recetaPublica.getPreparacion().equals("Preparacion antes de modificar"));
		assertTrue(fecheSena.recetaMasReciente().getPreparacion().equals("Preparacion despues de modificar"));
	}
	
	/* Test: @puedeSugerirse/1 */
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnUsuarioSiNoCumpleTodasSusCondiciones() {
		
		usuario = Usuario.crearPerfil(null);
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Vegano());
		
		receta = Receta.crearNueva();
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente(new Ingrediente("carne", 0f));

		assertFalse(usuario.esAdecuada(receta));
		assertFalse(usuario.puedeSugerirse(receta));
	}
	
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnUsuarioSiContieneComidasQueLeDisgustan() {
		
		usuario = Usuario.crearPerfil(null);
		usuario.agregarComidaQueLeDisgusta(new Ingrediente("carne"));
		
		receta = Receta.crearNueva();
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente(new Ingrediente("carne", 0f));

		assertFalse(usuario.puedeSugerirse(receta));
	}
	
	@Test
	public void testSePuedeSugerirUnaRecetaAUnUsuario() {
		
		usuario = Usuario.crearPerfil(null);
		
		receta = Receta.crearNueva();
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente(new Ingrediente("carne", 0f));

		assertTrue(usuario.puedeSugerirse(receta));
	}
	
	/* Test: @marcarFavorita/1 */
	@Test 
	public void testUnUsuarioPuedeAgregarUnaRecetaQuePuedeVerAlHistorial() {
		
		receta = Receta.crearNueva(arielFolino, null, null);
		RecetaPublica recetaPublica = RecetaPublica.crearNueva(null, null);
		
		arielFolino.marcarFavorita(recetaPublica);
		arielFolino.marcarFavorita(receta);
		
		List<Receta> expected = Arrays.asList(recetaPublica, receta);
		
		assertTrue(arielFolino.getHistorioal().containsAll(expected));
	}
	
	@Test 
	public void testUnUsuarioNoPuedeAgregarUnaRecetaQuePuedeVerAlHistorial() {
		expectedExcetption.expect(NoSePuedeGuardarLaRecetaEnElHistorial.class);
		
		receta = Receta.crearNueva(federicoHipper, null, null);
		arielFolino.marcarFavorita(receta);
	}
	
	/* Test: @cumpleTodasLasCondicionesDe/1 */
	@Test 
	public void testUnUsuarioNoCumpleTodasLasCondicionesDeOtroUsuario() {
		
		arielFolino.agregarCondicion(new Vegano());
		arielFolino.agregarCondicion(new Celiaco());
		arielFolino.agregarCondicion(new Diabetico());
		
		fecheSena.agregarCondicion(new Vegano());
		fecheSena.agregarCondicion(new Hipertenso());
		
		assertFalse(arielFolino.cumpleTodasLasCondicionesDe(fecheSena));
	}
	
	@Test 
	public void testUnUsuarioCumpleTodasLasCondicionesDeOtroUsuarioSiEsteUltimoNoTieneCondiciones() {
		assertTrue(fecheSena.noTieneCondiciones());
		assertTrue(arielFolino.cumpleTodasLasCondicionesDe(fecheSena));
	}
	
	@Test 
	public void testUnUsuarioCumpleTodasLasCondicionesDeOtroUsuario() {
		
		arielFolino.agregarCondicion(new Vegano());
		arielFolino.agregarCondicion(new Celiaco());
		arielFolino.agregarCondicion(new Diabetico());
		
		fecheSena.agregarCondicion(new Vegano());
		
		assertTrue(arielFolino.cumpleTodasLasCondicionesDe(fecheSena));
	}
	
	@Test 
	public void testUnUsuarioNoCumpleTodasLasCondicionesDeOtroUsuarioConDuplicados() {
		
		arielFolino.agregarCondicion(new Vegano());
		arielFolino.agregarCondicion(new Celiaco());
		arielFolino.agregarCondicion(new Diabetico());
		
		fecheSena.agregarCondicion(new Vegano());
		fecheSena.agregarCondicion(new Vegano());
		
		assertTrue(arielFolino.cumpleTodasLasCondicionesDe(fecheSena));
	}
	
}
