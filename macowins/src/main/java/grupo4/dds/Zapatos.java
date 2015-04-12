package grupo4.dds;

public class Zapatos extends Prenda {
	
	private int talle;
	
	public double precioBase(){
		return (400 + 5*talle);
	}

	public Zapatos(TipoDeImportacion tipoDeImportacion, Marca marca, int numDeTalle, MacoWins negocio) {
		super(tipoDeImportacion, marca, negocio);
		talle = numDeTalle;
	}

}
