package grupo4.dds;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

public class TestRepositorioDeRecetas {
	
	private RepositorioDeRecetas repositorio;
	private List<Receta> aux;
	
	@Before
	public void setUp() {
		repositorio = new RepositorioDeRecetas();
		aux = new ArrayList<>();
	}
	
	@Test
	public void testAgregarRecetasAlRepositorio() {
		Receta r1 = new Receta();
		Receta r2 = new Receta();
		Receta r3 = new Receta();
		
		repositorio.agregarReceta(r1);
		repositorio.agregarReceta(r2);
		repositorio.agregarReceta(r3);
		
		aux.add(r1);
		aux.add(r2);
		aux.add(r3);
		
		assertTrue(repositorio.listarTodas().containsAll(aux));
	}
	
	@Test
	public void testQuitarRecetaDelRepositorio() {
		Receta r1 = new Receta();
		Receta r2 = new Receta();
		Receta r3 = new Receta();
		
		repositorio.agregarReceta(r1);
		repositorio.agregarReceta(r2);
		repositorio.agregarReceta(r3);

		aux.add(r1);
		
		repositorio.quitarReceta(r2);
		repositorio.quitarReceta(r3);
		
		assertEquals(repositorio.listarTodas(), aux);	
	}
	
	
	
	
	

}
