package grupo4.dds.usuario.condicion;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "Condiciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_condicion")
public abstract class Condicion {
	
	@Id
	@Column(name = "id_condicion")
	protected long id;
	
	public String nombre;

	public abstract boolean esValidaCon(Usuario usuario);

	public abstract boolean subsanaCondicion(Usuario usuario);

	public abstract boolean esRecomendable(Receta receta);
	
	public void setNombre(String n){
		nombre = n;
	}
	
	public String getNombre(){
		return nombre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
