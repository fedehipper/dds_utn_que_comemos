package grupo4.dds.controller;

import grupo4.dds.main.Routes;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.Objects;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PerfilController {

	 public ModelAndView mostrar(Request request, Response response) {
			
		 	Long usuario=Long.parseLong(request.params("id"));
		 	
		 	Usuario user;
		 	
		 	HashMap<String, Object> viewModel = new HashMap<>();
		 	
		 	if (Objects.isNull(usuario)){
		 		user=Routes.usuarioActual;
		 	}
		 	else{
		 		user=RepositorioDeUsuarios.instance().buscar(usuario);
		 	}
		 	
		 	viewModel.put("nombre", user.getNombre());
		 	 	
		 	if (!Objects.isNull(user.getSexo())){
		    	viewModel.put("sexo", user.getSexo());
	 		}
		    
		    if (!Objects.isNull(user.getFechaNacimiento())){
		    	viewModel.put("nacimiento", user.getFechaNacimiento().toString());
	 		}
		    if (!Objects.isNull(user.getAltura())){
		    	viewModel.put("altura", user.getAltura());
	 		}
		    if (!Objects.isNull(user.getPeso())){
		    	viewModel.put("peso", user.getPeso());
		    }
		    if (!Objects.isNull(user.indiceDeMasaCorporal())){
		    	viewModel.put("imc", user.indiceDeMasaCorporal());
		    }
			if (!Objects.isNull(user.getRutina())){
		    	viewModel.put("rutina", user.getRutina().toString());
			}
		    if (!Objects.isNull(user.getPreferenciasAlimenticias())){
		    	viewModel.put("preferencias", user.getPreferenciasAlimenticias());
		    }
		    if(!Objects.isNull(user.getComidasQueLeDisgustan())){
			    viewModel.put("disgusta", user.getComidasQueLeDisgustan());
			}
		    if(!Objects.isNull(user.getCondiciones())){
		        viewModel.put("condiciones", user.getCondiciones());
		    }
		    if(!Objects.isNull(user.getFavoritas())){
		    	viewModel.put("fav",user.getFavoritas());
		    }
		    
		    return new ModelAndView(viewModel, "perfil.hbs");
	 }
	
}
