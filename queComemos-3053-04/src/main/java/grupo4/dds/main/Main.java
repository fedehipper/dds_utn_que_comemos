package grupo4.dds.main;

import static spark.Spark.get;
import static spark.SparkBase.port;

import spark.template.handlebars.HandlebarsTemplateEngine;

public class Main {
	
	public static void main(String[] args) {
		
		HomeController home = new HomeController();
	    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

	    port(8086);
	    
	    get("/", home::mostrar, engine);
	    get("/index.html", (request, response) -> {
	      response.redirect("/");
	      return null;
	    });
	    
	    
	    
	    get("/", home::listar, engine);
	    //post("/consultoras", recetas::crear);
	    //get("/consultoras/new", recetas::nuevo, engine);
	    //get("/consultoras/:id", recetas::mostrar, engine);

	}

}
