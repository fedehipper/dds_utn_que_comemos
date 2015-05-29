package grupo4.dds;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import grupo4.dds.decoradores.CarosEnPreparacion;
import grupo4.dds.decoradores.CondicionesUsuario;
import grupo4.dds.decoradores.DiezPrimeros;
import grupo4.dds.decoradores.ExcesoCalorias;
import grupo4.dds.decoradores.LeGustaAlUsuario;
import grupo4.dds.decoradores.Orden;
import grupo4.dds.decoradores.OrdenAlfabetico;
import grupo4.dds.decoradores.OrdenCalorias;
import grupo4.dds.decoradores.ResultadosPares;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Ingrediente;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Vegano;

public class TestDecoradores {

	private Usuario fecheSena;
	private EncabezadoDeReceta encabezado1;
	private EncabezadoDeReceta encabezado2;
	private EncabezadoDeReceta encabezado3;
	private EncabezadoDeReceta encabezado4;
	private EncabezadoDeReceta encabezado5;
	private Ingrediente carne;
	private Ingrediente fruta;
	private Ingrediente salmon;
	private Ingrediente huevo;
	private Ingrediente papa;
	private Receta receta1;
	private RecetaPublica receta2;
	private RecetaPublica receta3;
	private RecetaPublica receta4;
	private RecetaPublica receta5;
	private RecetaPublica receta6;
	private RecetaPublica receta7;
	private RecetaPublica receta8;
	private RecetaPublica receta9;
	private RecetaPublica receta10;
	private RecetaPublica receta11;
	private RecetaPublica receta12;
	private RepositorioDeRecetas unRepo;

	@Before
	public void setup() {
		fecheSena = new Usuario("Feche Sena", null, 1.70f, 65.0f, null);
		encabezado1 = new EncabezadoDeReceta("sopa", null, null, 600);
		encabezado2 = new EncabezadoDeReceta("pollo", null, null, 300);
		encabezado3 = new EncabezadoDeReceta("pure", null, null, 100);
		encabezado4 = new EncabezadoDeReceta("salmon", null, null, 200);
		encabezado5 = new EncabezadoDeReceta("papa", null, null, 999);
		carne = new Ingrediente("carne", 0f);
		fruta = new Ingrediente("fruta", 0f);
		huevo = new Ingrediente("huevo", 0f);
		salmon = new Ingrediente("salmon", 0f);
		receta1 = new Receta(fecheSena, encabezado1, null);
		receta2 = new RecetaPublica(encabezado2, null);
		receta3 = new RecetaPublica(encabezado3, null);
		receta4 = new RecetaPublica(encabezado4, null);
		receta5 = new RecetaPublica(encabezado5, null);
		receta6 = new RecetaPublica(encabezado3, null);
		receta7 = new RecetaPublica(encabezado4, null);
		receta8 = new RecetaPublica(encabezado2, null);
		receta9 = new RecetaPublica(encabezado3, null);
		receta10 = new RecetaPublica(encabezado4, null);
		receta11 = new RecetaPublica(encabezado3, null);
		receta12 = new RecetaPublica(encabezado4, null);
		unRepo = new RepositorioDeRecetas();
		unRepo.agregarReceta(receta1);
		unRepo.agregarReceta(receta2);
		unRepo.agregarReceta(receta3);
		unRepo.agregarReceta(receta4);
		unRepo.agregarReceta(receta5);
		unRepo.agregarReceta(receta6);
		unRepo.agregarReceta(receta7);
		unRepo.agregarReceta(receta8);
		unRepo.agregarReceta(receta9);
		unRepo.agregarReceta(receta10);
		unRepo.agregarReceta(receta11);
		unRepo.agregarReceta(receta12);
	}

	@Test
	public void testFiltroExcesoDeCalorias() {
		RepositorioDeRecetas rR = new RepositorioDeRecetas();

		ExcesoCalorias exceso = new ExcesoCalorias(rR);

		rR.agregarReceta(receta1);
		rR.agregarReceta(receta2);
		rR.agregarReceta(receta3);

		List<Receta> aux = Stream.of(receta2, receta3).collect(
				Collectors.toList());

		assertEquals(exceso.listarRecetasParaUnUsuario(fecheSena), aux);
	}

