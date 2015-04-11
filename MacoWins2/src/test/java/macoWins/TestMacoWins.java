package macoWins;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class TestMacoWins {

	private Prenda saco;
	private Prenda pantalon;
	private Prenda camisa;
	private MacoWins maco;
	private LocalDate fecha1;
	private LocalDate fecha2;
	
	@Before
	public void setUp() {
		
		saco = new Prenda( 300, "importada");
		pantalon = new Prenda( 200, "importada");
		camisa = new Prenda( 30, "nacional");
		maco = new MacoWins();
		
        //fecha1 04/04/2015
		fecha1 = LocalDate.of(2015, 04, 04);
        maco.vender(saco, 2, fecha1);
		maco.vender(pantalon, 4, fecha1);
		
		//fecha2 28/02/2016
		fecha2 = LocalDate.of(2016, 02, 28);
		maco.vender(camisa, 3, fecha2);
		maco.vender(pantalon, 3, fecha2);
		maco.vender(saco, 2, fecha2);
		
	}

	@Test
    public void testGananciaDelDiaConFecha1() {
        //04/04/2015
		assertTrue(maco.gananciasDelDia(fecha1) == 1820);	
    }
	
	@Test
    public void testGananciaDelDiaConFecha2() {
        //28/02/2016
		assertTrue(maco.gananciasDelDia(fecha2) == 1650);	
    }
    
	@Test
    public void testCantidadDeVentasConFecha1() {

		assertTrue((maco.ventasDeFecha(fecha1)).size() == 2);
    }

	@Test
    public void testCantidadDeVentasConFecha2() {
    	
		assertTrue((maco.ventasDeFecha(fecha2)).size() == 3);
    }

}
