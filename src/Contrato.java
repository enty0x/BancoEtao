public class Contrato {
    private int IdContrato;
    private String Tipo; //Cuenta Ahorro, Cuenta RUT, Cuenta Corriente

    private boolean FirmadoCliente;
    private boolean FirmadoEjecutivo;
    private Ejecutivo ejecutivo;
    private Cliente cliente;

    public Contrato(int IdContrato, String Tipo, Cliente cliente, Ejecutivo ejecutivo) {
        this.IdContrato = IdContrato;
        this.Tipo = Tipo;
        FirmadoCliente = false;
        FirmadoEjecutivo = false;
        this.cliente = cliente;
        this.ejecutivo = ejecutivo;
    }
    public int getIdContrato() {
        return IdContrato;
    }public String getTipo() {
        return Tipo;
    }
    public boolean isFirmado() {//(por cliente)
        return FirmadoCliente;
    }
    public void FirmaCliente(Cliente c) {
        if(this.cliente.equals(c)){FirmadoCliente = true;}
        else{System.out.println("Un cliente diferente al originario de la solicitud no puede firmar.");}
    }public void FirmaEjecutivo(Ejecutivo e) {
        if(this.ejecutivo.equals(e)){FirmadoEjecutivo = true;}
        else{System.out.println("Un ejecutivo diferente al originario de la solicitud no puede firmar.");}
    }
}
