package grupo4.dds;

public class Usuario {
	private double estatura;
	private double peso;
	
	
	public Usuario(double estatura, double peso){
		this.peso = peso;
		this.estatura = estatura;
	}
	
	public double indiceDeMasaCorporal(){
		return peso/(estatura*estatura);
	}

}
