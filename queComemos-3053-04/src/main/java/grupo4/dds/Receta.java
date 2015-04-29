package grupo4.dds;

import java.util.Collection;

public class Receta extends RecetaDelSistema {

	private Usuario creador;
	
	public Receta(){};
	public Receta(Usuario elCreador){
		creador=elCreador;
	}

	public boolean puedeSerVistaOModificadaPor(Usuario unUsuario){
		return (unUsuario == creador);
	}
	
}
