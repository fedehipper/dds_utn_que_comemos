package grupo4.dds.receta;

import grupo4.dds.usuario.Usuario;

import java.util.List;

public interface Repositorio {

	public void agregarReceta(Receta unaReceta);

	public void quitarReceta(Receta unaReceta);

	public List<Receta> listarRecetasPara(Usuario unUsuario);

	public List<Receta> getRecetas();

}