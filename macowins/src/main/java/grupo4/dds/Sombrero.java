package grupo4.dds;

public class Sombrero extends Prenda {
	
	
	private double coeficienteDeMetrosexualidad;
	
	public double precioBase(){
		return (150+coeficienteDeMetrosexualidad);
	}

	public Sombrero(TipoDeImportacion tipoDeImportacion, Marca marca, double coeficiente, MacoWins negocio) {
		 super(tipoDeImportacion, marca, negocio);
		coeficienteDeMetrosexualidad = coeficiente;
	}

}
