package grupo4.dds.main;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import grupo4.dds.controller.ConsultasController;
import grupo4.dds.controller.HomeController;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {
	
	public static void main(String[] args) {
		
		HomeController home = new HomeController();
		ConsultasController consultas = new ConsultasController();
	    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

	    port(8086);
	    
	    staticFileLocation("/public");
	    
	    get("/", home::listarRecetas, engine);
	    get("/index.html", (request, response) -> {
	      response.redirect("/");
	      return null;
	    });
	    
	    get("/consultas", consultas::listar,engine);
	    
	    after((rq, rs) -> {
	    	PerThreadEntityManagers.getEntityManager();
	    	PerThreadEntityManagers.closeEntityManager();
	    });
	}
}
