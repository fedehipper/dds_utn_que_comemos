package grupo4.dds.usuario.gestionDePerfiles;

import grupo4.dds.persistor.MongoPersistor;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RepositorioDeUsuarios implements RepoUsuarios {

	private static final RepositorioDeUsuarios self = new RepositorioDeUsuarios();
	private Map<String, Usuario> usuarios = new HashMap<>();
	
	
	public static RepositorioDeUsuarios get() {
		return self;
	}

	private RepositorioDeUsuarios() {

	}
	
//TODO: consultar si solo el administrador puede interactuar con el repo	
	@Override
	public void add(Usuario usuario) {
		usuarios.put(usuario.getNombre(), usuario);
	}

	@Override
	public void remove(Usuario usuario) {
		usuarios.remove(usuario.getNombre());
	}

	@Override
	public void update(Usuario usuario) {
		add(usuario);
	}

	@Override
	public Usuario get(Usuario usuario) {
		return usuarios.get(usuario.getNombre());
	}

	@Override
	public List<Usuario> list(Usuario usuario) {
		return usuarios.values()
				.stream()
				.filter(u -> u.getNombre().contains(usuario.getNombre()) && u.cumpleTodasLasCondicionesDe(usuario))
				.collect(Collectors.toList());
	}

	public void cargarUsuarios() {
		List<Usuario> usuariosAlmacenados = MongoPersistor.get().dataStore().find(Usuario.class).asList();
		usuariosAlmacenados.forEach(u -> usuarios.merge(u.getNombre(), u, (u1, u2) -> u2));
	}
	
	public void vaciar() {
		usuarios.clear();
	}
		
}
