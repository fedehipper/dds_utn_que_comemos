package grupo4.dds;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import grupo4.dds.excepciones.NoSePuedeModificarLaReceta;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.usuario.Usuario;

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
		receta = Receta.crearNueva();
		
		receta.setTotalCalorias(4500);
		assertFalse(receta.esValida());
	}
	
	@Test
	public void testEsValidaUnaRecetaConIngredientesY4500Calorias() {
		receta = Receta.crearNueva();
		
		Ingrediente carne = new Ingrediente("carne", 0f);
		receta.agregarIngrediente(carne);
		receta.setTotalCalorias(4500);
		assertTrue(receta.esValida());
	}
	
	/* Test: @modificarReceta/6 */
	@Test
	public void testPuedeModificarseUnaRecetaConElUsuarioQueLaCreo() throws NoSePuedeModificarLaReceta {
		Usuario usuario = Usuario.crearPerfil();		
		receta = Receta.crearNueva(usuario, new EncabezadoDeReceta(), "Preparación antes de modificar");
		
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		
		List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
		
		ingredientes.add(frutas);
				
		receta.modificarReceta(usuario, encabezado, ingredientes, null, "Preparación después de modificar", null);
		assertEquals(receta.getPreparacion(),"Preparación después de modificar");
	}
	
	@Test
	public void testNoPuedeModificarseUnaRecetaConUnUsuarioQueNoLaCreo() throws NoSePuedeModificarLaReceta {
		expectedExcetption.expect(NoSePuedeModificarLaReceta.class);
		
		Usuario usuario = Usuario.crearPerfil();		
		receta = Receta.crearNueva(usuario, new EncabezadoDeReceta(), "Preparación antes de modificar");
		
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		
		List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
		ingredientes.add(frutas);
				
		receta.modificarReceta(Usuario.crearPerfil(), encabezado, ingredientes, null, "Preparación después de modificar", null);
		assertEquals(receta.getPreparacion(), "Preparación después de modificar");
	}
	
	@Test
	public void testAlModificarUnaRecetaPublicaSeGeneraUnaNuevaRecetaConLasModificaciones() throws NoSePuedeModificarLaReceta {
		Usuario usuario = Usuario.crearPerfil();
		RecetaPublica recetaPublica = RecetaPublica.crearNueva(null, "Preparación antes de modificar");
		
		EncabezadoDeReceta encabezado = new EncabezadoDeReceta();
		encabezado.setTotalCalorias(4500);
		
		Ingrediente frutas = new Ingrediente("frutas", 0f);
		
		List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
		ingredientes.add(frutas);

		usuario.modificarReceta(recetaPublica, encabezado, ingredientes, null, "Preparación después de modificar", null);

		assertEquals(recetaPublica.getPreparacion(), "Preparación antes de modificar");
		assertEquals(usuario.recetaMasReciente().getPreparacion(), "Preparación después de modificar");
	}
	
	/* Test: @getPreparacion */
	@Test
	public void testLaPreparacionDeUnaRecetaSinSubrecetasEsLaSuya() {
		receta = Receta.crearNueva(null, null, "Preparación propia");
		assertEquals(receta.getPreparacion(), "Preparación propia");
	}
	
	@Test
	public void testLaPreparacionDeUnaRecetaEsLaSuyaYlaDeSusSubrecetas() {
		receta = Receta.crearNueva(null, null, "Preparación propia\n");
		receta.agregarSubreceta(Receta.crearNueva(null, null, "Preparación subreceta 1\n"));
		receta.agregarSubreceta(Receta.crearNueva(null, null, null));
		receta.agregarSubreceta(Receta.crearNueva(null, null, "Preparación subreceta 2\n"));
		
		assertEquals(receta.getPreparacion(), "Preparación propia\nPreparación subreceta 1\nPreparación subreceta 2\n");
	}
	
	/* Test: @getNombreIngredientes */
	@Test
	public void testLosIngredientesDeUnaRecetaSinSubrecetasSonLosSuyos() {
		receta = Receta.crearNueva(null, null, null);
		
		Ingrediente carne = new Ingrediente("carne", 0f);
		Ingrediente pollo = new Ingrediente("pollo", 0f);
		
		receta.agregarIngrediente(carne);
		receta.agregarIngrediente(pollo);
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(carne);
		expected.add(pollo);
		
		assertEquals(receta.getIngredientes(), expected);
	}
	
	/* Test: @getNombreCondimentos */
	@Test
	public void testLosCondimentosDeUnaRecetaSinSubrecetasSonLosSuyos() {
		receta = Receta.crearNueva(null, null, null);

		Ingrediente caldo = new Ingrediente("caldo", 0f);
		Ingrediente sal = new Ingrediente("sal", 0f);
		
		receta.agregarCondimento(caldo);
		receta.agregarCondimento(sal);
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(caldo);
		expected.add(sal);
		
		assertEquals(receta.getCondimentos(), expected);
	}
	
	@Test
	public void testLosIngredientesDeUnaRecetaSonLosSuyosYLosDeSusSubrecetas() {
		receta = Receta.crearNueva(null, null, null);
		
		Ingrediente carne = new Ingrediente("carne", 0f);
		Ingrediente pollo = new Ingrediente("pollo", 0f);
		Ingrediente chivito = new Ingrediente("chivito", 0f);
		Ingrediente chori = new Ingrediente("chori", 0f);
		
		receta.agregarIngrediente(carne);
		receta.agregarIngrediente(pollo);
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(carne);
		expected.add(pollo);
		expected.add(chivito);
		expected.add(chori);
		
		Receta sub1 = Receta.crearNueva(null, null, null);
		sub1.agregarIngrediente(chivito);
		Receta sub2 = Receta.crearNueva(null, null, null);
		sub2.agregarIngrediente(chori);
		
		receta.agregarSubreceta(sub1);
		receta.agregarSubreceta(sub2);
		
		assertTrue(receta.getIngredientes().containsAll(expected));
	}
	
	@Test
	public void testLosCondimentosDeUnaRecetaSonLosSuyosYLosDeSusSubrecetas() {
		receta = Receta.crearNueva(null, null, null);
		
		Ingrediente caldo = new Ingrediente("caldo", 0f);
		Ingrediente sal = new Ingrediente("sal", 0f);
		Ingrediente pimienta = new Ingrediente("pimienta", 0f);
		Ingrediente azucar = new Ingrediente("azucar", 0f);
		
		receta.agregarCondimento(caldo);
		receta.agregarCondimento(sal);
		
		List<Ingrediente> expected = new ArrayList<>();
		expected.add(caldo);
		expected.add(sal);
		expected.add(pimienta);
		expected.add(azucar);
		
		Receta sub1 = Receta.crearNueva(null, null, null);
		sub1.agregarCondimento(pimienta);
		Receta sub2 = Receta.crearNueva(null, null, null);
		sub2.agregarCondimento(azucar);
		
		receta.agregarSubreceta(sub1);
		receta.agregarSubreceta(sub2);
	
		assertTrue(receta.getCondimentos().containsAll(expected));
	}
	
}