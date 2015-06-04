package grupo4.dds.usuario;

import java.util.List;

public interface RepoUsuarios {

	public abstract void add(Usuario usuario);
	public abstract void remove(Usuario usuario);
	public abstract void update(Usuario usuario);
	public abstract Usuario get(Usuario usuario);
	public abstract List<Usuario> list(Usuario usuario);
	
}
