package grupo4.dds.filtrosYProcesos;

import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

public interface Filtro {

	public void filtrar(Usuario usuario, RepositorioDeRecetas repositorioDeRecetas);

}
