package grupo4.dds.monitores;

import grupo4.dds.excepciones.HoraInvalida;
import grupo4.dds.receta.Receta;
import grupo4.dds.receta.busqueda.filtros.Filtro;
import grupo4.dds.usuario.Usuario;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("horas")
public class CantidadDeHoras extends Monitor {

	private int[] consultasPorHora = new int[24];
	
	public void notificarConsulta(Usuario usuario, List<Receta> consulta, List<Filtro> parametros) {
		
		int horaActual = LocalTime.now().getHour();
		consultasPorHora[horaActual] += 1;
	}
	
	public int cantidadDeConsultasPor(int unaHora) {
		
		int cantConsultas = 0;
		
		try {
			cantConsultas = consultasPorHora[unaHora];
		}
		catch(ArrayIndexOutOfBoundsException err) {
			throw new HoraInvalida();
		}
		
		return cantConsultas;
	}
}
