package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.util.List;

public interface Monitor {

	public void notificarConsulta(Usuario usuario, List<Receta> resultadoConsulta, List<Filtro> parametros);

}
