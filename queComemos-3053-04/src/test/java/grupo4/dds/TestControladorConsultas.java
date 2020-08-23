package grupo4.dds;

import grupo4.dds.controller.ConsultasController;
import grupo4.dds.main.Routes;
import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioDeRecetas;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class TestControladorConsultas extends BaseTest {

    private List<Receta> recetasConsultadas;

    @Before
    public void setUp() {
        Routes.usuarioActual = fecheSena;
        recetasConsultadas = RepositorioDeRecetas.instance().listar();
    }

    @Test
    public void testSiElUsuarioBuscaConTodosLosFiltrosNulos() {
        List<Receta> recetas = recetasConsultadas.stream().filter(r -> fecheSena.puedeVer(r)).collect(Collectors.toList());
        assertEqualsList(recetas, new ConsultasController().recetasAMostrar(null, null, null, null, null));
    }

    @Test
    public void testSiElUsuarioBuscaConFiltroNombre() {
        recetasConsultadas = RepositorioDeRecetas.instance().buscar("receta5");
        assertEqualsList(recetasConsultadas, new ConsultasController().recetasAMostrar("receta5", null, null, null, null));
    }

}
