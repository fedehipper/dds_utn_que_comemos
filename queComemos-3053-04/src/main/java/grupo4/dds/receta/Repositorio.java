package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.List;

public interface Repositorio {

	public void agregarReceta(Receta unaReceta);

	public void quitarReceta(Receta unaReceta);

	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario);

	public List<Receta> getRecetas();

}