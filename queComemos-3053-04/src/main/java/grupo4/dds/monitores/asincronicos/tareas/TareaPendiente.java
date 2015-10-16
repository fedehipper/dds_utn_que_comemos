package grupo4.dds.monitores.asincronicos.tareas;

import grupo4.dds.persistencia.Persistible;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Tareas_Pendientes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_tarea")
public abstract class TareaPendiente implements Persistible {
	
	@Id
	@GeneratedValue
	@Column(name = "id_tarea")
	protected long id;
	
	@ManyToOne
	protected Usuario usuario;
	@ManyToMany
	protected List<Receta> resultadoConsulta;
	@ElementCollection
	protected List<String> parametros;
	
	public TareaPendiente() {}
	
	public TareaPendiente(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros) {
		this.usuario = usuario;
		this.resultadoConsulta = resultadoConsulta;
		this.parametros = parametros == null ? null : parametros.stream().map(Filtro::toString).collect(Collectors.toList());
	}
	
	public abstract void ejecutar();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
