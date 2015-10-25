package grupo4.dds.main;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import grupo4.dds.usuario.BuilderUsuario;
import grupo4.dds.usuario.Usuario;
import grupo4.dds.receta.builder.BuilderReceta;

public class Aux implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  public static void main(String[] args) {
    new Aux().run();
  }

  public void run() {
    withTransaction(() -> {
    	
    	Usuario maria =	new BuilderUsuario().femenino().nombre("Maria").altura(1.70f).peso(65.0f).build();
   
    	persist(maria);
    	//persist(new BuilderReceta().creador(maria).nombre("bife").calorias(499).facil());
    });
  }

}
