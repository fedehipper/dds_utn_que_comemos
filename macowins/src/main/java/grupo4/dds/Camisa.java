package grupo4.dds;

class Camisa extends Prenda {

	Camisa(TipoDeImportacion tipoDeImportacion, Marca marca, MacoWins negocio) {
		super(tipoDeImportacion, marca, negocio);
	}

	@Override
	protected double precioBase() {
		return 200;
	}

}