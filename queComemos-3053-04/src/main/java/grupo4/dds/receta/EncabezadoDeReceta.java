package grupo4.dds.receta;

import queComemos.entrega3.dominio.Dificultad;

public class EncabezadoDeReceta {

	protected String nombreDelPlato;
	protected Temporada temporada;
	protected int totalCalorias;
	protected Dificultad dificultad;	
	
	public EncabezadoDeReceta() {
	}

	public EncabezadoDeReceta(String nombreDelPlato, Temporada temporada, Dificultad dificultad) {
		this.nombreDelPlato = nombreDelPlato;
		this.temporada = temporada;
		this.dificultad = dificultad;
	}
	
	public EncabezadoDeReceta(String nombreDelPlato, Temporada temporada, Dificultad dificultad, int calorias) {
		this.nombreDelPlato = nombreDelPlato;
		this.temporada = temporada;
		this.dificultad = dificultad;
		this.totalCalorias = calorias;
	}
	

	public int getTotalCalorias() {
		return totalCalorias;
	}

	public void setTotalCalorias(int totalCalorias) {
		this.totalCalorias = totalCalorias;
	}

	public String getNombreDelPlato() {
		return nombreDelPlato;
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}
	
}
