package macoWins;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MacoWins {
	private int valorFijoDelNegocio;
	private Collection<Venta> ventas = new ArrayList<>();
	
	
	
	public double gananciasDelDia(LocalDate unaFecha) {
		return (this.ventasDeFecha(unaFecha).stream().mapToDouble(venta -> venta.precio(this)).sum());
	}
	
	public Collection<Venta> ventasDeFecha(LocalDate unaFecha){
		return (this.ventas.stream().filter(unaVenta -> (unaVenta.getFechaVenta() == unaFecha)).collect(Collectors.toList()));  
	}
	

	
	public void vender(Prenda unaPrenda, int cantidad, LocalDate fecha) {
		
		Venta venta = new Venta(unaPrenda, cantidad, fecha);
		ventas.add(venta);
	}
				

	public int getValorFijoDelNegocio() {
		return valorFijoDelNegocio;
	}

	public void cambiarValorFijoDelNegocio(int valorFijo) {
		valorFijoDelNegocio = valorFijo;
	}
	
}
	
