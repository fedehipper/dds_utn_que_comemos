package grupo4.dds;

import static grupo4.dds.usuario.Alimento.*;
import static grupo4.dds.usuario.Rutina.*;
import static grupo4.dds.usuario.Sexo.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Alimento;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class TestUsuario {

	private Usuario usuario;
	private Usuario fecheSena = new Usuario("Feche Sena", null, 1.70f, 65.0f, null);
	private Usuario federicoHipper = new Usuario("Federico Hipper", null, 1.91f, 102.0f, null);
	private Usuario arielFolino = new Usuario("Ariel Folino", null, 1.69f, 96.0f, null);
	private Usuario cristianMaldonado = new Usuario("Cristian Maldonado", null, 1.81f, 87.0f, null);
	private Usuario matiasMartino = new Usuario("Matías Martino", null, 1.74f, 79.0f, null);
	
	private Usuario pedro = new Usuario("pedro", MASCULINO,
			LocalDate.of(2015, 04, 23), 1.50f, 90.0f, ACTIVA_EJERCICIO_ADICIONAL);
	private Usuario juancho = new Usuario("juancho", MASCULINO, LocalDate.of(1000,
			04, 04), 1.80f, 70.0f, ACTIVA_EJERCICIO_ADICIONAL);
	private Receta recetaDeJuancho = new Receta(juancho);
	private RecetaPublica recetaDeTodos = new RecetaPublica();

	@Before
	public void setUp() {

		Collection<Alimento> preferencias = new ArrayList<>();
		preferencias.add(CARNE);
		pedro.setPreferenciasAlimenticias(preferencias);
		Collection<Alimento> preferenciasFrutas = new ArrayList<>();
		preferenciasFrutas.add(FRUTAS);
		juancho.setPreferenciasAlimenticias(preferenciasFrutas);

		recetaDeJuancho.agregarIngrediente("papa", 4.0f);
		recetaDeJuancho.setTotalCalorias(100);

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
		usuario = new Usuario("Ari", LocalDate.MIN, 1.7f, 75, ACTIVA_EJERCICIO_ADICIONAL);
		assertTrue(!usuario.esValido());
	}
	
	public void testNoEsValidoUnUsuarioSinCamposObligatorios() {
		usuario = new Usuario("Ariel", null, 0, 0, null);
		assertTrue(!usuario.esValido());
		
		usuario = new Usuario(null, LocalDate.MIN, 0, 0, null);
		assertTrue(!usuario.esValido());

		usuario = new Usuario(null, null, 1.7f, 0, null);
		assertTrue(!usuario.esValido());
		
		usuario = new Usuario(null, null, 0, 75, null);
		assertTrue(!usuario.esValido());
		
		usuario = new Usuario(null, null, 0, 0, ACTIVA_EJERCICIO_ADICIONAL);
		assertTrue(!usuario.esValido());
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
		
		assertTrue(!usuario.esValido());
	}

	/* Test: @sigueRutinaSaludable/0 */
	
	@Test
	public void testSigueRutinaSaludableUnUsuarioSinCondicionesConIMCEntre18Y30() {

	}
	
	/* Test: @sigueRutinaSaludable/0 */
	
	@Test
	public void testUsuarioAgregaUnaRecetaSiLePertenece() {
		juancho.agregarReceta(recetaDeJuancho);
		assertTrue(!juancho.getRecetas().isEmpty());
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaQueNoLePertenece(){
		pedro.agregarReceta(recetaDeJuancho);
		assertTrue(pedro.getRecetas().isEmpty());
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaPublica(){
		juancho.agregarReceta(recetaDeTodos);
		assertTrue(juancho.getRecetas().isEmpty());
	}
	
	@Test
	public void testJuanchoPuedeVerOModificarSuReceta() {
		assertTrue(juancho.puedeVer(recetaDeJuancho));
	}
	
	@Test
	public void testJuanchoPuedeVerOModificarUnaRecetaDelSistema() {
		assertTrue(juancho.puedeVer(recetaDeTodos));
	}
	
	@Test
	public void testPedroPuedeVerOModificarUnaRecetaDelSistema() {
		assertTrue(pedro.puedeVer(recetaDeTodos));
	}
	
	@Test
	public void testPedroNoPuedeVerOModificarUnaRecetaQueNoLePertenece() {
		assertFalse(pedro.puedeVer(recetaDeJuancho));
	}
	
}
