package grupo4.dds;

import java.time.LocalDate;

class Venta {	
	private Prenda prendaVendida;
	private int cantidadVendida;
	private LocalDate fechaVenta;
	
	Venta(Prenda prenda, int cantidad, LocalDate fecha){
		prendaVendida = prenda;
		cantidadVendida = cantidad;
		fechaVenta = fecha;
	}
	
	double precio() {
		return prendaVendida.precioFinal() * cantidadVendida;
	}
	
	LocalDate getFechaVenta() {
		return fechaVenta;
	}

}