	@Test
	public void testFiltroDeRecetasQueCumplenLasCondicionesDelUsuario() {

		RepositorioDeRecetas rR = new RepositorioDeRecetas();

		CondicionesUsuario filtroCondicion = new CondicionesUsuario(rR);

		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(fruta);

		rR.agregarReceta(receta1);
		rR.agregarReceta(receta2);
		rR.agregarReceta(receta3);

		Usuario ariel = new Usuario("ariel", null, 89.0f, 78f, null);
		GrupoUsuarios grupo = new GrupoUsuarios("grupo");
		grupo.agregarUsuario(fecheSena);
		grupo.agregarUsuario(ariel);
		ariel.agregarGrupo(grupo);
		fecheSena.agregarGrupo(grupo);

		List<Receta> aux = Stream.of(receta2, receta3).collect(
				Collectors.toList());

		Vegano vegano = new Vegano();
		ariel.agregarCondicion(vegano);

		assertEquals(filtroCondicion.listarRecetasParaUnUsuario(ariel), aux);
	}

	@Test
	public void testFiltroNoLeGustaAlUsuarioElNombreDelPlato() {

		RepositorioDeRecetas rR = new RepositorioDeRecetas();

		LeGustaAlUsuario filtroLeGusta = new LeGustaAlUsuario(rR);

		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(huevo);

		rR.agregarReceta(receta1);
		rR.agregarReceta(receta2);
		rR.agregarReceta(receta3);

		fecheSena.agregarComidaQueLeDisgusta(fruta);

		List<Receta> aux = Stream.of(receta1, receta3).collect(
				Collectors.toList());

		assertEquals(filtroLeGusta.listarRecetasParaUnUsuario(fecheSena), aux);
	}

	@Test
	public void testFiltroIngredientesCaronEnPreparacion() {

		RepositorioDeRecetas rR = new RepositorioDeRecetas();

		CarosEnPreparacion filtroCaros = new CarosEnPreparacion(rR);

		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(huevo);
		receta4.agregarIngrediente(salmon);

		rR.agregarReceta(receta1);
		rR.agregarReceta(receta2);
		rR.agregarReceta(receta3);
		rR.agregarReceta(receta4);

		filtroCaros.setIngredienteCaro(salmon);
		filtroCaros.setIngredienteCaro(carne);

		List<Receta> aux = Stream.of(receta2, receta3).collect(
				Collectors.toList());

		assertEquals(filtroCaros.listarRecetasParaUnUsuario(fecheSena), aux);
	}

	@Test
	public void testCombinarFiltros() {

		RepositorioDeRecetas rR = new RepositorioDeRecetas();
		CarosEnPreparacion filtroCaros = new CarosEnPreparacion(rR);
		/* creamos la combinacion de filtros */
		LeGustaAlUsuario filtroLeGustaYCaros = new LeGustaAlUsuario(filtroCaros);

		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(huevo);
		receta4.agregarIngrediente(salmon);

		rR.agregarReceta(receta1);
		rR.agregarReceta(receta2);
		rR.agregarReceta(receta3);
		rR.agregarReceta(receta4);

		filtroCaros.setIngredienteCaro(salmon);
		filtroCaros.setIngredienteCaro(carne);

		fecheSena.agregarComidaQueLeDisgusta(fruta);

		List<Receta> aux = Stream.of(receta3).collect(Collectors.toList());

		assertEquals(filtroLeGustaYCaros.listarRecetasParaUnUsuario(fecheSena),
				aux);
	}

	@Test
	public void testProcesamientoDevuelveLasDiezPrimerasRecetasConUnFiltro() {

		List<Receta> aux = Stream.of(receta2, receta3, receta4, receta5,
				receta6, receta7, receta8, receta9, receta10, receta11)
				.collect(Collectors.toList());
		receta1.agregarIngrediente(carne);

		CarosEnPreparacion filtroCaros = new CarosEnPreparacion(unRepo);
		filtroCaros.setIngredienteCaro(carne);
		DiezPrimeros diezPrimeros = new DiezPrimeros(filtroCaros);

		assertEquals(diezPrimeros.listarRecetasParaUnUsuario(fecheSena), aux);
	}

	@Test
	public void testProcesamientoDevuelveLasDiezPrimerasRecetasSinFiltros() {

		List<Receta> aux = Stream.of(receta1, receta2, receta3, receta4,
				receta5, receta6, receta7, receta8, receta9, receta10).collect(
				Collectors.toList());

		DiezPrimeros diezPrimeros = new DiezPrimeros(unRepo);

		assertEquals(diezPrimeros.listarRecetasParaUnUsuario(fecheSena), aux);
	}

