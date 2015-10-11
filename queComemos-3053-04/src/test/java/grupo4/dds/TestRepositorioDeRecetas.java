package grupo4.dds;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.monitores.CantidadDeVeganos;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.filtros.FiltroExcesoCalorias;
import grupo4.dds.receta.busqueda.filtros.FiltroNoEsAdecuada;
import grupo4.dds.receta.busqueda.filtros.FiltroNoLeGusta;
import grupo4.dds.receta.busqueda.filtros.FiltroRecetasCaras;
import grupo4.dds.receta.busqueda.postProcesamiento.Ordenar;
import grupo4.dds.repositorios.RepositorioDeRecetas;
import grupo4.dds.usuario.condicion.Vegano;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestRepositorioDeRecetas extends BaseTest {

	private RepositorioDeRecetas repositorio = RepositorioDeRecetas.get();
	
	private CantidadDeVeganos cantidadVeganos = new CantidadDeVeganos();
	private CantidadDeHoras cantidadHoras = new CantidadDeHoras();
	
	private List<Receta> expected;
	private List<Receta> consulta;
	private List<Filtro> filtros;
	
	@Before
	public void setUp() {
		
		expected = null;
		filtros = new ArrayList<>();
		consulta = new ArrayList<>();
		
		fecheSena.agregarGrupo(grupo1);
		federicoHipper.agregarGrupo(grupo2);
		arielFolino.agregarGrupo(grupo1);
		matiasMartino.agregarGrupo(grupo2);
		
		fecheSena.agregarCondicion(new Vegano());
		fecheSena.agregarComidaQueLeDisgusta(Ingrediente.nuevaComida("coliflor"));
		
		salmon.agregarIngrediente(Ingrediente.nuevaComida("carne"));
		lomito.agregarIngrediente(Ingrediente.nuevaComida("lomo"));
		coliflor.agregarIngrediente(Ingrediente.nuevaComida("coliflor"));
	}
	
	@Test
	public void testElListadoDeRecetasQuePuedeVerUnUsuarioNoPuedeContenerRecetasNoCompartidasEnAlgunoDeSusGrupos() {
		assertTrue(repositorio != null);
		
		List<Receta> recetasQuePuedeVer = repositorio.listarRecetasPara(arielFolino, null, null);
				
		assertFalse(recetasQuePuedeVer.contains(pollo));
		assertFalse(recetasQuePuedeVer.contains(pure));
		assertFalse(recetasQuePuedeVer.contains(bife));
	}
	
	@Test 
	public void testLasRecetasQuePuedeVerUnUsuarioSonPublicasOCompartidasEnALgunoDeSusGrupos() {
		expected = Arrays.asList(milanesa, sopa, salmon, lomito, coliflor);
		List<Receta> recetas = repositorio.listarRecetasPara(arielFolino, null, null);
		
		assertTrue(expected.containsAll(recetas) && recetas.containsAll(expected) && recetas.size() == expected.size());
	}	
	
	@Test
	public void testSiNoAplicoFiltrosNiPostProcesamientoObtengoTodasLasRecetasQuePuedeVerElUsuario() {
		expected = Arrays.asList(milanesa, sopa, salmon, lomito, coliflor);
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
		
		expected = Arrays.asList(sopa);
		List<Receta> recetas = repositorio.listarRecetasPara(fecheSena, filtros, null);
		assertTrue(expected.containsAll(recetas) && recetas.containsAll(expected) && recetas.size() == expected.size());
	}
	
	@Test 
	public void testListarRecetasQuePuedeVerUsuarioFiltradasPorVariosCriteriosYOrdenadasPorCalorias() {
		
		filtros.add(new FiltroNoEsAdecuada());	
		filtros.add(new FiltroNoLeGusta());	
		
		Ordenar procesamiento = new Ordenar((a,b) -> a.getTotalCalorias() - b.getTotalCalorias());
		
		expected = Arrays.asList(sopa, lomito, milanesa);
		System.out.println(repositorio.listarRecetasPara(fecheSena, filtros, procesamiento));
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
		
		consulta = Arrays.asList(salmon, lomito, coliflor);
		
		maria.setMarcaFavorita(true);
		RepositorioDeRecetas.get().listarRecetasPara(maria, null, null);
		maria.ejecutarMarcadoPendiente();
		
		assertTrue(maria.getHistorial().containsAll(consulta));
	}
	
}
