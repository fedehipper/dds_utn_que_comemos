package macoWins;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestMacoWins {

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
		
        //fecha 04/04/2015
        maco.vender(saco, 2, "04/04/2015");
		maco.vender(pantalon, 4, "04/04/2015");
		
		//fecha 28/02/2016
		maco.vender(camisa, 3, "28/02/2016");
		maco.vender(pantalon, 3, "28/02/2016");
		maco.vender(saco, 2, "28/02/2016");
		
	}

	@Test
    public void testGananciaDelDia() {
        
		assertTrue(maco.gananciasDelDia("04/04/2015") == 1820);
		assertTrue(maco.gananciasDelDia("28/02/2016") == 1650);
		
    }
    
	@Test
    public void testCantidadDeVentas() {
    	
		assertTrue((maco.ventasDeFecha("28/02/2016")).size() == 3);
		assertTrue((maco.ventasDeFecha("04/04/2015")).size() == 2);
    }

}
