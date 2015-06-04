package grupo4.dds;

import static org.junit.Assert.assertEquals;
import grupo4.dds.usuario.RepositorioDeUsuarios;
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

public class TestRepositorioDeUsuarios {
	
	private Usuario jorgeMartin = new Usuario("Jorge Martin", null, 1, 2, null);
	private Usuario jorgeFernandez = new Usuario("Jorge Fernandez", null, 3, 4, null);
	private Usuario arielFolino = new Usuario("Ariel Folino", null, 6, 5, null);
	private Usuario matiasMartin = new Usuario("Matias Martin", null, 7, 8, null);
	private Usuario fedeHipper = new Usuario("Federico Hipper", null, 9, 10, null);
	private Usuario fedeSenna = new Usuario("Federico Senna", null, 11, 12, null);
	private Usuario lauraMartin = new Usuario("Laura Martin", null, 13, 14, null);

	private RepositorioDeUsuarios repositorio = RepositorioDeUsuarios.get();
	
	@Before
	public void setUp() throws Exception {
		repositorio.vaciar();
		
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
		assertEquals(jorgeFernandez, repositorio.get(new Usuario("Jorge Fernandez", null, 0, 0, null)));
		assertEquals(lauraMartin, repositorio.get(new Usuario("Laura Martin", null, 0, 0, null)));
	}
	
	/* Test: @list/1 */
	@Test
	public void testListUsuarioConNombreJorge() {
		List<Usuario> expected = Arrays.asList(jorgeFernandez, jorgeMartin);
		assertEqualsList(expected, repositorio.list(new Usuario("Jorge", null, 0, 0, null)));
	}
	
	@Test
	public void testListUsuarioQueContienenMartinEnElNombre() {
		List<Usuario> expected = Arrays.asList(matiasMartin, lauraMartin, jorgeMartin);
		assertEqualsList(expected, repositorio.list(new Usuario("Martin", null, 0, 0, null)));
	}
	
	@Test
	public void testListVeganosYCeliacos() {
		List<Usuario> expected = Arrays.asList(matiasMartin, lauraMartin, jorgeMartin, jorgeFernandez, arielFolino);
		
		Usuario prototipo = new Usuario("Martin", null, 0, 0, null);
		prototipo.agregarCondicion(new Vegano());
		prototipo.agregarCondicion(new Celiaco());
		
		assertEqualsList(expected, repositorio.list(prototipo));
	}
	
	@Test
	public void testListDiabeticosConMartinEnElNombre() {
		List<Usuario> expected = Arrays.asList(matiasMartin, jorgeMartin);
		
		Usuario prototipo = new Usuario("Martin", null, 0, 0, null);
		prototipo.agregarCondicion(new Diabetico());
		
		assertEqualsList(expected, repositorio.list(prototipo));
	}
	
	private boolean assertEqualsList(Collection<Usuario> l1, Collection<Usuario> l2) {
		return l1.size() == l2.size() && l1.containsAll(l2);
	}

}
