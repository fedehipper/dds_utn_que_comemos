package grupo4.dds;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

public class TestRepositorioDeRecetas {
	
	private  RepositorioDeRecetas repositorio;
	private  List<Receta> aux;
	private Usuario fecheSena;
	private Usuario arielFolino;
	private Usuario matiasMartino;
	private Usuario federicoHipper;
	private Usuario cristianMaldonado;

	@Before
	public void setUp() {
		repositorio = new RepositorioDeRecetas();
		aux = new ArrayList<>();
		fecheSena = new Usuario("Feche Sena", null, 1.70f, 65.0f, null);
		arielFolino = new Usuario("Ariel Folino", null, 1.69f, 96.0f, null);
		matiasMartino = new Usuario("Mat�as Martino", null, 1.74f, 79.0f, null);
		federicoHipper = new Usuario("Federico Hipperdinger", null, 1.91f, 99.0f, null);
		cristianMaldonado = new Usuario("Cristian Maldonado", null, 1.34f, 87.0f, null);
	}
	
	@Test
	public void testAgregarRecetasAlRepositorio() {
		Receta r1 = new Receta();
		Receta r2 = new Receta();
		Receta r3 = new Receta();
		
		repositorio.agregarReceta(r1);
		repositorio.agregarReceta(r2);
		repositorio.agregarReceta(r3);
		aux = Stream.of(r1, r2, r3).collect(Collectors.toList());
		
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
	public void testListarRecetasQuePuedeVerUnUsuarioTodasPublicas() {
		RecetaPublica r1 = new RecetaPublica();
		RecetaPublica r2 = new RecetaPublica();
		RecetaPublica r3 = new RecetaPublica();
		
		repositorio.agregarReceta(r1);
		repositorio.agregarReceta(r2);
		repositorio.agregarReceta(r3);
		
		aux = Stream.of(r1, r2, r3).collect(Collectors.toList());
		
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		
		grupo1.agregarUsuario(matiasMartino);
		grupo1.agregarUsuario(arielFolino);

		GrupoUsuarios grupo2 = new GrupoUsuarios("grupo2");
		
		grupo2.agregarUsuario(federicoHipper);
		grupo2.agregarUsuario(cristianMaldonado);
		grupo2.agregarUsuario(arielFolino);
		
		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo2);
		fecheSena.agregarGrupo(grupo1);
		
		federicoHipper.agregarGrupo(grupo2);
		cristianMaldonado.agregarGrupo(grupo2);
	
		assertTrue(repositorio.listarRecetasPara(arielFolino).containsAll(aux));
	}
	
	@Test
	public void testListarRecetasQuePuedeVerUnUsuarioPrivadasYPPublicas() {
		Receta r1 = new Receta(fecheSena, null, null);
		Receta r2 = new Receta(cristianMaldonado, null, null);
		RecetaPublica r3 = new RecetaPublica();
		Receta r4 = new Receta(federicoHipper, null, null);
		
		repositorio.agregarReceta(r1);
		repositorio.agregarReceta(r2);
		repositorio.agregarReceta(r3);
		repositorio.agregarReceta(r4);
		
		aux = Stream.of(r1, r2, r3).collect(Collectors.toList());
		
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		
		grupo1.agregarUsuario(matiasMartino);
		grupo1.agregarUsuario(arielFolino);
		grupo1.agregarUsuario(fecheSena);

		GrupoUsuarios grupo2 = new GrupoUsuarios("grupo2");
		
		grupo2.agregarUsuario(cristianMaldonado);
		grupo2.agregarUsuario(arielFolino);
		
		matiasMartino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo1);
		arielFolino.agregarGrupo(grupo2);
		fecheSena.agregarGrupo(grupo1);
		cristianMaldonado.agregarGrupo(grupo2);
	
		assertTrue(repositorio.listarRecetasPara(arielFolino).containsAll(aux));
	}
	
	@Test
	public void testElUsuarioNoPuedeVerNingunaReceta() {
		
		Receta r1 = new Receta(fecheSena, null, null);
		Receta r2 = new Receta(cristianMaldonado, null, null);
		Receta r4 = new Receta(federicoHipper, null, null);
		
		repositorio.agregarReceta(r1);
		repositorio.agregarReceta(r2);
		repositorio.agregarReceta(r4);
		
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		
		grupo1.agregarUsuario(matiasMartino);
		grupo1.agregarUsuario(arielFolino);
		
		GrupoUsuarios grupo2 = new GrupoUsuarios("grupo2");
		
		grupo2.agregarUsuario(cristianMaldonado);
		
		matiasMartino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);
		cristianMaldonado.agregarGrupo(grupo2);
	
		assertTrue(repositorio.listarRecetasPara(arielFolino).isEmpty());
	}
	
}
