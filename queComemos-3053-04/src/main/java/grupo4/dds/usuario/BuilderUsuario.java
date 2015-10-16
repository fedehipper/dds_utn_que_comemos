package grupo4.dds.usuario;

import grupo4.dds.receta.Ingrediente;
import grupo4.dds.repositorios.RepositorioDeSolicitudes;
import grupo4.dds.usuario.condicion.Condicion;

import java.time.LocalDate;
import java.util.List;

public class BuilderUsuario {

	private Usuario usuario = new Usuario();
	
	public static Usuario prototipo(String nombre, List<Condicion> condiciones) {
		
		Usuario prototipo = new Usuario();
		
		prototipo.nombre = nombre;
		
		if(condiciones != null) 
			prototipo.condiciones = condiciones;
		
		return prototipo;
	}
	
	public static Usuario prototipo(String nombre) {
		return prototipo(nombre, null);
	}
	
	public BuilderUsuario nombre(String nombre) {
		usuario.nombre = nombre;
		return this;
	}
		
	public BuilderUsuario rutina(Rutina rutina) {
		usuario.rutina = rutina;
		return this;
	}
	
	public BuilderUsuario altura(float altura) {
		usuario.altura = altura;
		return this;
	}
		
	public BuilderUsuario peso(float peso) {
		usuario.peso = peso;
		return this;
	}
		
	public BuilderUsuario masculino() {
		usuario.sexo = Sexo.MASCULINO;
		return this;
	}
	
	public BuilderUsuario femenino() {
		usuario.sexo = Sexo.FEMENINO;
		return this;
	}
		
	public BuilderUsuario mail(String mail) {
		usuario.mail = mail;
		return this;
	}

	public BuilderUsuario nacimiento(LocalDate fechaNacimiento) {
		usuario.fechaNacimiento = fechaNacimiento;
		return this;
	}
	
	public BuilderUsuario condicion(Condicion condicion) {
		usuario.agregarCondicion(condicion);
		return this;
	}

	public BuilderUsuario leGusta(Ingrediente alimento) {
		usuario.agregarPreferenciaAlimenticia(alimento);
		return this;
	}

	public BuilderUsuario leDisgusta(Ingrediente alimento) {
		usuario.agregarComidaQueLeDisgusta(alimento);
		return this;
	}

	public BuilderUsuario grupo(GrupoUsuarios grupo) {
		usuario.agregarGrupo(grupo);
		return this;
	}
	
	public BuilderUsuario condiciones(Condicion condicion) {
		usuario.agregarCondicion(condicion);
		return this;
	}
	
	public BuilderUsuario leGustan(Ingrediente alimento) {
		usuario.agregarPreferenciaAlimenticia(alimento);
		return this;
	}
	
	public BuilderUsuario leDisgustan(Ingrediente alimento) {
		usuario.agregarComidaQueLeDisgusta(alimento);
		return this;
	}
	
	public BuilderUsuario grupos(GrupoUsuarios grupo) {
		usuario.agregarGrupo(grupo);
		return this;
	}
	
	public Usuario build() {
		RepositorioDeSolicitudes.instance().solicitarIncorporación(usuario);
		
		return usuario;
	}
	
}
