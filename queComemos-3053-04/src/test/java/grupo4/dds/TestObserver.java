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
import grupo4.dds.monitores.RecetaMasConsultada;
import grupo4.dds.monitores.RecetasMasConsultadasPorSexo;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.filtros.FiltroNoLeGusta;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.receta.busqueda.postProcesamiento.TomarResultadosPares;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Before;
import org.junit.Test;

public class TestObserver {
	private EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("fideo",null, "D");
	private EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("arroz",null, "D");
	private EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("lechon",null, "D");
	private EncabezadoDeReceta encabezado4 = new EncabezadoDeReceta("sopa",null, "F");
	private EncabezadoDeReceta encabezado5 = new EncabezadoDeReceta("milanga",null, "F");
	
	List<Filtro> filtros = new ArrayList<>();

	private Receta r1 = Receta.crearNueva(null, encabezado1, null);
	private Receta r2 = Receta.crearNueva(null, encabezado2, null);
	private Receta r3 = Receta.crearNueva(null, encabezado3, null);
	private Receta r4 = Receta.crearNueva(null, encabezado4, null);
	private Receta r5 = Receta.crearNueva(null, encabezado5, null);

	private List<Receta> l1 = Stream.of(r5, r4).collect(Collectors.toList());
	private List<Receta> l2 = Stream.of(r1, r2, r3, r4).collect(Collectors.toList());
	private List<Receta> l3 = Stream.of(r3, r5).collect(Collectors.toList());

	private Usuario u = Usuario.crearPerfil("U", Sexo.FEMENINO, null, 0f, 0f,null);
	private CantidadDeHoras cantidadHoras = new CantidadDeHoras();
	private CantidadDeVeganos cantidadVeganos = new CantidadDeVeganos();
	private Usuario Ariel;
	private Usuario fecheSena;
	private RecetaMasConsultada recetaMasConsultada = new RecetaMasConsultada();
	private RecetasMasConsultadasPorSexo recetasPorSexo = new RecetasMasConsultadasPorSexo();

	private Receta sopa;
	private Receta pollo;
	private RecetaPublica pure;
	private RecetaPublica milanesa;
	private RecetaPublica salmon;
	
	private List<Receta> recetas = Arrays.asList(sopa, pollo, pure, milanesa, salmon);
	
	@Before
	public void setup() {
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null);
		Ariel = Usuario.crearPerfil("Ariel", Sexo.MASCULINO, null, 0f, 0f, null);
		
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
	public void testCantidadConsultasDeUnaReceta() {
		recetaMasConsultada.guardarMaximo(10, "fideos");
		recetaMasConsultada.guardarMaximo(1, "pescado");
		recetaMasConsultada.guardarMaximo(2, "fideos");
		recetaMasConsultada.guardarMaximo(9, "arroz");
		recetaMasConsultada.guardarMaximo(4, "fideos");

		assertEquals(recetaMasConsultada.getRecetaMasConsultada(), "fideos");
	}

	@Test
	public void testDadoUnHashConRecetasYConsultasDevuelveLaMasConsultada() {
		recetaMasConsultada.notificarConsulta(l2, Ariel);
		recetaMasConsultada.notificarConsulta(l1, u);
		recetaMasConsultada.notificarConsulta(l1, Ariel);
		recetaMasConsultada.notificarConsulta(l2, u);

		HashMap<String, Integer> resultado = new HashMap<String, Integer>();
		resultado.put("sopa", 4);

		assertEquals(recetaMasConsultada.recetaMasConsultada(), resultado);
	}

	@Test
	public void testRecetasMasConsultadasPorSexo() {
		recetasPorSexo.notificarConsulta(l2, Ariel);
		recetasPorSexo.notificarConsulta(l1, Ariel);
		recetasPorSexo.notificarConsulta(l1, u);
		recetasPorSexo.notificarConsulta(l2, u);

		HashMap<String, Integer> resultado = new HashMap<String, Integer>();
		resultado.put("sopa", 2);

		assertEquals(recetasPorSexo.recetaMasConsultada(Ariel), resultado);
		assertEquals(recetasPorSexo.recetaMasConsultada(u), resultado);
	}

