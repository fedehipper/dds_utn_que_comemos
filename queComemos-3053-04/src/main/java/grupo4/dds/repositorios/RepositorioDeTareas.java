package grupo4.dds.repositorios;

import grupo4.dds.monitores.asincronicos.tareas.TareaPendiente;

public class RepositorioDeTareas extends Repositorio<TareaPendiente> {

    private static volatile RepositorioDeTareas instance = null;

    public static RepositorioDeTareas instance() {
        if (instance == null) {
            instance = new RepositorioDeTareas();
        }
        return instance;
    }

    public RepositorioDeTareas() {
        elementType = TareaPendiente.class;
    }

    public void ejecutarTodas() {
        list().forEach(TareaPendiente::ejecutar);
    }

}
