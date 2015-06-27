package grupo4.dds.command;

import grupo4.dds.usuario.Usuario;


public interface Command {
	
	public void ejecutar(Usuario usuario);

}
