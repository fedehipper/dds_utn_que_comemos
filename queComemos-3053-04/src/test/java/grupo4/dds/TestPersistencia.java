package grupo4.dds;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.receta.builder.BuilderRecetaPublica;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.*;

import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;

public class TestPersistencia extends BaseTest {

	private Usuario maria;
	private Usuario ariel;
	private Receta pollo;
	private RecetaPublica milanesa;
	private Usuario fecheSena;
	private Receta receta1;
	private Receta receta2;
	private Receta receta3;
	private GrupoUsuarios grupo;
	
	@Before
	public void setUp() {
			
		grupo = GrupoUsuarios.crearGrupo("elEscuadronSuicida");
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null, false, null);
		
		maria = Usuario.crearPerfil("Maria", Sexo.FEMENINO, null, 1.70f, 65.0f, null, false, null);
		ariel = Usuario.crearPerfil("Ariel", Sexo.MASCULINO, null, 0f, 0f, null, false, null);	
		
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
	
	
	
	@Test
	public void testPersistirRecetasDeLosUsuarios() {
		
		receta1 = new BuilderReceta().setCreador(fecheSena).
				 setPreparacion("Preparación antes de modificar").
				 setTotalCalorias(4500).
				 setNombreDelPlato("pez").
				 setIngrediente(Ingrediente.nuevoIngrediente("frutas", 0f)).
				 build();
		receta3 = new BuilderReceta().setCreador(fecheSena).
				 setPreparacion("arina y agua").
				 setTotalCalorias(4500).
				 setNombreDelPlato("pasta").
				 setIngrediente(Ingrediente.nuevoIngrediente("facturas", 0f)).
				 build();
		
		List<Receta> recetas = new ArrayList<Receta>();
		recetas.add(receta1);
		recetas.add(receta3);
		
		fecheSena.agregarReceta(receta1);
		fecheSena.agregarReceta(receta3);
		

		entityManager().persist(fecheSena);
		
		TypedQuery<Usuario> q = entityManager().createQuery("SELECT u FROM Usuario u WHERE u.nombre  = 'Feche Sena'", Usuario.class); //.setParameter("idFeche", fecheSena.getId());

		Usuario usuarioConsultado = q.getSingleResult();
		

		assertTrue(usuarioConsultado.getRecetas().containsAll(recetas));
					
	}
	
	
	@Test 
	 public void testConsultaUsuariosQuePertenecenAUnGrupo() {
	  
	  grupo.agregarUsuario(ariel);
	  grupo.agregarUsuario(maria);
	  grupo.agregarUsuario(fecheSena);
	  
	  entityManager().persist(grupo);
	  
	  TypedQuery<GrupoUsuarios> q = entityManager().createQuery("SELECT g FROM GrupoUsuarios g WHERE g.nombre = 'elEscuadronSuicida'", GrupoUsuarios.class);
	  
	  GrupoUsuarios grupoConsultado = q.getSingleResult();
	  
	  assertTrue(grupoConsultado.esMiembro(ariel));
	  
	 }
	

	@Test
	public void testConsultaDeCondicion(){
		maria.agregarCondicion(new Vegano());
		maria.agregarCondicion(new Celiaco());
		entityManager().persist(maria);
		
		TypedQuery<Usuario> q = entityManager().createQuery("SELECT u FROM Usuario u WHERE u.nombre = 'Maria'", Usuario.class);
		Usuario usuarioConsultado=q.getSingleResult();
		
		assertTrue(usuarioConsultado.esVegano());
	}
	
	@Test
	public void testSePersisteElHistorialDeUnUsuario(){
		
		receta1 = new BuilderReceta().setCreador(fecheSena).
				 setPreparacion("meter al horno por 5 horas").
				 setTotalCalorias(4000).
				 setNombreDelPlato("fideos al horno").
				 setIngrediente(Ingrediente.nuevoIngrediente("fideos", 0f)).
				 build();
		
		receta3 = new BuilderReceta().setCreador(fecheSena).
				 setPreparacion("tirar a la parrilla").
				 setTotalCalorias(4500).
				 setNombreDelPlato("asado").
				 setIngrediente(Ingrediente.nuevoIngrediente("carne", 0f)).
				 build();
		
		fecheSena.marcarFavorita(receta1);
		fecheSena.marcarFavorita(receta3);
		
		entityManager().persist(fecheSena);
		
		TypedQuery<Usuario> q = entityManager().createQuery("SELECT u FROM Usuario u WHERE u.nombre = 'Feche Sena'", Usuario.class);
		
		Usuario usuarioConsultado = q.getSingleResult();
		
		assertTrue(usuarioConsultado.getHistorial().contains(receta1));
		
	}

	
	@Test 
	public void testVerificarSiSePersistenLasSubrecetas(){
		
		receta1 = new BuilderReceta().setCreador(ariel).
				 setPreparacion("mandale cualquiera").
				 setTotalCalorias(4000).
				 setNombreDelPlato("mandale cualquiera").
				 setIngrediente(Ingrediente.nuevoIngrediente("cualquiera", 0f)).
				 build();
		
		receta2 = new BuilderReceta().setCreador(ariel).
				 setPreparacion("receta distinta a la anterior").
				 setTotalCalorias(4500).
				 setNombreDelPlato("es distinta").
				 setIngrediente(Ingrediente.nuevoIngrediente("distinto a cualquiera", 0f)).
				 build();
		
		receta3 = new BuilderReceta().setCreador(ariel).
				 setPreparacion("otra mas que no es como las anteriores").
				 setTotalCalorias(4500).
				 setNombreDelPlato("no es pasta").
				 setIngrediente(Ingrediente.nuevoIngrediente("pasta", 0f)).
				 build();
		
		receta1.agregarSubreceta(receta2);
		receta1.agregarSubreceta(receta3);
		
		List<Receta> recetas = new ArrayList<>();
		recetas.add(receta2);
		recetas.add(receta3);
		
		entityManager().persist(receta1);
		
		TypedQuery<Receta> q = entityManager().createQuery("SELECT r FROM Receta r WHERE id = :id",Receta.class).
				setParameter("id", receta1.getId());
		
		Receta recetaConsultada = q.getSingleResult();
		
		assertTrue(recetaConsultada.getSubrecetas().containsAll(recetas));
	}
	
	
	
}