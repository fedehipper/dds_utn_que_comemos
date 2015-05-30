package grupo4.dds.filtrosYProcesos;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LeGustaAlUsuario implements Filtro {
	
	public void filtrar(Usuario usuario, RepositorioDeRecetas repoRecetas) {
		
		List<Receta> filtroDeReceta = new ArrayList<>();
		
		filtroDeReceta = repoRecetas.listarRecetasPara(usuario).stream().filter(r -> r.leGustanLosIngredientesAl(usuario)).collect(Collectors.toList());
		 
		repoRecetas.actualizarConsultaDeRecetas(filtroDeReceta);
	}

}