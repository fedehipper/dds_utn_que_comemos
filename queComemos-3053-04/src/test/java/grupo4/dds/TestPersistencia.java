package grupo4.dds;

import static org.junit.Assert.*;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.receta.builder.BuilderRecetaPublica;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;

public class TestPersistencia extends BaseTest {

	private Usuario maria;
	private Usuario ariel;
	private Receta pollo;
	private RecetaPublica milanesa;

	@Before
	public void setUp() {
		
		maria = Usuario.crearPerfil("Maria", Sexo.FEMENINO, null,
				1.70f, 65.0f, null, false, null);
		ariel = Usuario.crearPerfil("Ariel", Sexo.MASCULINO, null,
				0f, 0f, null, false, null);	
		
		milanesa = (RecetaPublica) (new BuilderRecetaPublica()).setTotalCalorias(150)
		.setIngrediente(null).setNombreDelPlato("Sopa").setDificultad(Dificultad.FACIL).build();
		pollo = (new BuilderReceta()).setTotalCalorias(150)
		.setIngrediente(null).setCreador(maria)
		.setNombreDelPlato("Pollo").setDificultad(Dificultad.FACIL).build();
	}
	
	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void testAlCrearUnUsuarioEsPersistido() {
		
		assertTrue(entityManager().contains(ariel));
		assertTrue(entityManager().contains(maria));
	}
	
	@Test
	public void testAlCrearUnaRecetaEsPersistida() {
		
		assertTrue(entityManager().contains(pollo));
		assertTrue(entityManager().contains(milanesa));
	}

	@Test
	public void testAlCrearUnGrupoEsPersistido() {
		assertTrue(entityManager().contains(GrupoUsuarios.crearGrupo("")));
	}
	
}