package grupo4.dds;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import grupo4.dds.decoradores.ExcesoCalorias;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

public class TestDecoradores {

	@Before
	public void setup() {
		
	}
	
	@Test
	public void testFiltroExcesoDeCalorias() {
		RepositorioDeRecetas rR = new RepositorioDeRecetas();
		
		ExcesoCalorias exceso = new ExcesoCalorias(rR);
		
		EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("sopa", null, null, 600);
		EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("pollo", null, null, 300);
		EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("pure", null, null, 100);
		
		Usuario fecheSena = new Usuario("Feche Sena", null, 1.70f, 65.0f, null);
		
		Receta r1 = new Receta(fecheSena, encabezado1, null);
		RecetaPublica r2 = new RecetaPublica(encabezado2, null);
		RecetaPublica r3 = new RecetaPublica(encabezado3, null);
		
		rR.agregarReceta(r1);
		rR.agregarReceta(r2);
		rR.agregarReceta(r3);
		
		List<Receta> aux = Stream.of(r2, r3).collect(Collectors.toList());
		
		assertEquals(exceso.listarRecetasParaUnUsuario(fecheSena), aux );
	}
	
	
	
}
