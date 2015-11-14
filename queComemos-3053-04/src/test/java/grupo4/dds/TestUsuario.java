package grupo4.dds;

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
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.receta.builder.BuilderRecetaPublica;
import grupo4.dds.usuario.BuilderUsuario;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Rutina;
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

public class TestUsuario extends BaseTest {

	@Rule public ExpectedException expectedExcetption = ExpectedException.none();
	
	@Before
	public void setUp() {
		bife.agregarIngrediente(Ingrediente.nuevoIngrediente("carne", 0f));
		
		arielFolino.agregarCondicion(Vegano.instance());
		arielFolino.agregarCondicion(Celiaco.instance());
		arielFolino.agregarCondicion(Diabetico.instance());
	}
	
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
		Usuario usuario = new BuilderUsuario().nombre("Ari").altura(1.75f).peso(75f).rutina(Rutina.SEDENTARIA_SIN_EJERCICIO).nacimiento(LocalDate.MIN).build();
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testNoEsValidoUnUsuarioConLaFechaActualComoDiaDeNacimiento() {
		Usuario usuario = new BuilderUsuario().nombre("Ari").altura(1.75f).peso(75f).rutina(Rutina.SEDENTARIA_SIN_EJERCICIO).nacimiento(LocalDate.now()).build();
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testNoEsValidoUnUsuarioSinCamposObligatorios() {
		Usuario usuario = new BuilderUsuario().nombre("Ariel").peso(75f).rutina(Rutina.SEDENTARIA_SIN_EJERCICIO).nacimiento(LocalDate.MIN).build();
		assertFalse(usuario.esValido());
		
		usuario = new BuilderUsuario().nombre("Ariel").altura(1.75f).rutina(Rutina.SEDENTARIA_SIN_EJERCICIO).nacimiento(LocalDate.MIN).build();
		assertFalse(usuario.esValido());

		usuario = new BuilderUsuario().nombre("Ariel").altura(1.75f).peso(75f).nacimiento(LocalDate.MIN).build();
		assertFalse(usuario.esValido());
		
		usuario = new BuilderUsuario().nombre("Ariel").altura(1.75f).peso(75f).rutina(Rutina.SEDENTARIA_SIN_EJERCICIO).build();
		assertFalse(usuario.esValido());
		
		usuario = new BuilderUsuario().altura(1.75f).peso(75f).rutina(Rutina.SEDENTARIA_SIN_EJERCICIO).nacimiento(LocalDate.MIN).build();
		assertFalse(usuario.esValido());
	}
	
	@Test
	public void testEsValidoUnUsuarioSinCondiciones() {
		Usuario usuario = new BuilderUsuario().nombre("Ariel").altura(1.75f).peso(75f).rutina(Rutina.SEDENTARIA_SIN_EJERCICIO).nacimiento(LocalDate.MIN).build();
		
		assertTrue(usuario.esValido());
	}

	@Test
	public void testEsValidoUnUsuarioConCondicionesValidas() {
		Usuario usuario = new BuilderUsuario().nombre("Ariel").masculino().altura(1.75f).peso(75f).rutina(Rutina.ACTIVA_EJERCICIO_ADICIONAL).nacimiento(LocalDate.MIN).build();
		
		usuario.agregarCondicion(Celiaco.instance());
		usuario.agregarCondicion(Hipertenso.instance());
		usuario.agregarCondicion(Diabetico.instance());

		usuario.agregarPreferenciaAlimenticia(Ingrediente.nuevoIngrediente("chivito", 0f));	
		assertTrue(usuario.esValido());
	}
	
	@Test
	public void testNoEsValidoUnUsuarioConAlgunaCondicionInvalida() {
		Usuario usuario = new BuilderUsuario().nombre("Ariel").masculino().altura(1.75f).peso(75f).rutina(Rutina.ACTIVA_EJERCICIO_ADICIONAL).nacimiento(LocalDate.MIN).build();
		
		usuario.agregarCondicion(Celiaco.instance());
		usuario.agregarCondicion(Hipertenso.instance());
		usuario.agregarCondicion(Vegano.instance());
		
		usuario.agregarPreferenciaAlimenticia(Ingrediente.nuevoIngrediente("chivito", 0f));
		
		assertFalse(usuario.esValido());
	}

	/* Test: @sigueRutinaSaludable/0 */
	@Test
	public void testSigueRutinaSaludableUnUsuarioSinCondicionesConIMCEntre18Y30() {
		assertTrue(matiasMartino.sigueRutinaSaludable());
	}
	
	@Test
	public void testNoSigueRutinaSaludableUnUsuarioConCondicionesSinSubsanar() {
		arielFolino.agregarCondicion(Hipertenso.instance());
		assertFalse(arielFolino.sigueRutinaSaludable());
	}
	
	@Test
	public void testSigueRutinaSaludableUnUsuarioConCondicionesSubsanadas() {
		
		arielFolino.agregarCondicion(Hipertenso.instance());
		arielFolino.agregarPreferenciaAlimenticia(Ingrediente.nuevoIngrediente("frutas", 0f));
		
		assertFalse(arielFolino.sigueRutinaSaludable());
	}
	
	/* Test: @leGusta/1 */
	@Test
	public void testLeGustaLaCarneAUnUsuario() {
		matiasMartino.agregarPreferenciaAlimenticia(Ingrediente.nuevaComida("carne"));
		assertTrue(matiasMartino.leGusta("carne"));
	}
	
	@Test
	public void testNoLeGustanLasFrutasAUnUsuario() {
		matiasMartino.agregarPreferenciaAlimenticia(Ingrediente.nuevaComida("carne"));
		assertFalse(matiasMartino.leGusta("fruta"));
	}
	
	/* Test: @esAdecuada/1 */
	@Test
	public void testNoEsAdecuadaUnaRecetaParaUnUsuarioSiEsInvalida() {
		assertFalse(arielFolino.esAdecuada(new Receta()));
	}
	
	@Test
	public void testEsAdecuadaUnaRecetaParaUnUsuarioSinCondiciones() {
		assertTrue(arielFolino.esAdecuada(salmon));
	}
	
	@Test
	public void testEsAdecuadaUnaRecetaParaUnUsuarioSiEsRecomendableParaTodasSusCondiciones() {
		
		arielFolino.agregarCondicion(Hipertenso.instance());
		
		Ingrediente frutas = Ingrediente.nuevoIngrediente("frutas", 0f);
		Ingrediente azucar = Ingrediente.nuevoIngrediente("azucar", 99.9f);
		Ingrediente melon = Ingrediente.nuevoIngrediente("melon", 0f);
		Ingrediente pescado = Ingrediente.nuevoIngrediente("pescado", 0f);
		
		milanesa.agregarIngredientes(Arrays.asList(azucar, frutas, melon, pescado));
		
		assertTrue(arielFolino.esAdecuada(milanesa));
	}
	
	@Test
	public void testNoEsAdecuadaUnaRecetaParaUnUsuarioSiNoEsRecomendableParaAlgunaDeSusCondiciones() {
		
		arielFolino.agregarCondicion(Hipertenso.instance());
		
		Ingrediente frutas = Ingrediente.nuevoIngrediente("frutas", 0f);
		Ingrediente azucar = Ingrediente.nuevoIngrediente("azucar", 99.9f);
		Ingrediente melon = Ingrediente.nuevoIngrediente("melon", 0f);
		Ingrediente carne = Ingrediente.nuevoIngrediente("carne", 0f);
		
		milanesa.agregarIngredientes(Arrays.asList(azucar, frutas, melon, carne));
		
		assertFalse(arielFolino.esAdecuada(milanesa));
	}
	
	/* Test: @puedeVer/1 */
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaSiLePertenece() {
		assertTrue(fecheSena.puedeVer(milanesa));
	}
	
	@Test
	public void testUnUsuarioNoPuedeVerUnaRecetaSiNoLePerteneceAElNiANingunMiembroDeSusGrupos() {
		federicoHipper.agregarGrupo(GrupoUsuarios.crearGrupo("grupo1"));
		arielFolino.agregarGrupo(GrupoUsuarios.crearGrupo("grupo2"));
			
		assertFalse(arielFolino.puedeVer(pollo));
	}
	
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaPublica() {
		RecetaPublica recetaPublica = new BuilderRecetaPublica().hacerValida().build();
		assertTrue(arielFolino.puedeVer(recetaPublica));
	}
	
	@Test
	public void testUnUsuarioPuedeVerUnaRecetaSiLePerteneceAAlgunMiembroDeSusGrupos() {
		
		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);

		assertTrue(fecheSena.puedeVer(sopa));
		assertTrue(matiasMartino.puedeVer(sopa));
	}
		
