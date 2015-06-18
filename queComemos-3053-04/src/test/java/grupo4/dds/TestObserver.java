package grupo4.dds;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.monitores.CantidadDeVeganos;
import grupo4.dds.monitores.RecetasMasConsultadas;
import grupo4.dds.monitores.RecetasMasConsultadasPorSexo;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;

public class TestObserver {
	private EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("fideo",null, Dificultad.DIFICIL);
	private EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("arroz",null, Dificultad.DIFICIL);
	private EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("lechon",null, Dificultad.DIFICIL);
	private EncabezadoDeReceta encabezado4 = new EncabezadoDeReceta("sopa",null, Dificultad.FACIL);
	private EncabezadoDeReceta encabezado5 = new EncabezadoDeReceta("milanesa",null, Dificultad.FACIL);
	
	List<Filtro> filtros = new ArrayList<>();

	private Receta r1 = Receta.crearNueva(null, encabezado1, null);
	private Receta r2 = Receta.crearNueva(null, encabezado2, null);
	private Receta r3 = Receta.crearNueva(null, encabezado3, null);
	private Receta r4 = Receta.crearNueva(null, encabezado4, null);
	private Receta r5 = Receta.crearNueva(null, encabezado5, null);

	private List<Receta> l1 = Stream.of(r5, r4).collect(Collectors.toList());
	private List<Receta> l2 = Stream.of(r1, r2, r3, r4).collect(Collectors.toList());
	private List<Receta> l3 = Stream.of(r3, r5).collect(Collectors.toList());

	private Usuario u = Usuario.crearPerfil("U", Sexo.FEMENINO, null, 0f, 0f,null, false);
	private CantidadDeHoras cantidadHoras = new CantidadDeHoras();
	private CantidadDeVeganos cantidadVeganos = new CantidadDeVeganos();
	private Usuario Ariel;
	private Usuario fecheSena;
	private RecetasMasConsultadas recetasMasConsultadas = new RecetasMasConsultadas();
	private RecetasMasConsultadasPorSexo recetasPorSexo = new RecetasMasConsultadasPorSexo();

	private Receta sopa;
	private Receta pollo;
	private RecetaPublica pure;
	private RecetaPublica milanesa;
	private RecetaPublica salmon;
	
	private List<Receta> recetas = Arrays.asList(sopa, pollo, pure, milanesa, salmon);
	
	@Before
	public void setup() {
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null, false);
		Ariel = Usuario.crearPerfil("Ariel", Sexo.MASCULINO, null, 0f, 0f, null, false);
		
		sopa = Receta.crearNueva(Ariel, new EncabezadoDeReceta("sopa", null, null, 100), null);
		pollo = Receta.crearNueva(fecheSena, new EncabezadoDeReceta("pollo", null, null, 300), null);
		pure = RecetaPublica.crearNueva(new EncabezadoDeReceta("pure", null, null, 600), null);
		milanesa = RecetaPublica.crearNueva(new EncabezadoDeReceta("milanesa", null, null, 999), null);
		salmon = RecetaPublica.crearNueva(new EncabezadoDeReceta("salmon", null, null, 200), null);
		
