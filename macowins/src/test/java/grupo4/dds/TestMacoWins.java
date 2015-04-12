package grupo4.dds;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import static grupo4.dds.TipoDeImportacion.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMacoWins {
	
	private static MacoWins maco = new MacoWins();
	private static Prenda saco = new Saco(NACIONAL, new Armani(), 5, maco);
	private static Prenda pantalon = new Pantalon(NACIONAL, new Sarkany(), 5, maco);
	private static Prenda camisa = new Camisa(IMPORTADA, new Sarkany(), maco);
	private static Prenda zapato = new Zapatos(NACIONAL, new Sarkany(), 5, maco);
	private static Prenda sombrero = new Sombrero(IMPORTADA, new Armani(), 5, maco);
	private static LocalDate fecha1 = LocalDate.of(2015, 04, 04);
	private static LocalDate fecha2 = LocalDate.of(2016, 02, 28);
	

	@BeforeClass
	 public static void setUpBeforeClass() throws Exception {
		
        //fecha1 04/04/2015
        maco.vender(saco, 2, fecha1);
		maco.vender(pantalon, 4, fecha1);
		maco.vender(sombrero, 2, fecha1);
		maco.vender(camisa, 3, fecha1);
		
		//fecha2 28/02/2016
		maco.vender(camisa, 3, fecha2);
		maco.vender(pantalon, 3, fecha2);
		maco.vender(zapato, 2, fecha2);
				
	}

	@Test
	public void testGananciasDelDia() {
		assertEquals(maco.gananciasDelDia(fecha1), 5427.95, 0.1);
		assertEquals(maco.gananciasDelDia(fecha2), 3876, 0.1);
	}

	@Test
	public void testVentasDeFecha() {
		assertEquals(maco.ventasDeFecha(fecha1).size(), 4);
		assertEquals(maco.ventasDeFecha(fecha2).size(), 3);
	}

}
