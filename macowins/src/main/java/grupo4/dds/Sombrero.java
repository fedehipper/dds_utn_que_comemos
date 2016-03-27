package grupo4.dds;

class Sombrero extends Prenda {
	
	private double coeficienteDeMetrosexualidad;
	
	public double precioBase(){
		return 150 + coeficienteDeMetrosexualidad;
	}

	Sombrero(TipoDeImportacion tipoDeImportacion, Marca marca, double coeficiente, MacoWins negocio) {
		super(tipoDeImportacion, marca, negocio);
		coeficienteDeMetrosexualidad = coeficiente;
	}

}
