package grupo4.dds;

import grupo4.dds.receta.RepositorioDeRecetas;

import org.junit.After;
import org.junit.Before;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public abstract class BaseTest implements WithGlobalEntityManager {

	@Before
	public void baseSetUp() {
		entityManager().getTransaction().begin();
	}
	
	@After
	public void baseTierDown() {
		entityManager().getTransaction().rollback();
		RepositorioDeRecetas.get().vaciar();
	}
	
}
