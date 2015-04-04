package grupo4.dds;

import java.util.Date;

public class Venta {

	private int cantidad;
	private Prenda prenda;
	private Date fecha;
	
	public Venta(int cantidad, Prenda prenda, Date fecha) {
		super();
		this.cantidad = cantidad;
		this.prenda = prenda;
		this.fecha = fecha;
	}
	
	public float precioTotal() {
		
		return prenda.precioFinal() * cantidad;
		
	}

	public Date getFecha() {
		return fecha;
	}
		
}
