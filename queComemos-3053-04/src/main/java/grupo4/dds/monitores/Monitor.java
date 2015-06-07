package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.List;

public interface Monitor {

	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor);

}
