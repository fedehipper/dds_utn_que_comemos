package grupo4.dds;

import java.util.ArrayList;
import java.util.Collection;

public class RecetaDelSistema {
	
	private String nombreDelPlato;
	private Collection<String> listaIngredientes = new ArrayList<>();
	private Collection<String> listaCondimentos = new ArrayList<>();
	private String preparacionDeReceta; 
	private int calorias;
	private String dificultad;
	private Collection<Temporada> temporada = new ArrayList<>();
	private RecetaDelSistema subReceta;
	
	

}
