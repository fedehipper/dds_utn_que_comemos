package grupo4.dds;

import java.time.LocalDate;


public class Venta {
	
	private Prenda prendaVendida;
	private int cantidadVendida;
	private LocalDate fechaVenta;
	
	public Venta(Prenda prenda, int cantidad, LocalDate fecha){
		prendaVendida = prenda;
		cantidadVendida = cantidad;
		fechaVenta = fecha;
	
	}
	
	public double precio() { 
		return ((prendaVendida.precioFinal()) * cantidadVendida);
	}
	
	
	public LocalDate getFechaVenta() {
		return fechaVenta;
	}
	
	public void setFechaVenta(LocalDate fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	
	


}