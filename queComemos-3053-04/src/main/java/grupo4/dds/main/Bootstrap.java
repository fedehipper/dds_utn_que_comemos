package grupo4.dds.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import grupo4.dds.usuario.BuilderUsuario;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import grupo4.dds.receta.Ingrediente;
import grupo4.dds.receta.builder.*;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps,
		TransactionalOps {

	public static void main(String[] args) {
		new Bootstrap().run();
	}

	public void run() {
		withTransaction(() -> {

			persist(new BuilderUsuario().nombre("pepe").build());
			persist(new BuilderUsuario().nombre("pepa").build());
			persist(new BuilderReceta().nombre("papa").calorias(2500).facil()
					.invierno()
					.ingrediente(Ingrediente.nuevoIngrediente("papas", 0f))
					.build());
			persist(new BuilderReceta().nombre("milanga").mediana()
					.calorias(4000)
					.ingrediente(Ingrediente.nuevoIngrediente("carne", 0f))
					.build());
			persist(new BuilderReceta().nombre("papas").mediana()
					.calorias(4500)
					.ingrediente(Ingrediente.nuevoIngrediente("sal", 0f))
					.build());
			;
		});
		commitTransaction();
	}

}
