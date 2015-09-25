package grupo4.dds;

import static org.junit.Assert.*;
import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.monitores.CantidadDeVeganos;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.filtros.FiltroExcesoCalorias;
import grupo4.dds.receta.busqueda.filtros.FiltroNoEsAdecuada;
import grupo4.dds.receta.busqueda.filtros.FiltroNoLeGusta;
import grupo4.dds.receta.busqueda.filtros.FiltroRecetasCaras;
import grupo4.dds.receta.busqueda.postProcesamiento.Ordenar;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Vegano;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;

public class TestRepositorioDeRecetas {
	
	private Usuario fecheSena;
	private Usuario raul;
	private Usuario arielFolino;
	private Usuario matiasMartino;
	private Usuario federicoHipper;
	
	private CantidadDeHoras cantidadHoras = new CantidadDeHoras();
	private CantidadDeVeganos cantidadVeganos = new CantidadDeVeganos();
	private List<Receta> expected;
	private List<Receta> consulta;
	private List<Filtro> filtros;
	private RepositorioDeRecetas repositorio = RepositorioDeRecetas.get();
	
	private Receta receta1;
	private Receta receta2;
	private Receta receta3;
	private Receta receta4;
	private Receta receta5;
	private RecetaPublica receta6;
	private RecetaPublica receta7;
	private RecetaPublica receta8;
	
	@Before
	public void setUp() {
		expected = null;
		filtros = new ArrayList<>();
		consulta = new ArrayList<>();
		repositorio.vaciar();
		
		raul = Usuario.crearPerfil("Raul", null, null, 1.70f, 65.0f, null, true, null);
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null, false, null);
		arielFolino = Usuario.crearPerfil("Ariel Folino", null, null, 1.69f, 96.0f, null, true, null);
		matiasMartino = Usuario.crearPerfil("Matías Martino", null, null, 1.74f, 79.0f, null, false, null);
		federicoHipper = Usuario.crearPerfil("Federico Hipperdinger", null, null, 1.91f, 99.0f, null, false, null);
		
		GrupoUsuarios grupo1 = GrupoUsuarios.crearGrupo("grupo1");
		GrupoUsuarios grupo2 = GrupoUsuarios.crearGrupo("grupo2");
				
		fecheSena.agregarGrupo(grupo1);
		federicoHipper.agregarGrupo(grupo2);
		arielFolino.agregarGrupo(grupo1);
		matiasMartino.agregarGrupo(grupo2);
		
		fecheSena.agregarCondicion(new Vegano());
		fecheSena.agregarComidaQueLeDisgusta(Ingrediente.nuevaComida("coliflor"));
		
		receta1 = Receta.crearNueva(fecheSena, new EncabezadoDeReceta("receta1", null, Dificultad.DIFICIL, 999), null);
		receta2 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta2", null, Dificultad.DIFICIL, 300), null);
		receta3 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta3", null, null, 600), null);
		receta4 = Receta.crearNueva(arielFolino, new EncabezadoDeReceta("receta4", null, null, 100), null);
		receta5 = Receta.crearNueva(matiasMartino, new EncabezadoDeReceta("receta5", null, null, 499), null);
		receta6 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta6", null, null, 200), null);
		receta7 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta7", null, null, 300), null);
		receta8 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta8", null, null, 100), null);
		
