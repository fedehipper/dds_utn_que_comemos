package grupo4.dds;

class Saco extends Prenda {

    private int botones;

    public double precioBase() {
        return 300 + 10 * botones;
    }

    Saco(TipoDeImportacion tipoDeImportacion, Marca marca, int cantBotones, MacoWins negocio) {
        super(tipoDeImportacion, marca, negocio);
        botones = cantBotones;
    }
}
