package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;


public class RecetasMasConsultadasPorSexo implements Monitor {
	
	private HashMap<String, Integer> contadorDeRecetasM = new HashMap<String, Integer>();
	private HashMap<String, Integer> contadorDeRecetasF = new HashMap<String, Integer>();
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuarioConsultor) {

		if (usuarioConsultor.getSexo() == Sexo.MASCULINO) 
			this.contar(contadorDeRecetasM, consulta);
		else
			this.contar(contadorDeRecetasF, consulta);						
	}
	

	// TODO : falta obtener la receta mas consultada por los hombres y la receta mas consultada por las mujeres
	
	
	public void contar(HashMap<String, Integer> contadorDeRecetas, List<Receta> consulta) {
		
		ListIterator<Receta> r =  consulta.listIterator();
		while(r.hasNext()) {
			if (contadorDeRecetas.containsKey(r.next().getNombreDelPlato())) 
				contadorDeRecetas.replace(r.next().getNombreDelPlato(), contadorDeRecetas.hashCode() + 1);
			else
				contadorDeRecetas.put(r.next().getNombreDelPlato(), 1);
		}
	}
	
}
