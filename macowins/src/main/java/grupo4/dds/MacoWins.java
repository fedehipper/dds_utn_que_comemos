package grupo4.dds;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

class MacoWins {
	private Collection<Venta> ventas = new ArrayList<>();
	
	
	
	double gananciasDelDia(LocalDate unaFecha) {
		return ventasDeFecha(unaFecha).stream().mapToDouble(Venta::precio).sum();
	}
	
	Collection<Venta> ventasDeFecha(LocalDate unaFecha){
		return (this.ventas.stream().filter(unaVenta -> (unaVenta.getFechaVenta() == unaFecha)).collect(Collectors.toList()));  
	}
	

	void vender(Prenda unaPrenda, int cantidad, LocalDate fecha) {
		Venta venta = new Venta(unaPrenda, cantidad, fecha);
		ventas.add(venta);
	}

	int getValorFijoDelNegocio() {
		int valorFijoDelNegocio;
		valorFijoDelNegocio = 100;
		return valorFijoDelNegocio;
	}

	
}
	