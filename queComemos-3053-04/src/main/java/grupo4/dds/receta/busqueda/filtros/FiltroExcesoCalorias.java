package grupo4.dds.receta.busqueda.filtros;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class FiltroExcesoCalorias implements Filtro {

	@Override
	public boolean test(Usuario u, Receta r) {
		return r.getTotalCalorias() <= 500 && u.indiceDeMasaCorporal() <= 25;
	}
	
	public String getNombre(){
		return "ExcesoDeCalorias";
	}

}
