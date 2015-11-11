package grupo4.dds.main;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import grupo4.dds.controller.HomeController;
import grupo4.dds.repositorios.RepositorioDeUsuarios;
import grupo4.dds.usuario.BuilderUsuario;
import grupo4.dds.usuario.Usuario;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {
	
	static public Usuario usuarioActual;
	
	public static void main(String[] args) {
		
		HomeController home = new HomeController();
	    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

	    port(8086);
	    
	    staticFileLocation("/public");
	    
	    usuarioActual = RepositorioDeUsuarios.instance().get(BuilderUsuario.prototipo("Matías Martino"));
	    
	    get("/", home::listarRecetas, engine);
	    get("/index.html", (request, response) -> {
	      response.redirect("/");
	      return null;
	    });
	    
	    after((rq, rs) -> {
	    	PerThreadEntityManagers.getEntityManager();
	    	PerThreadEntityManagers.closeEntityManager();
	    });
	}
}
