package grupo4.dds;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import grupo4.dds.filtrosYProcesos.CarosEnPreparacion;
import grupo4.dds.filtrosYProcesos.CondicionesUsuario;
import grupo4.dds.filtrosYProcesos.DiezPrimeros;
import grupo4.dds.filtrosYProcesos.ExcesoCalorias;
import grupo4.dds.filtrosYProcesos.LeGustaAlUsuario;
import grupo4.dds.filtrosYProcesos.ResultadosPares;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Grupo;
import grupo4.dds.usuario.Ingrediente;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Before;
import org.junit.Test;

public class TestFiltros {

	private Usuario fecheSena;
	private EncabezadoDeReceta encabezado1;
	private EncabezadoDeReceta encabezado2;
	private EncabezadoDeReceta encabezado3;
	private EncabezadoDeReceta encabezado4;
	private Ingrediente carne;
	private Ingrediente fruta;
	private Ingrediente salmon;
	private Ingrediente huevo;
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
		carne = new Ingrediente("carne", 0f);
		fruta = new Ingrediente("fruta", 0f);
		huevo = new Ingrediente("huevo" , 0f);
		salmon = new Ingrediente("salmon", 0f);
		receta1 = new Receta(fecheSena, encabezado1, null);
		receta2 = new RecetaPublica(encabezado2, null);
		receta3 = new RecetaPublica(encabezado3, null);
		receta4 = new RecetaPublica(encabezado4, null);
		receta5 = new RecetaPublica(encabezado2, null);
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
	public void testFiltrarPorExcesoCalorias() {
		RepositorioDeRecetas repo = new RepositorioDeRecetas();
		
		ExcesoCalorias exceso = new ExcesoCalorias();		
		repo.setFiltro(exceso);
		
		repo.agregarReceta(receta1);
		repo.agregarReceta(receta2);
		repo.agregarReceta(receta3);
		
		List<Receta> aux = Stream.of(receta2, receta3).collect(Collectors.toList());
		
		assertEquals(repo.filtrarListaDeRecetas(fecheSena), aux );
	}
	
	@Test 
	public void testFiltroDeRecetasQueCumplenLasCondicionesDelUsuario() {
		RepositorioDeRecetas rR = new RepositorioDeRecetas();
		
		CondicionesUsuario filtroCondicion = new CondicionesUsuario();
		rR.setFiltro(filtroCondicion);
		
		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(fruta);
		
		rR.agregarReceta(receta1);
		rR.agregarReceta(receta2);
		rR.agregarReceta(receta3);
		
		Usuario ariel = new Usuario("ariel", null, 89.0f, 78f, null);
		Grupo grupo = new Grupo("grupo");
		grupo.agregarUsuario(fecheSena);
		grupo.agregarUsuario(ariel);
		ariel.agregarGrupo(grupo);
		fecheSena.agregarGrupo(grupo);
		
		List<Receta> aux = Stream.of(receta2, receta3).collect(Collectors.toList());
		
		Vegano vegano = new Vegano();
		ariel.agregarCondicion(vegano);
		
		assertEquals(rR.filtrarListaDeRecetas(ariel), aux );
	}
	
	@Test
	public void testCombiarFiltroCondicionDelUsuarioConExcesoCalorias() {
		RepositorioDeRecetas repo = new RepositorioDeRecetas();
		
		ExcesoCalorias exceso = new ExcesoCalorias();		
		repo.setFiltro(exceso);
		
		CondicionesUsuario filtroCondicion = new CondicionesUsuario();
		repo.setFiltro(filtroCondicion);
		
		receta1.agregarIngrediente(fruta);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(fruta);
		
		repo.agregarReceta(receta1);
		repo.agregarReceta(receta2);
		repo.agregarReceta(receta3);
		
		Usuario ariel = new Usuario("ariel", null, 89.0f, 78f, null);
		Grupo grupo = new Grupo("grupo");
		grupo.agregarUsuario(fecheSena);
		grupo.agregarUsuario(ariel);
		ariel.agregarGrupo(grupo);
		fecheSena.agregarGrupo(grupo);
		
		List<Receta> aux = Stream.of(receta2, receta3).collect(Collectors.toList());
		
		Vegano vegano = new Vegano();
		ariel.agregarCondicion(vegano);
		
		assertEquals(repo.filtrarListaDeRecetas(ariel), aux);
	}
	
	@Test
	public void testFiltroNoLeGustaAlUsuarioElNombreDelPlato() {
	
		RepositorioDeRecetas repo = new RepositorioDeRecetas();
		
		LeGustaAlUsuario filtroLeGusta = new LeGustaAlUsuario();
		repo.setFiltro(filtroLeGusta);
		
		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(huevo);
		
		repo.agregarReceta(receta1);
		repo.agregarReceta(receta2);
		repo.agregarReceta(receta3);
		
		fecheSena.agregarComidaQueLeDisgusta(fruta);
	
		List<Receta> aux = Stream.of(receta1, receta3).collect(Collectors.toList());
		
		assertEquals(repo.filtrarListaDeRecetas(fecheSena), aux);
	}
	
