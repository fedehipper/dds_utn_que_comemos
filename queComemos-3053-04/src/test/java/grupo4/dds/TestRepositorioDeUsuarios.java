package grupo4.dds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestRepositorioDeUsuarios extends BaseTest {
	
	private Usuario jorgeMartin = Usuario.crearPerfil("Jorge Martin", null, null, 1, 2, null, false, null);
	private Usuario jorgeFernandez = Usuario.crearPerfil("Jorge Fernandez", null, null, 3, 4, null, false, null);
	private Usuario arielFolino = Usuario.crearPerfil("Ariel Folino", null, null, 6, 5, null, false, null);
	private Usuario matiasMartin = Usuario.crearPerfil("Matias Martin", null, null, 7, 8, null, false, null);
	private Usuario fedeHipper = Usuario.crearPerfil("Federico Hipper", null, null, 9, 10, null, false, null);
	private Usuario fedeSenna = Usuario.crearPerfil("Federico Senna", null, null, 11, 12, null, false, null);
	private Usuario lauraMartin = Usuario.crearPerfil("Laura Martin", null, null, 13, 14, null, false, null);

	private RepositorioDeUsuarios repositorio = RepositorioDeUsuarios.get();
	
	@Before
	public void setUp() {
		
		repositorio.add(jorgeMartin);
		repositorio.add(jorgeFernandez);
		repositorio.add(arielFolino);
		repositorio.add(matiasMartin);
		repositorio.add(fedeHipper);
		repositorio.add(fedeSenna);
		repositorio.add(lauraMartin);
		
		jorgeFernandez.agregarCondicion(new Vegano());
		jorgeMartin.agregarCondicion(new Vegano());
		matiasMartin.agregarCondicion(new Vegano());
		lauraMartin.agregarCondicion(new Celiaco());
		jorgeFernandez.agregarCondicion(new Celiaco());
		arielFolino.agregarCondicion(new Celiaco());
		fedeSenna.agregarCondicion(new Hipertenso());
		jorgeMartin.agregarCondicion(new Diabetico());
		matiasMartin.agregarCondicion(new Diabetico());
	}

	/* Test: @get/1 */
	@Test
	public void testGsetUsuarioConElMismoNombreDelUsuarioPrototipo() {
		entityManager().flush();
		assertEquals(jorgeFernandez.getId(), repositorio.get(Usuario.crearPerfil("Jorge Fernandez", null, null, 0, 0, null, false, null)).getId());
		assertEquals(lauraMartin, repositorio.get(Usuario.crearPerfil("Laura Martin", null, null, 0, 0, null, false, null)));
	}
	
	/* Test: @list/1 */
	@Test
	public void testListarUsuarioConNombreJorge() {
		List<Usuario> expected = Arrays.asList(jorgeFernandez, jorgeMartin);
		assertEqualsList(expected, repositorio.list(Usuario.prototipo("Jorge")));
	}
	
	@Test
	public void testListarUsuariosQueContienenMartinEnElNombre() {
		List<Usuario> expected = Arrays.asList(matiasMartin, lauraMartin, jorgeMartin);
		assertEqualsList(expected, repositorio.list(Usuario.prototipo("Martin")));
	}
	
	@Test
	public void testListarVeganosYCeliacos() {
		List<Usuario> expected = Arrays.asList(jorgeMartin, matiasMartin);
		
		Usuario prototipo = Usuario.prototipo("Martin");
		prototipo.agregarCondicion(new Vegano());
		prototipo.agregarCondicion(new Diabetico());
		assertEqualsList(expected, repositorio.list(prototipo));
	}
	
	@Test
	public void testListarDiabeticosConMartinEnElNombre() {
		List<Usuario> expected = Arrays.asList(matiasMartin, jorgeMartin);
		
		Usuario prototipo = Usuario.prototipo("Martin");
		prototipo.agregarCondicion(new Diabetico());
		
		assertEqualsList(expected, repositorio.list(prototipo));
	}

	
	@Test
	public void testActualizarGrupo() {
		entityManager().clear();
		
		GrupoUsuarios grupo = GrupoUsuarios.crearGrupo("");
		jorgeMartin.agregarGrupo(grupo);
		
		repositorio.update(jorgeMartin);
		
		assertTrue(repositorio.get(jorgeMartin).perteneceA(grupo));
	}
	
	private void assertEqualsList(Collection<Usuario> l1, Collection<Usuario> l2) {
		assertTrue(l1.size() == l2.size() && l1.containsAll(l2));
	}
	
}
