package grupo4.dds.monitores.asincronicos;

import grupo4.dds.usuario.Usuario;

import java.util.function.Consumer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Tareas_Pendientes")
public class TareaPendiente {
	
	@Id
	@GeneratedValue
	@Column(name = "id_tarea")
	protected long id;
	
	@ManyToOne
	private Usuario usuario;
	@Transient
	private Consumer<Usuario> operacion;
	
	@SuppressWarnings("unused")
	private TareaPendiente() {}
	
	public TareaPendiente(Usuario usuario, Consumer<Usuario> operacion) {
		this.usuario = usuario;
		this.operacion = operacion;
	}
	
	public void ejecutar() {
		operacion.accept(usuario);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
