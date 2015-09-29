package grupo4.dds.repositorios;

import grupo4.dds.usuario.GrupoUsuarios;

import java.util.List;

public class RepositorioDeGrupos extends Repositorio {

private static final RepositorioDeGrupos self = new RepositorioDeGrupos();
	
	public static RepositorioDeGrupos get() {
		return self;
	}

	public GrupoUsuarios get(GrupoUsuarios grupo) {
		return entityManager().find(GrupoUsuarios.class, grupo.getId());
	}

	public List<GrupoUsuarios> list() {
		return entityManager().createQuery("from GrupoUsuarios", GrupoUsuarios.class).getResultList();
	}
	
}
