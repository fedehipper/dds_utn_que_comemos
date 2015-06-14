package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class CantidadDeHoras implements Monitor {

	private HashMap<Integer, Integer> horasDelDia = new HashMap<>();
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {
		
		int ahora = new Integer(LocalTime.now().getHour());
		if (horasDelDia.get(ahora) == null)
			horasDelDia.put(ahora, 1);
		else
			horasDelDia.put(ahora, horasDelDia.get(ahora) + 1);
	}
	
	public int cantidadDeConsultasPor(int unaHora) {
		return horasDelDia.get(unaHora);
	}
	
}
