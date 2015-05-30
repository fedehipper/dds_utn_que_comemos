package grupo4.dds.usuario;


import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;

import java.util.ArrayList;
import java.util.List;

public class GrupoUsuarios {
	
	private String nombre;
	private List<Usuario> usuarios = new ArrayList<>();
	private List<Ingrediente> preferenciasAlimenticias = new ArrayList<>();
	
	
	/* Constructores */
	
	public GrupoUsuarios(String nombre) {
		this.nombre = nombre;
	}
	
	/* Servicios */
	
	public boolean puedeSugerirse(Receta receta) {
		return receta.contieneAlguna(preferenciasAlimenticias) && esAdecuadaParaMiembros(receta);
	}

	public boolean puedeVer(Receta receta) {
		return usuarios.stream().anyMatch(u -> receta.puedeSerVistaPor(u));
	}

	public boolean esMiembro(Usuario usuario) {
		return usuarios.contains(usuario);
	}
	
	/* Servicios privados */
	
	private boolean esAdecuadaParaMiembros(Receta receta) {
		return usuarios.stream().allMatch(u -> u.esAdecuada(receta));
	}
	
	/* Accesors and Mutators */
	
	public void agregarUsuario(Usuario usuario) {
		//TODO: Validar que el usuario no exista, o usar un Collection Set
		usuarios.add(usuario);
	}
	
	public void agregarPreferenciaAlimenticia(Ingrediente comida) {
		preferenciasAlimenticias.add(comida);
	}
	
	public List<Ingrediente> getPreferenciasAlimenticias() {
		return preferenciasAlimenticias;
	}
	
	public String getNombre() {
		return nombre;
	}
	
}
