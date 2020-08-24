package grupo4.dds;

public class Armani implements Marca {

    @Override
    public double precioFinal(Prenda prenda) {
        return prenda.precioOriginal() * 1.65;
    }

}
