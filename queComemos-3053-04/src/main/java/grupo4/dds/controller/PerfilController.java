package grupo4.dds.controller;

import grupo4.dds.main.Routes;
import grupo4.dds.receta.Receta;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.Usuario;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PerfilController {

	 public ModelAndView mostrar(Request request, Response response) {
			
		 	Long usuario=Long.parseLong(request.params("id"));
		 	
		 	DecimalFormat df = new DecimalFormat("0.00"); 
		 	
		 	Usuario user;
		 	List<Receta> recetasFavoritas;
		 	HashMap<String, Object> viewModel = new HashMap<>();
		 	
		 	if (Objects.isNull(usuario)){
		 		long id =Routes.usuarioActual.getId();
		 		user = RepositorioDeUsuarios.instance().buscar(id);
		 	}
		 	else{
		 		user=RepositorioDeUsuarios.instance().buscar(usuario);
		 	}
		 	
		 	viewModel.put("nombre", user.getNombre());
	 	
		 	if (!Objects.isNull(user.getSexo())){
		    	viewModel.put("sexo", user.getSexo().toString().substring(0, 1));
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
		    	viewModel.put("imc", df.format(user.indiceDeMasaCorporal() ) );
		    }
			if (!Objects.isNull(user.getRutina())){
		    	viewModel.put("rutina", mayusPrimera(user.getRutina().toString().replace('_', ' ').toLowerCase()));
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
		    if(!Objects.isNull(user.getFavoritas()))
		    	viewModel.put("fav",user.getFavoritas());
		    	else
		    	viewModel.put("fav", "Aún no tiene recetas favoritas");
		    
		   if(!Objects.isNull(user.getHistorial())){
			   recetasFavoritas = user.getHistorial();
			   viewModel.put("recetasFav",user.getHistorial());
			   
		   }
		   
		    String salud="Normal";
		    
		    if (user.indiceDeMasaCorporal()>30){
		    	salud="Su estado de salud es CRITICO!!!!!";
		    }
		    else{if(user.indiceDeMasaCorporal()<18){
		    	salud="Su IMC es menor de lo esperado normalmente";
		    }
		    }
		    
		    viewModel.put("salud", salud);
		    
		    return new ModelAndView(viewModel, "perfil.hbs");
	 }
	 
	public String mayusPrimera(String cadena){
		String mayuscula=cadena.charAt(0)+""; 
		mayuscula=mayuscula.toUpperCase();
		cadena=cadena.replaceFirst 
		(cadena.charAt(0)+"", mayuscula); 
		return cadena;
		}
	
}
