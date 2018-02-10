package grupo4.dds.receta.busqueda.postProcesamiento;

import grupo4.dds.receta.Receta;

import java.util.List;

public interface PostProcesamiento {

    List<Receta> procesar(List<Receta> recetas);

}
