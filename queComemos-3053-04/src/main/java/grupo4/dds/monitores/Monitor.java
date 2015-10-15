package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "Monitores")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_monitor")
public abstract class Monitor {

	@Id
	@GeneratedValue
	@Column(name = "id_monitor")
	protected long id;

	public abstract void notificarConsulta(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros);

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
