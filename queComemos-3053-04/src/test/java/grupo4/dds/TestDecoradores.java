package grupo4.dds;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import grupo4.dds.decoradores.CondicionesUsuario;
import grupo4.dds.decoradores.ExcesoCalorias;
import grupo4.dds.decoradores.LeGustaAlUsuario;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Grupo;
import grupo4.dds.usuario.Ingrediente;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Vegano;

public class TestDecoradores {

	private Usuario fecheSena;
		
	@Before
	public void setup() {
		fecheSena = new Usuario("Feche Sena", null, 1.70f, 65.0f, null);
	}
	
	@Test
	public void testFiltroExcesoDeCalorias() {
		RepositorioDeRecetas rR = new RepositorioDeRecetas();
		
		ExcesoCalorias exceso = new ExcesoCalorias(rR);
		
		EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("sopa", null, null, 600);
		EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("pollo", null, null, 300);
		EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("pure", null, null, 100);
		

		Receta r1 = new Receta(fecheSena, encabezado1, null);
		RecetaPublica r2 = new RecetaPublica(encabezado2, null);
		RecetaPublica r3 = new RecetaPublica(encabezado3, null);
		
		rR.agregarReceta(r1);
		rR.agregarReceta(r2);
		rR.agregarReceta(r3);
		
		List<Receta> aux = Stream.of(r2, r3).collect(Collectors.toList());
		
		assertEquals(exceso.listarRecetasParaUnUsuario(fecheSena), aux );
	}
	
	@Test
	public void testFiltroDeRecetasQueCumplenLasCondicionesDelUsuario() {
		
		Ingrediente carne = new Ingrediente("carne", 0f);
		Ingrediente fruta = new Ingrediente("fruta", 0f);
		
		RepositorioDeRecetas rR = new RepositorioDeRecetas();
		
		CondicionesUsuario filtroCondicion = new CondicionesUsuario(rR);
		
		EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("sopa", null, null, 600);
		EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("pollo", null, null, 300);
		EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("pure", null, null, 100);
		
		Receta receta1 = new Receta(fecheSena, encabezado1, null);
		RecetaPublica receta2 = new RecetaPublica(encabezado2, null);
		RecetaPublica receta3 = new RecetaPublica(encabezado3, null);
		
		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(fruta);
		
		rR.agregarReceta(receta1);
		rR.agregarReceta(receta2);
		rR.agregarReceta(receta3);
		
		Usuario ariel = new Usuario("ariel", null, 89.0f, 78f, null);
		Grupo grupo = new Grupo("grupo");
		grupo.agregarUsuario(fecheSena);
		grupo.agregarUsuario(ariel);
		ariel.agregarGrupo(grupo);
		fecheSena.agregarGrupo(grupo);
		
		List<Receta> aux = Stream.of(receta2, receta3).collect(Collectors.toList());
		
		Vegano vegano = new Vegano();
		ariel.agregarCondicion(vegano);
		
		assertEquals(filtroCondicion.listarRecetasParaUnUsuario(ariel), aux );
	}
	
	@Test
	public void testFiltroNoLeGustaAlUsuarioElNombreDelPlato() {
		Ingrediente carne = new Ingrediente("carne", 0f);
		Ingrediente fruta = new Ingrediente("fruta", 0f);
		Ingrediente huevo = new Ingrediente("huevo" , 0f);
		
		RepositorioDeRecetas rR = new RepositorioDeRecetas();
		
		LeGustaAlUsuario filtroLeGusta = new LeGustaAlUsuario(rR);
		
		EncabezadoDeReceta encabezado1 = new EncabezadoDeReceta("milanesasConPure", null, null, 600);
		EncabezadoDeReceta encabezado2 = new EncabezadoDeReceta("ensaladaRusa", null, null, 300);
		EncabezadoDeReceta encabezado3 = new EncabezadoDeReceta("EnsaladaDeRucula", null, null, 100);
		
		Receta receta1 = new Receta(fecheSena, encabezado1, null);
		RecetaPublica receta2 = new RecetaPublica(encabezado2, null);
		RecetaPublica receta3 = new RecetaPublica(encabezado3, null);
		
		receta1.agregarIngrediente(carne);
		receta2.agregarIngrediente(fruta);
		receta3.agregarIngrediente(huevo);
		
		rR.agregarReceta(receta1);
		rR.agregarReceta(receta2);
		rR.agregarReceta(receta3);
		
		fecheSena.agregarComidaQueLeDisgusta(fruta);
	
		List<Receta> aux = Stream.of(receta1, receta3).collect(Collectors.toList());
		
		assertEquals(filtroLeGusta.listarRecetasParaUnUsuario(fecheSena), aux);
	}
	
	
	
}
