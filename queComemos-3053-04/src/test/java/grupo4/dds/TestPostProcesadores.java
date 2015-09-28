package grupo4.dds;

import static org.junit.Assert.assertEquals;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.Ordenar;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.receta.busqueda.postProcesamiento.TomarDiezPrimeros;
import grupo4.dds.receta.busqueda.postProcesamiento.TomarResultadosPares;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestPostProcesadores extends BaseTest {

	private PostProcesamiento procesamiento;
	private List<Receta> recetas;
	private List<Receta> subRecetas;
	private List<Receta> subRecetasOrdenadasAlfabeticamente;
	private List<Receta> subRecetasOrdenadasPorCalorias;
	private RepositorioDeRecetas mockRepositorioRecetas;
	private RepositorioDeRecetas mockRepositorioSubRecetas;

	@Before
	public void setUp() {

		RecetaPublica receta1 = RecetaPublica.crearNueva(new EncabezadoDeReceta("sopa", null, null, 100), null);
		RecetaPublica receta2 = RecetaPublica.crearNueva(new EncabezadoDeReceta("pollo", null, null, 300), null);
		RecetaPublica receta3 = RecetaPublica.crearNueva(new EncabezadoDeReceta("pure", null, null, 600), null);
		RecetaPublica receta4 = RecetaPublica.crearNueva(new EncabezadoDeReceta("papa", null, null, 999), null);
		RecetaPublica receta5 = RecetaPublica.crearNueva(new EncabezadoDeReceta("salmon", null, null, 200), null);
		RecetaPublica receta6 = RecetaPublica.crearNueva(new EncabezadoDeReceta("milanesa", null, null, 100), null);
		RecetaPublica receta7 = RecetaPublica.crearNueva(new EncabezadoDeReceta("empanada", null, null, 300), null);
		RecetaPublica receta8 = RecetaPublica.crearNueva(new EncabezadoDeReceta("tomate", null, null, 600), null);
		RecetaPublica receta9 = RecetaPublica.crearNueva(new EncabezadoDeReceta("albondiga", null, null, 999), null);
		RecetaPublica receta10 = RecetaPublica.crearNueva(new EncabezadoDeReceta("remolacha", null, null, 200), null);
		RecetaPublica receta11 = RecetaPublica.crearNueva(new EncabezadoDeReceta("zanahoria", null, null, 100), null);
		RecetaPublica receta12 = RecetaPublica.crearNueva(new EncabezadoDeReceta("bondiola", null, null, 200), null);
		
		recetas = Arrays.asList(receta1, receta2, receta3, receta4, receta5, receta6, receta7, 
				receta8, receta9, receta10, receta11, receta12);
		
		subRecetas = Arrays.asList(receta9, receta11, receta7, receta12);
		subRecetasOrdenadasAlfabeticamente = Arrays.asList(receta9, receta12, receta7, receta11);
		subRecetasOrdenadasPorCalorias = Arrays.asList(receta11, receta12, receta7, receta9);
		
		mockRepositorioRecetas = new RepositorioDeRecetas() {
			public List<Receta> listarRecetasPara(Usuario usuario, List<Filtro> filtros, PostProcesamiento postProcesamiento) {
				return postProcesamiento.procesar(recetas);
			}
		};
		
		mockRepositorioSubRecetas = new RepositorioDeRecetas() {
			public List<Receta> listarRecetasPara(Usuario usuario, List<Filtro> filtros, PostProcesamiento postProcesamiento) {
				return postProcesamiento.procesar(subRecetas);
			}
		};
		
	}
	
	@Test
	public void testTomar10Primero() {
		procesamiento = new TomarDiezPrimeros();		
		
		assertEquals(recetas.subList(0, 9), mockRepositorioRecetas.listarRecetasPara(null, null, procesamiento));
	}
	
	@Test
	public void testTomarPares() {
		procesamiento = new TomarResultadosPares();		
		
		List<Receta> aux = new ArrayList<Receta>();
		
		for (int i = 0; i < subRecetas.size(); i += 2) {
			aux.add(subRecetas.get(i));
		}
		
		assertEquals(aux, mockRepositorioSubRecetas.listarRecetasPara(null, null, procesamiento));
	}
	
	@Test
	public void testOrdenarAlfbeticamente() {
		procesamiento = new Ordenar((a,b) -> a.getNombreDelPlato().compareTo(b.getNombreDelPlato()));		
		
		assertEquals(subRecetasOrdenadasAlfabeticamente, mockRepositorioSubRecetas.listarRecetasPara(null, null, procesamiento));
	}
	
	@Test
	public void testOrdenarPorCalorias() {
		procesamiento = new Ordenar((a,b) -> a.getTotalCalorias() - b.getTotalCalorias());		
		
		assertEquals(subRecetasOrdenadasPorCalorias, mockRepositorioSubRecetas.listarRecetasPara(null, null, procesamiento));
	}
}
