package grupo4.dds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.BuilderUsuario;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestRepositorioDeUsuarios extends BaseTest {
	
	private RepositorioDeUsuarios repositorio = RepositorioDeUsuarios.instance();
	
	private Usuario jorgeMartin = new BuilderUsuario().nombre("Jorge Martin").build();
	private Usuario jorgeFernandez = new BuilderUsuario().nombre("Jorge Fernandez").build();
	private Usuario lauraMartin = new BuilderUsuario().nombre("Laura Martin").build();
	
	@Before
	public void setUp() {
		
		repositorio.add(jorgeMartin);
		repositorio.add(jorgeFernandez);
		repositorio.add(arielFolino);
		repositorio.add(matiasMartino);
		repositorio.add(lauraMartin);
		
		jorgeFernandez.agregarCondicion(Vegano.instance()).agregarCondicion(Celiaco.instance());
		jorgeMartin.agregarCondicion(Vegano.instance()).agregarCondicion(Diabetico.instance());
		matiasMartino.agregarCondicion(Vegano.instance()).agregarCondicion(Diabetico.instance());
		lauraMartin.agregarCondicion(Celiaco.instance());
		arielFolino.agregarCondicion(Celiaco.instance());
		fecheSena.agregarCondicion(Hipertenso.instance());
	}

	/* Test: @get/1 */
	@Test
	public void testGsetUsuarioConElMismoNombreDelUsuarioPrototipo() {
		assertEquals(jorgeFernandez.getId(), repositorio.get(BuilderUsuario.prototipo("Jorge Fernandez")).getId());
		assertEquals(lauraMartin, repositorio.get(BuilderUsuario.prototipo("Laura Martin")));
	}
	
	/* Test: @list/1 */
	@Test
	public void testListarUsuarioConNombreJorge() {
		List<Usuario> expected = Arrays.asList(jorgeFernandez, jorgeMartin);
		assertEqualsList(expected, repositorio.list(BuilderUsuario.prototipo("Jorge")));
	}
	
	@Test
	public void testListarUsuariosQueContienenMartinEnElNombre() {
		List<Usuario> expected = Arrays.asList(matiasMartino, lauraMartin, jorgeMartin);
		assertEqualsList(expected, repositorio.list(BuilderUsuario.prototipo("Martin")));
	}
	
	@Test
	public void testListarVeganosYCeliacos() {
		List<Usuario> expected = Arrays.asList(jorgeMartin, matiasMartino);
		
		Usuario prototipo = BuilderUsuario.prototipo("Martin", Arrays.asList(Vegano.instance(), Diabetico.instance()));
		assertEqualsList(expected, repositorio.list(prototipo));
	}
	
	@Test
	public void testListarDiabeticosConMartinEnElNombre() {
		List<Usuario> expected = Arrays.asList(matiasMartino, jorgeMartin);
		
		Usuario prototipo = BuilderUsuario.prototipo("Martin");
		prototipo.agregarCondicion(Diabetico.instance());
		
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
	
}
