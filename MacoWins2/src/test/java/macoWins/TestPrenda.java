package macoWins;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class TestPrenda {

	private Prenda saco;
	private Prenda pantalon;
	private Prenda camisa;
	private MacoWins maco;
	
	@Before
	public void setUp() {
		
		saco = new Prenda( 300, "importada");
		pantalon = new Prenda( 200, "importada");
		camisa = new Prenda( 30, "nacional");
		maco = new MacoWins();
	}
		
	@Test
    public void testPrecioFinalSaco() {
		
    	assertTrue(saco.precioFinal(maco) == 390 );
         }  
	
	@Test
    public void testPrecioFinalPantalon() {		
      assertTrue(pantalon.precioFinal(maco) == 260);
          }  

	@Test
    public void testPrecioFinalCamisa() {
        assertTrue(camisa.precioFinal(maco) == 30);      
    }  


}
