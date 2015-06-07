package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.List;
import java.util.ListIterator;

public class CantidadDeVeganos implements Monitor {

	private Integer contadorDeVeganos = 0;
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {
		
		ListIterator<Receta> r =  consulta.listIterator(); 
	
		while(r.hasNext()) {
			if(r.next().getDificultad() == "D" && usuarioConsultor.esVegano()) 
				this.contadorDeVeganos = this.contadorDeVeganos + 1;
		}
	}
	
}
