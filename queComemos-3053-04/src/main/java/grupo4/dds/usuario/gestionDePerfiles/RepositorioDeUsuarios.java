package grupo4.dds.usuario.gestionDePerfiles;

import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioDeUsuarios implements RepoUsuarios, WithGlobalEntityManager {

	private static final RepositorioDeUsuarios self = new RepositorioDeUsuarios();
	
	public static RepositorioDeUsuarios get() {
		return self;
	}

	private RepositorioDeUsuarios() {}
	
	@Override
	public void add(Usuario usuario) {
		entityManager().persist(usuario);
	}

	@Override
	public void remove(Usuario usuario) {
		Usuario usuarioARemover = entityManager().find(Usuario.class, usuario.getId());
		entityManager().remove(usuarioARemover);
	}

	@Override
	public void update(Usuario usuario) {
		entityManager().persist(usuario);
	}

	@Override
	public Usuario get(Usuario usuario) {
		return listWithMatchingName(usuario).isEmpty() ? null : listWithMatchingName(usuario).get(0);
	}

	@Override
	public List<Usuario> list(Usuario prototipo) {
		return listWithMatchingName(prototipo).stream().filter(u -> u.cumpleTodasLasCondicionesDe(prototipo)).
				collect(Collectors.toList());
	}

	private List<Usuario> listWithMatchingName(Usuario usuario) {
		String query = "from Usuario where nombre like '%" + usuario.getNombre() + "%'";
		return entityManager().createQuery(query, Usuario.class).getResultList();
	}

}
