package grupo4.dds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.monitores.CantidadDeVeganos;
import grupo4.dds.monitores.Monitor;
import grupo4.dds.monitores.RecetaMasConsultada;
import grupo4.dds.monitores.RecetasMasConsultadasPorSexo;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
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
	
	
	List<Filtro> filtros = new ArrayList<>();

	@Before
	public void setup() {
		RepositorioDeRecetas.get().vaciar();
		Ariel = Usuario.crearPerfil("Ariel", Sexo.MASCULINO, null, 0f, 0f, null);
		u = Usuario.crearPerfil("U", Sexo.FEMENINO, null, 0f, 0f,null);
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
}