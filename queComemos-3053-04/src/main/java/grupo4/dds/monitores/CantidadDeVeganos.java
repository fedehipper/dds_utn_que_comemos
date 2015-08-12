package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.List;

public class CantidadDeVeganos implements Monitor {

	private int contadorDeVeganos = 0;
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuario) {
		if(consulta.stream().anyMatch(Receta::esDificil) && usuario.esVegano()) 
			contadorDeVeganos++;
	}
	
	public int getContadorDeVeganos() {
		return contadorDeVeganos;
	}
	
}
