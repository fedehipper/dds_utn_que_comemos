package macoWins;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestPrenda {

	private Prenda saco;
	private Prenda pantalon;
	private Prenda camisa;
	
	@Before
	public void setUp() {
		
		saco = new Prenda( 300, "importada");
		pantalon = new Prenda( 200, "importada");
		camisa = new Prenda( 30, "nacional");
		maco = new MacoWins
	}
		
	@Test
    public void testPrecioFinal() {
		
    	assertTrue(saco.precioFinal(maco) == 390 );
        assertTrue(pantalon.precioFinal(maco) == 260);
        assertTrue(camisa.precioFinal(maco) == 30);
        
    }  

}
