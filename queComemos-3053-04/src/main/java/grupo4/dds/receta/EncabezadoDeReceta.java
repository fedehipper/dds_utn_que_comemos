package grupo4.dds.receta;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;

import queComemos.entrega3.dominio.Dificultad;

@Embedded
public class EncabezadoDeReceta {
    
	protected String nombreDelPlato;
	@Transient
	protected Temporada temporada;
	@Transient
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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dificultad == null) ? 0 : dificultad.hashCode());
		result = prime * result
				+ ((nombreDelPlato == null) ? 0 : nombreDelPlato.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EncabezadoDeReceta))
			return false;
		EncabezadoDeReceta other = (EncabezadoDeReceta) obj;
		if (dificultad != other.dificultad)
			return false;
		if (nombreDelPlato == null) {
			if (other.nombreDelPlato != null)
				return false;
		} else if (!nombreDelPlato.equals(other.nombreDelPlato))
			return false;
		return true;
	}
	
}
