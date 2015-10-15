package grupo4.dds.monitores.asincronicos.mail;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class Mail {

	private Usuario usuario;
	private List<String> parametros;
	private List<Receta> consulta;

	public Mail(Usuario usuario, List<Receta> consulta, List<String> parametros) {
		this.usuario = usuario;
		this.consulta = consulta;
		this.parametros = parametros;
	}

	public void enviarMail() {
		this.crearMensaje();
		EMailer.enviarMail(this);
	}

	public String crearMensaje() {
		String consultaFiltro = parametros.toString();
		String consultaTexto = consulta.stream()
				.map(c -> c.getNombreDelPlato()).collect(Collectors.toList())
				.toString();
		return usuario.getNombre() + consultaTexto + consultaFiltro;
	}
}