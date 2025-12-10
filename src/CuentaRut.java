import java.io.Serializable;

public class CuentaRut implements Cuenta,Serializable {
    private int NumeroCuenta;
    private int Saldo;
    private String estado;
    private Cliente client;

    public CuentaRut(int NumeroCuenta, String estado, Cliente c) {
        this.NumeroCuenta = NumeroCuenta;
        this.Saldo = 0;
        this.estado = estado;
        this.client = c;
    }

    //GET AND SET
    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}
    public int getSaldo() {return Saldo;}

    @Override
    public int getNumCuenta() {
        return NumeroCuenta;
    }
    @Override
    public Cliente getClient() {
        return null;
    }
    @Override
    public void recibeDinero(int cantidad) {
        Saldo += cantidad;
    }
}
