package grupo4.dds.command;

import grupo4.dds.receta.RepositorioDeRecetas;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.receta.busqueda.postProcesamiento.PostProcesamiento;
import grupo4.dds.usuario.Usuario;

import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class LoguearConsultas implements Command{

	private Usuario usuario;
	private int consultas = 0;
	private Logger log = Logger.getLogger("ConsultasConMasDe100Resultados");
	private RepositorioDeRecetas repositorio;
	private PostProcesamiento postprocesamiento;
	private List<Filtro> filtros;

	
	public LoguearConsultas(Usuario usuario){
		this.usuario = usuario;
	}

	public void ejecutar() {
		BasicConfigurator.configure();
		consultas = usuario.recetasQuePuedeVer(repositorio,filtros,postprocesamiento).size();
		if (consultas > 100){
			if (log.isTraceEnabled()){
				log.trace("MasDe100Resultados");				
			}
		}
		
	}

}