		receta1.agregarIngrediente(Ingrediente.nuevaComida(""));
		receta2.agregarIngrediente(Ingrediente.nuevaComida(""));
		receta3.agregarIngrediente(Ingrediente.nuevaComida(""));
		receta4.agregarIngrediente(Ingrediente.nuevaComida(""));
		receta5.agregarIngrediente(Ingrediente.nuevaComida(""));
		receta6.agregarIngrediente(Ingrediente.nuevaComida("carne"));
		receta7.agregarIngrediente(Ingrediente.nuevaComida("lomo"));
		receta8.agregarIngrediente(Ingrediente.nuevaComida("coliflor"));
	}
	
	@Test
	public void testElListadoDeRecetasQuePuedeVerUnUsuarioNoPuedeContenerRecetasNoCompartidasEnAlgunoDeSusGrupos() {
		assertTrue(repositorio != null);
		
		List<Receta> recetasQuePuedeVer = repositorio.listarRecetasPara(arielFolino, null, null);
				
		assertFalse(recetasQuePuedeVer.contains(receta2));
		assertFalse(recetasQuePuedeVer.contains(receta3));
		assertFalse(recetasQuePuedeVer.contains(receta5));
	}
	
	@Test 
	public void testLasRecetasQuePuedeVerUnUsuarioSonPublicasOCompartidasEnALgunoDeSusGrupos() {
		expected = Arrays.asList(receta1, receta4, receta6, receta7, receta8);
		List<Receta> recetas = repositorio.listarRecetasPara(arielFolino, null, null);
		
		assertTrue(expected.containsAll(recetas) && recetas.containsAll(expected) && recetas.size() == expected.size());
	}	
	
	@Test
	public void testSiNoAplicoFiltrosNiPostProcesamientoObtengoTodasLasRecetasQuePuedeVerElUsuario() {
		expected = Arrays.asList(receta1, receta4, receta6, receta7, receta8);
		List<Receta> recetas = repositorio.listarRecetasPara(arielFolino, null, null);
		
		assertTrue(expected.containsAll(recetas) && recetas.containsAll(expected) && recetas.size() == expected.size());
	}
	
	@Test 
	public void testListarRecetasQuePuedeVerUsuarioFiltradasPorVariosCriterios() {
		
		FiltroRecetasCaras filtroRecetasCaras = new FiltroRecetasCaras();
		filtroRecetasCaras.agregarIngredienteCaro(Ingrediente.nuevaComida("lomo"));
		
		filtros.add(new FiltroExcesoCalorias());	
		filtros.add(new FiltroNoEsAdecuada());	
		filtros.add(new FiltroNoLeGusta());	
		filtros.add(filtroRecetasCaras);	
		
		expected = Arrays.asList(receta4);
		List<Receta> recetas = repositorio.listarRecetasPara(fecheSena, filtros, null);
		assertTrue(expected.containsAll(recetas) && recetas.containsAll(expected) && recetas.size() == expected.size());
	}
	
	@Test 
	public void testListarRecetasQuePuedeVerUsuarioFiltradasPorVariosCriteriosYOrdenadasPorCalorias() {
		
		filtros.add(new FiltroNoEsAdecuada());	
		filtros.add(new FiltroNoLeGusta());	
		
		Ordenar procesamiento = new Ordenar((a,b) -> a.getTotalCalorias() - b.getTotalCalorias());
		
		expected = Arrays.asList(receta4, receta7, receta1);
		assertTrue(expected.containsAll(repositorio.listarRecetasPara(fecheSena, filtros, procesamiento)));
	}
	
	@Test 
	public void testSiListamosRecetasParaUnUsuarioSeNotificanALosMonitoresRegistrados() {
		
		filtros.add(new FiltroNoLeGusta());	
		arielFolino.agregarCondicion(new Vegano());
		
		RepositorioDeRecetas.get().setMonitor(cantidadHoras);
		RepositorioDeRecetas.get().setMonitor(cantidadVeganos);
				
		RepositorioDeRecetas.get().listarRecetasPara(arielFolino, filtros , null);
		RepositorioDeRecetas.get().listarRecetasPara(federicoHipper, filtros , null);
		
		assertTrue(cantidadHoras.cantidadDeConsultasPor(LocalTime.now().getHour()) == 2);
		assertTrue(cantidadVeganos.getContadorDeVeganos() == 1);
		
	}
	
	@Test 
	public void testMarcarFavoritasLasRecetasConsultadasParaUnUsuario() {
		consulta = Arrays.asList(receta6, receta7, receta8);
		RepositorioDeRecetas.get().listarRecetasPara(raul, null, null);
		raul.ejecutarMarcadoPendiente();
		assertTrue(raul.getHistorial().containsAll(consulta));
	}
	
}
