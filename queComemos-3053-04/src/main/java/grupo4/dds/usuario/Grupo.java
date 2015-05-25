package grupo4.dds.usuario;


import grupo4.dds.receta.Receta;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
	
	private List<Usuario> usuarios = new ArrayList<>();
	private String nombreDelClub;
	private List<Ingrediente> palabrasClave = new ArrayList<>();
	
	
	public boolean sugerirReceta(Receta unaReceta) {
		return (unaReceta.compartenPalabrasClave(palabrasClave) & (usuarios.stream().allMatch(u -> u.esAdecuada(unaReceta))));
	}

	public Grupo(String nombre) {
		setNombreDelClub(nombre);
	}
	
	public void agregarUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}
	
	public void agregarPalabrasClave(Ingrediente palabraClave) {
		palabrasClave.add(palabraClave);
	}
	
	// punto 2,  el grupo pregunta si alguno es el dueño, no si la puede ver porqeu agarra por el preguntar a todos 
	// los grupos nuevamente
	public boolean puedenVerLaReceta(Receta receta) {
		return usuarios.stream().anyMatch(u -> u.esElDuenio(receta));
	}
	
	/* getters and setters*/
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	public List<Ingrediente> getPalabrasClave() {
		return palabrasClave;
	}
	
	public String getNombreDelClub() {
		return nombreDelClub;
	}

	public void setNombreDelClub(String nombreDelClub) {
		this.nombreDelClub = nombreDelClub;
	}

}
	
	
	