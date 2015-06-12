package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import queComemos.entrega3.dominio.Dificultad;

public class CantidadDeVeganos implements Monitor {

	private Integer contadorDeVeganos = 0;
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {
		
		if(consulta.stream().anyMatch(r -> r.getDificultad() == Dificultad.DIFICIL) && usuarioConsultor.esVegano()) 
			this.contadorDeVeganos = this.contadorDeVeganos + 1;
	}
	
	public Integer getContadorDeVeganos() {
		return contadorDeVeganos;
	}
	
}
