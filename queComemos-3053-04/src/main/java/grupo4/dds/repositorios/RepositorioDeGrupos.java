package grupo4.dds.repositorios;

import grupo4.dds.usuario.GrupoUsuarios;

public class RepositorioDeGrupos extends Repositorio<GrupoUsuarios> {

private static final RepositorioDeGrupos self = new RepositorioDeGrupos();
	
	public static RepositorioDeGrupos instance() {
		return self;
	}
	
	private RepositorioDeGrupos() {
		elementType = GrupoUsuarios.class;
	}
}
