package grupo4.dds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Ventas {
	
	private List<Venta> ventas = new ArrayList<Venta>();
	
	public float gananciasDelDia(Date fecha) {
		
		return ventasDelDia(fecha).stream().map(Venta::precioTotal).reduce(0f, (a,b)->a+b);
		
	}
	
	private List<Venta> ventasDelDia(Date fecha) {
		
		return ventas.stream().filter(venta -> venta.getFecha().equals(fecha)).collect(Collectors.toList());
		
	}

	public void agregarVenta(Venta venta) {
		
		ventas.add(venta);
		
	}
	
}