	/* Test: @puedeModificar/1 */
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaSiLePertenece() {
		assertTrue(fecheSena.puedeModificar(milanesa));
	}
	
	@Test
	public void testUnUsuarioNoPuedeModificarUnaRecetaSiNoLePerteneceNiAAlgunMiembroDeSusGrupos() {
		matiasMartino.agregarGrupo(GrupoUsuarios.crearGrupo("grupo1"));
		arielFolino.agregarGrupo(GrupoUsuarios.crearGrupo("grupo2"));
		
		assertFalse(arielFolino.puedeModificar(milanesa));
	}
	
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaPublica() {
		RecetaPublica recetaPublica = new BuilderRecetaPublica().hacerValida().build();
		assertTrue(arielFolino.puedeModificar(recetaPublica));
	}
	
	@Test
	public void testUnUsuarioPuedeModificarUnaRecetaSiLePerteneceAAlgunMiembroDeSusGrupos() {
		
		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);

		assertTrue(arielFolino.puedeModificar(milanesa));
	}

	/* Test: @agregarReceta/1 */
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaInadecuadaParaEl(){
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		Ingrediente carne = Ingrediente.nuevaComida("carne");
		Receta receta = new BuilderReceta().hacerValida().ingrediente(carne).creador(fecheSena).build();
		fecheSena.agregarCondicion(Vegano.instance());
		
		assertFalse(fecheSena.esAdecuada(receta));
		
		fecheSena.agregarReceta(receta);
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaQueNoLePertenece(){
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);
		
		fecheSena.agregarReceta(sopa);
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaPublica(){
		expectedExcetption.expect(NoSePuedeAgregarLaReceta.class);

		fecheSena.agregarReceta(lomito);
	}
	
	
	@Test
	public void testUnUsuarioPuedeAgregarUnaRecetaValidaPropia(){
		arielFolino.agregarReceta(sopa);
	}

	/* Test: @modificarReceta/6 */
	@Test
	public void testUnUsuarioModificaUnaRecetaQuePuedeModificar(){
		
		milanesa.setPreparacion("Preparacion antes de modificar");
		
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setTotalCalorias(4500);
		
		List<Ingrediente> ingredientes = Arrays.asList(Ingrediente.nuevoIngrediente("frutas", 0f));
		
		fecheSena.modificarReceta(milanesa, encabezado, ingredientes, null, "Preparacion despues de modificar", null);
		assertTrue(milanesa.getPreparacion().equals("Preparacion despues de modificar"));
	}
	
	@Test
	public void testUnUsuarioNoModificaUnaRecetaQueNoPuedeModificar(){
		expectedExcetption.expect(NoSePuedeModificarLaReceta.class);
		
		matiasMartino.modificarReceta(milanesa, null, null, null, "", null);
	}
	
	@Test
	public void testUnUsuarioModificaUnaRecetaPublicaPeroSoloElVeLosCambios(){

		RecetaPublica recetaPublica = new BuilderRecetaPublica().hacerValida().build();
		recetaPublica.setPreparacion("Preparacion antes de modificar");
		
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setTotalCalorias(4500);
		
		List<Ingrediente> ingredientes = new ArrayList<>();
		Ingrediente frutas = Ingrediente.nuevoIngrediente("frutas", 0f);
		
		ingredientes.add(frutas);

		fecheSena.modificarReceta(recetaPublica, encabezado, ingredientes, null, "Preparacion despues de modificar", null);

		assertTrue(recetaPublica.getPreparacion().equals("Preparacion antes de modificar"));
		assertTrue(fecheSena.recetaMasReciente().getPreparacion().equals("Preparacion despues de modificar"));
	}
	
	/* Test: @puedeSugerirse/1 */
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnUsuarioSiNoCumpleTodasSusCondiciones() {
		
		fecheSena.agregarCondicion(Celiaco.instance());
		fecheSena.agregarCondicion(Vegano.instance());
		
		assertFalse(fecheSena.esAdecuada(bife));
		assertFalse(fecheSena.puedeSugerirse(bife));
	}
	
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnUsuarioSiContieneComidasQueLeDisgustan() {
		fecheSena.agregarComidaQueLeDisgusta(Ingrediente.nuevaComida("carne"));
		assertFalse(fecheSena.puedeSugerirse(bife));
	}
	
	@Test
	public void testSePuedeSugerirUnaRecetaAUnUsuario() {
		assertTrue(fecheSena.puedeSugerirse(bife));
	}
	
	/* Test: @marcarFavorita/1 */
	@Test 
	public void testUnUsuarioPuedeAgregarUnaRecetaQuePuedeVerAlHistorial() {
		
		arielFolino.marcarFavorita(lomito);	 
		arielFolino.marcarFavorita(sopa);
		
		List<Receta> expected = Arrays.asList(lomito, sopa);
		
		assertTrue(arielFolino.getFavoritas().containsAll(expected));
	}
	
	@Test 
	public void testUnUsuarioNoPuedeAgregarUnaRecetaQuePuedeVerAlHistorial() {
		expectedExcetption.expect(NoSePuedeGuardarLaRecetaEnElHistorial.class);
		
		arielFolino.marcarFavorita(pollo);
	}
	
	/* Test: @cumpleTodasLasCondicionesDe/1 */
	@Test 
	public void testUnUsuarioNoCumpleTodasLasCondicionesDeOtroUsuario() {

		fecheSena.agregarCondicion(Vegano.instance());
		fecheSena.agregarCondicion(Hipertenso.instance());
		
		assertFalse(arielFolino.cumpleTodasLasCondicionesDe(fecheSena));
	}
	
	@Test 
	public void testUnUsuarioCumpleTodasLasCondicionesDeOtroUsuarioSiEsteUltimoNoTieneCondiciones() {
		assertTrue(fecheSena.noTieneCondiciones());
		assertTrue(arielFolino.cumpleTodasLasCondicionesDe(fecheSena));
	}
	
	@Test 
	public void testUnUsuarioCumpleTodasLasCondicionesDeOtroUsuario() {
		
		fecheSena.agregarCondicion(Vegano.instance());
		
		assertTrue(arielFolino.cumpleTodasLasCondicionesDe(fecheSena));
	}
	
	@Test 
	public void testUnUsuarioNoCumpleTodasLasCondicionesDeOtroUsuarioConDuplicados() {
		
		fecheSena.agregarCondicion(Vegano.instance());
		fecheSena.agregarCondicion(Vegano.instance());
		
		assertTrue(arielFolino.cumpleTodasLasCondicionesDe(fecheSena));
	}
	
}
