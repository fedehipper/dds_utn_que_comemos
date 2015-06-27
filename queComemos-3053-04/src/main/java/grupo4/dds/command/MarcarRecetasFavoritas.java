package grupo4.dds.command;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MarcarRecetasFavoritas implements Command {
	
	private List<Receta> consulta = new ArrayList<>();
	
	public MarcarRecetasFavoritas(List<Receta> consulta) {
		this.consulta = consulta;
	}
	
	public void ejecutar(Usuario usuario) {
		if (usuario.marcarFavoritaEstaActivada())
			usuario.marcarRecetasComoFavoritas(consulta);
	}
	
}
