package grupo4.dds;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestUsuario {

    private Usuario fecheSena = new Usuario(1.70,65);
    
    
		
	@Test
	public void testConPeso65YAltura() {
		assertEquals(fecheSena.indiceDeMasaCorporal(), 38.235 , 0.001);
	}

}
