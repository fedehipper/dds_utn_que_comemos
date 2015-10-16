package grupo4.dds.usuario.gestionDePerfiles;

import grupo4.dds.persistencia.Persistible;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Solicitudes_Alta_Usuarios")
public class SolicitudAltaUsuario implements Persistible {

	@Id
	@GeneratedValue()
	@Column(name = "id_solicitud")
	private long id;	
	
	@OneToOne(cascade = CascadeType.PERSIST)
	Usuario usuario;
	String detalle;
	boolean estado;
	
	@SuppressWarnings("unused")
	private SolicitudAltaUsuario(){}
	
	public SolicitudAltaUsuario(Usuario usuario) {
		this.usuario = usuario;
		this.estado = false;
	}

	public void aceptada() {
		estado = true;
		RepositorioDeUsuarios.instance().add(usuario);
	}

	public void rechazada(String motivo) {
		estado = false;
		detalle = motivo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public boolean estado() {
		return estado;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
