package grupo4.dds.repositorios;

import grupo4.dds.persistencia.Persistible;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public abstract class Repositorio<T extends Persistible> implements WithGlobalEntityManager {

	protected Class<T> elementType;
	
	public void add(T entity) {
		entityManager().persist(entity);
	}
	
	public void addAll(List<T> entities) {
		entities.forEach(e -> add(e));
	}

	public void remove(T entity) {
		Persistible entityARemover = entityManager().find(entity.getClass(), entity.getId());
		entityManager().remove(entityARemover);
	}

	public void update(T entity) {
		entityManager().merge(entity);
	}

	public T get(T entity) {
		return get(elementType, entity);
	}
	
	public List<T> list() {
		return list(elementType);
	}

	protected T get(Class<T> type, T entity) {
		return entityManager().find(type, entity.getId());
	}
	
	protected List<T> list(Class<T> type) {
		return entityManager().createQuery("from " + type.getSimpleName(), type).getResultList();
	}
	
}