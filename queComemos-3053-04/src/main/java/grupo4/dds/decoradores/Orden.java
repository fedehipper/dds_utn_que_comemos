package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public interface Orden extends Filtro {
	
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario);
	
}
