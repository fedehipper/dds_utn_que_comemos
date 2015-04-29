package grupo4.dds;

import static org.junit.Assert.*;
import static grupo4.dds.Rutina.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class TestUsuario {

	private Usuario fecheSena = new Usuario(1.70, 65.0);
	private Usuario federicoHipper = new Usuario(1.91, 102.0);
	private Usuario arielFolino = new Usuario(1.69, 96.0);
	private Usuario cristianMaldonado = new Usuario(1.81, 87.0);
	private Usuario pedro = new Usuario("pedro", 'M',
			LocalDate.of(2015, 04, 23), 1.50, 90.0, ACTIVA_EJERCICIO_ADICIONAL);
	private Usuario juancho = new Usuario("juancho", 'M', LocalDate.of(1000,
			04, 04), 1.80, 70.0, ACTIVA_EJERCICIO_ADICIONAL);
	private Celiaco celiaco = new Celiaco();
	private Vegano vegano = new Vegano();
	private Diabetico diabetico = new Diabetico();
	private Receta recetaDeJuancho = new Receta(juancho);
	private RecetaDelSistema recetaDeTodos = new RecetaDelSistema();
	
	@Before
	public void setUp() {

		Collection<String> preferencias = new ArrayList<>();
		preferencias.add("carne");
		pedro.setPreferenciasAlimenticias(preferencias);
		Collection<String> preferenciasFrutas = new ArrayList<>();
		preferenciasFrutas.add("frutas");
		juancho.setPreferenciasAlimenticias(preferenciasFrutas);
		
		
	}

	@Test
	public void testConPeso65YAltura170() {
		assertEquals(fecheSena.indiceDeMasaCorporal(), 22.491, 0.001);
	}

	@Test
	public void testConPeso102YAltura191() {
		assertEquals(federicoHipper.indiceDeMasaCorporal(), 27.959, 0.001);
	}

	@Test
	public void testConPeso96YAltura169() {
		assertEquals(arielFolino.indiceDeMasaCorporal(), 33.612, 0.001);
	}

	@Test
	public void testConPeso87YAltura181() {
		assertEquals(cristianMaldonado.indiceDeMasaCorporal(), 26.555, 0.001);
	}

	@Test
	public void esFechaDeNacimientoPedro() {
		assertTrue(pedro.esFechaDeNacimiento());
	}

	@Test
	public void testTieneCamposObligatoriosDePedro() {
		assertTrue(pedro.tieneCamposObligatorios());
	}

	@Test
	public void testTieneMasDeCuatroCaracteresPedro() {
		assertTrue(pedro.tieneMasDe(4));
	}

	@Test
	public void testCumpleCondicionesPedro() {
		assertTrue(pedro.cumpleCondiciones());
	}

	@Test
	public void testEsValidoPedro() {
		assertTrue(pedro.esValido());
	}

	@Test
	public void testEsValidoFecheSena() {
		assertFalse(fecheSena.esValido());

	}

	@Test
	public void testImcEstaEntre18Y30() {
		assertTrue(juancho.imcEstaEntre(18, 30));
	}


	@Test
	public void testCumpleNecesidades() {
		juancho.agregarCondicion(vegano);
		juancho.agregarCondicion(diabetico);
		juancho.agregarCondicion(celiaco);
		assertTrue(juancho.cumpleNecesidades());
	}

	@Test
	public void testJuanchoPuedeVerOModificarSuReceta(){
		assertTrue(juancho.puedeVerOModificar(recetaDeJuancho));
	}
	
	@Test
	public void testPedroPuedeVerOModificarUnaRecetaDelSistema(){
		assertTrue(pedro.puedeVerOModificar(recetaDeTodos));
	}
}
