package grupo4.dds;

import static grupo4.dds.usuario.Rutina.*;
import static grupo4.dds.usuario.Sexo.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import grupo4.dds.excepciones.NoSeEncontroLaReceta;
import grupo4.dds.excepciones.NoSePuedeAgregarLaReceta;
import grupo4.dds.excepciones.NoSePuedeGuardarLaRecetaEnElHistorial;
import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		federicoHipper = new Usuario("Federico Hipperdinger", null, 1.91f, 99.0f, null);
		cristianMaldonado = new Usuario("Cristian Maldonado", null, 1.34f, 87.0f, null);
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
	
	@Test
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
		
		Ingrediente chivito = new Ingrediente("chivito", 0f);

		usuario.agregarPreferenciaAlimenticia(chivito);	
		assertTrue(usuario.esValido());
	}
	
	@Test
	public void testNoEsValidoUnUsuarioConAlgunaCondicionInvalida() {
		usuario = new Usuario("Ariel", LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL);
		
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
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		usuario.agregarPreferenciaAlimenticia(frutas);
	
		assertTrue(usuario.esValido());
	}
	
	/* Test: @leGusta/1 */
	@Test
	public void testLeGustaLaCarneAUnUsuario() {
		usuario = new Usuario();
		Ingrediente carne = new Ingrediente("carne", 0f);
		
		usuario.agregarPreferenciaAlimenticia(carne);
		assertTrue(usuario.leGusta("carne"));
	}
	
	@Test
	public void testNoLeGustanLasFrutasAUnUsuario() {
		usuario = new Usuario();
		
		Ingrediente carne = new Ingrediente("carne", 0f);
				
		usuario.agregarPreferenciaAlimenticia(carne);
		assertFalse(usuario.leGusta("fruta"));
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
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);	
		
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
		usuario = new Usuario();
		
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Hipertenso());
		usuario.agregarCondicion(new Vegano());
			
		receta = new Receta();
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
		receta = new Receta(fecheSena, null, null);
		assertTrue(fecheSena.puedeVer(receta));
	}
	
	@Test
	public void testUnUsuarioNoPuedeVerUnaRecetaSiNoLePerteneceAElNiANingunMiembroDeSusGrupos() {
		receta = new Receta(matiasMartino, null, null);		
		
		matiasMartino.agregarGrupo(new GrupoUsuarios("grupo1"));
		arielFolino.agregarGrupo(new GrupoUsuarios("grupo2"));
			
		assertFalse(arielFolino.puedeVer(receta));
	}
	
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaPublica() {
		RecetaPublica recetaPublica = new RecetaPublica();
		assertTrue(arielFolino.puedeVer(recetaPublica));
	}
	
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaSiLePerteneceAAlgunMiembroDeSusGrupos() {
				
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		
		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);

		receta = new Receta(arielFolino, null, null);
		
		assertTrue(fecheSena.puedeVer(receta));
		assertTrue(matiasMartino.puedeVer(receta));
	}
		
	/* Test: @puedeModificar/1 */
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaSiLePertenece() {
		receta = new Receta(fecheSena, null, null);
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		assertTrue(fecheSena.puedeModificar(receta));
	}
	
	@Test
	public void testUnUsuarioNoPuedeModificarUnaRecetaSiNoLePerteneceNiAAlgunMiembroDeSusGrupos() {
		receta = new Receta(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		matiasMartino.agregarGrupo(new GrupoUsuarios("grupo1"));
		arielFolino.agregarGrupo(new GrupoUsuarios("grupo2"));
		
		assertFalse(arielFolino.puedeModificar(receta));
	}
	
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaPublica() {
		RecetaPublica recetaPublica = new RecetaPublica();
		assertTrue(arielFolino.puedeModificar(recetaPublica));
	}
	
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaSiLePerteneceAAlgunMiembroDeSusGrupos() {
		receta = new Receta(fecheSena, null, null);
		
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
		
		receta = new Receta(fecheSena, null, null);
		assertFalse(fecheSena.esAdecuada(receta));
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaQueNoLePertenece(){
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		receta = new Receta(matiasMartino, null, null);
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaPublica(){
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		receta = new RecetaPublica();
		receta.setTotalCalorias(4500);
		Ingrediente frutas = new Ingrediente("frutas", 0f);
	
		receta.agregarIngrediente(frutas);
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioPuedeAgregarUnaRecetaValidaPropia(){
		receta = new Receta(matiasMartino, null, null);
		receta.setTotalCalorias(4500);

		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		matiasMartino.agregarReceta(receta);
	}

	/* Test: @modificarReceta/6 */
	@Test
	public void testUnUsuarioModificaUnaRecetaQuePuedeModificar(){
		
		receta = new Receta(fecheSena, null, "Preparacion antes de modificar");
		
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
		
		receta = new Receta(fecheSena, null, null);
		receta.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		receta.agregarIngrediente(frutas);
		
		matiasMartino.modificarReceta(receta, null, null, null, "", null);
	}
	
	@Test
	public void testUnUsuarioModificaUnaRecetaPublicaPeroSoloElVeLosCambios(){
		RecetaPublica recetaPublica = new RecetaPublica(null, "Preparacion antes de modificar");
		
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
		
		usuario = new Usuario();
		usuario.agregarCondicion(new Celiaco());
		usuario.agregarCondicion(new Vegano());
		
		receta = new Receta();
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente(new Ingrediente("carne", 0f));

		assertFalse(usuario.esAdecuada(receta));
		assertFalse(usuario.puedeSugerirse(receta));
	}
	
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnUsuarioSiContieneComidasQueLeDisgustan() {
		
		usuario = new Usuario();
		usuario.agregarComidaQueLeDisgusta(new Ingrediente("carne"));
		
		receta = new Receta();
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente(new Ingrediente("carne", 0f));

		assertFalse(usuario.puedeSugerirse(receta));
	}
	
	@Test
	public void testSePuedeSugerirUnaRecetaAUnUsuario() {
		
		usuario = new Usuario();
		
		receta = new Receta();
		receta.setTotalCalorias(4500);
		receta.agregarIngrediente(new Ingrediente("carne", 0f));

		assertTrue(usuario.puedeSugerirse(receta));
	}
	
	
	
	
	
	
	

	
	
	
	@Test 
	public void testRecetasQuePuedeVerDelRepositorio() {
		receta = new Receta(fecheSena, null, null);
		Receta r2 = new Receta(cristianMaldonado, null, null);
		
		RepositorioDeRecetas repositorio = new RepositorioDeRecetas();
		repositorio.agregarReceta(receta);
		repositorio.agregarReceta(r2);
		
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		
		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);

		assertTrue(arielFolino.recetasQuePuedeVer(repositorio).contains(receta));
	}
		
	@Test 
	public void testRecetasQueNoPuedeVerDelRepositorio() {
		receta = new Receta(fecheSena, null, null);
		Receta r2 = new Receta(cristianMaldonado, null, null);
		
		RepositorioDeRecetas repositorio = new RepositorioDeRecetas();
		repositorio.agregarReceta(receta);
		repositorio.agregarReceta(r2);
		
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");

		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);

		assertFalse(arielFolino.recetasQuePuedeVer(repositorio).contains(r2));
	}
		
	@Test 
	public void testAgregarUnaRecetaAlHistorial() {
		receta = new Receta(arielFolino, null, null);
		RecetaPublica rPublica = new RecetaPublica();
		
		arielFolino.marcarFavorita(rPublica);
		arielFolino.marcarFavorita(receta);
		
		List<Receta> aux = Stream.of(rPublica, receta).collect(Collectors.toList());
		
		assertTrue(arielFolino.getHistorioal().containsAll(aux));
	}
	
	@Test
	public void testBuscarUnaRecetaQueEsteEnElRepositorioDeRecetas() {
		EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("fideos", null, null);
		EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("huevosFritos", null, null);
		EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("sopaDeVerdura", null, null);
		receta = new Receta(arielFolino, encabezado1, null);
		RecetaPublica rPublica1 = new RecetaPublica(encabezado2, null);
		RecetaPublica rPublica2 = new RecetaPublica(encabezado3, null);
		RepositorioDeRecetas repositorio = new RepositorioDeRecetas();
		
		repositorio.agregarReceta(receta);
		repositorio.agregarReceta(rPublica1);
		repositorio.agregarReceta(rPublica2);

		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");

		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);
		
		assertTrue(fecheSena.buscarUnaReceta("fideos", repositorio).equals(receta));
	}

	@Test
	public void testBuscarUnaRecetaPublicaQueEsteEnElRepositorioDeRecetas() {
		EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("fideos", null, null);
		EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("huevosFritos", null, null);
		EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("sopaDeVerdura", null, null);
		receta = new Receta(arielFolino, encabezado1, null);
		RecetaPublica rPublica1 = new RecetaPublica(encabezado2, null);
		RecetaPublica rPublica2 = new RecetaPublica(encabezado3, null);
		RepositorioDeRecetas repositorio = new RepositorioDeRecetas();
		
		repositorio.agregarReceta(receta);
		repositorio.agregarReceta(rPublica1);
		repositorio.agregarReceta(rPublica2);

		assertTrue(fecheSena.buscarUnaReceta("sopaDeVerdura", repositorio).equals(rPublica2));
	}
	
	@Test(expected = NoSeEncontroLaReceta.class)
	public void testBuscarUnaRecetaQueNoEsteEnElRepositorioDeRecetas() {
		EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("huevosFritos", null, null);
		EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("sopaDeVerdura", null, null);
		
		RecetaPublica rPublica1 = new RecetaPublica(encabezado2, null);
		RecetaPublica rPublica2 = new RecetaPublica(encabezado3, null);
		
		RepositorioDeRecetas repositorio = new RepositorioDeRecetas();
		
		repositorio.agregarReceta(rPublica1);
		repositorio.agregarReceta(rPublica2);
		
		assertNull(fecheSena.buscarUnaReceta("fideos", repositorio));
	}

	@Test(expected = NoSeEncontroLaReceta.class)
	public void testBuscarRecetaQueNoEsPosibleVerPorElUsuario() {
		EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("fideos", null, null);
		EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("huevosFritos", null, null);
		EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("sopaDeVerdura", null, null);
		receta = new Receta(arielFolino, encabezado1, null);
		RecetaPublica rPublica1 = new RecetaPublica(encabezado2, null);
		RecetaPublica rPublica2 = new RecetaPublica(encabezado3, null);
		RepositorioDeRecetas repositorio = new RepositorioDeRecetas();
		
		repositorio.agregarReceta(receta);
		repositorio.agregarReceta(rPublica1);
		repositorio.agregarReceta(rPublica2);

		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
			
		matiasMartino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);
		
		assertNull(fecheSena.buscarUnaReceta("fideos", repositorio));
	}

	@Test(expected = NoSePuedeGuardarLaRecetaEnElHistorial.class)
	public void testNoSePuedeAgregarRecetaAlHistorial() {
		receta = new Receta(fecheSena, null, null);
		arielFolino.marcarFavorita(receta);
	}

	@Test
	public void testBuscarYAgregarRecetaEnElHistorial() {
		
		EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("fideos", null, null);
		EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("huevosFritos", null, null);
		EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("sopaDeVerdura", null, null);
		receta = new Receta(arielFolino, encabezado1, null);
		RecetaPublica rPublica1 = new RecetaPublica(encabezado2, null);
		RecetaPublica rPublica2 = new RecetaPublica(encabezado3, null);
		RepositorioDeRecetas repositorio = new RepositorioDeRecetas();
		
		repositorio.agregarReceta(receta);
		repositorio.agregarReceta(rPublica1);
		repositorio.agregarReceta(rPublica2);

		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		
		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);
		
		fecheSena.buscarYAgregarAlHistorial("fideos", repositorio);
				
		assertTrue(fecheSena.getHistorioal().contains(receta));
	}
}
