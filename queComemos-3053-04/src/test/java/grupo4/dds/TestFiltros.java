package grupo4.dds;

import static org.junit.Assert.assertEquals;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.filtros.FiltroNoEsAdecuada;
import grupo4.dds.receta.busqueda.filtros.FiltroExcesoCalorias;
import grupo4.dds.receta.busqueda.filtros.FiltroNoLeGusta;
import grupo4.dds.receta.busqueda.filtros.FiltroRecetasCaras;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class TestFiltros {
	
	private Usuario ariel;
	private Usuario fecheSena;
	
	private List<Filtro> filtros;
	private List<Receta> expected;
	private RepositorioDeRecetas mockRepositorio;	
	
	private Receta sopa;
	private Receta pollo;
	private RecetaPublica pure;
	private RecetaPublica milanesa;
	private RecetaPublica salmon;

	@Before
	public void setUp() {
		ariel = Usuario.crearPerfil("Ariel Folino", null, null, 1.69f, 96.0f, null, false, null);
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null, false, null);
		
		expected = null;
		filtros = new ArrayList<>();

		sopa = Receta.crearNueva(ariel, new EncabezadoDeReceta("sopa", null, null, 100), null);
		pollo = Receta.crearNueva(fecheSena, new EncabezadoDeReceta("pollo", null, null, 300), null);
		pure = RecetaPublica.crearNueva(new EncabezadoDeReceta("pure", null, null, 600), null);
		milanesa = RecetaPublica.crearNueva(new EncabezadoDeReceta("milanesa", null, null, 999), null);
		salmon = RecetaPublica.crearNueva(new EncabezadoDeReceta("salmon", null, null, 200), null);
		
		mockRepositorio = new RepositorioDeRecetas() {
			
			private List<Receta> recetas = Arrays.asList(sopa, pollo, pure, milanesa, salmon);
			public List<Receta> listarRecetasPara(Usuario usuario, List<Filtro> filtros, PostProcesamiento postProcesamiento) {
				return recetas.stream().filter(r -> filtros.get(0).test(usuario, r)).collect(Collectors.toList());
			}
		};
		
	}
	
	@Test
	public void testFiltrarPorExcesoCalorias() {
		filtros.add(new FiltroExcesoCalorias());		
		expected = Arrays.asList(sopa, pollo, salmon);
		
		assertEquals(new ArrayList<Receta>(), mockRepositorio.listarRecetasPara(ariel, filtros, null));
		assertEquals(expected, mockRepositorio.listarRecetasPara(fecheSena, filtros, null));
	}
	
	@Test
	public void testFiltrarPorGusto() {
		filtros.add(new FiltroNoLeGusta());		
		expected = Arrays.asList(pollo, pure, salmon);
		
		fecheSena.agregarComidaQueLeDisgusta(new Ingrediente("brocoli"));
		fecheSena.agregarComidaQueLeDisgusta(new Ingrediente("coliflor"));
		
		sopa.agregarIngrediente(new Ingrediente("brocoli"));
		milanesa.agregarIngrediente(new Ingrediente("coliflor"));
		pollo.agregarIngrediente(new Ingrediente("pollo"));
		pure.agregarIngrediente(new Ingrediente("papa"));
		salmon.agregarIngrediente(new Ingrediente("tomate"));
		
		assertEquals(expected, mockRepositorio.listarRecetasPara(fecheSena, filtros, null));
	}
	
	@Test
	public void testFiltrarPorCondiciones() {
		filtros.add(new FiltroNoEsAdecuada());		
		expected = Arrays.asList(sopa, milanesa, salmon);
		
		ariel.agregarCondicion(new Celiaco());
		ariel.agregarCondicion(new Hipertenso());
		ariel.agregarCondicion(new Vegano());
		
		sopa.agregarIngrediente(new Ingrediente("cabellito de angel"));		
		pollo.agregarIngrediente(new Ingrediente("pollo"));
		sopa.agregarCondimento(new Ingrediente("ajo"));		
		pollo.agregarCondimento(new Ingrediente("sal"));
		pure.agregarIngrediente(new Ingrediente("carne"));
		milanesa.agregarIngrediente(new Ingrediente("papa"));
		salmon.agregarIngrediente(new Ingrediente("tomate"));
		
		assertEquals(expected, mockRepositorio.listarRecetasPara(ariel, filtros, null));
	}
	
	@Test
	public void testFiltrarRecetasCaras() {
		
		FiltroRecetasCaras filtroCaras = new FiltroRecetasCaras();
		filtroCaras.agregarIngredienteCaro(new Ingrediente("pollo"));
		filtroCaras.agregarIngredienteCaro(new Ingrediente("tomate"));
		filtros.add(filtroCaras);
		
		expected = Arrays.asList(sopa, pure, milanesa);
		
		sopa.agregarIngrediente(new Ingrediente("brocoli"));
		milanesa.agregarIngrediente(new Ingrediente("coliflor"));
		pollo.agregarIngrediente(new Ingrediente("pollo"));
		pure.agregarIngrediente(new Ingrediente("papa"));
		salmon.agregarIngrediente(new Ingrediente("tomate"));
		
		assertEquals(expected, mockRepositorio.listarRecetasPara(ariel, filtros, null));
	}
	
}
