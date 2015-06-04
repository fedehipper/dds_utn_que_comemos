package grupo4.dds.usuario;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class RepoUsuarios {
	
	private Set<Usuario> usuarios = new HashSet<>();
	
	public void add(Usuario usuario) {
		this.usuarios.add(usuario);
	}
	
	public void remove(Usuario usuario) {
		this.usuarios.remove(usuario);
	}
	
	public void update(Usuario usuario) {
		this.remove(usuario);
		this.add(usuario);
	}
	
	public Stream<Usuario> list(Usuario usuario) {
		return this.usuarios.stream().filter(u -> u.getNombre() == usuario.getNombre() && u.getCondiciones().containsAll(usuario.getCondiciones()));
	}
	
	public Stream<Usuario> get(Usuario usuario) {
		return this.usuarios.stream().filter(u -> u.getNombre() == (usuario.getNombre()));
	}
	
}
