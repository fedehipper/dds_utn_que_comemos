package grupo4.dds;

class Sarkany implements Marca {

    public double precioFinal(Prenda prenda) {
        double precio = prenda.precioOriginal();
        return precio > 500 ? precio * 1.35 : precio * 1.1;
    }
}
