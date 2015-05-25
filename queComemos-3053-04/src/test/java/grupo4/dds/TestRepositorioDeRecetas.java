package grupo4.dds;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

public class TestRepositorioDeRecetas {
	
	private  RepositorioDeRecetas repositorio;
	private  List<Receta> aux;
	private  Usuario fecheSena;
	
	@Before
	public void setUp() {
		repositorio = new RepositorioDeRecetas();
		aux = new ArrayList<>();
		fecheSena = new Usuario();
	}
	
	@Test
	public void testAgregarRecetasAlRepositorio() {
		Receta r1 = new Receta();
		Receta r2 = new Receta();
		Receta r3 = new Receta();
		
		repositorio.agregarReceta(r1);
		repositorio.agregarReceta(r2);
		repositorio.agregarReceta(r3);
		List<Receta> aux = Stream.of(r1, r2, r3).collect(Collectors.toList());
		
		assertTrue(repositorio.getRecetas().containsAll(aux));
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
		
		assertEquals(repositorio.getRecetas(), aux);	
	}
	
	@Test
	public void testListarTodasLasRecetasPublicas() {
		RecetaPublica r1 = new RecetaPublica();
		RecetaPublica r2 = new RecetaPublica();
		Receta r3 = new Receta(fecheSena, null, null);
		
		repositorio.agregarReceta(r1);
		repositorio.agregarReceta(r2);
		repositorio.agregarReceta(r3);
		
		aux.add(r1);
		aux.add(r2);
	
		assertTrue(repositorio.listarRecetasPublicas().containsAll(aux));
		assertTrue(repositorio.listarRecetasPublicas().size() == 2);
	}

	

}
