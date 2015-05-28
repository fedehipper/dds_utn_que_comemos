package grupo4.dds.receta;

import grupo4.dds.filtrosYProcesos.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RepositorioDeRecetas implements Repositorio {

	private List<Receta> recetas = new ArrayList<>();

	private List<Filtro> filtros = new ArrayList<>();
	private List<Receta> consultaDeRecetas = new ArrayList<>();
	
	public void agregarReceta(Receta unaReceta) {
		this.recetas.add(unaReceta);
	}
	
	public void quitarReceta(Receta unaReceta) {
		this.recetas.remove(unaReceta);
	}
	
	// entrega 2, punto 2
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {
		this.consultaDeRecetas = this.recetas;
		return recetas.stream().filter(r -> unUsuario.puedeVer(r)).collect(Collectors.toList());
	}
	
	public List<Receta> getRecetas() {
		return this.recetas;
	}
	
	public void setFiltro(Filtro filtro) {
		this.filtros.add(filtro);
	}
	
	public List<Receta> filtrarListaDeRecetas(Usuario usuario) {
		this.filtros.forEach(f -> f.filtrar(usuario, this));
		return this.consultaDeRecetas;
	}
	
	public void actualizarConsultaDeRecetas(List<Receta> recetaConFiltro) {
		this.consultaDeRecetas = interseccion(this.consultaDeRecetas, recetaConFiltro);
	}
	
	public List<Receta> interseccion(List<Receta> receta1, List<Receta> receta2){
		receta1.retainAll(receta2);
	    return receta1;
	}
	
	
	
}
