package grupo4.dds;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import grupo4.dds.controller.HomeController;
import grupo4.dds.main.Routes;
import grupo4.dds.monitores.RecetasMasConsultadas;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.builder.BuilderReceta;

public class TestControladorHome extends BaseTest {
	
	private List<Receta> masConsultadasPorUsuario;
	private List<Receta> favoritas;
	private HomeController controlador = new HomeController();
	
	@Before
	public void setUp() {
		
		Routes.usuarioActual = matiasMartino;
		
		Ingrediente ing = Ingrediente.nuevoIngrediente("", 0);
		
		Receta receta1 = new BuilderReceta().creador(matiasMartino).nombre("receta1").dificil().calorias(999).ingrediente(ing).build();
		Receta receta2 = new BuilderReceta().creador(matiasMartino).nombre("receta2").dificil().calorias(300).ingrediente(ing).build();
		Receta receta3 = new BuilderReceta().creador(matiasMartino).nombre("receta3").calorias(600).ingrediente(ing).build();
		Receta receta4 = new BuilderReceta().creador(matiasMartino).nombre("receta4").dificil().calorias(999).ingrediente(ing).build();
		Receta receta5 = new BuilderReceta().creador(matiasMartino).nombre("receta5").dificil().calorias(300).ingrediente(ing).build();
		Receta receta6 = new BuilderReceta().creador(matiasMartino).nombre("receta6").calorias(600).ingrediente(ing).build();
		Receta receta7 = new BuilderReceta().creador(matiasMartino).nombre("receta7").dificil().calorias(999).ingrediente(ing).build();
		Receta receta8 = new BuilderReceta().creador(matiasMartino).nombre("receta8").dificil().calorias(300).ingrediente(ing).build();
		Receta receta9 = new BuilderReceta().creador(matiasMartino).nombre("receta9").calorias(600).ingrediente(ing).build();
		Receta receta10 = new BuilderReceta().creador(matiasMartino).nombre("receta10").dificil().calorias(999).ingrediente(ing).build();
		Receta receta11 = new BuilderReceta().creador(matiasMartino).nombre("receta11").dificil().calorias(300).ingrediente(ing).build();
		Receta receta12 = new BuilderReceta().creador(matiasMartino).nombre("receta12").calorias(600).ingrediente(ing).build();
		
		favoritas = Arrays.asList(receta11, receta2, receta3, receta4, receta5, receta6, receta7, receta8, receta12);
		masConsultadasPorUsuario = Arrays.asList(receta1, receta10, receta9, receta4, receta5, receta6, receta7, receta8, receta12);
		
	}
	
	@Test
	public void testSiElUsuarioEntraPorPrimeraVezVeraLas10RecetasMasConsultadas() {
		assertEqualsList(new RecetasMasConsultadas().recetasMasConsultadas(10), new HomeController().recetasAMostrar());
	}

	@Test
	public void testSiElUsuarioTieneRecetasFavoritasVeraLasPrimeras10() {
		
		matiasMartino.marcarFavoritas(favoritas);
		assertEqualsList(favoritas, controlador.recetasAMostrar());
	}

	@Test
	public void testSiElUsuarioNoTieneRecetasFavoritasVeraLasUltimas10RecetasQueConsulto() {
		
		matiasMartino.consulto(masConsultadasPorUsuario);
		assertEqualsList(masConsultadasPorUsuario, controlador.recetasAMostrar());
	}
	
}