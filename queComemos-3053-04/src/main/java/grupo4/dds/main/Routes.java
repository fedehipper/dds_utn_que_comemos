package grupo4.dds.main;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;




import java.util.Objects;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;




import grupo4.dds.controller.ConsultasController;

import grupo4.dds.controller.HomeController;
import grupo4.dds.controller.PerfilController;
import grupo4.dds.controller.RecetaController;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.BuilderUsuario;
import grupo4.dds.usuario.Usuario;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.post;
public class Routes {
	
	static public Usuario usuarioActual;
	
	public static void main(String[] args) {
		
		HomeController home = new HomeController();
		ConsultasController consultas = new ConsultasController();
		ConsultasController consultasConResultados = new ConsultasController();
		RecetaController receta = new RecetaController();
	    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
	    PerfilController perfil = new PerfilController();
	    

	    port(8086);
	    
	    staticFileLocation("/public");
	    
	    usuarioActual = RepositorioDeUsuarios.instance().get(BuilderUsuario.prototipo("Matias Martino"));
	  
	    get("/", home::listarRecetas, engine);
	    get("/index.html", (request, response) -> {
	      response.redirect("/");
	      return null;
	    });
 
		get("/perfil/:id", perfil::mostrar, engine);
		get("/perfil", (request, response) -> {
				response.redirect("/perfil/"+ Objects.toString(usuarioActual.getId()));
				return null;
		});
	    //get("/consultas", consultas::mostrar,engine);
	    get("/consultas", consultasConResultados::listar,engine);
	    get("/consultas/buscar", consultas::mostrar, engine);
	    get("/receta/:id", receta::mostrar,engine);
	   // get("/editar/:id", edicion::mostrar,engine);
	    get("/editar/:id", receta::nuevo,engine);
	    
	    after((rq, rs) -> {
	    	PerThreadEntityManagers.getEntityManager();
	    	PerThreadEntityManagers.closeEntityManager();
	    });
	}
}
