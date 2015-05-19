package grupo4.dds.usuario;

import grupo4.dds.excepciones.NoSePuedeSugerirRecetaAlGrupo;
import grupo4.dds.receta.Receta;

import java.util.List;

public class Grupo {
	
	private List<Usuario> usuarios;
	private String nombreDelClub;
	private List<Ingrediente> palabrasClave;
	
	
	// testear, 
	public void sugerirReceta(Receta unaReceta) {
		if(!((unaReceta.compartenPalabrasClave(palabrasClave) & (usuarios.stream().allMatch(u -> u.esAdecuada(unaReceta))))))
				throw new NoSePuedeSugerirRecetaAlGrupo();
	}

	
	
	
	
	
}