	@Test
	public void testFiltroIngredientesCarosEnPreparacion() {
		
		RepositorioDeRecetas repo = new RepositorioDeRecetas();
		
		CarosEnPreparacion filtroCaros = new CarosEnPreparacion();
		repo.setFiltro(filtroCaros);
		
		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(huevo);
		receta4.agregarIngrediente(salmon);
		
		repo.agregarReceta(receta1);
		repo.agregarReceta(receta2);
		repo.agregarReceta(receta3);
		repo.agregarReceta(receta4);
		
		filtroCaros.setIngredienteCaro(salmon);
		filtroCaros.setIngredienteCaro(carne);
	
		List<Receta> aux = Stream.of(receta2, receta3).collect(Collectors.toList());
		
		assertEquals(repo.filtrarListaDeRecetas(fecheSena), aux);	
	}
	
	@Test 
	public void testCombinarFiltros() {
		
		RepositorioDeRecetas repo = new RepositorioDeRecetas();
		
		CarosEnPreparacion filtroCaros = new CarosEnPreparacion();
		repo.setFiltro(filtroCaros);
		
		LeGustaAlUsuario filtroLeGustaYCaros = new LeGustaAlUsuario();
		repo.setFiltro(filtroLeGustaYCaros);
		
		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta); 
		receta3.agregarIngrediente(huevo);
		receta4.agregarIngrediente(salmon);
		
		repo.agregarReceta(receta1);
		repo.agregarReceta(receta2);
		repo.agregarReceta(receta3);
		repo.agregarReceta(receta4);
		
		filtroCaros.setIngredienteCaro(salmon);
		filtroCaros.setIngredienteCaro(carne);
	
		fecheSena.agregarComidaQueLeDisgusta(fruta);
		
		List<Receta> aux = Stream.of(receta3).collect(Collectors.toList());
		
		assertEquals(repo.filtrarListaDeRecetas(fecheSena), aux);
	}
	
	@Test
	public void testProcesamientoDevuelveLasDiezPrimerasRecetasConUnFiltro() {
		
		List<Receta> aux = Stream.of(receta2, receta3, receta4, receta5, receta6, receta7, receta8, receta9, receta10, receta11).collect(Collectors.toList());
		receta1.agregarIngrediente(carne);
		
		CarosEnPreparacion filtroCaros = new CarosEnPreparacion();
		unRepo.setFiltro(filtroCaros);
		
		filtroCaros.setIngredienteCaro(carne);
		DiezPrimeros diezPrimeros = new DiezPrimeros();
		unRepo.setProceso(diezPrimeros);
		
		assertEquals(unRepo.procesarListaDeRecetas(unRepo.filtrarListaDeRecetas(fecheSena)), aux);
	}
	
	
	@Test
	public void testProcesamientoDevuelveLasDiezPrimerasRecetasSinFiltros() {
		
		List<Receta> aux = Stream.of(receta1, receta2, receta3, receta4, receta5, receta6, receta7, receta8, receta9, receta10).collect(Collectors.toList());

		DiezPrimeros diezPrimeros = new DiezPrimeros();
		unRepo.setProceso(diezPrimeros);
		
		assertEquals(unRepo.procesarListaDeRecetas(unRepo.listarRecetasParaUnUsuario(fecheSena)), aux);
	}
	
	@Test
	public void testProcesamientoDevuelveLasDiezPrimerasRecetasConFiltrosCombinados() {
		
		CarosEnPreparacion filtroCaros = new CarosEnPreparacion();
		unRepo.setFiltro(filtroCaros);

		LeGustaAlUsuario filtroLeGusta = new LeGustaAlUsuario();
		unRepo.setFiltro(filtroLeGusta);
		
		DiezPrimeros diezPrimeros = new DiezPrimeros();
		unRepo.setProceso(diezPrimeros);
		
		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta); 
		receta3.agregarIngrediente(huevo);
		
		unRepo.agregarReceta(receta1);
		unRepo.agregarReceta(receta2);
		unRepo.agregarReceta(receta3);
		
		filtroCaros.setIngredienteCaro(salmon);
		filtroCaros.setIngredienteCaro(carne);
	
		fecheSena.agregarComidaQueLeDisgusta(fruta);
		
		List<Receta> aux = Stream.of(receta3, receta4, receta5, receta6, receta7, receta8, receta9, receta10, receta11, receta12).collect(Collectors.toList());
		
		assertEquals(unRepo.procesarListaDeRecetas(unRepo.filtrarListaDeRecetas(fecheSena)), aux);
	}
	
	@Test 
	public void testProcesamientoPosteriorDevuelveRecetasPares() {
		
		LeGustaAlUsuario filtroLeGusta = new LeGustaAlUsuario();
		unRepo.setFiltro(filtroLeGusta);
		
		ResultadosPares resultadosPares = new ResultadosPares();
		unRepo.setProceso(resultadosPares);

		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta); 
		receta3.agregarIngrediente(huevo);
		
		fecheSena.agregarComidaQueLeDisgusta(fruta);
		
		List<Receta> aux = Stream.of(receta3, receta5, receta7, receta9, receta11).collect(Collectors.toList());
		
		assertEquals(unRepo.procesarListaDeRecetas(unRepo.filtrarListaDeRecetas(fecheSena)), aux);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
