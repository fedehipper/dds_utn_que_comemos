package grupo4.dds;

public class Camisa extends Prenda {

	public Camisa(TipoDeImportacion tipoDeImportacion, Marca marca, MacoWins negocio) {
		super(tipoDeImportacion, marca, negocio);

	}

	@Override
	protected double precioBase() {
		
		return 200;
	}

}
