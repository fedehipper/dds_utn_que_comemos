package grupo4.dds.receta.busqueda.filtros;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

public interface Filtro {

    boolean test(Usuario usuario, Receta receta);

}
