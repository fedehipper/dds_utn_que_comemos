package grupo4.dds;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Ingrediente;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Before;
import org.junit.Test;

public class TestGrupoUsuarios {
	
	private GrupoUsuarios grupo;
	private Usuario fecheSena;
	private Usuario ariel;
	private Usuario matias;
	private Receta receta;

	@Before
	public void setUp() {
		grupo = new GrupoUsuarios("grosos");
		fecheSena = new Usuario();
		matias = new Usuario();
		ariel = new Usuario();
		receta = new Receta();
		receta.setTotalCalorias(4500);
	}

	/* Test: @puedeSugerirse/1 */
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnGrupoSiNoContieneAlgunaDeSusPreferencias(){
		receta.agregarIngrediente(new Ingrediente("carne",0f));
		receta.agregarIngrediente(new Ingrediente("pescado",0f));
		
		grupo.agregarPreferenciaAlimenticia(new Ingrediente("fruta",0f));
		assertFalse(grupo.puedeSugerirse(receta));
	}
	
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnGrupoSiNoEsAdecuadaParaTodosSusMiembros(){
		receta.agregarIngrediente(new Ingrediente("carne",0f));
		receta.agregarIngrediente(new Ingrediente("pescado",0f));

		ariel.agregarGrupo(grupo);
		fecheSena.agregarGrupo(grupo);
		ariel.agregarCondicion(new Celiaco());
		fecheSena.agregarCondicion(new Vegano());
		
		grupo.agregarPreferenciaAlimenticia(new Ingrediente("carne",0f));
		
		assertFalse(grupo.puedeSugerirse(receta));
	}
	
	@Test
	public void testSePuedeSugerirUnaRecetaAUnGrupo(){
		receta.agregarIngrediente(new Ingrediente("verdura",0f));
		receta.agregarIngrediente(new Ingrediente("pescado",0f));

		ariel.agregarGrupo(grupo);
		fecheSena.agregarGrupo(grupo);
		ariel.agregarCondicion(new Celiaco());
		fecheSena.agregarCondicion(new Vegano());
		
		grupo.agregarPreferenciaAlimenticia(new Ingrediente("pescado",0f));
		
		assertTrue(grupo.puedeSugerirse(receta));
	}
	
	/* Test: @puedeVer/1 */
	@Test
	public void testUnGrupoPuedeVerUnaRecetaSiAlgunoDeSusMiembrosLaVe() {
		receta = new Receta(fecheSena, null, null);
		
		grupo.agregarUsuario(matias);
		grupo.agregarUsuario(ariel);
		grupo.agregarUsuario(fecheSena);

		assertTrue(grupo.puedeVer(receta));
	}
	
	@Test
	public void testUnGrupoNoPuedeVerUnaRecetaSiNingunoDeSusMiembrosLaVe() {
		receta = new Receta(fecheSena, null, null);
		
		grupo.agregarUsuario(matias);
		grupo.agregarUsuario(ariel);

		assertFalse(grupo.puedeVer(receta));
	}
}
