package grupo4.dds;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestUsuario {

    private Usuario fecheSena = new Usuario(1.70, 65);
    private Usuario federicoHipper = new Usuario(1.91, 102);
	private Usuario arielFolino = new Usuario(1.69,95);    
		
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
}
