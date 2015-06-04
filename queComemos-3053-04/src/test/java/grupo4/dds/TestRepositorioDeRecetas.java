package grupo4.dds;

import static org.junit.Assert.*;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestRepositorioDeRecetas {
	
	private Usuario fecheSena;
	private Usuario arielFolino;
	private Usuario matiasMartino;
	private Usuario federicoHipper;
	
	private List<Receta> expected;
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
		repositorio.vaciar();
		
		fecheSena = new Usuario("Feche Sena", null, 1.70f, 65.0f, null);
		arielFolino = new Usuario("Ariel Folino", null, 1.69f, 96.0f, null);
		matiasMartino = new Usuario("Matías Martino", null, 1.74f, 79.0f, null);
		federicoHipper = new Usuario("Federico Hipperdinger", null, 1.91f, 99.0f, null);
		
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		GrupoUsuarios grupo2 = new GrupoUsuarios("grupo2");
		
		fecheSena.agregarGrupo(grupo1);
		federicoHipper.agregarGrupo(grupo2);
		arielFolino.agregarGrupo(grupo1);
		matiasMartino.agregarGrupo(grupo2);
		
		fecheSena.agregarCondicion(new Vegano());
		fecheSena.agregarComidaQueLeDisgusta(new Ingrediente("coliflor"));
		
		receta1 = Receta.crearNueva(fecheSena, new EncabezadoDeReceta("receta1", null, null, 999), null);
		receta2 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta2", null, null, 300), null);
		receta3 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta3", null, null, 600), null);
		receta4 = Receta.crearNueva(arielFolino, new EncabezadoDeReceta("receta4", null, null, 100), null);
		receta5 = Receta.crearNueva(matiasMartino, new EncabezadoDeReceta("receta5", null, null, 499), null);
		receta6 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta6", null, null, 200), null);
		receta7 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta7", null, null, 300), null);
		receta8 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta8", null, null, 100), null);
		
		receta1.agregarIngrediente(new Ingrediente(""));
		receta2.agregarIngrediente(new Ingrediente(""));
		receta3.agregarIngrediente(new Ingrediente(""));
		receta4.agregarIngrediente(new Ingrediente(""));
		receta5.agregarIngrediente(new Ingrediente(""));
		receta6.agregarIngrediente(new Ingrediente("carne"));
		receta7.agregarIngrediente(new Ingrediente("lomo"));
		receta8.agregarIngrediente(new Ingrediente("coliflor"));
	}
	
	/* Test: @listarRecetasPara/1 */
	@Test 
	public void testElListadoDeRecetasQuePuedeVerUnUsuarioNoPuedeContenerRecetasNoCompartidasEnAlgunoDeSusGrupos() {
		assertTrue(repositorio != null);
		
		List<Receta> recetasQuePuedeVer = repositorio.listarRecetasPara(arielFolino);
		
		assertFalse(recetasQuePuedeVer.contains(receta2));
		assertFalse(recetasQuePuedeVer.contains(receta3));
		assertFalse(recetasQuePuedeVer.contains(receta5));
	}
	
	@Test 
	public void testLasRecetasQuePuedeVerUnUsuarioSonPublicasOCompartidasEnALgunoDeSusGrupos() {
		expected = Arrays.asList(receta1, receta4, receta6, receta7, receta8);
		assertEquals(expected, repositorio.listarRecetasPara(arielFolino));
	}	
	
	/* Test: @listarRecetasPara/3 */
	@Test
	public void testSiNoAplicoFiltrosNiPostProcesamientoObtengoTodasLasRecetasQuePuedeVerElUsuario() {
		expected = Arrays.asList(receta1, receta4, receta6, receta7, receta8);
		assertEquals(expected, repositorio.listarRecetasPara(arielFolino, null, null));
	}
	
	@Test
	public void testListarRecetasQuePuedeVerUsuarioFiltradasPorVariosCriterios() {
		
		FiltroRecetasCaras filtroRecetasCaras = new FiltroRecetasCaras();
		filtroRecetasCaras.agregarIngredienteCaro(new Ingrediente("lomo"));
		
		filtros.add(new FiltroExcesoCalorias());	
		filtros.add(new FiltroNoEsAdecuada());	
		filtros.add(new FiltroNoLeGusta());	
		filtros.add(filtroRecetasCaras);	
		
		expected = Arrays.asList(receta4);
		assertEquals(expected, repositorio.listarRecetasPara(fecheSena, filtros, null));
	}
	
	@Test
	public void testListarRecetasQuePuedeVerUsuarioFiltradasPorVariosCriteriosYOrdenadasPorCalorias() {
		
		filtros.add(new FiltroNoEsAdecuada());	
		filtros.add(new FiltroNoLeGusta());	
		
		Ordenar procesamiento = new Ordenar((a,b) -> a.getTotalCalorias() - b.getTotalCalorias());
		
		expected = Arrays.asList(receta4, receta7, receta1);
		assertEquals(expected, repositorio.listarRecetasPara(fecheSena, filtros, procesamiento));
	}
	
	private boolean assertEquals(Collection<Receta> l1, Collection<Receta> l2) {
		return l1.size() == l2.size() && l1.containsAll(l2);
	}
	
}
