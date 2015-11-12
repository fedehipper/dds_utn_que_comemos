package grupo4.dds.controller;

import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Condicion;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;
import grupo4.dds.receta.Ingrediente;

import java.util.HashMap;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PerfilController {

	 public ModelAndView mostrar(Request request, Response response) {
			
		 	String usuario=request.params("usuario");
		 	
		 	Usuario user=RepositorioDeUsuarios.instance().getConNombre(usuario);
		 	
		 	HashMap<String, Object> viewModel = new HashMap<>();
		 	
		 	//TODO cuando persista bien borrar estos tres, sus import y el else del if
		 	List<Ingrediente> gusta=new ArrayList<>();
		 	List<Ingrediente> disgusta=new ArrayList<>();
		 	List<Condicion> condiciones=new ArrayList<>();		 	
		 	gusta.add(Ingrediente.nuevaComida("carne"));
		 	gusta.add(Ingrediente.nuevaComida("arroz"));
		 	disgusta.add(Ingrediente.nuevaComida("sushi"));
		 	condiciones.add(Vegano.instance());
		 	condiciones.add(Hipertenso.instance());
		 	//
		 	
		 	if (!Objects.isNull(user)){
			    viewModel.put("nombre", user.getNombre());
			    viewModel.put("sexo", user.getSexo());
			    viewModel.put("nacimiento", user.getFechaNacimiento().toString());
			    viewModel.put("altura", user.getAltura());
			    viewModel.put("peso", user.getPeso());
			    viewModel.put("imc", user.indiceDeMasaCorporal());
			    viewModel.put("rutina", user.getRutina().toString());
			    viewModel.put("preferencias", user.getPreferenciasAlimenticias());
			    viewModel.put("disgusta", user.getComidasQueLeDisgustan());
			    viewModel.put("condiciones", user.getCondiciones());
			}
		 	else{
			 	viewModel.put("nombre","ariel");
			 	viewModel.put("altura",1.73);	
			 	viewModel.put("sexo","V");
			 	viewModel.put("nacimiento","24-9-1992");
			 	viewModel.put("peso",100);
			 	viewModel.put("imc",(100/(1.73*1.73)));
			 	viewModel.put("rutina","No Hace Un Carajo");
			    viewModel.put("preferencias", gusta );
			    viewModel.put("disgusta", disgusta);
			    viewModel.put("condiciones", condiciones);   
		 	}
		    
		    return new ModelAndView(viewModel, "perfil.hbs");
	 }
	
}
