package grupo4.dds;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.receta.builder.BuilderRecetaPublica;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;

import org.hibernate.Session;
import org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker;
import org.hibernate.sql.Select;
import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;

public class TestPersistencia extends BaseTest {

	private Usuario maria;
	private Usuario ariel;
	private Receta pollo;
	private RecetaPublica milanesa;
	private Usuario fecheSena;
	private Usuario arielFolino;
	private Usuario matiasMartino;
	private Usuario federicoHipper;
	private Usuario cristianMaldonado;
	private Receta receta1;
	private Receta receta2;
	private Receta receta3;
	
	@Before
	public void setUp() {
			
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null, false, null);
		arielFolino = Usuario.crearPerfil("Ariel Folino", null, null, 1.69f, 96.0f, null, false, null);
		matiasMartino = Usuario.crearPerfil("Matías Martino", null, null, 1.74f, 79.0f, null, false, null);
		federicoHipper = Usuario.crearPerfil("Federico Hipperdinger", null, null, 1.91f, 99.0f, null, false, null);
		cristianMaldonado = Usuario.crearPerfil("Cristian Maldonado", null, null, 1.34f, 87.0f, null, false, null);
		
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
	public void testPersistirRecetasDeSusCreadires() {
		
		receta1 = new BuilderReceta().setCreador(fecheSena).
				 setPreparacion("Preparación antes de modificar").
				 setTotalCalorias(4500).
				 setIngrediente(Ingrediente.nuevoIngrediente("frutas", 0f)).
				 build();
		receta2 = new BuilderReceta().setCreador(federicoHipper).
				 setPreparacion("entra cuchillo salen las tripas").
				 setTotalCalorias(450).
				 setIngrediente(Ingrediente.nuevoIngrediente("pezcado", 0f)).
				 build();
		receta3 = new BuilderReceta().setCreador(fecheSena).
				 setPreparacion("arina y agua").
				 setTotalCalorias(4500).
				 setIngrediente(Ingrediente.nuevoIngrediente("facturas", 0f)).
				 build();
		
		fecheSena.agregarReceta(receta1);
		fecheSena.agregarReceta(receta3);
		
		entityManager().persist(fecheSena);
		
		TypedQuery<Receta> query = entityManager().createQuery("SELECT u.recetas FROM Usuario u WHERE u.nombre LIKE 'Feche Sena'", Receta.class);
		List<Receta> consultaRecetas = query.getResultList();
		
		assertTrue(consultaRecetas.contains(receta1));
					
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}