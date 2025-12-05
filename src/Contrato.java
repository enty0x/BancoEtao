public class Contrato {
    private int IdContrato;
    private String Tipo; //Cuenta Ahorro, Cuenta RUT, Cuenta Corriente

    private boolean Firmado;
    private EjecutivoCH ejecutivo;
    private ClienteCH cliente;

    public Contrato(int IdContrato, String Tipo, ClienteCH cliente, EjecutivoCH ejecutivo) {
        this.IdContrato = IdContrato;
        this.Tipo = Tipo;
        Firmado = false;
        this.cliente = cliente;
        this.ejecutivo = ejecutivo;
    }
    public int getIdContrato() {
        return IdContrato;
    }public String getTipo() {
        return Tipo;
    }
    public boolean isFirmado() {
        return Firmado;
    }

}
