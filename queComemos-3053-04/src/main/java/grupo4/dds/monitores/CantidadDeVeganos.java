package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("veganos")
public class CantidadDeVeganos extends Monitor {

	private int contadorDeVeganos = 0;
	
	public void notificarConsulta(Usuario usuario, List<Receta> consulta, List<Filtro> parametros) {
		if(consulta.stream().anyMatch(Receta::esDificil) && usuario.esVegano()) 
			contadorDeVeganos++;
	}
	
	public int getContadorDeVeganos() {
		return contadorDeVeganos;
	}
	
}
