package grupo4.dds.repositorios;

import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDeUsuarios extends Repositorio {

	private static final RepositorioDeUsuarios self = new RepositorioDeUsuarios();
	
	public static RepositorioDeUsuarios get() {
		return self;
	}

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

}
