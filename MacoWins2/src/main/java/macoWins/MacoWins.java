package macoWins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MacoWins {
	private int valorFijo;
	
	private Collection<Venta> ventas = new ArrayList<>();
	
	public double gananciasDelDia(String unaFecha) {
		return (this.ventasDeFecha(unaFecha).stream().mapToDouble(venta -> venta.precio(this.valorFijo)).sum());
	}
	
	public Collection<Venta> ventasDeFecha(String unaFecha){
		return (this.ventas.stream().filter(unaVenta -> (unaVenta.coincideFechaCon(unaFecha))).collect(Collectors.toList()));  
	}
	
	public void vender(Prenda unaPrenda, int cantidad, String fecha) {
		Venta venta = new Venta(unaPrenda, cantidad, fecha);
		ventas.add(venta);
	}
				
	public void cambiarValorFijo(int valor) {
		valorFijo = valor;
	}
	
}
	
