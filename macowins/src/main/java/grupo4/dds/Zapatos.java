package grupo4.dds;

class Zapatos extends Prenda {

	private int talle;

	@Override
	public double precioBase(){
		return 400 + 5 * this.talle;
	}

	Zapatos(TipoDeImportacion tipoDeImportacion, Marca marca, int numDeTalle, MacoWins negocio) {
		super(tipoDeImportacion, marca, negocio);
		this.talle = numDeTalle;
	}

}
