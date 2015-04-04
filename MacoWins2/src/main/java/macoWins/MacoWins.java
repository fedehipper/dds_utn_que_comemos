package macoWins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MacoWins {
	private int valorFijoDelNegocio;
	
	private Collection<Venta> ventas = new ArrayList<>();
	
	
	public int valorFijoDelNegocio() {
		return valorFijoDelNegocio;
	}
	
	public double gananciasDelDia(String unaFecha) {
		return (this.ventasDeFecha(unaFecha).stream().mapToDouble(venta -> venta.precio(this)).sum());
	}
	
	public Collection<Venta> ventasDeFecha(String unaFecha){
		return (this.ventas.stream().filter(unaVenta -> (unaVenta.getFechaVenta() == unaFecha)).collect(Collectors.toList()));  
	}
	
	public void vender(Prenda unaPrenda, int cantidad, String fecha) {
		Venta venta = new Venta(unaPrenda, cantidad, fecha);
		ventas.add(venta);
	}
				
	public void cambiarValorFijoDelNegocio(int valorFijo) {
		valorFijoDelNegocio = valorFijo;
	}
	
}
	