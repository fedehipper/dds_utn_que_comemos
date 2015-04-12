package grupo4.dds;

public class Sarkany implements Marca{
	
	public double precioFinal(Prenda prenda) {
		double precioOriginal ;
		
		return (precioOriginal = prenda.precioOriginal() ) > 500 ? precioOriginal*1.35 : precioOriginal*1.1;

		}

}
