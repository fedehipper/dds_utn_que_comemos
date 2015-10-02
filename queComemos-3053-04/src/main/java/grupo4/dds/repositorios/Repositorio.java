package grupo4.dds.repositorios;

import grupo4.dds.persistencia.Persistible;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public abstract class Repositorio implements WithGlobalEntityManager {

	public void add(Persistible entity) {
		entityManager().persist(entity);
	}

	public void remove(Persistible entity) {
		Persistible entityARemover = entityManager().find(entity.getClass(), entity.getId());
		entityManager().remove(entityARemover);
	}

	public void update(Persistible entity) {
		entityManager().merge(entity);
	}

}