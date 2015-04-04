package grupo4.dds;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TestVentas {
	
	private Ventas ventas;
	private Date fechaDePrueba;
	private Date fechaDePrueba2;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		
		ventas = new Ventas();
		fechaDePrueba = new Date(2015, 03, 03);
		fechaDePrueba2 = new Date(2015, 03, 04);
		
		Prenda.fijarValorDeNegocio(50f);
		
		ventas.agregarVenta(new Venta(4, new Saco(false), fechaDePrueba));
		ventas.agregarVenta(new Venta(2, new Pantalon(false), fechaDePrueba));
		ventas.agregarVenta(new Venta(6, new Camisa(false), fechaDePrueba));
		
		ventas.agregarVenta(new Venta(4, new Saco(true), fechaDePrueba2));
		ventas.agregarVenta(new Venta(2, new Pantalon(true), fechaDePrueba2));
		ventas.agregarVenta(new Venta(6, new Camisa(true), fechaDePrueba2));
		
	}

	@Test
	public void testGananciasDelDia() {
		
		assertEquals(ventas.gananciasDelDia(fechaDePrueba), 3500f,0.1);
		assertEquals(ventas.gananciasDelDia(fechaDePrueba2), 4550f, 0.1);
		
	}
	
}
