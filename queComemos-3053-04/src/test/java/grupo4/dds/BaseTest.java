package grupo4.dds;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.receta.builder.BuilderRecetaPublica;
import grupo4.dds.usuario.BuilderUsuario;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;

import org.junit.After;
import org.junit.Before;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public abstract class BaseTest implements WithGlobalEntityManager {

	protected Usuario maria;
	protected Usuario fecheSena;
	protected Usuario arielFolino;
	protected Usuario matiasMartino;
	protected Usuario federicoHipper;
	protected Usuario cristianMaldonado;
	
	protected GrupoUsuarios grupo1;
	protected GrupoUsuarios grupo2;
	
	protected Receta milanesa;
	protected Receta pollo;
	protected Receta pure;
	protected Receta sopa;
	protected Receta bife;
	protected RecetaPublica salmon;
	protected RecetaPublica lomito;
	protected RecetaPublica coliflor;
	
	protected List<Receta> todasLasRecetas;
	
	@Before
	public void baseSetUp() {
		entityManager().getTransaction().begin();
		
		maria = new BuilderUsuario().femenino().nombre("Maria").altura(1.70f).peso(65.0f).build();
		fecheSena = new BuilderUsuario().masculino().nombre("Federico Sena").altura(1.70f).peso(65.0f).mail("fesena92@gmail.com").build();
		arielFolino = new BuilderUsuario().masculino().nombre("Ariel Folino").altura(1.69f).peso(96.0f).build();
		matiasMartino = new BuilderUsuario().masculino().nombre("Matías Martino").altura(1.74f).peso(79.0f).build();
		federicoHipper = new BuilderUsuario().masculino().nombre("Federico Hipperdinger").altura(1.91f).peso(102.0f).build();
		cristianMaldonado = new BuilderUsuario().masculino().nombre("Cristian Maldonado").altura(1.81f).peso(87.0f).build();
		
		grupo1 = GrupoUsuarios.crearGrupo("grupo1");
		grupo2 = GrupoUsuarios.crearGrupo("grupo2");
		
		Ingrediente ing = Ingrediente.nuevoIngrediente("", 0);
		
		milanesa = new BuilderReceta().creador(fecheSena).nombre("milanesa").dificil().calorias(999).ingrediente(ing).build();
		pollo = new BuilderReceta().creador(federicoHipper).nombre("pollo").dificil().calorias(300).ingrediente(ing).build();
		pure = new BuilderReceta().creador(federicoHipper).nombre("pure").calorias(600).ingrediente(ing).build();
		sopa = new BuilderReceta().creador(arielFolino).nombre("sopa").calorias(100).facil().ingrediente(ing).build();
		bife = new BuilderReceta().creador(maria).nombre("bife").calorias(499).facil().ingrediente(ing).build();
		salmon = new BuilderRecetaPublica().nombre("salmon").calorias(200).ingrediente(ing).build();
		lomito = new BuilderRecetaPublica().nombre("lomito").calorias(300).ingrediente(ing).build();
		coliflor = new BuilderRecetaPublica().nombre("coliflor hervida").calorias(100).facil().ingrediente(ing).build();
		
		todasLasRecetas = Arrays.asList(milanesa, pollo, pure, sopa, bife, salmon, lomito, coliflor);
	}
	
	@After
	public void baseTierDown() {
		entityManager().getTransaction().rollback();
	}
	
	protected <T> void assertEqualsList(Collection<T> l1, Collection<T> l2) {
		assertTrue(l1.size() == l2.size() && l1.containsAll(l2));
	}
	
}