	@Test
	public void testProcesamientoDevuelveLasDiezPrimerasRecetasConFiltrosCombinados() {

		CarosEnPreparacion filtroCaros = new CarosEnPreparacion(unRepo);

		LeGustaAlUsuario filtroLeGustaYCaros = new LeGustaAlUsuario(filtroCaros);

		DiezPrimeros diezPrimeros = new DiezPrimeros(filtroLeGustaYCaros);

		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(huevo);

		unRepo.agregarReceta(receta1);
		unRepo.agregarReceta(receta2);
		unRepo.agregarReceta(receta3);

		filtroCaros.setIngredienteCaro(salmon);
		filtroCaros.setIngredienteCaro(carne);

		fecheSena.agregarComidaQueLeDisgusta(fruta);

		List<Receta> aux = Stream.of(receta3, receta4, receta5, receta6,
				receta7, receta8, receta9, receta10, receta11, receta12)
				.collect(Collectors.toList());

		assertEquals(diezPrimeros.listarRecetasParaUnUsuario(fecheSena), aux);
	}

	@Test
	public void testProcesamientoPosteriorDevuelveRecetasPares() {

		LeGustaAlUsuario filtroLeGusta = new LeGustaAlUsuario(unRepo);

		ResultadosPares resultadosPares = new ResultadosPares(filtroLeGusta);

		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(huevo);

		fecheSena.agregarComidaQueLeDisgusta(fruta);

		List<Receta> aux = Stream.of(receta3, receta5, receta7, receta9,
				receta11).collect(Collectors.toList());

		assertEquals(resultadosPares.listarRecetasParaUnUsuario(fecheSena), aux);
	}

	@Test
	public void testProcesamientoPosteriorDevuelveLaListaOrdenadaPorCriterioTotalDeCalorias() {

		RepositorioDeRecetas repositorio = new RepositorioDeRecetas();
		repositorio.agregarReceta(receta1);
		repositorio.agregarReceta(receta2);
		repositorio.agregarReceta(receta3);
		repositorio.agregarReceta(receta4);

		Orden orden = new Orden(repositorio);

		orden.setCriterio(new OrdenCalorias());

		List<Receta> aux = Stream.of(receta3, receta4, receta2, receta1)
				.collect(Collectors.toList());

		assertEquals(orden.listarRecetasParaUnUsuario(fecheSena), aux);
	}

	@Test
	public void testProcesamientoPosteriorDevuelveLaListaOrdenadaPorCriterioAlfabetico() {
		RepositorioDeRecetas repositorio = new RepositorioDeRecetas();
		repositorio.agregarReceta(receta1);
		repositorio.agregarReceta(receta2);
		repositorio.agregarReceta(receta3);
		repositorio.agregarReceta(receta4);

		Orden orden = new Orden(repositorio);

		orden.setCriterio(new OrdenAlfabetico());

		List<Receta> aux = Stream.of(receta2, receta3, receta4, receta1)
				.collect(Collectors.toList());

		assertEquals(orden.listarRecetasParaUnUsuario(fecheSena), aux);
	}

	@Test
	public void testCombinarFiltrosCarosConLeGustaAlUsuarioYProcesoFinalOrdenAlfabetico() {

		RepositorioDeRecetas repo = new RepositorioDeRecetas();

		CarosEnPreparacion filtroCarosEnPreparacion = new CarosEnPreparacion(
				repo);

		filtroCarosEnPreparacion.setIngredienteCaro(carne);
		filtroCarosEnPreparacion.setIngredienteCaro(huevo);

		LeGustaAlUsuario filtroLeGusta = new LeGustaAlUsuario(
				filtroCarosEnPreparacion);

		Orden procesoOrden = new Orden(filtroLeGusta);
		OrdenAlfabetico ordenAlfabetico = new OrdenAlfabetico();
		procesoOrden.setCriterio(ordenAlfabetico);

		fecheSena.agregarComidaQueLeDisgusta(fruta);

		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(huevo);
		receta3.agregarIngrediente(fruta);
		receta4.agregarIngrediente(salmon);
		receta5.agregarIngrediente(papa);

		repo.agregarReceta(receta1);
		repo.agregarReceta(receta2);
		repo.agregarReceta(receta3);
		repo.agregarReceta(receta4);
		repo.agregarReceta(receta5);

		List<Receta> aux = Stream.of(receta5, receta4).collect(
				Collectors.toList());

		assertEquals(procesoOrden.listarRecetasParaUnUsuario(fecheSena), aux);

	}

}