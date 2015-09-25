package grupo4.dds;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.GrupoUsuarios;
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
		
		grupo = GrupoUsuarios.crearGrupo("grosos");
		fecheSena = Usuario.crearPerfil("fecheSena");
		matias = Usuario.crearPerfil("matias");
		ariel = Usuario.crearPerfil("ariel");
		receta = Receta.crearNueva();
		receta.setTotalCalorias(4500);
	}

	/* Test: @puedeSugerirse/1 */
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnGrupoSiNoContieneAlgunaDeSusPreferencias(){
		receta.agregarIngrediente(Ingrediente.nuevoIngrediente("carne", 0f));
		receta.agregarIngrediente(Ingrediente.nuevoIngrediente("pescado", 0f));
		
		grupo.agregarPreferenciaAlimenticia(Ingrediente.nuevoIngrediente("fruta", 0f));
		assertFalse(grupo.puedeSugerirse(receta));
	}
	
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnGrupoSiNoEsAdecuadaParaTodosSusMiembros(){
		receta.agregarIngrediente(Ingrediente.nuevoIngrediente("carne", 0f));
		receta.agregarIngrediente(Ingrediente.nuevoIngrediente("pescado", 0f));

		ariel.agregarGrupo(grupo);
		fecheSena.agregarGrupo(grupo);
		ariel.agregarCondicion(new Celiaco());
		fecheSena.agregarCondicion(new Vegano());
		
		grupo.agregarPreferenciaAlimenticia(Ingrediente.nuevoIngrediente("carne", 0f));
		
		assertFalse(grupo.puedeSugerirse(receta));
	}
	
	@Test
	public void testSePuedeSugerirUnaRecetaAUnGrupo(){
		receta.agregarIngrediente(Ingrediente.nuevoIngrediente("verdura", 0f));
		receta.agregarIngrediente(Ingrediente.nuevoIngrediente("pescado", 0f));

		ariel.agregarGrupo(grupo);
		fecheSena.agregarGrupo(grupo);
		ariel.agregarCondicion(new Celiaco());
		fecheSena.agregarCondicion(new Vegano());
		
		grupo.agregarPreferenciaAlimenticia(Ingrediente.nuevoIngrediente("pescado", 0f));
		
		assertTrue(grupo.puedeSugerirse(receta));
	}
	
	/* Test: @puedeVer/1 */
	@Test
	public void testUnGrupoPuedeVerUnaRecetaSiAlgunoDeSusMiembrosLaVe() {
		receta = Receta.crearNueva(fecheSena, null, "");
		
		grupo.agregarUsuario(matias);
		grupo.agregarUsuario(ariel);
		grupo.agregarUsuario(fecheSena);

		assertTrue(grupo.puedeVer(receta));
	}
	
	@Test
	public void testUnGrupoNoPuedeVerUnaRecetaSiNingunoDeSusMiembrosLaVe() {
		receta = Receta.crearNueva(fecheSena, null, null);
		
		grupo.agregarUsuario(matias);
		grupo.agregarUsuario(ariel);

		assertFalse(grupo.puedeVer(receta));
	}
}
