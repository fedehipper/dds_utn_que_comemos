package grupo4.dds.receta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import queComemos.entrega3.dominio.Dificultad;

@Entity
public class EncabezadoDeReceta {

	@Id
	@GeneratedValue
	@Column(name = "ENCABEZADO_ID")
	private long encabezadoId;
	
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

	public void setNombreDelPlato(String nombreDelPlato) {
		this.nombreDelPlato = nombreDelPlato;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}

	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
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
