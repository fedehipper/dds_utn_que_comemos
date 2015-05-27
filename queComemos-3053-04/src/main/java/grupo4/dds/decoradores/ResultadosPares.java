package grupo4.dds.decoradores;

import grupo4.dds.receta.Receta;
import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.receta.Repositorio;

import java.util.ArrayList;
import java.util.List;

public class ResultadosPares extends ProcesamientoPosterior {
	
	public ResultadosPares(RepositorioDeRecetas repo) {
		repositorio = repo;
	}
	
	public ResultadosPares(Repositorio repo) {
		repositorio = repo;
	}
	
	public List<Receta> listarRecetasParaUnUsuario(Usuario unUsuario) {		
	 	  return pares(this.repositorio.listarRecetasParaUnUsuario(unUsuario));
	}
	
	public List<Receta> pares(List<Receta> unaLista) {
		int cont = 1;
		List<Receta> resultado = new ArrayList<>();
		unaLista.get(cont);
		
		while(cont < unaLista.size() ){
			resultado.add(unaLista.get(cont));
			cont = cont + 2;
		}
		return resultado;	
	}
	
}


 