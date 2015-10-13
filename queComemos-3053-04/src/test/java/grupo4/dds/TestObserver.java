package grupo4.dds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.monitores.CantidadDeVeganos;
import grupo4.dds.monitores.RecetasMasConsultadas;
import grupo4.dds.monitores.RecetasMasConsultadasPorSexo;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.condicion.Vegano;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class TestObserver extends BaseTest implements WithGlobalEntityManager {
	
	private List<Receta> consulta1;
	private List<Receta> consulta2;
	
	@Before
	public void setup() {
		consulta1 = Arrays.asList(sopa, sopa, bife, pure, coliflor, bife, sopa, salmon, bife, bife);
		consulta2 = Arrays.asList(sopa, sopa, sopa, salmon, bife, bife);
	}

	@Test
	public void testMonitorDeConsultasPorHora() {
		
		CantidadDeHoras cantidadHoras = new CantidadDeHoras();
		
		cantidadHoras.notificarConsulta(consulta1, null);
		cantidadHoras.notificarConsulta(consulta1, null);
		cantidadHoras.notificarConsulta(consulta1, null);

		assertTrue(cantidadHoras.cantidadDeConsultasPor(LocalTime.now().getHour()) == 3);
	}

	@Test
	public void testMonitorContadorDeVeganos() {
		
		CantidadDeVeganos cantidadVeganos = new CantidadDeVeganos();
		List<Receta> consultaConRecetaDificil = new ArrayList<Receta>(consulta1);
		consultaConRecetaDificil.add((new BuilderReceta()).calorias(150).ingrediente(null).creador(maria).nombre("Ratatouille").dificil().build());
		
		cantidadVeganos.notificarConsulta(consulta1, arielFolino);
		arielFolino.agregarCondicion(new Vegano());
		cantidadVeganos.notificarConsulta(consulta1, arielFolino);
		cantidadVeganos.notificarConsulta(consulta1, maria);
		cantidadVeganos.notificarConsulta(consultaConRecetaDificil, arielFolino);
		cantidadVeganos.notificarConsulta(consultaConRecetaDificil, arielFolino);
		maria.agregarCondicion(new Vegano());
		cantidadVeganos.notificarConsulta(consultaConRecetaDificil, maria);
		
		assertEquals(3, cantidadVeganos.getContadorDeVeganos());		
	}

	@Test
	public void testMonitorRecetasMasConsultadas() {

		RecetasMasConsultadas recetasMasConsultadas = new RecetasMasConsultadas();
		recetasMasConsultadas.notificarConsulta(consulta1, null);
		
		Set<Receta> masConsultadas = recetasMasConsultadas.recetasMasConsultadas(1).keySet();
		assertTrue(masConsultadas.contains(bife));
	}

	@Test
	public void testMonitorRecetasMasConsultadasPorHombres() {
		
		RecetasMasConsultadasPorSexo recetasMasConsultadas = new RecetasMasConsultadasPorSexo();
		recetasMasConsultadas.notificarConsulta(consulta1, arielFolino);

		Set<Receta> masConsultadas = recetasMasConsultadas.recetasMasConsultadasPor(Sexo.MASCULINO, 1).keySet();
		assertTrue(masConsultadas.contains(bife));
	}
	
	@Test
	public void testMonitorRecetasMasConsultadasPorMujeres() {
		
		RecetasMasConsultadasPorSexo recetasMasConsultadas = new RecetasMasConsultadasPorSexo();
		recetasMasConsultadas.notificarConsulta(consulta2, maria);
		
		Set<Receta> masConsultadas = recetasMasConsultadas.recetasMasConsultadasPor(Sexo.FEMENINO, 1).keySet();
		assertTrue(masConsultadas.contains(sopa));
	}
	
}
