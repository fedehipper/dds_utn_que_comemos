package grupo4.dds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import grupo4.dds.persistor.MongoPersistor;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.gestionDePerfiles.RepositorioDeUsuarios;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import queComemos.entrega3.dominio.Dificultad;

public class TestPersistencia {

	private Usuario maria;
	private Usuario ariel;
	private Datastore ds = MongoPersistor.get().dataStore();

	@Before
	public void setUp() throws Exception {

		ds.getCollection(Usuario.class).drop();
		ds.getCollection(Receta.class).drop();
		ds.getCollection(RecetaPublica.class).drop();
		RepositorioDeRecetas.get().vaciar();
		RepositorioDeUsuarios.get().vaciar();
		
		maria = Usuario.crearPerfil("Maria", Sexo.FEMENINO, null,
				1.70f, 65.0f, null, false, null);
		ariel = Usuario.crearPerfil("Ariel", Sexo.MASCULINO, null,
				0f, 0f, null, false, null);		
	}

	@Test
	public void testPersistirUsuarios() {

		ds.save(maria);
		ds.save(ariel);

		assertListEquals(Arrays.asList(maria, ariel), ds.find(Usuario.class)
				.asList());
	}

	@Test
	public void testAlCrearUnaRecetaEsPersistida() {

		Receta sopa = (new BuilderReceta()).setTotalCalorias(150)
				.setIngrediente(null).setCreador(ariel)
				.setNombreDelPlato("Sopa").setDificultad(Dificultad.FACIL)
				.build();
		Receta pollo = (new BuilderReceta()).setTotalCalorias(150)
				.setIngrediente(null).setCreador(maria)
				.setNombreDelPlato("Pollo").setDificultad(Dificultad.FACIL)
				.build();

		assertListEquals(Arrays.asList(sopa, pollo), ds.find(Receta.class)
				.asList());
	}
	
	@Test
	public void testAlCrearUnaRecetaSePersistenSusDetalles() {

		Receta sopa = (new BuilderReceta()).setTotalCalorias(150)
				.setIngrediente(null).setCreador(ariel)
				.setNombreDelPlato("Sopa").setDificultad(Dificultad.FACIL)
				.build();

		Receta sopaPersistida = ds.find(Receta.class).asList().get(0);
		
		assertEquals(sopa.getNombreDelPlato(), sopaPersistida.getNombreDelPlato());
		assertEquals(sopa.getCreador(), sopaPersistida.getCreador());
		assertEquals(sopa.getPreparacion(), sopaPersistida.getPreparacion());
		assertEquals(sopa.getIngredientes(), sopaPersistida.getIngredientes());
	}

	@Test
	public void testAlCrearUnaRecetaSeGeneraLaRelacionConSuCreador() {

		Receta receta = (new BuilderReceta()).setTotalCalorias(1500)
				.setIngrediente(new Ingrediente()).setCreador(ariel).build();

		assertEquals(ariel, ds.get(Receta.class, receta.getId()).getCreador());
	}

	@Test
	public void testLasRecetasMarcadasComoFavoritasSePersisten() {
		
		Receta receta1 = Receta.crearNueva(ariel, new EncabezadoDeReceta("receta1", null, Dificultad.DIFICIL, 999), null);
		Receta receta2 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta4", null, null, 600), null);
		
		ariel.marcarFavorita(receta1);
		ariel.marcarFavorita(receta2);
		
		assertEquals(ariel.getHistorial(), new HashSet<Receta>(Arrays.asList(receta1, receta2)));
		assertEquals(ariel.getHistorial(), ds.get(Usuario.class, ariel.getId()).getHistorial());
	}
	
	@Test
	public void testElHistorialDeRecetasFavoritasSePersiste() {
		
		Receta receta1 = Receta.crearNueva(ariel, new EncabezadoDeReceta("receta1", null, Dificultad.DIFICIL, 999), null);
		Receta receta2 = Receta.crearNueva(ariel, new EncabezadoDeReceta("receta2", null, Dificultad.DIFICIL, 300), null);
		Receta receta4 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta4", null, null, 600), null);
		
		ariel.setMarcaFavorita(false);
		ariel.recetasQuePuedeVer(RepositorioDeRecetas.get()).forEach(r -> ds.save(r));
		
		ds.save(Receta.crearNueva(maria, new EncabezadoDeReceta("receta3", null, null, 600), null));
		
		ariel.setMarcaFavorita(true);
		ariel.recetasQuePuedeVer(RepositorioDeRecetas.get());
		ariel.ejecutarMarcadoPendiente();
		
		assertEquals(ariel.getHistorial(), new HashSet<Receta>(Arrays.asList(receta1, receta2, receta4)));
		assertEquals(ariel.getHistorial(), ds.get(Usuario.class, ariel.getId()).getHistorial());
	}	
	
	@Test
	public void testElRepositorioDeRecetasSeInicializaConLasRecetasDeLaBase() {
		
		Set<Receta> recetasExternas = RepositorioDeRecetas.get().todasLasRecetas();
		Set<Receta> todasLasRecetas = new HashSet<Receta>(recetasExternas);
		
		Receta receta1 = Receta.crearNueva(ariel, new EncabezadoDeReceta("receta1", null, Dificultad.DIFICIL, 999), null);
		Receta receta2 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta2", null, null, 600), null);
		
		todasLasRecetas.add(receta1);
		todasLasRecetas.add(receta2);
		
		ds.save(receta1);
		ds.save(receta2);
		
		RepositorioDeRecetas.get().vaciar();
		assertEquals(RepositorioDeRecetas.get().todasLasRecetas(), recetasExternas);
		
		RepositorioDeRecetas.get().cargarRecetas();
		assertEquals(RepositorioDeRecetas.get().todasLasRecetas(), todasLasRecetas);
	}
	
	@Test
	public void testElRepositorioDeUsuariosSeInicializaConLousuariosDeLaBase() {
		
		RepositorioDeUsuarios.get().list(Usuario.crearPerfil(""));		
		assertTrue(RepositorioDeUsuarios.get().list(Usuario.crearPerfil("")).isEmpty());
		
		System.out.println(RepositorioDeUsuarios.get().list(Usuario.crearPerfil("")));
		
		ds.save(maria);
		ds.save(ariel);
		
		RepositorioDeUsuarios.get().cargarUsuarios();
		assertListEquals(Arrays.asList(maria, ariel), RepositorioDeUsuarios.get().list(Usuario.crearPerfil("")));
	}
	
	private <E> void assertListEquals(List<E> l1, List<E> l2) {
		assertTrue(l1.containsAll(l2) && l2.containsAll(l1));
	}
}
