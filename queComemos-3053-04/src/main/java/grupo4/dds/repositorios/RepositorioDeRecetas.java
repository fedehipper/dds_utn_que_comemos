package grupo4.dds.repositorios;

import grupo4.dds.monitores.Monitor;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.Temporada;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.usuario.Usuario;

import java.util.HashSet;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import queComemos.entrega3.dominio.Dificultad;

import java.util.Objects;
import static java.util.stream.Collectors.toList;

public class RepositorioDeRecetas extends Repositorio<Receta> implements WithGlobalEntityManager {

    private static final RepositorioDeRecetas self = new RepositorioDeRecetas();

    public static RepositorioDeRecetas instance() {
        return self;
    }

    public RepositorioDeRecetas() {
        elementType = Receta.class;
    }

    public List<Receta> listar() {
        return entityManager().createQuery("from Receta", Receta.class).getResultList();
    }

    /* Servicios */
    public List<Receta> listarRecetasPara(Usuario usuario, List<Filtro> filtros, PostProcesamiento postProcesamiento) {
        List<Receta> recetasFiltradas = filtrarRecetas(usuario, filtros);
        List<Receta> consulta = postProcesamiento == null ? recetasFiltradas : postProcesamiento.procesar(recetasFiltradas);
        notificarATodos(usuario, consulta, filtros);
        usuario.consulto(consulta);
        return consulta;
    }

    private List<Receta> filtrarRecetas(Usuario usuario, List<Filtro> filtros) {
        List<Receta> recetasQuePuedeVerUsuario = recetasQuePuedeVer(usuario);;
        if (filtros != null) {
            for (Filtro filtro : filtros) {
                recetasQuePuedeVerUsuario = recetasQuePuedeVerUsuario.stream().filter(r -> filtro.test(usuario, r)).collect(toList());
            }
        }
        return recetasQuePuedeVerUsuario;
    }

    public void notificarATodos(Usuario usuario, List<Receta> consulta, List<Filtro> filtros) {
        monitores().forEach(monitor -> monitor.notificarConsulta(usuario, consulta, filtros));
    }

    public List<Monitor> monitores() {
        return entityManager().createQuery("from Monitor", Monitor.class).getResultList();
    }

    public Receta buscar(long id) {
        return entityManager().find(Receta.class, id);
    }

    public List<Receta> buscar(String nombreReceta) {
        return entityManager().createQuery("from Receta where nombreDelPlato like '%" + nombreReceta + "%'", Receta.class).getResultList();
    }

    /* Servicios privados */
    private List<Receta> recetasQuePuedeVer(Usuario usuario) {

        HashSet<Receta> todasLasRecetas = new HashSet<>(this.list());
        todasLasRecetas.addAll(RepositorioRecetasExterno.get().getRecetas());

        return todasLasRecetas.stream().filter(r -> usuario.puedeVer(r)).collect(toList());
    }

    /* Accesors and Mutators */
    public void agregarMonitor(Monitor monitor) {
        entityManager().persist(monitor);
    }

    public void removerMonitor(Monitor monitor) {
        entityManager().remove(monitor);
    }

    public List<Receta> buscarPorFiltros(String nombreReceta, String dificultad, String temporada, String caloriasDesde, String caloriasHasta) {
        String query = "from Receta where id=id";

        if (!(Objects.isNull(nombreReceta) || nombreReceta.isEmpty())) {
            query = query + " AND nombreDelPlato like '%" + nombreReceta + "%'";
        }

        if (!((Objects.isNull(caloriasDesde) || Objects.isNull(caloriasHasta)) || (caloriasDesde.isEmpty() || caloriasHasta.isEmpty()))) {
            query = query + " AND totalCalorias between " + Integer.parseInt(caloriasDesde) + " AND " + Integer.parseInt(caloriasHasta);
        }

        if (!(Objects.isNull(dificultad) || dificultad.isEmpty())) {
            query = query + " AND  dificultad = " + Dificultad.valueOf(dificultad).ordinal();
        }

        if (!(Objects.isNull(temporada) || temporada.isEmpty())) {
            query = query + " AND temporada = " + Temporada.valueOf(temporada).ordinal();
        }

        return entityManager().createQuery(query, Receta.class).getResultList();
    }

}
