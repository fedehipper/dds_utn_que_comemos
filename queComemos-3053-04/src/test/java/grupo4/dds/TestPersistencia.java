package grupo4.dds;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Vegano;

import org.junit.Test;

public class TestPersistencia extends BaseTest {
	
	
	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void testAlCrearUnUsuarioEsPersistido() {
		
		assertTrue(entityManager().contains(arielFolino));
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
	
	@Test
	public void testPersistirRecetasDeLosUsuarios() {
		
		arielFolino.agregarReceta(sopa);		

		entityManager().persist(arielFolino);
		
		TypedQuery<Usuario> q = entityManager().createQuery("SELECT u FROM Usuario u WHERE u.nombre  = 'Ariel Folino'", Usuario.class); //.setParameter("idFeche", fecheSena.getId());

		Usuario usuarioConsultado = q.getSingleResult();
		

		assertTrue(usuarioConsultado.getRecetas().contains(sopa));
					
	}
	
	
	@Test 
	 public void testConsultaUsuariosQuePertenecenAUnGrupo() {
	  
	  grupo1.agregarUsuario(arielFolino);
	  grupo1.agregarUsuario(maria);
	  grupo1.agregarUsuario(fecheSena);
	  
	  entityManager().persist(grupo1);
	  
	  TypedQuery<GrupoUsuarios> q = entityManager().createQuery("SELECT g FROM GrupoUsuarios g WHERE g.nombre = 'grupo1'", GrupoUsuarios.class);
	  
	  GrupoUsuarios grupoConsultado = q.getSingleResult();
	  
	  assertTrue(grupoConsultado.esMiembro(arielFolino));
	  
	 }
	

	@Test
	public void testConsultaDeCondicion(){
		maria.agregarCondicion(Vegano.instance());
		maria.agregarCondicion(Celiaco.instance());
		entityManager().persist(maria);
		
		TypedQuery<Usuario> q = entityManager().createQuery("SELECT u FROM Usuario u WHERE u.nombre = 'Maria'", Usuario.class);
		Usuario usuarioConsultado=q.getSingleResult();
		
		assertTrue(usuarioConsultado.esVegano());
	}
	
	@Test
	public void testSePersisteElHistorialDeUnUsuario(){
		
		arielFolino.marcarFavorita(lomito);
		arielFolino.marcarFavorita(sopa);
		
		entityManager().persist(arielFolino);
		
		TypedQuery<Usuario> q = entityManager().createQuery("SELECT u FROM Usuario u WHERE u.nombre = 'Ariel Folino'", Usuario.class);
		
		Usuario usuarioConsultado = q.getSingleResult();
		
		assertTrue(usuarioConsultado.getFavoritas().contains(lomito));
		
	}

	
	@Test 
	public void testVerificarSiSePersistenLasSubrecetas(){
			
		milanesa.agregarSubreceta(pollo);
		milanesa.agregarSubreceta(pure);
		
		List<Receta> recetas = new ArrayList<>();
		recetas.add(pollo);
		recetas.add(pure);
		
		entityManager().persist(milanesa);
		
		TypedQuery<Receta> q = entityManager().createQuery("SELECT r FROM Receta r WHERE id = :id",Receta.class).
				setParameter("id", milanesa.getId());
		
		Receta recetaConsultada = q.getSingleResult();
		
		assertTrue(recetaConsultada.getSubrecetas().containsAll(recetas));
	}
	
	@Test
	public void testPersistenciaIngredientesDeReceta() {
		
		Receta larga = new BuilderReceta().creador(arielFolino).preparacion("mandale cualquiera").
			calorias(4000).
			ingrediente(Ingrediente.nuevoIngrediente("dulce de leche", 0f)).
			ingrediente(Ingrediente.nuevoIngrediente("ajo", 0f)).
			ingrediente(Ingrediente.nuevoIngrediente("morron", 0f)).
			ingrediente(Ingrediente.nuevoIngrediente("apio", 0f)).
			ingrediente(Ingrediente.nuevoIngrediente("leche", 0f)).
			build();
		List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
		ingredientes = larga.getIngredientes();
		
		entityManager().persist(larga);

		TypedQuery<Receta> q = entityManager().createQuery("SELECT r FROM Receta r WHERE id = :id", Receta.class).
				setParameter("id", larga.getId());
		
		Receta receta = q.getSingleResult();
		
		assertEquals(receta.getIngredientes(), ingredientes);
		
	}
	
	@Test 
	public void testUsuarioPerteneceAMasDeUnGrupo() {
		
		grupo1.agregarUsuario(maria);
		grupo2.agregarUsuario(maria);

		List<GrupoUsuarios> grupos = new ArrayList<GrupoUsuarios>();
		grupos.add(grupo1);
		grupos.add(grupo2);
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(fecheSena);
		usuarios.add(maria);
		usuarios.add(arielFolino);
		
		entityManager().persist(grupo1);
		entityManager().persist(grupo2);
		
		TypedQuery<GrupoUsuarios> q = entityManager().createQuery("SELECT g FROM GrupoUsuarios g WHERE id IN( :id1, :id2 )", GrupoUsuarios.class)
				.setParameter("id1", grupo1.getId())
				.setParameter("id2", grupo2.getId());
			
		List<GrupoUsuarios> gruposConsultados = q.getResultList();
		
		assertTrue(maria.perteneceA(gruposConsultados.get(0)) && maria.perteneceA(gruposConsultados.get(1)));
		
		
	}
	
}