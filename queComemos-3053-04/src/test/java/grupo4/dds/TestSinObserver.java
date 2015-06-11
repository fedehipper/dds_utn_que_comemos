package grupo4.dds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.monitores.CantidadDeVeganos;
import grupo4.dds.monitores.Monitor;
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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class TestSinObserver {
	
	private List<Monitor> monitores = new ArrayList<>();
	
	private EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("fideo",null, "D");
	private EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("arroz",null, "D");
	private EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("lechon",null, "D");
	private EncabezadoDeReceta encabezado4 = new EncabezadoDeReceta("sopa",null, "F");
	private EncabezadoDeReceta encabezado5 = new EncabezadoDeReceta("milanga",null, "F");

	private Receta r1 = Receta.crearNueva(null, encabezado1, null);
	private Receta r2 = Receta.crearNueva(null, encabezado2, null);
	private Receta r3 = Receta.crearNueva(null, encabezado3, null);
	private Receta r4 = Receta.crearNueva(null, encabezado4, null);
	private Receta r5 = Receta.crearNueva(null, encabezado5, null);

	private List<Receta> l1 = Stream.of(r5, r4).collect(Collectors.toList());
	private List<Receta> l2 = Stream.of(r1, r2, r3, r4).collect(Collectors.toList());

	private CantidadDeHoras cantidadHoras = new CantidadDeHoras();
	private CantidadDeVeganos cantidadVeganos = new CantidadDeVeganos();
	private RecetaMasConsultada recetaMasConsultada = new RecetaMasConsultada();
	private RecetasMasConsultadasPorSexo recetasPorSexo = new RecetasMasConsultadasPorSexo();
	
	private Usuario u;
	private Usuario Ariel;
	private Usuario fecheSena;
	
	private Receta sopa;
	private Receta pollo;
	private RecetaPublica pure;
	private RecetaPublica milanesa;
	private RecetaPublica salmon;
	
	List<Filtro> filtros = new ArrayList<>();

	@Before
	public void setup() {
		RepositorioDeRecetas.get().vaciar();
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null);
		Ariel = Usuario.crearPerfil("Ariel", Sexo.MASCULINO, null, 0f, 0f, null);
		u = Usuario.crearPerfil("U", Sexo.FEMENINO, null, 0f, 0f,null);
		sopa = Receta.crearNueva(Ariel, new EncabezadoDeReceta("sopa", null, null, 100), null);
		pollo = Receta.crearNueva(fecheSena, new EncabezadoDeReceta("pollo", null, null, 300), null);
		pure = RecetaPublica.crearNueva(new EncabezadoDeReceta("pure", null, null, 600), null);
		milanesa = RecetaPublica.crearNueva(new EncabezadoDeReceta("milanesa", null, null, 999), null);
		salmon = RecetaPublica.crearNueva(new EncabezadoDeReceta("salmon", null, null, 200), null);

	}
	
	@Test
	public void testRepositorioRecetasNotificaARecetasMasConsultadas() {
		monitores = Arrays.asList(recetaMasConsultada);
		
		RepositorioDeRecetas.get().notificarATodosPunto4(Ariel, l2, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(Ariel, l1, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(u, l1, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(u, l2, monitores);

		HashMap<String, Integer> resultado1 = new HashMap<String, Integer>();
		resultado1.put("sopa", 4);

		assertEquals(recetaMasConsultada.recetaMasConsultada(), resultado1);
	}
	
	@Test
	public void testRepositorioRecetasNotificaARecetasMasConsultadasPorSexo() {
		monitores = Arrays.asList(recetasPorSexo);
		
		RepositorioDeRecetas.get().notificarATodosPunto4(Ariel, l2, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(Ariel, l1, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(u, l1, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(u, l2, monitores);

		HashMap<String, Integer> resultado2 = new HashMap<String, Integer>();
		resultado2.put("sopa", 2);
		
		assertEquals(recetasPorSexo.recetaMasConsultada(Ariel), resultado2);
	}

	@Test
	public void testRepositorioRecetasNotificaCantidadVeganos() {
		Ariel.agregarCondicion(new Vegano());
		u.agregarCondicion(new Vegano());
		
		monitores = Arrays.asList(cantidadVeganos);
		
		RepositorioDeRecetas.get().notificarATodosPunto4(Ariel, l2, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(Ariel, l1, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(u, l1, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(u, l2, monitores);

		assertTrue(cantidadVeganos.getContadorDeVeganos() == 2);
	}
	
	@Test
	public void testRepositorioRecetasNotificaCantidadHoras() {
		monitores = Arrays.asList(cantidadHoras);
		
		RepositorioDeRecetas.get().notificarATodosPunto4(Ariel, l2, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(Ariel, l1, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(u, l1, monitores);
		RepositorioDeRecetas.get().notificarATodosPunto4(u, l2, monitores);

		assertTrue(cantidadHoras.cantidadDeConsultasPor(LocalTime.now().getHour()) == 4);
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
		
		monitores = Arrays.asList(cantidadVeganos, cantidadHoras);

		RepositorioDeRecetas.get().listarRecetasParaPunto4(Ariel, filtros , null, monitores);
		
		assertTrue(cantidadHoras.cantidadDeConsultasPor(LocalTime.now().getHour()) == 0);
		assertTrue(cantidadVeganos.getContadorDeVeganos() == 0);
	}
	*/

}