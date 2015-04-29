package grupo4.dds;

import static grupo4.dds.Rutina.ACTIVA_EJERCICIO_ADICIONAL;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class testCondiciones {

	private Usuario juancho = new Usuario("juancho", 'M', LocalDate.of(1000,
			04, 04), 1.80, 70.0, ACTIVA_EJERCICIO_ADICIONAL);
	private Celiaco celiaco = new Celiaco();
	private Vegano vegano = new Vegano();
	private Diabetico diabetico = new Diabetico();
	private Hipertenso hipertenso = new Hipertenso();
	private Receta recetaDeJuancho = new Receta(juancho);
	private RecetaDelSistema recetaDeTodos = new RecetaDelSistema();

	@Before
	public void setUp() throws Exception {

		Collection<String> preferenciasFrutas = new ArrayList<>();
		preferenciasFrutas.add("frutas");
		juancho.setPreferenciasAlimenticias(preferenciasFrutas);
	}

	@Test
	public void testCumpleNecesidadesDeCeliaco() {
		assertTrue(celiaco.cumpleNecesidades(juancho));
	}

	@Test
	public void testCumpleNecesidadesDeVegano() {
		assertTrue(vegano.cumpleNecesidades(juancho));
	}

	@Test
	public void testCumpleNecesidadesDeDiabetico() {
		assertTrue(diabetico.cumpleNecesidades(juancho));
	}

	@Test
	public void testCumpleNecesidadesDeHipertenso() {
		assertTrue(hipertenso.cumpleNecesidades(juancho));
	}

}
