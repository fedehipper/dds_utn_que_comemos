package grupo4.dds.receta.busqueda.filtros;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class FiltroNoEsAdecuada implements Filtro {

	@Override
	public boolean test(Usuario u, Receta r) {
		return u.esAdecuada(r);
	}
	
}
