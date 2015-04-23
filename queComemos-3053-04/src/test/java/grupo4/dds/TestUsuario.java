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
	private Usuario arielFolino = new Usuario(1.69,95.0); 
	private Usuario cristianMaldonado = new Usuario(1.81,87.0);
	private Usuario pedro = new Usuario("pedro",'M',LocalDate.of(2015, 04, 22), 1.50, 90.0, ACTIVA_EJERCICIO_ADICIONAL);
	
	
	@Before
	public void setUp(){
		Collection<String> preferencias = new ArrayList<>();
		preferencias.add("carne");
		pedro.setPreferenciasAlimenticias(preferencias);
	}

		
	@Test
	public void testConPeso65YAltura170() {
		assertEquals(fecheSena.indiceDeMasaCorporal(), 38.235 , 0.001);
	}
	
	@Test
	public void testConPeso102YAltura191() {
		assertEquals(federicoHipper.indiceDeMasaCorporal(), 53.403, 0.001);
	}

	@Test
	public void testConPeso96YAltura169() {
		assertEquals(arielFolino.indiceDeMasaCorporal(), 56.213 , 0.001);
	}	
	
     @Test
	public void testConPeso87YAltura181() {
	 assertEquals(cristianMaldonado.indiceDeMasaCorporal(), 48.066 , 0.001);
     }
		
	@Test
	public void esFechaDeNacimientoPedro(){
		assertTrue(pedro.esFechaDeNacimiento());
	}
	
	@Test
	public void testTieneCamposObligatoriosDePedro(){
		assertTrue(pedro.tieneCamposObligatorios());
	}
	
	@Test
	public void testTieneMasDeCuatroCaracteresPedro(){
		assertTrue(pedro.tieneMasDe(4));
	}
	
	@Test 
	public void testCumpleCondicionesPedro(){
		assertTrue(pedro.cumpleCondiciones());
	}
	
	@Test
	public void testEsValidoPedro(){
		assertTrue(pedro.esValido());
	}
	
	@Test
	public void testEsValidoFecheSena(){
		assertFalse(fecheSena.esValido());

	}
}
