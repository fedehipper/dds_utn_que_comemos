package grupo4.dds;

import static org.junit.Assert.*;
import static grupo4.dds.usuario.Rutina.*;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaDelSistema;
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
		recetaDeJuancho.getIngredientes().put("morron", 80.0);
		recetaDeJuancho.getIngredientes().put("sal", 90.0);
		recetaDeJuancho.getIngredientes().put("caldo", 8.0);
		recetaDeJuancho.getIngredientes().put("carne", 90.0);
		recetaDeJuancho.getCondimentos().put("azucar", 100.0);
		recetaDeJuancho.setCalorias(10);	
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
