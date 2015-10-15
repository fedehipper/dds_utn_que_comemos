package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Usuarios")
public class ContadorReceta {

	@Id
	@GeneratedValue
	@Column(name = "id_usuario")
	private long id;

	private Receta receta;
	private int cantConsultas;
	
	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public int getCantConsultas() {
		return cantConsultas;
	}

	public void setCantConsultas(int cantConsultas) {
		this.cantConsultas = cantConsultas;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}	
	
}
