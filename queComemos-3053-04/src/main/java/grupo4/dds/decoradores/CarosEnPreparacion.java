package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Repositorio;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Ingrediente;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarosEnPreparacion implements Repositorio{

	private List<Ingrediente> ingredientesCaros = new ArrayList<>();
	private List<Receta> recetas = new ArrayList<>();
	private Repositorio repositorio;
	
	public CarosEnPreparacion(RepositorioDeRecetas repo){
		repositorio = repo;
	}
	
	public void agregarReceta(Receta unaReceta) {
		this.recetas.add(unaReceta);
	}
	
	public void quitarReceta(Receta unaReceta) {
		this.recetas.remove(unaReceta);
	}
	
	public List<Receta> getRecetas() {
		return this.recetas;
	}
	
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {		
	 	  return this.repositorio.listarRecetasParaUnUsuario(unUsuario).stream().filter(r -> r.interseccion(r.getIngredientes(),(this.ingredientesCaros)).isEmpty()).collect(Collectors.toList());
	}
	
	public void setIngredienteCaro(Ingrediente unIngrediente) {
		this.ingredientesCaros.add(unIngrediente);
	}
	
}
