package grupo4.dds;

import static org.junit.Assert.assertEquals;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.Ordenar;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.receta.busqueda.postProcesamiento.TomarDiezPrimeros;
import grupo4.dds.receta.busqueda.postProcesamiento.TomarResultadosPares;
import grupo4.dds.repositorios.RepositorioDeRecetas;
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

		Receta receta1 = sopa;
		Receta receta2 = pollo;
		Receta receta3 = pure;
		Receta receta4 = lomito;
		Receta receta5 = salmon;
		Receta receta6 = milanesa;
		Receta receta7 = new BuilderReceta().hacerValida().nombre("empanada").calorias(498).build();
		Receta receta8 = new BuilderReceta().hacerValida().nombre("tomate").build();
		Receta receta9 = bife;
		Receta receta10 = new BuilderReceta().hacerValida().nombre("remolacha").build();
		Receta receta11 = new BuilderReceta().hacerValida().nombre("zanahoria").build();
		Receta receta12 = coliflor;
		
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
