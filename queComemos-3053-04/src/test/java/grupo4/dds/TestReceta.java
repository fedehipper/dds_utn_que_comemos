package grupo4.dds;

import static grupo4.dds.usuario.Rutina.ACTIVA_EJERCICIO_ADICIONAL;
import static grupo4.dds.usuario.Sexo.MASCULINO;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.Receta;
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
	private Usuario juancho = new Usuario("juancho", MASCULINO,
			LocalDate.of(2015, 04, 23), 1.50f, 90.0f, ACTIVA_EJERCICIO_ADICIONAL);
	private RecetaPublica receta = new RecetaPublica();
	private Celiaco celiaco = new Celiaco();
	private Vegano vegano = new Vegano();
	private Diabetico diabetico = new Diabetico();
	private Hipertenso hipertenso = new Hipertenso();
	private Receta recetaDePedro = new Receta(pedro);
	private Receta recetaDeJuancho = new Receta(juancho);
		

	
	@Before
	public void setUp() {
		receta.getIngredientes().put("morron", 80.0f);
		receta.getCondimentos().put("sal", 90.0f);
		receta.getIngredientes().put("caldo", 8.0f);
		receta.getIngredientes().put("carne", 90.0f);
		receta.getCondimentos().put("azucar", 101.0f);
		recetaDePedro.getIngredientes().put("miel", 60.0f);
		recetaDePedro.getIngredientes().put("agua", 160.0f);
		recetaDePedro.getIngredientes().put("levadura", 50.0f);
		recetaDePedro.getIngredientes().put("hojas de menta", 1.0f);
		recetaDeJuancho.getIngredientes().put("miel", 60.0f);
		recetaDeJuancho.getIngredientes().put("agua", 160.0f);
		recetaDeJuancho.getIngredientes().put("levadura", 50.0f);
		recetaDeJuancho.getIngredientes().put("hojas de menta", 1.0f);
	}

	@Test
	public void testRecetaEsValida() {
		assertTrue(receta.esValida());
	}
			
	@Test 
	public void testEsRecetaAdecuadaParaUnCeliaco() {
		pedro.agregarCondicion(celiaco);
		assertTrue(pedro.esAdecuada(receta));
	}
	
	@Test 
	public void testEsRecetaInadecuadaParaUnVegano() {
		pedro.agregarCondicion(vegano);
		assertTrue(!(pedro.esAdecuada(receta)));
	}
		
	@Test 
	public void testEsRecetaInadecuadaParaUnDiabetico() {
		pedro.agregarCondicion(diabetico);
		assertTrue(!(pedro.esAdecuada(receta)));
	}
	
	@Test 
	public void testEsRecetaInadecuadaParaUnHipertenso() {
		pedro.agregarCondicion(hipertenso);
		assertTrue(!(pedro.esAdecuada(receta)));
	}
	
	@Test
	public void testUnUsuarioPuedeAgregarUnaRecetaValidaSiEsElCreador(){
		pedro.agregarReceta(recetaDePedro);
		assertTrue(pedro.getRecetas().contains(recetaDePedro));
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaValidaSiNoEsElCreador(){
		pedro.agregarReceta(recetaDeJuancho);
		assertFalse(pedro.getRecetas().contains(recetaDeJuancho));
	}
}

