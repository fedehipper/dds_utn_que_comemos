package macoWins;

public class Venta {
	
	private Prenda prendaVendida;
	private int cantidadVendida;
	private String fechaVenta;
	
	public Venta(Prenda prendaVendida, int cantidadVendida, String fechaVenta) {
		
		this.prendaVendida = prendaVendida;
		this.cantidadVendida = cantidadVendida;
		this.fechaVenta = fechaVenta;
	}

	public double precio() { 
		return ((prendaVendida.precioFinal()) * cantidadVendida);
	}

	public boolean coincideFechaCon(String unaFecha) {
		return fechaVenta.equals(unaFecha);
	}

}
