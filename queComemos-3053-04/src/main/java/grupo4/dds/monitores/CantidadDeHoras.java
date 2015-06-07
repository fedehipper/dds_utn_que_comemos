package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class CantidadDeHoras implements Monitor {

	private HashMap<Integer, Integer> horasDelDia = new HashMap<Integer, Integer>();
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {
		this.horasDelDia.put(LocalTime.now().getHour(), horasDelDia.get(LocalTime.now().getHour()) + 1);
	}
	
	public Integer cantidadDeConsultasPor(int unaHora) {
		return horasDelDia.get(unaHora);
	}
	
}
