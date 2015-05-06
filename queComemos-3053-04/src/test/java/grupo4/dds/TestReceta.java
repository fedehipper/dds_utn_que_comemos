package grupo4.dds;

import static grupo4.dds.usuario.Rutina.ACTIVA_EJERCICIO_ADICIONAL;
import static grupo4.dds.usuario.Sexo.MASCULINO;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class TestReceta {
	
	private Usuario pedro = new Usuario("pedro", MASCULINO,
			LocalDate.of(2015, 04, 23), 1.50f, 90.0f, ACTIVA_EJERCICIO_ADICIONAL);
	private RecetaPublica receta = new RecetaPublica();
	private Celiaco celiaco = new Celiaco();
	private Vegano vegano = new Vegano();
	private Diabetico diabetico = new Diabetico();
	private Hipertenso hipertenso = new Hipertenso();
	private RecetaPublica recetaDePedro = new RecetaPublica();
	

	
	@Before
	public void setUp() {
		receta.agregarIngrediente("morron", 80.0f);
		receta.agregarIngrediente("sal", 90.0f);
		receta.agregarIngrediente("caldo", 8.0f);
		receta.agregarIngrediente("carne", 90.0f);
		receta.agregarCondimento("azucar", 100.0f);
		receta.setTotalCalorias(10);	
		recetaDePedro.agregarIngrediente("miel", 60.0f);
		recetaDePedro.agregarIngrediente("agua", 160.0f);
		recetaDePedro.agregarIngrediente("levadura", 50.0f);
		recetaDePedro.agregarIngrediente("hojas de menta", 1.0f);
		recetaDePedro.setTotalCalorias(50);
	}

	@Test
	public void testRecetaEsValida() {
		assertTrue(receta.esValida());
	}
	
	

	@Test
	public void esRecetaAdecuadaEnCeliacos() {
		assertTrue((celiaco.esRecomendable(receta))); 
	}
		


	@Test 
	public void testEsRecetaInadecuadaParaUnUsuario() {
		pedro.agregarCondicion(hipertenso);
		pedro.agregarCondicion(vegano);
		pedro.agregarCondicion(celiaco);
		pedro.agregarCondicion(diabetico);
		pedro.agregarReceta(receta);
		assertTrue(!(pedro.esAdecuada(receta)));
	}

	
	@Test
	public void testUnUsuarioPuedeAgregarUnaRecetaValidaSiEsElCreador(){
		pedro.agregarReceta(recetaDePedro);
		assertTrue(pedro.getRecetas().contains(recetaDePedro));
	}
}

