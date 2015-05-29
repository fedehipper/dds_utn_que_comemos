package grupo4.dds;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Ingrediente;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Condicion;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Before;
import org.junit.Test;

public class TestGrupo {
	
	private GrupoUsuarios grupo;
	private Usuario fecheSena;
	private Usuario elPanadero;
	private Usuario ariel;
	private Usuario matias;
	private Receta receta;
	private Ingrediente carne;
	private Ingrediente fruta;
	private Ingrediente chori;
	private Ingrediente azucar;
	private Condicion vegano;
	private Condicion diabetico;

	@Before
	public void setUp() {
		grupo = new GrupoUsuarios("grosos");
		fecheSena = new Usuario();
		elPanadero = new Usuario();
		matias = new Usuario();
		ariel = new Usuario();
		fruta = new Ingrediente("fruta", 0f);
		chori = new Ingrediente("chori", 0f);
		azucar = new Ingrediente("azucar", 90f);
		receta = new Receta();
		receta.setTotalCalorias(4500);
		vegano = new Vegano();
		diabetico = new Diabetico();
		
		fecheSena.agregarCondicion(vegano);
		elPanadero.agregarCondicion(diabetico);
		
		grupo.agregarPreferencia(chori);
	}

	@Test
	public void testAgregarUsuarioAGrupo() {
		grupo.agregarUsuario(fecheSena);
	     assertTrue(grupo.esMiembro(fecheSena));
	}
	
	@Test
	public void testAgregarPalabraClave(){
		grupo.agregarPreferencia(carne);
		assertTrue(grupo.getPreferenciasAlimenticias().contains(carne));
	}
	
	@Test
	public void testNoSePuedeSugerirRecetaAGrupoGrosos(){
		receta.agregarIngrediente(carne);
		fecheSena.agregarPreferenciaAlimenticia(fruta);
		assertFalse(grupo.puedeSugerirse(receta));
	}
	
	@Test
	public void testSePuedeSugerirRecetaAGrupoGrosos(){
		receta.agregarIngrediente(azucar);
		receta.agregarIngrediente(fruta);
		
		Receta sub1 = new Receta(null, null, null);
		sub1.agregarIngrediente(carne);
		
		receta.agregarSubreceta(sub1);
	
		fecheSena.agregarPreferenciaAlimenticia(fruta);
		grupo.agregarPreferencia(azucar);
			
		assertTrue(grupo.puedeSugerirse(receta));
	}
	
	@Test 
	public void testSiVariosGruposAgreganUnUsuarioEntoncesElUsuarioContieneEsosGruposEnSuColeccion() {
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		GrupoUsuarios grupo2 = new GrupoUsuarios("grupo2");
		GrupoUsuarios grupo3 = new GrupoUsuarios("grupo3");
		
		grupo1.agregarUsuario(fecheSena);
		grupo2.agregarUsuario(fecheSena);
		grupo3.agregarUsuario(fecheSena);
	
		fecheSena.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo2);
		fecheSena.agregarGrupo(grupo3);
		
		List<GrupoUsuarios> aux = new ArrayList<>();
		
		aux.add(grupo1);
		aux.add(grupo3);
		aux.add(grupo2);
		
		assertTrue(fecheSena.getGrupos().containsAll(aux));			
	}
	
	@Test
	public void testSiHayUnMiembroQueCreoLaRecetaTodosLaPuedenVer() {
		receta = new Receta(fecheSena, null, null);
		
		grupo = new GrupoUsuarios("grupo");
		
		grupo.agregarUsuario(matias);
		grupo.agregarUsuario(ariel);
		grupo.agregarUsuario(fecheSena);

		assertTrue(grupo.puedeVer(receta));
	}
	
	@Test
	public void testSiNoHayUnMiembroQueCreoLaRecetaNingunoLaPuedeVer() {
		receta = new Receta(fecheSena, null, null);
		
		grupo = new GrupoUsuarios("grupo");
		
		grupo.agregarUsuario(matias);
		grupo.agregarUsuario(ariel);

		assertFalse(grupo.puedeVer(receta));
	}
	
	@Test
	public void todosLosMiembrosDelGrupoConocenUnaRecetaPublica() {
		RecetaPublica recetaPublica = new RecetaPublica();
		
		grupo = new GrupoUsuarios("grupo");
		
		grupo.agregarUsuario(fecheSena);
		grupo.agregarUsuario(ariel);
		
		assertTrue(grupo.puedeVer(recetaPublica));
	}
	
	
	
	
	

}
