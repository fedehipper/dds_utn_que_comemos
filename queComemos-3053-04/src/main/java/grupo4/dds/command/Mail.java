package grupo4.dds.command;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class Mail {

	private Usuario usuario;
	private List<Filtro> filtros;
	private List<Receta> consulta;

	public Mail() {
	}

	public Mail(Usuario usuario, List<Receta> consulta, List<Filtro> filtros) {
		this.usuario = usuario;
		this.consulta = consulta;
		this.filtros = filtros;

	}
	
	public void enviarMail(MailSender mailSender) {
		this.crearMensaje();
		mailSender.enviarMail(this);
	}

	public String crearMensaje() {
		if (usuario != null && consulta != null && filtros != null) {
			String consultaFiltro = filtros.stream().map(f -> f.getNombre())
					.collect(Collectors.toList()).toString();
			String consultaTexto = consulta.stream()
					.map(c -> c.getNombreDelPlato())
					.collect(Collectors.toList()).toString();
			return usuario.getNombre() + consultaTexto + consultaFiltro;
		} else {
			throw new RuntimeException();
		}
	}



}