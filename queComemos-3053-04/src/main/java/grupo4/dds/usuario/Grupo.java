package grupo4.dds.usuario;

import grupo4.dds.excepciones.NoSePuedeSugerirRecetaAlGrupo;
import grupo4.dds.receta.Receta;

import java.util.List;

public class Grupo {
	
	private List<Usuario> usuarios;
	private String nombreDelClub;
	private List<Ingrediente> palabrasClave;
	
	
	// testear
	public boolean sugerirReceta(Receta unaReceta) {
		
		if(!((unaReceta.compartenPalabrasClave(palabrasClave) & (usuarios.stream().allMatch(u -> u.esAdecuada(unaReceta))))))
			throw new NoSePuedeSugerirRecetaAlGrupo();
		
		return (unaReceta.compartenPalabrasClave(palabrasClave) & (usuarios.stream().allMatch(u -> u.esAdecuada(unaReceta))));
	}

	// testear
	public Grupo(String nombre) {
		nombreDelClub = nombre;
	}
	
	// testear
	public void agregarUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}
	
	// testear
	public void agregarPalabrasClave(Ingrediente palabraClave) {
		palabrasClave.add(palabraClave);
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

}
	
	
	