package grupo4.dds.receta;

public class EncabezadoDeReceta {

	protected String nombreDelPlato;
	protected Temporada temporada;
	protected String dificultad;	
	
	public EncabezadoDeReceta() {
	}

	public EncabezadoDeReceta(String nombreDelPlato, Temporada temporada,
			String dificultad) {
		this.nombreDelPlato = nombreDelPlato;
		this.temporada = temporada;
		this.dificultad = dificultad;
	}

	public String getNombreDelPlato() {
		return nombreDelPlato;
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public String getDificultad() {
		return dificultad;
	}
	
}
