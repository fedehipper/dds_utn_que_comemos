package grupo4.dds.main;

import java.util.ArrayList;
import java.util.List;


import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;


import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.repositorios.RepositorioDeSolicitudes;
import grupo4.dds.usuario.BuilderUsuario;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Rutina;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.usuario.condicion.Celiaco;
import grupo4.dds.usuario.condicion.Diabetico;
import grupo4.dds.usuario.condicion.Hipertenso;
import grupo4.dds.usuario.condicion.Vegano;


public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps,TransactionalOps {

	public static void main(String[] args) {
		new Bootstrap().run();
	}

	public void run() {
		withTransaction(() -> {
			
			
			Usuario fecheSena = new BuilderUsuario().masculino().nombre("Federico Sena").altura(1.70f).peso(65.0f).mail("fesena92@gmail.com").build();
			Usuario arielFolino = new BuilderUsuario().masculino().nombre("Ariel Folino").altura(1.69f).peso(96.0f).condicion(Vegano.instance()).condicion(Celiaco.instance()).build();
			Usuario federicoHipper = new BuilderUsuario().masculino().nombre("Federico Hipperdinger").altura(1.91f).peso(102.0f).condicion(Diabetico.instance()).build();
			Usuario matiasMartino = new BuilderUsuario().masculino().nombre("Matías Martino").altura(1.74f).peso(79.0f).condicion(Hipertenso.instance()).condicion(Vegano.instance()).build();
			new BuilderUsuario().masculino().nombre("Cristian Maldonado").altura(1.81f).peso(87.0f).condicion(Hipertenso.instance()).condicion(Vegano.instance()).build();
		
	
			RepositorioDeSolicitudes.instance().aprobarTodas();

			GrupoUsuarios.crearGrupo("grupo1");
			GrupoUsuarios.crearGrupo("grupo2");

			Ingrediente pocaSal = Ingrediente.nuevoIngrediente("sal", 10);
			Ingrediente azucar = Ingrediente.nuevoIngrediente("azucar", 50);
			Ingrediente oregano = Ingrediente.nuevoIngrediente("oregano", 50);
			Ingrediente muchaSal = Ingrediente.nuevoIngrediente("sal", 150);
			
			List<Ingrediente> condimentos = new ArrayList<>();
			condimentos.add(oregano);
			condimentos.add(muchaSal);

			Receta milanesa = new BuilderReceta().invierno().creador(fecheSena).nombre("milanesa").dificil().calorias(999).ingrediente(pocaSal).
			preparacion("una feta de carne, la introducimos en un plato con pan raliado, y wala!! ha nacido una milanga ").condimentos(condimentos).build();
			Receta pollo = new BuilderReceta().verano().creador(federicoHipper).nombre("pollo").dificil().calorias(300).ingrediente(muchaSal).
			preparacion("desplumamos el ave condimentamos con lo que tenga a mano y al horno").condimento(oregano).build();
			Receta pure = new BuilderReceta().invierno().creador(federicoHipper).nombre("pure").calorias(600).ingrediente(oregano).
			preparacion("hervir papas a 147 Cº y a una presion de 1 atm, esperamos 2 horas y con un tenedor aplastamos y hacemos pure a la papa ").build();
			Receta sopa = new BuilderReceta().verano().creador(arielFolino).nombre("sopa").calorias(100).facil().ingrediente(pocaSal).
			preparacion("revanar verdura, de su preferencia y cubrir un volumen de 3*Pi*8cm ^ 2 * una altura de 25 cm con agua y vertir la verdura").build();
			Receta fideos = new BuilderReceta().invierno().creador(matiasMartino).nombre("fideos con manteca").calorias(499).dificil().ingrediente(azucar).build();
			
			fecheSena.marcarFavorita(milanesa);
			federicoHipper.marcarFavorita(pure);
			federicoHipper.marcarFavorita(pollo);
			arielFolino.marcarFavorita(sopa);
			matiasMartino.marcarFavorita(fideos);
			
			matiasMartino.agregarPreferenciaAlimenticia(azucar);
			matiasMartino.agregarPreferenciaAlimenticia(pocaSal);
			matiasMartino.agregarComidaQueLeDisgusta(muchaSal);
			matiasMartino.setRutina(Rutina.SEDENTARIA_CON_ALGO_EJERCICIO);
			
			
		});
		
		
		
		System.exit(0);

	}
}