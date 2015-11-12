package grupo4.dds.repositorios;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDeUsuarios extends Repositorio<Usuario> {

	private static final RepositorioDeUsuarios self = new RepositorioDeUsuarios();
	
	public static RepositorioDeUsuarios instance() {
		return self;
	}

	public RepositorioDeUsuarios() {
		elementType = Usuario.class;
	}
	
	@Override
	public Usuario get(Usuario usuario) {
		return listWithMatchingName(usuario).isEmpty() ? null : listWithMatchingName(usuario).get(0);
	}

	public List<Usuario> list(Usuario prototipo) {
		return listWithMatchingName(prototipo).stream().filter(u -> u.cumpleTodasLasCondicionesDe(prototipo)).
				collect(Collectors.toList());
	}
	
	private List<Usuario> listWithMatchingName(Usuario usuario) {
		String query = "from Usuario where nombre like '%" + usuario.getNombre() + "%'";
		return entityManager().createQuery(query, Usuario.class).getResultList();
	}
	
	private List<Usuario> listWithName(String usuario) {
		String query = "from Usuario where nombre like '%" + usuario + "%'";
		return entityManager().createQuery(query, Usuario.class).getResultList();
	}
	
	public Usuario getConNombre(String usuario) {
		return listWithName(usuario).isEmpty() ? null : listWithName(usuario).get(0);
	}

	public Usuario buscar(long id) {
		 return entityManager().find(Usuario.class, id);

	}

}
