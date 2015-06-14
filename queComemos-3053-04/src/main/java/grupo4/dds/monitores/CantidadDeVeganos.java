package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import queComemos.entrega3.dominio.Dificultad;

public class CantidadDeVeganos implements Monitor {

	private int contadorDeVeganos = 0;
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuario) {
		if(consulta.stream().anyMatch(r -> r.getDificultad() == Dificultad.DIFICIL) && usuario.esVegano()) 
			contadorDeVeganos++;
	}
	
	public int getContadorDeVeganos() {
		return contadorDeVeganos;
	}
	
}
