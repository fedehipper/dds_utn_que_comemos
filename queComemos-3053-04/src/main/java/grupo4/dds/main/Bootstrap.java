package grupo4.dds.main;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.builder.BuilderReceta;
import grupo4.dds.receta.builder.BuilderRecetaPublica;
import grupo4.dds.repositorios.RepositorioDeSolicitudes;
import grupo4.dds.usuario.BuilderUsuario;
import grupo4.dds.usuario.GrupoUsuarios;
import grupo4.dds.usuario.Usuario;


public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps,TransactionalOps {

	public static void main(String[] args) {
		new Bootstrap().run();
	}

	public void run() {
		withTransaction(() -> {

			Usuario maria = new BuilderUsuario().femenino().nombre("Maria").altura(1.70f).peso(65.0f).build();
			Usuario fecheSena = new BuilderUsuario().masculino().nombre("Federico Sena").altura(1.70f).peso(65.0f).mail("fesena92@gmail.com").build();
			Usuario arielFolino = new BuilderUsuario().masculino().nombre("Ariel Folino").altura(1.69f).peso(96.0f).build();
			Usuario federicoHipper = new BuilderUsuario().masculino().nombre("Federico Hipperdinger").altura(1.91f).peso(102.0f).build();
			new BuilderUsuario().masculino().nombre("Matías Martino").altura(1.74f).peso(79.0f).build();
			new BuilderUsuario().masculino().nombre("Cristian Maldonado").altura(1.81f).peso(87.0f).build();
			
			RepositorioDeSolicitudes.instance().aprobarTodas();

			GrupoUsuarios.crearGrupo("grupo1");
			GrupoUsuarios.crearGrupo("grupo2");

			Ingrediente pocaSal = Ingrediente.nuevoIngrediente("sal", 10);
			Ingrediente azucar = Ingrediente.nuevoIngrediente("azucar", 50);
			Ingrediente oregano = Ingrediente.nuevoIngrediente("oregano", 50);
			Ingrediente muchaSal = Ingrediente.nuevoIngrediente("sal", 150);

			new BuilderReceta().invierno().creador(fecheSena).nombre("milanesa").dificil().calorias(999).ingrediente(pocaSal).build();
			new BuilderReceta().verano().creador(federicoHipper).nombre("pollo").dificil().calorias(300).ingrediente(muchaSal).build();
			new BuilderReceta().invierno().creador(federicoHipper).nombre("pure").calorias(600).ingrediente(oregano).build();
			new BuilderReceta().verano().creador(arielFolino).nombre("sopa").calorias(100).facil().ingrediente(pocaSal).build();
			new BuilderReceta().otonio().creador(maria).nombre("bife").calorias(499).facil().ingrediente(azucar).build();
			new BuilderRecetaPublica().primavera().nombre("salmon").calorias(200).ingrediente(azucar).build();
			new BuilderRecetaPublica().invierno().nombre("lomito").calorias(300).ingrediente(pocaSal).build();
			new BuilderRecetaPublica().verano().nombre("coliflor hervida").calorias(100).facil().ingrediente(muchaSal).build();

		});

		System.exit(0);

	}
}