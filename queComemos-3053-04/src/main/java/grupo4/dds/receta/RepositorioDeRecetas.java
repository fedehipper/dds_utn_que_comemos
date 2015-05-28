package grupo4.dds.receta;

import grupo4.dds.filtrosYProcesos.Filtro;
import grupo4.dds.filtrosYProcesos.Procesamiento;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RepositorioDeRecetas implements Repositorio {

	private List<Receta> recetas = new ArrayList<>();

	private List<Filtro> filtros = new ArrayList<>();
	private List<Receta> consultaDeRecetas = new ArrayList<>();
	
	private Procesamiento procesoFinal;
	
	
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
	
	
	public List<Receta> procesarListaDeRecetas(List<Receta> recetaConFiltros) {
		this.procesoFinal.procesar(recetaConFiltros, this);
		return this.consultaDeRecetas;
	}
	
	public void procesoFinalTerminado(List<Receta> recetasFinal) {
		this.consultaDeRecetas = recetasFinal;
	}
	
	public void actualizarConsultaDeRecetas(List<Receta> recetasConFiltro) {
		this.consultaDeRecetas = interseccion(this.consultaDeRecetas, recetasConFiltro);
	}
	
	public List<Receta> interseccion(List<Receta> receta1, List<Receta> receta2){
		receta1.retainAll(receta2);
	    return receta1;
	}
	
	public void setProceso(Procesamiento procesoFinal) {
		this.procesoFinal = procesoFinal;
	}
	
}
