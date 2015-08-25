package grupo4.dds;

import static org.junit.Assert.assertEquals;
import grupo4.dds.excepciones.HoraInvalida;
import grupo4.dds.monitores.CantidadDeHoras;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestMonitoresDeConsultas {

	@Rule public ExpectedException expectedExcetption = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test()
	public void testSiNoSeRealizaronConsultasRetorna0() {
		
		CantidadDeHoras monitorConsultasPorHora = new CantidadDeHoras();
		
		assertEquals(0, monitorConsultasPorHora.cantidadDeConsultasPor(0));
		assertEquals(0, monitorConsultasPorHora.cantidadDeConsultasPor(15));
		assertEquals(0, monitorConsultasPorHora.cantidadDeConsultasPor(23));		
	}
	
	@Test()
	public void testNoSePuedeSolicitarLaCantidaDeConsultasDeUnaHoraMayorA23() {
		expectedExcetption.expect(HoraInvalida.class);
		
		(new CantidadDeHoras()).cantidadDeConsultasPor(24);
	}
	
	@Test()
	public void testNoSePuedeSolicitarLaCantidaDeConsultasDeUnaHoraMenoresA0() {
		expectedExcetption.expect(HoraInvalida.class);
		
		(new CantidadDeHoras()).cantidadDeConsultasPor(-1);
	}

}
