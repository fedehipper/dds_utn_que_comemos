package grupo4.dds;

class Armani implements Marca {

    public double precioFinal(Prenda prenda) {
        return prenda.precioOriginal() * 1.65;
    }

}
