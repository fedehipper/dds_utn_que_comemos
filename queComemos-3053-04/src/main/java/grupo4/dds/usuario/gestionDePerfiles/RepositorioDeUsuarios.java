package grupo4.dds.usuario.gestionDePerfiles;

import grupo4.dds.monitores.Monitor;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.RepoUsuarios;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RepositorioDeUsuarios implements RepoUsuarios {

	private static final RepositorioDeUsuarios self = new RepositorioDeUsuarios();
	private Map<String, Usuario> usuarios = new HashMap<>();
	private Set<Monitor> monitores = new HashSet<>();
	
	
	public static RepositorioDeUsuarios get() {
		return self;
	}

	private RepositorioDeUsuarios() {}
	
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

	public void vaciar() {
		usuarios.clear();
	}
	
	// punto 3) observer
	public void registrarConsulta(RepositorioDeRecetas repoRecetas) {
		this.monitores.forEach(monitor -> repoRecetas.notificar(monitor));
	}
	
	/* Seters AND Getters */
	
	public void setMonitor(Monitor monitor) {
		this.monitores.add(monitor);
	}
	
	
	
}
