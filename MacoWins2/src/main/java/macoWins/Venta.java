package macoWins;

import java.time.LocalDate;

import macoWins.MacoWins;
import macoWins.Prenda;


public class Venta {
	
	private Prenda prendaVendida;
	private int cantidadVendida;
	private LocalDate fechaVenta;
	
	public Venta(Prenda prenda, int cantidad, LocalDate fecha){
		prendaVendida = prenda;
		cantidadVendida = cantidad;
		fechaVenta = fecha;
	}
	
	public double precio(MacoWins maco) { 
		return ((prendaVendida.precioFinal(maco)) * cantidadVendida);
	}
	
	public Prenda getPrendaVendida() {
		return prendaVendida;
	}
	
	public void setPrendaVendida(Prenda prendaVendida) {
		this.prendaVendida = prendaVendida;
	}
	
	public int getCantidad() {
		return cantidadVendida;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidadVendida = cantidad;
	}
	
	public LocalDate getFechaVenta() {
		return fechaVenta;
	}
	
	public void setFechaVenta(LocalDate fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	
	


}
