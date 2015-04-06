package macoWins;

import macoWins.MacoWins;
import macoWins.Prenda;


public class Venta {
	
	private Prenda prendaVendida;
	private int cantidadVendida;
	private String fechaVenta;
	
	public Venta(Prenda prenda, int cantidad, String fecha){
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
	
	public String getFechaVenta() {
		return fechaVenta;
	}
	
	public void setFechaVenta(String fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	
	public void coincideFechaCon(String unaFecha) {
		return (this.fechaVenta = fechaVenta);
	}


}
