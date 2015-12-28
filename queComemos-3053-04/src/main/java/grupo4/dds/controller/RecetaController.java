package grupo4.dds.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import grupo4.dds.main.Routes;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.repositorios.RepositorioDeRecetas;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class RecetaController implements WithGlobalEntityManager,
		TransactionalOps {

	public ModelAndView mostrar(Request request, Response response) {

		Receta receta = RepositorioDeRecetas.instance().buscar(
				Long.parseLong(request.params("id")));
		long usuarioId = Routes.usuarioActual.getId();
		Usuario usuario = RepositorioDeUsuarios.instance().buscar(usuarioId);

		HashMap<String, Object> viewModel = new HashMap<>();

		viewModel.put("nombreDelPlato", receta.getNombreDelPlato());
		if (!Objects.isNull(receta.getCreador()))
			viewModel.put("creador", receta.getCreador());
		else
			viewModel.put("creador", "Nadie");
		viewModel.put("cantidadCalorias", receta.getTotalCalorias());
		viewModel.put("dificultad", receta.getDificultad());
		viewModel.put("ingredientes", receta.getIngredientes());
		viewModel.put("temporada", receta.getTemporada());
		viewModel.put("condimentos", receta.getCondimentos());
		viewModel.put("preparacion", receta.getPreparacion());
		viewModel.put("marcaFavorita", usuario.getMarcaFavorita());
		viewModel.put("condiciones", usuario.getCondiciones());
		viewModel.put("id", Long.parseLong(request.params("id")));

		viewModel.put("favorita", usuario.getHistorial().contains(receta));

		return new ModelAndView(viewModel, "receta.hbs");
	}

	public Receta recetaMostrada(long id) {
		return RepositorioDeRecetas.instance().buscar(id);
	}

	public ModelAndView nuevo(Request request, Response response) {

		Receta receta = RepositorioDeRecetas.instance().buscar(
				Long.parseLong(request.params("id")));

		long usuarioId = Routes.usuarioActual.getId();
		Usuario usuario = RepositorioDeUsuarios.instance().buscar(usuarioId);

		HashMap<String, Object> viewModel = new HashMap<>();

		List<Ingrediente> ingredientes= new ArrayList<Ingrediente>();
		
		ingredientes.addAll((entityManager().createQuery("FROM Ingrediente",Ingrediente.class).getResultList()));

		viewModel.put("nombreDelPlato", receta.getNombreDelPlato());
		viewModel.put("calorias", receta.getTotalCalorias());
		viewModel.put("temporada", receta.getTemporada());
		viewModel.put("dificultad", receta.getDificultad());
		viewModel.put("preparacion", receta.getPreparacion());
		viewModel.put("ingredientes", receta.getIngredientes());
		viewModel.put("condimentos", receta.getCondimentos());
		viewModel.put("favorita", usuario.getHistorial().contains(receta));
		viewModel.put("id", Long.parseLong(request.params("id")));
		viewModel.put("ingredientesAgregar", ingredientes);
		viewModel.put("condimentosAgregar", ingredientes);
		
		return new ModelAndView(viewModel, "editar.hbs");
	}

	public Void crear(Request request, Response response) {

		Receta receta = RepositorioDeRecetas.instance().buscar(
				Long.parseLong(request.params("id")));
		String nombre = request.queryParams("nombreDelPlato");
		String calorias = request.queryParams("calorias");
		String dificultad = request.queryParams("dificultad");
		String temporada = request.queryParams("temporada");
		String favorita = request.queryParams("fav");
		String condimento = request.queryParams("condimento");
		String nombreIngrediente = request.queryParams("nombreIngrediente");
		String dosis = request.queryParams("dosis");
		String preparacion = request.queryParams("preparacion");
		String ingredientePorEliminar = request
				.queryParams("ingredienteSeleccionado");
		String condimentoPorEliminar = request
				.queryParams("condimentoSeleccionado");

		long usuarioId = Routes.usuarioActual.getId();
		Usuario usuario = RepositorioDeUsuarios.instance().buscar(usuarioId);

		withTransaction(() -> {

			if (receta.getOrigen() == "Publica") {
				receta.crearReceta(nombre, dificultad, temporada, calorias,
						preparacion, favorita, usuario, condimento,
						nombreIngrediente, dosis, receta.getIngredientes(),
						receta.getCondimentos(), ingredientePorEliminar,
						condimentoPorEliminar);

			} else {
				receta.actualizarReceta(nombre, dificultad, temporada,
						calorias, preparacion, favorita, condimento,
						nombreIngrediente, dosis, ingredientePorEliminar,
						condimentoPorEliminar);
			}
		});

		response.redirect("/receta/" + Long.parseLong(request.params("id")));
		return null;
	}

	public List<String> getTemporadas() {

		HashMap<Integer, String> temporadasBase = new HashMap<>();
		List<Integer> idTemporada = entityManager().createQuery(
				"select distinct temporada from Receta", Integer.class)
				.getResultList();
		idTemporada.forEach(t -> temporadasBase.put(t,
				this.dameStringTemporada(t)));

		return temporadasBase.values().stream().collect(Collectors.toList());

	}

	public String dameStringTemporada(Integer valor) {

		switch (valor) {

		case 0:
			return "INVIERNO";
		case 1:
			return "VERANO";
		case 2:
			return "OTONIO";
		case 3:
			return "PRIMAVERA";
		case 4:
			return "TODOELANIO";

		default:
			return "";
		}

	}

}
