package grupo4.dds.comparadores;


import java.util.Comparator;
import grupo4.dds.receta.Receta;

public class ComparaAlfabeticamente implements Comparator<Receta>{

	public int compare(Receta o1, Receta o2) {
		Receta r1 = (Receta) o1;
		Receta r2 = (Receta) o2;
		return r1.getNombreDelPlato().compareTo(r2.getNombreDelPlato());
	}

	
	
}
