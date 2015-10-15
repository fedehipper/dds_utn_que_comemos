package grupo4.dds.receta.busqueda.filtros;

import grupo4.dds.misc.CoberturaIgnore;
import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public class FiltroNoEsAdecuada implements Filtro {

	public boolean test(Usuario u, Receta r) {
		return u.esAdecuada(r);
	}
	
	@Override
	@CoberturaIgnore
	public String toString() {
		return "Inadecuada";
	}
}
