package grupo4.dds.main;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import grupo4.dds.controller.ConsultasController;
import grupo4.dds.controller.HomeController;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {
	
	public static void main(String[] args) {
		
		HomeController home = new HomeController();
		ConsultasController consultas = new ConsultasController();
	    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

	    port(8086);
	    
	    get("/", home::mostrar, engine);
	    get("/index.html", (request, response) -> {
	      response.redirect("/");
	      return null;
	    });
	    
	    get("/", home::listar, engine);
	    get("/consultas", consultas::listar, engine);
	    //post("/consultas", consultas::listar);
	    //post("/consultas", recetas::crear);
	    //get("/consultas/new", recetas::nuevo, engine);
	    //get("/consultas/:id", recetas::mostrar, engine);

	}

}
