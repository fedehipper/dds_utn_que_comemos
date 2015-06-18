package grupo4.dds;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;

public class TestCommand {
	
	
	List<Filtro> filtros = new ArrayList<>();
	
	private Usuario federicoHipper;
	private RepositorioDeRecetas repositorio = RepositorioDeRecetas.get();
	
	private Receta receta2;
	private Receta receta3;
	private RecetaPublica receta6;
	private RecetaPublica receta7;
	private RecetaPublica receta8;
	
	private List<Receta> consulta = new ArrayList<Receta>();
	
	
	@Before
	public void setUp() {
		repositorio.vaciar();
		
		federicoHipper = Usuario.crearPerfil("Federico Hipperdinger", null, null, 1.91f, 99.0f, null, true);

		receta2 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta2", null, Dificultad.DIFICIL, 300), null);
		receta3 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta3", null, null, 600), null);
		receta6 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta6", null, null, 200), null);
		receta7 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta7", null, null, 300), null);
		receta8 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta8", null, null, 100), null);
			
		consulta = Arrays.asList(receta2, receta3, receta6, receta7, receta8);
	}
	

	@Test
	public void testEjecutarCommandMarcarComoFavoritasATodasLasRecetas() {
		repositorio.ejecutarAcciones(federicoHipper, consulta);
		assertEquals(federicoHipper.getHistorial(), consulta);
	}
	
	
	
	

}
