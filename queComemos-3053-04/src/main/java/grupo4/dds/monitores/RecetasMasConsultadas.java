package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class RecetasMasConsultadas implements Monitor {

	private HashMap<String, Integer> contadorDeRecetas = new HashMap<String, Integer>();
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {

			ListIterator<Receta> r =  consulta.listIterator(); 
				
			while(r.hasNext()) {
				if (contadorDeRecetas.containsKey(r.next().getNombreDelPlato())) 
					contadorDeRecetas.replace(r.next().getNombreDelPlato(), contadorDeRecetas.hashCode() + 1);
				else
					contadorDeRecetas.put(r.next().getNombreDelPlato(), 1);
			}
				
	}

		
}
