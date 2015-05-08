package grupo4.dds;

import static grupo4.dds.usuario.Rutina.ACTIVA_EJERCICIO_ADICIONAL;
import static grupo4.dds.usuario.Sexo.MASCULINO;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.NoTienePermisoParaAgregarReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestReceta {
	
	private Usuario pedro = new Usuario("pedro", MASCULINO,
			LocalDate.of(2015, 04, 23), 1.50f, 90.0f, ACTIVA_EJERCICIO_ADICIONAL);
	private Usuario juancho = new Usuario("juancho", MASCULINO,
			LocalDate.of(2015, 04, 23), 1.50f, 90.0f, ACTIVA_EJERCICIO_ADICIONAL);
	private RecetaPublica recetaPublica = new RecetaPublica();
	private Celiaco celiaco = new Celiaco();
	private Vegano vegano = new Vegano();
	private Diabetico diabetico = new Diabetico();
	private Hipertenso hipertenso = new Hipertenso();
	private Receta recetaDePedro = new Receta(pedro);
	private Receta recetaDeJuancho = new Receta(juancho);
		
	@Rule public ExpectedException expectedExcetption = ExpectedException.none();
	
	@Before
	public void setUp() {

		recetaPublica.agregarIngrediente("morron", 80.0f);
		recetaPublica.agregarCondimento("sal", 90.0f);
		recetaPublica.agregarCondimento("caldo", 8.0f);
		recetaPublica.agregarIngrediente("carne", 90.0f);
		recetaPublica.agregarCondimento("azucar", 100.0f);
		recetaPublica.setTotalCalorias(10);	
		
		recetaDePedro.agregarIngrediente("miel", 60.0f);
		recetaDePedro.agregarIngrediente("agua", 160.0f);
		recetaDePedro.agregarIngrediente("levadura", 50.0f);
		recetaDePedro.agregarIngrediente("hojas de menta", 1.0f);
		recetaDePedro.setTotalCalorias(50);

	}

	@Test
	public void testRecetaEsValida() {
		assertTrue(recetaPublica.esValida());
	}
			
	@Test 
	public void testEsRecetaAdecuadaParaUnCeliaco() {
		pedro.agregarCondicion(celiaco);
		assertTrue(pedro.esAdecuada(recetaPublica));
	}
	
	@Test 
	public void testEsRecetaInadecuadaParaUnVegano() {
		pedro.agregarCondicion(vegano);
		assertTrue(!(pedro.esAdecuada(recetaPublica)));
	}
		
	@Test 
	public void testEsRecetaAdecuadaParaUnDiabetico() {
		pedro.agregarCondicion(diabetico);
		assertTrue(pedro.esAdecuada(recetaPublica));
	}
	
	@Test 
	public void testEsRecetaInadecuadaParaUnHipertenso() {
		pedro.agregarCondicion(hipertenso);
		assertTrue(!pedro.esAdecuada(recetaPublica));
	}
	
	@Test
	public void testUnUsuarioPuedeAgregarUnaRecetaValidaSiEsElCreador() throws NoTienePermisoParaAgregarReceta{
		pedro.agregarReceta(recetaDePedro);
		assertTrue(pedro.getRecetas().contains(recetaDePedro));
	}
	
	@Test
	public void testUnUsuarioNoPuedeAgregarUnaRecetaValidaSiNoEsElCreador() throws NoTienePermisoParaAgregarReceta{
		expectedExcetption.expect(NoTienePermisoParaAgregarReceta.class);
		
		pedro.agregarReceta(recetaDeJuancho);
	}
}

