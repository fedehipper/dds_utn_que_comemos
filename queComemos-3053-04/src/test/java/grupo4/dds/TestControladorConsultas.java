package grupo4.dds;

import grupo4.dds.controller.ConsultasController;
import grupo4.dds.controller.HomeController;
import grupo4.dds.main.Routes;
import grupo4.dds.monitores.RecetasMasConsultadas;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.repositorios.RepositorioDeRecetas;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class TestControladorConsultas extends BaseTest {
	
	private List<Receta> recetasConsultadas;
	private List<Receta> favoritas;
	private HomeController controlador = new HomeController();
	
	@Before
	public void setUp() {
		
		Routes.usuarioActual = fecheSena;
		
		Ingrediente ing = Ingrediente.nuevoIngrediente("", 0);
		
		Receta receta1 = new BuilderReceta().creador(fecheSena).nombre("receta1").dificil().calorias(999).ingrediente(ing).build();
		Receta receta2 = new BuilderReceta().creador(fecheSena).nombre("receta2").dificil().calorias(300).ingrediente(ing).build();
		Receta receta3 = new BuilderReceta().creador(fecheSena).nombre("receta3").calorias(600).ingrediente(ing).build();
		Receta receta4 = new BuilderReceta().creador(fecheSena).nombre("receta4").dificil().calorias(999).ingrediente(ing).build();
		Receta receta5 = new BuilderReceta().creador(fecheSena).nombre("receta5").dificil().calorias(300).ingrediente(ing).build();
		Receta receta6 = new BuilderReceta().creador(fecheSena).nombre("receta6").calorias(600).ingrediente(ing).build();
		Receta receta7 = new BuilderReceta().creador(fecheSena).nombre("receta7").dificil().calorias(999).ingrediente(ing).build();
		Receta receta8 = new BuilderReceta().creador(fecheSena).nombre("receta8").dificil().calorias(300).ingrediente(ing).build();
		
		recetasConsultadas = RepositorioDeRecetas.instance().listar();
		
	}
	
	@Test
	public void testSiElUsuarioBuscaConTodosLosFiltrosNulos() {
		List<Receta> recetas = recetasConsultadas.stream().filter(r-> fecheSena.puedeVer(r)).collect(Collectors.toList());
		assertEqualsList(recetas, new ConsultasController().recetasAMostrar(null,null,null,null,null));
	}
	
	@Test
	public void testSiElUsuarioBuscaConFiltroNombre(){
		recetasConsultadas = RepositorioDeRecetas.instance().buscar("receta5");
		assertEqualsList(recetasConsultadas, new ConsultasController().recetasAMostrar("receta5",null,null,null,null));
	}

}
