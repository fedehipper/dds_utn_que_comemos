package grupo4.dds;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.monitores.CantidadDeVeganos;
import grupo4.dds.monitores.RecetaMasConsultada;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Test;


public class TestObserver {
	private static EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("fideo", null, "D");
	private static EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("arroz", null, "D");
	private static EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("lechon", null, "D");
	private static EncabezadoDeReceta encabezado4 = new EncabezadoDeReceta("sopa", null, "F");
	private static EncabezadoDeReceta encabezado5 = new EncabezadoDeReceta("milanga", null, "F");
	
	private static Receta r1 = Receta.crearNueva(null, encabezado1, null);
	private static Receta r2 = Receta.crearNueva(null, encabezado2, null);
	private static Receta r3 = Receta.crearNueva(null, encabezado3, null);
	private static Receta r4 = Receta.crearNueva(null, encabezado4, null);
	private static Receta r5 = Receta.crearNueva(null, encabezado5, null);
	
	private static List<Receta> l1 = Stream.of(r5, r4).collect(Collectors.toList());
	private static List<Receta> l2 = Stream.of(r1, r2, r3, r4).collect(Collectors.toList());
	private List<Receta> l3 = Stream.of(r3, r5).collect(Collectors.toList());
	
	private static Usuario u = Usuario.crearPerfil();
	private CantidadDeHoras cantidadHoras = new CantidadDeHoras();
	private CantidadDeVeganos cantidadVeganos = new CantidadDeVeganos();
	private Vegano vegeno = new Vegano();
	private static Usuario Ariel = Usuario.crearPerfil();
	private static RecetaMasConsultada recetaMasConsultada = new RecetaMasConsultada();
	
	
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
		Ariel.agregarCondicion(vegeno);
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
	
	
	
	
	
	
	
	
	
}
