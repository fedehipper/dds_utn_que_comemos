package grupo4.dds.repositorios;

import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.usuario.BuilderUsuario;
import grupo4.dds.usuario.Usuario;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import queComemos.entrega3.repositorio.BusquedaRecetas;
import queComemos.entrega3.repositorio.RepoRecetas;

public class RepositorioRecetasExterno {

	private static final RepositorioRecetasExterno self = new RepositorioRecetasExterno();
	private RepoRecetas repoExterno = new RepoRecetas();
	
	/* Constructores */
	
	public static RepositorioRecetasExterno get() {
		return self;
	}

	protected RepositorioRecetasExterno() {}

	/* Servicios */
	
	public List<Receta> getRecetas() {
		Type tipoLista = new TypeToken<ArrayList<queComemos.entrega3.dominio.Receta>>(){}.getType();
		List<queComemos.entrega3.dominio.Receta> recetasExternas = new Gson().fromJson(repoExterno.getRecetas(new BusquedaRecetas()), tipoLista);
		
		return recetasExternas.stream().map(re -> importarReceta(re)).collect(Collectors.toList());
	}

	/* Servicios privados */
	
	public Receta importarReceta(queComemos.entrega3.dominio.Receta recetaExterna) {

		EncabezadoDeReceta encabezado = new EncabezadoDeReceta(
				recetaExterna.getNombre(), null,
				recetaExterna.getDificultadReceta(),
				recetaExterna.getTotalCalorias());
		
		List<Ingrediente> ingredientes = recetaExterna.getIngredientes()
				.stream().map(nombre -> Ingrediente.nuevaComida(nombre))
				.collect(Collectors.toList());
		
		Usuario usuario = RepositorioDeUsuarios.instance().get(BuilderUsuario.prototipo(recetaExterna.getAutor()));
		
		return (new BuilderReceta()).creador(usuario).encabezado(encabezado).ingredientes(ingredientes).build();
	}

}
