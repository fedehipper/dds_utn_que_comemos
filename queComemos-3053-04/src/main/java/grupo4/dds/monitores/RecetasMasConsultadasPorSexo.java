package grupo4.dds.monitores;

import grupo4.dds.receta.Receta;
import grupo4.dds.usuario.Sexo;
import grupo4.dds.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;


public class RecetasMasConsultadasPorSexo implements Monitor {
	
	private HashMap<String, Integer> contadorDeRecetasM = new HashMap<String, Integer>();
	private HashMap<String, Integer> contadorDeRecetasF = new HashMap<String, Integer>();
	
	private String recetaMasConsultadaM;
	private Integer cantidadDeConsultasM = 0;
	private String recetaMasConsultadaF;
	private Integer cantidadDeConsultasF = 0;
	
	public void notificarConsulta(List<Receta> consulta, Usuario usuario) {

		if (usuario.getSexo() == Sexo.MASCULINO) 
			this.acumular(consulta, usuario, contadorDeRecetasM);
		else
			this.acumular(consulta, usuario, contadorDeRecetasF);						
	}
	
	public void acumular(List<Receta> consulta, Usuario usuarioConsultor, HashMap<String, Integer> contador) {

		ListIterator<Receta> r =  consulta.listIterator(); 
				
		while(r.hasNext()) {
			String nom = r.next().getNombreDelPlato();
			
			if (contador.containsKey(nom)) 
				contador.put(nom, contador.get(nom) + 1);
			else
				contador.put(nom, 1);
		}
	}
		
	public HashMap<String, Integer> recetaMasConsultada(Usuario usuario) {
		
		HashMap<String, Integer> receta = new HashMap<String, Integer>();
		
		if (usuario.getSexo() == Sexo.MASCULINO) {
			contadorDeRecetasM.forEach((r, v) -> guardarMaximo(v, r, usuario.getSexo()));
			receta.put(recetaMasConsultadaM, cantidadDeConsultasM);
		}
		else {
			contadorDeRecetasF.forEach((r, v) -> guardarMaximo(v, r, usuario.getSexo()));
			receta.put(recetaMasConsultadaF, cantidadDeConsultasF);	
		}
		return receta;
	}
	

	public void guardarMaximo(Integer unValor, String unaReceta, Sexo sexo) {
		
			if (unValor >= cantidadDeConsultasM && sexo == Sexo.MASCULINO) {
				cantidadDeConsultasM = unValor;
				recetaMasConsultadaM = unaReceta;
			}
			if (unValor >= cantidadDeConsultasF && sexo == Sexo.FEMENINO) {
				cantidadDeConsultasF = unValor;
				recetaMasConsultadaF = unaReceta;
			}
	}
	
	
}
