package grupo4.dds.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import grupo4.dds.monitores.CantidadDeHoras;
import grupo4.dds.monitores.CantidadDeVeganos;
import grupo4.dds.receta.EncabezadoDeReceta;
import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RecetaPublica;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.filtros.FiltroExcesoCalorias;
import grupo4.dds.receta.busqueda.filtros.FiltroNoEsAdecuada;
import grupo4.dds.receta.busqueda.filtros.FiltroNoLeGusta;
import grupo4.dds.receta.busqueda.filtros.FiltroRecetasCaras;
import grupo4.dds.receta.busqueda.postProcesamiento.Ordenar;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Vegano;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;

public class Algo {

	
	private static Usuario fecheSena;
	private static Usuario arielFolino;
	private static Usuario matiasMartino;
	private static Usuario federicoHipper;
	
	private CantidadDeHoras cantidadHoras = new CantidadDeHoras();
	private CantidadDeVeganos cantidadVeganos = new CantidadDeVeganos();
	private static List<Receta> expected;
	private static List<Filtro> filtros;
	private static RepositorioDeRecetas repositorio = RepositorioDeRecetas.get();
	
	private static Receta receta1;
	private static Receta receta2;
	private static Receta receta3;
	private static Receta receta4;
	private static Receta receta5;
	private static RecetaPublica receta6;
	private static RecetaPublica receta7;
	private static RecetaPublica receta8;	

	
	public static void main(String[] args) {
		
		expected = null;
		filtros = new ArrayList<>();
		repositorio.vaciar();
		
		fecheSena = Usuario.crearPerfil("Feche Sena", null, null, 1.70f, 65.0f, null, false);
		arielFolino = Usuario.crearPerfil("Ariel Folino", null, null, 1.69f, 96.0f, null, false);
		matiasMartino = Usuario.crearPerfil("Matías Martino", null, null, 1.74f, 79.0f, null, false);
		federicoHipper = Usuario.crearPerfil("Federico Hipperdinger", null, null, 1.91f, 99.0f, null, false);
		
		GrupoUsuarios grupo1 = new GrupoUsuarios("grupo1");
		GrupoUsuarios grupo2 = new GrupoUsuarios("grupo2");
				
		fecheSena.agregarGrupo(grupo1);
		federicoHipper.agregarGrupo(grupo2);
		arielFolino.agregarGrupo(grupo1);
		matiasMartino.agregarGrupo(grupo2);
		
		fecheSena.agregarCondicion(new Vegano());
		fecheSena.agregarComidaQueLeDisgusta(new Ingrediente("coliflor"));
		
		receta1 = Receta.crearNueva(fecheSena, new EncabezadoDeReceta("receta1", null, Dificultad.DIFICIL, 999), null);
		receta2 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta2", null, Dificultad.DIFICIL, 300), null);
		receta3 = Receta.crearNueva(federicoHipper, new EncabezadoDeReceta("receta3", null, null, 600), null);
		receta4 = Receta.crearNueva(arielFolino, new EncabezadoDeReceta("receta4", null, null, 100), null);
		receta5 = Receta.crearNueva(matiasMartino, new EncabezadoDeReceta("receta5", null, null, 499), null);
		receta6 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta6", null, null, 200), null);
		receta7 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta7", null, null, 300), null);
		receta8 = RecetaPublica.crearNueva(new EncabezadoDeReceta("receta8", null, null, 100), null);
		
		receta1.agregarIngrediente(new Ingrediente(""));
		receta2.agregarIngrediente(new Ingrediente(""));
		receta3.agregarIngrediente(new Ingrediente(""));
		receta4.agregarIngrediente(new Ingrediente(""));
		receta5.agregarIngrediente(new Ingrediente(""));
		receta6.agregarIngrediente(new Ingrediente("carne"));
		receta7.agregarIngrediente(new Ingrediente("lomo"));
		receta8.agregarIngrediente(new Ingrediente("coliflor"));

	

		expected = Arrays.asList(receta1, receta4, receta6, receta7, receta8);
		
		RepositorioDeRecetas.get().listarRecetasPara(arielFolino, null, null);
		System.out.println(arielFolino.getHistorial());
		System.out.println(expected);
		
	}
	
}