package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CantidadDeHoras implements Monitor {

	private List<Integer> horasDelDia = new ArrayList<>();
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {
		
		this.horasDelDia.add(LocalTime.now().getHour());
	}
	
}
