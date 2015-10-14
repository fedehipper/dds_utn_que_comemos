package grupo4.dds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
import grupo4.dds.excepciones.NoSePuedeSetearCreadorARecetaPublica;
import grupo4.dds.excepciones.RecetaInvalida;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.receta.builder.BuilderRecetaPublica;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestReceta extends BaseTest {
	
	private Receta receta;
		
	@Rule public ExpectedException expectedExcetption = ExpectedException.none();
	
	/* Test: @esValida/0 */
	@Test
	public void testNoEsValidaUnaRecetaSinIngredientes() {
		expectedExcetption.expect(RecetaInvalida.class);
		receta = new BuilderReceta().calorias(4500).nombre("Mondongo").build();
	}
	
	@Test 
	public void testEsValidaUnaRecetaConIngredientesY4500Calorias() {
		receta = new BuilderReceta().calorias(4500).ingrediente(Ingrediente.nuevoIngrediente("carne", 0f)).build();
		
		assertTrue(receta.esValida());
	}
	
	/* Test: @modificarReceta/6 */
	@Test
	public void testPuedeModificarseUnaRecetaConElUsuarioQueLaCreo() {
		milanesa.setPreparacion("Preparación antes de modificar");
				
		milanesa.modificarReceta(fecheSena, milanesa.getEncabezado(), milanesa.getIngredientes(), null, "Preparación después de modificar", null);
		assertEquals(milanesa.getPreparacion(),"Preparación después de modificar");
	}
	
	@Test
	public void testNoPuedeModificarseUnaRecetaConUnUsuarioQueNoLaCreo() {
		expectedExcetption.expect(NoSePuedeModificarLaReceta.class);
		
		milanesa.setPreparacion("Preparación antes de modificar");
		milanesa.modificarReceta(matiasMartino, milanesa.getEncabezado(), milanesa.getIngredientes(), null, "Preparación después de modificar", null);
		
		assertEquals(milanesa.getPreparacion(), "Preparación después de modificar");
	}
	
	@Test
	public void testAlModificarUnaRecetaPublicaSeGeneraUnaNuevaRecetaConLasModificaciones() {
		Receta recetaPublica = new BuilderRecetaPublica().hacerValida().build();
		recetaPublica.setPreparacion("Preparación antes de modificar");
		
		federicoHipper.modificarReceta(recetaPublica, recetaPublica.getEncabezado(), recetaPublica.getIngredientes(), null, "Preparación después de modificar", null);

		assertEquals(recetaPublica.getPreparacion(), "Preparación antes de modificar");
		assertEquals(federicoHipper.recetaMasReciente().getPreparacion(), "Preparación después de modificar");
	}
	
	/* Test: @getPreparacion */
	@Test
	public void testLaPreparacionDeUnaRecetaSinSubrecetasEsLaSuya() {

		receta = new BuilderReceta().preparacion("Preparación propia").
									 calorias(400).
									 ingrediente(Ingrediente.nuevoIngrediente("test", 0f)).
									 build();

		assertEquals(receta.getPreparacion(), "Preparación propia");
	}
	
	@Test
	public void testLaPreparacionDeUnaRecetaEsLaSuyaYlaDeSusSubrecetas() {
		receta = new BuilderReceta().preparacion("Preparación propia\n").
									 subreceta(new BuilderReceta().preparacion("Preparación subreceta 1\n").
											 						  calorias(400).
											 						  ingrediente(Ingrediente
																			.nuevoIngrediente(
																					"test",
																					0f)).
											 						  build()).
									 subreceta(new BuilderReceta().preparacion("Preparación subreceta 2\n").
											 						  calorias(400).
											 						  ingrediente(Ingrediente
																			.nuevoIngrediente(
																					"test",
																					0f)).
											 						  build()).
									 calorias(400).
									 ingrediente(Ingrediente.nuevoIngrediente("test", 0f)).
									 build();
		
		assertEquals(receta.getPreparacion(), "Preparación propia\nPreparación subreceta 1\nPreparación subreceta 2\n");
	}
	
	/* Test: @getNombreIngredientes */
	@Test
	public void testLosIngredientesDeUnaRecetaSinSubrecetasSonLosSuyos() {
		Ingrediente carne = Ingrediente.nuevoIngrediente("carne", 0f);
		Ingrediente pollo = Ingrediente.nuevoIngrediente("pollo", 0f);
		
		receta = new BuilderReceta().calorias(400).ingrediente(carne).ingrediente(pollo).build();
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(carne);
		expected.add(pollo);
		
		assertEquals(receta.getIngredientes(), expected);
	}
	
	/* Test: @getNombreCondimentos */
	@Test
	public void testLosCondimentosDeUnaRecetaSinSubrecetasSonLosSuyos() {
		Ingrediente caldo = Ingrediente.nuevoIngrediente("caldo", 0f);
		Ingrediente sal = Ingrediente.nuevoIngrediente("sal", 0f);
		
		receta = new BuilderReceta().calorias(400).ingrediente(Ingrediente.nuevoIngrediente("carne", 0f)).condimento(caldo).condimento(sal).build();
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(caldo);
		expected.add(sal);
		
		assertEquals(receta.getCondimentos(), expected);
	}
	
	@Test
	public void testLosIngredientesDeUnaRecetaSonLosSuyosYLosDeSusSubrecetas() {
		Ingrediente carne = Ingrediente.nuevoIngrediente("carne", 0f);
		Ingrediente pollo = Ingrediente.nuevoIngrediente("pollo", 0f);
		Ingrediente chivito = Ingrediente.nuevoIngrediente("chivito", 0f);
		Ingrediente chori = Ingrediente.nuevoIngrediente("chori", 0f);
		
		receta = new BuilderReceta().calorias(400).
									 ingrediente(carne).
									 ingrediente(pollo).
									 subreceta(new BuilderReceta().calorias(400).ingrediente(chivito).build()).
									 subreceta(new BuilderReceta().calorias(400).ingrediente(chori).build()).
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

		
		Ingrediente caldo = Ingrediente.nuevoIngrediente("caldo", 0f);
		Ingrediente sal = Ingrediente.nuevoIngrediente("sal", 0f);
		Ingrediente pimienta = Ingrediente.nuevoIngrediente("pimienta", 0f);
		Ingrediente azucar = Ingrediente.nuevoIngrediente("azucar", 0f);
		
		receta = new BuilderReceta().calorias(400).
				 ingrediente(Ingrediente.nuevoIngrediente("carne", 0f)).
				 condimento(azucar).condimento(pimienta).
				 subreceta(new BuilderReceta().calorias(400).
						 							ingrediente(Ingrediente
															.nuevoIngrediente(
																	"carne", 0f)).
						 							condimento(sal).
						 							build()).
				 subreceta(new BuilderReceta().calorias(400).
						 							ingrediente(Ingrediente
															.nuevoIngrediente(
																	"carne", 0f)).
						 							condimento(caldo).
						 							build()).
				 build();
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(caldo);
		expected.add(sal);
		expected.add(pimienta);
		expected.add(azucar);
	
		assertTrue(receta.getCondimentos().containsAll(expected));
	}
	
	@Test
	public void testNoSePuedeSetearCreadorARecetaPublica() {
		expectedExcetption.expect(NoSePuedeSetearCreadorARecetaPublica.class);

		new BuilderRecetaPublica().creador(null);
	}
}