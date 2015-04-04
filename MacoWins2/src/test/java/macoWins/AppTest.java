package macoWins;

import macoWins.MacoWins;
import macoWins.Prenda;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	
	
	/* instancia de prendas*/
	Prenda saco = new Prenda( 300,  "importada");
	Prenda pantalon = new Prenda( 200,  "importada");
	Prenda camisa = new Prenda( 30,  "Nacional");
	
	MacoWins maco = new MacoWins();
		
    public AppTest( String testName )
    {
        super( testName );
    }
    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testPrecioFinal()
    {
    	assertTrue( saco.precioFinal(maco) == 390 );
        assertTrue(pantalon.precioFinal(maco) == 260);
        assertTrue(camisa.precioFinal(maco) == 30);
    }  
    
    public void testGananciaDelDia() {
    	
        //fecha 04/04/2015
        maco.vender(saco, 2, "04/04/2015");
		maco.vender(pantalon, 4, "04/04/2015");
		
		assertTrue(maco.gananciasDelDia("04/04/2015") == 1820);
		
		//fecha 28/02/2016
		maco.vender(camisa, 3, "28/02/2016");
		maco.vender(pantalon, 3, "28/02/2016");
		maco.vender(saco, 2, "28/02/2016");
        
		assertTrue(maco.gananciasDelDia("28/02/2016") == 1650);
		
    }
    
    public void testCantidadDeVentas() {
    	this.testGananciaDelDia();    	
		assertTrue((maco.ventasDeFecha("28/02/2016")).size() == 3);
		assertTrue((maco.ventasDeFecha("04/04/2015")).size() == 2);
    }
}
