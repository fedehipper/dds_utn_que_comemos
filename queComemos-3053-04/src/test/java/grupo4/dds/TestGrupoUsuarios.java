package grupo4.dds;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Test;

public class TestGrupoUsuarios extends BaseTest {
	
	/* Test: @puedeSugerirse/1 */
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnGrupoSiNoContieneAlgunaDeSusPreferencias(){
		milanesa.agregarIngrediente(Ingrediente.nuevoIngrediente("carne", 0f));
		milanesa.agregarIngrediente(Ingrediente.nuevoIngrediente("pescado", 0f));
		
		grupo1.agregarPreferenciaAlimenticia(Ingrediente.nuevoIngrediente("fruta", 0f));
		assertFalse(grupo1.puedeSugerirse(milanesa));
	}
	
	@Test
	public void testNoSePuedeSugerirUnaRecetaAUnGrupoSiNoEsAdecuadaParaTodosSusMiembros(){
		milanesa.agregarIngrediente(Ingrediente.nuevoIngrediente("carne", 0f));
		milanesa.agregarIngrediente(Ingrediente.nuevoIngrediente("pescado", 0f));

		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);
		arielFolino.agregarCondicion(Celiaco.instance());
		fecheSena.agregarCondicion(Vegano.instance());
		
		grupo1.agregarPreferenciaAlimenticia(Ingrediente.nuevoIngrediente("carne", 0f));
		
		assertFalse(grupo1.puedeSugerirse(milanesa));
	}
	
	@Test
	public void testSePuedeSugerirUnaRecetaAUnGrupo(){
		milanesa.agregarIngrediente(Ingrediente.nuevoIngrediente("verdura", 0f));
		milanesa.agregarIngrediente(Ingrediente.nuevoIngrediente("pescado", 0f));

		arielFolino.agregarGrupo(grupo1);
		fecheSena.agregarGrupo(grupo1);
		arielFolino.agregarCondicion(Celiaco.instance());
		fecheSena.agregarCondicion(Vegano.instance());
		
		grupo1.agregarPreferenciaAlimenticia(Ingrediente.nuevoIngrediente("pescado", 0f));
		
		assertTrue(grupo1.puedeSugerirse(milanesa));
	}
	
	/* Test: @puedeVer/1 */
	@Test
	public void testUnGrupoPuedeVerUnaRecetaSiAlgunoDeSusMiembrosLaVe() {
		
		grupo1.agregarUsuario(matiasMartino);
		grupo1.agregarUsuario(arielFolino);
		grupo1.agregarUsuario(fecheSena);

		assertTrue(grupo1.puedeVer(milanesa));
	}
	
	@Test
	public void testUnGrupoNoPuedeVerUnaRecetaSiNingunoDeSusMiembrosLaVe() {
		
		grupo1.agregarUsuario(matiasMartino);
		grupo1.agregarUsuario(arielFolino);

		assertFalse(grupo1.puedeVer(milanesa));
	}
}
