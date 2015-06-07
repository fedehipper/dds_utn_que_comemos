package grupo4.dds;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.monitores.CantidadDeVeganos;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Test;


public class TestObserver {
	private EncabezadoDeReceta encabezadoDificil = new EncabezadoDeReceta(null, null, "D");
	private EncabezadoDeReceta encabezadoFacil = new EncabezadoDeReceta(null, null, "F");
	private Receta r1 = Receta.crearNueva(null, encabezadoDificil, null);
	private Receta r2 = Receta.crearNueva(null, encabezadoDificil, null);
	private Receta r3 = Receta.crearNueva(null, encabezadoDificil, null);
	private Receta r4 = Receta.crearNueva(null, encabezadoFacil, null);
	private Receta r5 = Receta.crearNueva(null, encabezadoFacil, null);
	
	private List<Receta> l1 = Stream.of(r5, r4).collect(Collectors.toList());
	private List<Receta> l2 = Stream.of(r1, r2, r3, r4).collect(Collectors.toList());
	private List<Receta> l3 = Stream.of(r3, r5).collect(Collectors.toList());
	
	private Usuario u = Usuario.crearPerfil();
	private CantidadDeHoras cantidadHoras = new CantidadDeHoras();
	private CantidadDeVeganos cantidadVeganos = new CantidadDeVeganos();
	private Vegano vegeno = new Vegano();
	private Usuario Ariel = Usuario.crearPerfil();
	
	
	@Test
	public void testAumentaContadorDeHorasEnHora_2() {
		cantidadHoras.notificarConsulta(l1, u);
		cantidadHoras.notificarConsulta(l2, u);
		cantidadHoras.notificarConsulta(l3, u);
		
		assertTrue(cantidadHoras.cantidadDeConsultasPor(2) == 3);
	}
	
	@Test
	public void testAumentaContadorDeVeganos() {
		cantidadVeganos.notificarConsulta(l1, Ariel);
		Ariel.agregarCondicion(vegeno);
		cantidadVeganos.notificarConsulta(l2, u);
		cantidadVeganos.notificarConsulta(l3, Ariel);
		
		assertTrue(cantidadVeganos.getContadorDeVeganos() == 1);
	}
	
	
	
}
