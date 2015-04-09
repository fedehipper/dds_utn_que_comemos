package macoWins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MacoWins {
	
	private static int ValorFijo;
	private Collection<Venta> ventas = new ArrayList<>();
	
	public double gananciasDelDia(String unaFecha) {
		return (ventasDeFecha(unaFecha).stream().mapToDouble(venta -> venta.precio()).sum());
	}
	
	public Collection<Venta> ventasDeFecha(String unaFecha){
		return ventas.stream().filter(unaVenta -> (unaVenta.coincideFechaCon(unaFecha))).collect(Collectors.toList());  
	}
	
	public void vender(Prenda unaPrenda, int cantidad, String fecha) {
		Venta venta = new Venta(unaPrenda, cantidad, fecha);
		ventas.add(venta);
	}
				
	public static int getValorFijo() {
		return ValorFijo;
	}

	public static void setValorFijo(int valorFijo) {
		ValorFijo = valorFijo;
	}
	
}
	
