package grupo4.dds;

import static grupo4.dds.usuario.Alimento.FRUTAS;
import static grupo4.dds.usuario.Rutina.ACTIVA_EJERCICIO_ADICIONAL;
import static grupo4.dds.usuario.Sexo.MASCULINO;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Alimento;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class TestCondiciones {

	private Usuario juancho = new Usuario("juancho", MASCULINO, LocalDate.of(1000,
			04, 04), 1.80f, 70.0f, ACTIVA_EJERCICIO_ADICIONAL);
	private Celiaco celiaco = new Celiaco();
	private Vegano vegano = new Vegano();
	private Diabetico diabetico = new Diabetico();
	private Hipertenso hipertenso = new Hipertenso();
	private Receta recetaDeJuancho = new Receta(juancho);

	@Before
	public void setUp() throws Exception {
		recetaDeJuancho.getIngredientes().put("morron", 80.0);
		recetaDeJuancho.getIngredientes().put("sal", 90.0);
		recetaDeJuancho.getIngredientes().put("caldo", 8.0);
		recetaDeJuancho.getIngredientes().put("carne", 90.0);
		recetaDeJuancho.getCondimentos().put("azucar", 100.0);
		recetaDeJuancho.setCalorias(10);	
		Collection<Alimento> preferenciasFrutas = new ArrayList<>();
		preferenciasFrutas.add(FRUTAS);
		juancho.setPreferenciasAlimenticias(preferenciasFrutas);
	}

	@Test
	public void testCumpleNecesidadesDeCeliaco() {
		assertTrue(celiaco.subsanaCondicion(juancho));
	}

	@Test
	public void testCumpleNecesidadesDeVegano() {
		assertTrue(vegano.subsanaCondicion(juancho));
	}

	@Test
	public void testCumpleNecesidadesDeDiabetico() {
		assertTrue(diabetico.subsanaCondicion(juancho));
	}

	@Test
	public void testCumpleNecesidadesDeHipertenso() {
		assertTrue(hipertenso.subsanaCondicion(juancho));
	}
	
	@Test
	public void esRecetaInadecuadaEnHipertensos() {
		assertTrue(!(hipertenso.esRecomendable(recetaDeJuancho)));
	}
	
	@Test
	public void esRecetaInadecuadaEnVeganos() {
		assertTrue(!(vegano.esRecomendable(recetaDeJuancho)));
	}
	
	@Test
	public void esRecetaAdecuadaEnCeliacos() {
		assertTrue((celiaco.esRecomendable(recetaDeJuancho))); 
	}
	
	@Test
	public void esRecetaAdecuadaEnDiabeticos() {
		assertTrue(diabetico.esRecomendable(recetaDeJuancho));
	}
}
