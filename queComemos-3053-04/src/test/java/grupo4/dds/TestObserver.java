package grupo4.dds;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import org.junit.Test;


public class TestObserver {
	
	private Receta r1 = Receta.crearNueva();
	private Receta r2 = Receta.crearNueva();
	private Receta r3 = Receta.crearNueva();
	private Receta r4 = Receta.crearNueva();
	private Receta r5 = Receta.crearNueva();
	
	private List<Receta> l1 = Stream.of(r2, r3).collect(Collectors.toList());
	private List<Receta> l2 = Stream.of(r1, r2, r3, r4).collect(Collectors.toList());
	private List<Receta> l3 = Stream.of(r3, r5).collect(Collectors.toList());
	
	private Usuario u = Usuario.crearPerfil(null, null, null, 0, 0, null);
	private CantidadDeHoras cantidadHoras = new CantidadDeHoras();
	
	
	@Test
	public void testAumentaContadorDeHorasEnHora_1() {
		cantidadHoras.notificarConsulta(l1, u);
		cantidadHoras.notificarConsulta(l2, u);
		cantidadHoras.notificarConsulta(l3, u);
		
		assertTrue(cantidadHoras.cantidadDeConsultasPor(2) == 3);
		
	}
	
}
