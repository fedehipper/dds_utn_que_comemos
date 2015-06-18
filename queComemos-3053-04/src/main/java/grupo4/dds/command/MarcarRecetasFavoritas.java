package grupo4.dds.command;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class marcarRecetasFavoritas implements Command {
	
	private Usuario usuario;
	private List<Receta> consulta = new ArrayList<>();
	
	// instancio la accion en la clase que la usa
	public marcarRecetasFavoritas(Usuario usuario ,List<Receta> consulta) {
		this.usuario = usuario;
	}
	
	public void ejecutar() {
		usuario.marcarRecetasComoFavoritas(consulta);
	}
	

}
