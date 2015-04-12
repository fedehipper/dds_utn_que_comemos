package grupo4.dds;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static grupo4.dds.TipoDeImportacion.*;

public class TestPrendas {
	
	private static MacoWins maco = new MacoWins();
	private Prenda saco;
	private Prenda pantalon;
	private Prenda camisa;
	private Prenda zapato;
	private Prenda sombrero;

	
	@Test
	public void testPrecioBaseSaco() {
		saco = new Saco(NACIONAL, new Armani(), 5, maco);
		assertEquals(saco.precioBase(), 350, 0.1);
	}
	
	@Test
	public void testPrecioBasePantalon() {
		pantalon = new Pantalon(NACIONAL, new Sarkany(), 5, maco);
		assertEquals(pantalon.precioBase(), 255, 0.1);
	}

	@Test
	public void testPrecioBaseCamisa() {
		camisa = new Camisa(IMPORTADA, new Sarkany(), maco);
		assertEquals(camisa.precioBase(), 200, 0.1);
	}

	@Test
	public void testPrecioBaseSombrero() {
		sombrero = new Sombrero(IMPORTADA, new Armani(), 5, maco);
		assertEquals(sombrero.precioBase(), 155, 0.1);
	}

	
	@Test
	public void testPrecioBaseZapato() {
		zapato = new Zapatos(NACIONAL, new Armani(), 5, maco);
		assertEquals(zapato.precioBase(), 425, 0.1);
	}


	@Test
	public void testPrecioOriginalSaco() {
		saco = new Saco(NACIONAL, new Armani(), 5, maco);
		assertEquals(saco.precioOriginal(), 450, 0.1);
	}
	
	@Test
	public void testPrecioOriginalPantalon() {
		pantalon = new Pantalon(NACIONAL, new Sarkany(), 5, maco);
		assertEquals(pantalon.precioOriginal(), 355, 0.1);
	}

	@Test
	public void testPrecioOriginalCamisa() {
		camisa = new Camisa(IMPORTADA, new Sarkany(), maco);
		assertEquals(camisa.precioOriginal(), 390, 0.1);
	}

	@Test
	public void testPrecioOriginalSombrero() {
		sombrero = new Sombrero(IMPORTADA, new Armani(), 5, maco);
		assertEquals(sombrero.precioOriginal(), 331.5, 0.1);
	}

	
	@Test
	public void testPrecioOriginalZapato() {
		zapato = new Zapatos(NACIONAL, new Armani(), 5, maco);
		assertEquals(zapato.precioOriginal(), 525, 0.1);
	}


	
	@Test
	public void testPrecioFinalSaco() {
		saco = new Saco(NACIONAL, new Armani(), 5, maco);
		assertEquals(saco.precioFinal(), 742.5, 0.1);
	}
	
	@Test
	public void testPrecioFinalPantalon() {
		pantalon = new Pantalon(NACIONAL, new Sarkany(), 5, maco);
		assertEquals(pantalon.precioFinal(), 390.5, 0.1);
	}

	@Test
	public void testPrecioFinalCamisa() {
		camisa = new Camisa(IMPORTADA, new Sarkany(), maco);
		assertEquals(camisa.precioFinal(), 429, 0.1);
	}

	@Test
	public void testPrecioFinalSombrero() {
		sombrero = new Sombrero(IMPORTADA, new Armani(), 5, maco);
		assertEquals(sombrero.precioFinal(), 546.975, 0.1);
	}

	
	@Test
	public void testPrecioFinalZapato() {
		zapato = new Zapatos(NACIONAL, new Sarkany(), 5, maco);
		assertEquals(zapato.precioFinal(), 708.75, 0.1);
	}

}
