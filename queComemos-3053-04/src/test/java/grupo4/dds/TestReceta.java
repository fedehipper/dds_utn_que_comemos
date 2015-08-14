package grupo4.dds;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
import grupo4.dds.excepciones.RecetaInvalida;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
/*import grupo4.dds.receta.RecetaPublica;*/
import grupo4.dds.usuario.Usuario;
import grupo4.dds.receta.builder.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TestReceta {
	
	private Receta receta;
		
	@Rule public ExpectedException expectedExcetption = ExpectedException.none();
	
	@Before
	public void setUp() {

	}
	
	/* Test: @esValida/0 */
	@Test
	public void testNoEsValidaUnaRecetaSinIngredientes() {
		try{
		receta = new BuilderReceta().setTotalCalorias(4500).setNombreDelPlato("Mondongo").build();
		}
		catch(RecetaInvalida e){}
	}
	
	@Test 
	public void testEsValidaUnaRecetaConIngredientesY4500Calorias() {
		receta = new BuilderReceta().setTotalCalorias(4500).setIngrediente(new Ingrediente("carne", 0f)).build();
		
		assertTrue(receta.esValida());
	}
	
	/* Test: @modificarReceta/6 */
	@Test
	public void testPuedeModificarseUnaRecetaConElUsuarioQueLaCreo() throws NoSePuedeModificarLaReceta {
		Usuario usuario = Usuario.crearPerfil(null);		

		receta = new BuilderReceta().setCreador(usuario).
									 setPreparacion("Preparación antes de modificar").
									 setTotalCalorias(4500).
									 setIngrediente(new Ingrediente("frutas", 0f)).
									 build();
				
		receta.modificarReceta(usuario, receta.getEncabezado(), receta.getIngredientes(), null, "Preparación después de modificar", null);
		assertEquals(receta.getPreparacion(),"Preparación después de modificar");
	}
	
	@Test
	public void testNoPuedeModificarseUnaRecetaConUnUsuarioQueNoLaCreo() {
		expectedExcetption.expect(NoSePuedeModificarLaReceta.class);
		
		Usuario usuario = Usuario.crearPerfil("unUsuario");		
		Usuario usuario2 = Usuario.crearPerfil("otroUsuario");		
		
		receta = new BuilderReceta().setCreador(usuario).
									 setPreparacion("Preparación antes de modificar").
									 setTotalCalorias(4500).
									 setIngrediente(new Ingrediente("frutas", 0f)).
									 build();
				
		receta.modificarReceta(usuario2, receta.getEncabezado(), receta.getIngredientes(), null, "Preparación después de modificar", null);
		assertEquals(receta.getPreparacion(), "Preparación después de modificar");
	}
	
	@Test
	public void testAlModificarUnaRecetaPublicaSeGeneraUnaNuevaRecetaConLasModificaciones() throws NoSePuedeModificarLaReceta  {
		Usuario usuario = Usuario.crearPerfil(null);
		Receta recetaPublica = new BuilderRecetaPublica().setPreparacion("Preparación antes de modificar").
																 setTotalCalorias(400).
																 setIngrediente(new Ingrediente("frutas", 0f)).
																 build();
															
		
		usuario.modificarReceta(recetaPublica, recetaPublica.getEncabezado(), recetaPublica.getIngredientes(), null, "Preparación después de modificar", null);

		assertEquals(recetaPublica.getPreparacion(), "Preparación antes de modificar");
		assertEquals(usuario.recetaMasReciente().getPreparacion(), "Preparación después de modificar");
	}
	
	/* Test: @getPreparacion */
	@Test
	public void testLaPreparacionDeUnaRecetaSinSubrecetasEsLaSuya() {

		receta = new BuilderReceta().setPreparacion("Preparación propia").
									 setTotalCalorias(400).
									 setIngrediente(new Ingrediente("test",0f)).
									 build();

		assertEquals(receta.getPreparacion(), "Preparación propia");
	}
	
	@Test
	public void testLaPreparacionDeUnaRecetaEsLaSuyaYlaDeSusSubrecetas() {
		receta = new BuilderReceta().setPreparacion("Preparación propia\n").
									 setSubreceta(new BuilderReceta().setPreparacion("Preparación subreceta 1\n").
											 						  setTotalCalorias(400).
											 						  setIngrediente(new Ingrediente("test",0f)).
											 						  build()).
									 setSubreceta(new BuilderReceta().setPreparacion("Preparación subreceta 2\n").
											 						  setTotalCalorias(400).
											 						  setIngrediente(new Ingrediente("test",0f)).
											 						  build()).
									 setTotalCalorias(400).
									 setIngrediente(new Ingrediente("test",0f)).
									 build();
		
		assertEquals(receta.getPreparacion(), "Preparación propia\nPreparación subreceta 1\nPreparación subreceta 2\n");
	}
	
	/* Test: @getNombreIngredientes */
	@Test
	public void testLosIngredientesDeUnaRecetaSinSubrecetasSonLosSuyos() {
		Ingrediente carne = new Ingrediente("carne", 0f);
		Ingrediente pollo = new Ingrediente("pollo", 0f);
		
		receta = new BuilderReceta().setTotalCalorias(400).setIngrediente(carne).setIngrediente(pollo).build();
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(carne);
		expected.add(pollo);
		
		assertEquals(receta.getIngredientes(), expected);
	}
	
	/* Test: @getNombreCondimentos */
	@Test
	public void testLosCondimentosDeUnaRecetaSinSubrecetasSonLosSuyos() {
		Ingrediente caldo = new Ingrediente("caldo", 0f);
		Ingrediente sal = new Ingrediente("sal", 0f);
		
		receta = new BuilderReceta().setTotalCalorias(400).setIngrediente(new Ingrediente("carne",0f)).setCondimento(caldo).setCondimento(sal).build();
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(caldo);
		expected.add(sal);
		
		assertEquals(receta.getCondimentos(), expected);
	}
	
	@Test
	public void testLosIngredientesDeUnaRecetaSonLosSuyosYLosDeSusSubrecetas() {
		Ingrediente carne = new Ingrediente("carne", 0f);
		Ingrediente pollo = new Ingrediente("pollo", 0f);
		Ingrediente chivito = new Ingrediente("chivito", 0f);
		Ingrediente chori = new Ingrediente("chori", 0f);
		
		receta = new BuilderReceta().setTotalCalorias(400).
									 setIngrediente(carne).
									 setIngrediente(pollo).
									 setSubreceta(new BuilderReceta().setTotalCalorias(400).setIngrediente(chivito).build()).
									 setSubreceta(new BuilderReceta().setTotalCalorias(400).setIngrediente(chori).build()).
									 build();
			
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(carne);
		expected.add(pollo);
		expected.add(chivito);
		expected.add(chori);
		
		assertTrue(receta.getIngredientes().containsAll(expected));
	}
	
	@Test
	public void testLosCondimentosDeUnaRecetaSonLosSuyosYLosDeSusSubrecetas() {

		
		Ingrediente caldo = new Ingrediente("caldo", 0f);
		Ingrediente sal = new Ingrediente("sal", 0f);
		Ingrediente pimienta = new Ingrediente("pimienta", 0f);
		Ingrediente azucar = new Ingrediente("azucar", 0f);
		
		receta = new BuilderReceta().setTotalCalorias(400).
				 setIngrediente(new Ingrediente("carne", 0f)).
				 setCondimento(azucar).setCondimento(pimienta).
				 setSubreceta(new BuilderReceta().setTotalCalorias(400).
						 							setIngrediente(new Ingrediente("carne", 0f)).
						 							setCondimento(sal).
						 							build()).
				 setSubreceta(new BuilderReceta().setTotalCalorias(400).
						 							setIngrediente(new Ingrediente("carne", 0f)).
						 							setCondimento(caldo).
						 							build()).
				 build();
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(caldo);
		expected.add(sal);
		expected.add(pimienta);
		expected.add(azucar);
	
		assertTrue(receta.getCondimentos().containsAll(expected));
	}
	
}