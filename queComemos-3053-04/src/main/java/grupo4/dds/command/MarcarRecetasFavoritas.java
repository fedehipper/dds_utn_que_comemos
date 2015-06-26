package grupo4.dds.command;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MarcarRecetasFavoritas implements Command {
	
	private Usuario usuario;
	private List<Receta> consulta = new ArrayList<>();
	
	public MarcarRecetasFavoritas(Usuario usuario ,List<Receta> consulta) {
		this.usuario = usuario;
		this.consulta = consulta;
	}
	
	public void ejecutar() {
		if (usuario.esMarcaFavorita())
			usuario.marcarRecetasComoFavoritas(consulta);
	}
	
}