		RepositorioDeRecetas.get().agregarListaDeRecetas(recetas);
	}

	@Test
	public void testAumentaContadorDeHorasEnHoraActual() {
		cantidadHoras.notificarConsulta(l1, u);
		cantidadHoras.notificarConsulta(l2, u);
		cantidadHoras.notificarConsulta(l3, u);

		assertTrue(cantidadHoras.cantidadDeConsultasPor(LocalTime.now().getHour()) == 3);
	}

	@Test
	public void testAumentaContadorDeVeganos() {
		cantidadVeganos.notificarConsulta(l1, Ariel);
		Ariel.agregarCondicion(new Vegano());
		cantidadVeganos.notificarConsulta(l2, u);
		cantidadVeganos.notificarConsulta(l3, Ariel);

		assertTrue(cantidadVeganos.getContadorDeVeganos() == 1);
	}

	@Test
	public void testDadoUnHashConRecetasYConsultasDevuelveLaMasConsultada() {
		recetasMasConsultadas.notificarConsulta(l2, Ariel);
		recetasMasConsultadas.notificarConsulta(l1, u);
		recetasMasConsultadas.notificarConsulta(l1, Ariel);
		recetasMasConsultadas.notificarConsulta(l2, u);

		HashMap<Receta, Integer> recetas = new HashMap<>();
		recetas.put(r4, 4);

		assertEquals(recetasMasConsultadas.recetasMasConsultadas(1), recetas);
	}

	@Test
	public void testRecetasMasConsultadasPorSexoFemenino() {
		recetasPorSexo.notificarConsulta(l1, Ariel);
		recetasPorSexo.notificarConsulta(l1, Ariel);
		recetasPorSexo.notificarConsulta(l2, Ariel);
		recetasPorSexo.notificarConsulta(l1, u);
		recetasPorSexo.notificarConsulta(l3, u);
		recetasPorSexo.notificarConsulta(l3, u);

		HashMap<Receta, Integer> resultado = new HashMap<>();
		resultado.put(r5, 3);
		resultado.put(r3, 2);		
	
		assertEquals(recetasPorSexo.recetasMasConsultadasPor(Sexo.FEMENINO, 2), resultado);
	}
	
	@Test
	public void testRecetasMasConsultadasPorSexoMasculino() {
		recetasPorSexo.notificarConsulta(l1, Ariel);
		recetasPorSexo.notificarConsulta(l1, Ariel);
		recetasPorSexo.notificarConsulta(l2, Ariel);
		recetasPorSexo.notificarConsulta(l1, u);
		recetasPorSexo.notificarConsulta(l3, u);
		recetasPorSexo.notificarConsulta(l3, u);

		HashMap<Receta, Integer> resultado = new HashMap<>();
		resultado.put(r4, 3);
		resultado.put(r5, 2);			
		
		assertEquals(recetasPorSexo.recetasMasConsultadasPor(Sexo.MASCULINO, 2), resultado);

	}

	@Test
	public void testReposiorioRecetasNotificaAUnMonitor() {

		RepositorioDeRecetas.get().notificar(recetasMasConsultadas, Ariel, l1);
		RepositorioDeRecetas.get().notificar(recetasMasConsultadas, Ariel, l1);
		RepositorioDeRecetas.get().notificar(recetasMasConsultadas, u, l2);
	
		HashMap<Receta, Integer> recetas = new HashMap<>();
		recetas.put(r4, 3);
		recetas.put(r5, 2);
		
		assertEquals(recetasMasConsultadas.recetasMasConsultadas(2), recetas);
	}

	@Test
	public void testRepositorioRecetasNotificaARecetasMasConsultadas() {
		RepositorioDeRecetas.get().setMonitor(recetasMasConsultadas);

		RepositorioDeRecetas.get().notificarATodos(Ariel, l2);
		RepositorioDeRecetas.get().notificarATodos(Ariel, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l3);
		RepositorioDeRecetas.get().notificarATodos(u, l2);
		
		HashMap<Receta, Integer> recetas = new HashMap<>();
		recetas.put(r4, 5);
		recetas.put(r5, 4);
		recetas.put(r3, 3);
		
		assertEquals(recetasMasConsultadas.recetasMasConsultadas(3), recetas);
	}
	
	@Test
	public void testRepositorioRecetasNotificaARecetasMasConsultadasPorSexo() {
		RepositorioDeRecetas.get().setMonitor(recetasPorSexo);

		RepositorioDeRecetas.get().notificarATodos(Ariel, l2);
		RepositorioDeRecetas.get().notificarATodos(Ariel, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l2);

		HashMap<Receta, Integer> resultado = new HashMap<>();
		resultado.put(r4, 2);
		
		assertEquals(recetasPorSexo.recetasMasConsultadasPor(Sexo.MASCULINO, 1), resultado);
	}

	@Test
	public void testRepositorioRecetasNotificaCantidadVeganos() {
		RepositorioDeRecetas.get().setMonitor(cantidadVeganos);
		
		Ariel.agregarCondicion(new Vegano());
		u.agregarCondicion(new Vegano());
		RepositorioDeRecetas.get().notificarATodos(Ariel, l2);
		RepositorioDeRecetas.get().notificarATodos(Ariel, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l2);

		assertTrue(cantidadVeganos.getContadorDeVeganos() == 2);
	}
	
	@Test
	public void testRepositorioRecetasNotificaCantidadHoras() {
		RepositorioDeRecetas.get().setMonitor(cantidadHoras);

		RepositorioDeRecetas.get().notificarATodos(Ariel, l2);
		RepositorioDeRecetas.get().notificarATodos(Ariel, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l2);

		assertTrue(cantidadHoras.cantidadDeConsultasPor(LocalTime.now().getHour()) == 4);
	}
	
}
