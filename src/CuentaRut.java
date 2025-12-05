import java.io.Serializable;

public class CuentaRut implements Cuenta,Serializable {
    private int NumeroCuenta;
    private int Saldo;
    private String estado;
    private String claveOnline;
    private int claveCajero;
    private Cliente client;

    public CuentaRut(int NumeroCuenta, int Saldo, String estado, String claveOnline, int claveCajero) {
        this.NumeroCuenta = NumeroCuenta;
        this.Saldo = Saldo;
        this.estado = estado;
        this.claveOnline = claveOnline;
        this.claveCajero = claveCajero;
    }

    //GET AND SET
    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}
    public int getClaveCajero() {return claveCajero;}
    public int getNumeroCuenta() {return NumeroCuenta;}
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