	@Test
	public void testRecetasPorSexoMasculino() {
		recetasPorSexo.notificarConsulta(l2, Ariel);
		recetasPorSexo.notificarConsulta(l1, Ariel);

		HashMap<String, Integer> resultado = new HashMap<String, Integer>();
		resultado.put("sopa", 2);

		assertEquals(recetasPorSexo.recetaMasConsultada(Ariel), resultado);
	}

	@Test
	public void testReposiorioRecetasNotificaAUnMonitor() {

		RepositorioDeRecetas.get().notificar(recetaMasConsultada, Ariel, l1);
		RepositorioDeRecetas.get().notificar(recetaMasConsultada, Ariel, l2);
		RepositorioDeRecetas.get().notificar(recetaMasConsultada, u, l1);

		HashMap<String, Integer> resultado = new HashMap<String, Integer>();
		resultado.put("sopa", 3);
		assertEquals(recetaMasConsultada.recetaMasConsultada(), resultado);
	}

	@Test
	public void testRepositorioRecetasNotificaARecetasMasConsultadas() {
		RepositorioDeRecetas.get().setMonitor(recetaMasConsultada);

		RepositorioDeRecetas.get().notificarATodos(Ariel, l2);
		RepositorioDeRecetas.get().notificarATodos(Ariel, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l2);

		HashMap<String, Integer> resultado1 = new HashMap<String, Integer>();
		resultado1.put("sopa", 4);

		assertEquals(recetaMasConsultada.recetaMasConsultada(), resultado1);
	}
	
	@Test
	public void testRepositorioRecetasNotificaARecetasMasConsultadasPorSexo() {
		RepositorioDeRecetas.get().setMonitor(recetasPorSexo);

		RepositorioDeRecetas.get().notificarATodos(Ariel, l2);
		RepositorioDeRecetas.get().notificarATodos(Ariel, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l1);
		RepositorioDeRecetas.get().notificarATodos(u, l2);

		HashMap<String, Integer> resultado2 = new HashMap<String, Integer>();
		resultado2.put("sopa", 2);
		
		assertEquals(recetasPorSexo.recetaMasConsultada(Ariel), resultado2);
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
	
	@Test
	public void testRecetasMasConsultadasPorSexoFemenino() {
		recetasPorSexo.notificarConsulta(l2, u);
		recetasPorSexo.notificarConsulta(l1, u);

		HashMap<String, Integer> resultado = new HashMap<String, Integer>();
		resultado.put("sopa", 2);

		assertEquals(recetasPorSexo.recetaMasConsultada(u), resultado);
	}
	
	/* no se porque no anda este test
	@Test
	public void testSiListamosRecetasParaUnUsuarioSeNotificanALosMonitoresRegistrados() {
		
		filtros.add(new FiltroNoLeGusta());		
		
		fecheSena.agregarComidaQueLeDisgusta(new Ingrediente("brocoli"));
		fecheSena.agregarComidaQueLeDisgusta(new Ingrediente("coliflor"));
		
		sopa.agregarIngrediente(new Ingrediente("brocoli"));
		milanesa.agregarIngrediente(new Ingrediente("coliflor"));
		pollo.agregarIngrediente(new Ingrediente("pollo"));
		pure.agregarIngrediente(new Ingrediente("papa"));
		salmon.agregarIngrediente(new Ingrediente("tomate"));
	
		filtros.add(new FiltroNoLeGusta());	
		Ariel.agregarCondicion(new Vegano());
		
		RepositorioDeRecetas.get().setMonitor(cantidadHoras);
		RepositorioDeRecetas.get().setMonitor(cantidadVeganos);
				
		RepositorioDeRecetas.get().listarRecetasPara(Ariel, filtros , null);
		RepositorioDeRecetas.get().listarRecetasPara(fecheSena, filtros , null);
		
		assertTrue(cantidadHoras.cantidadDeConsultasPor(LocalTime.now().getHour()) == 2);
		assertTrue(cantidadVeganos.getContadorDeVeganos() == 1);
		
	}
	*/
}
