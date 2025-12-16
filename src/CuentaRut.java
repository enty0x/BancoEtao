import java.io.Serializable;

public class CuentaRut implements Cuenta,Serializable {
    private int NumeroCuenta;
    private int saldo;
    private Cliente client;

    public CuentaRut(int NumeroCuenta, Cliente c) {
        this.NumeroCuenta = NumeroCuenta;
        this.saldo = 0;
        this.client = c;
    }
    @Override
    public int getSaldo() {return saldo;}
    @Override
    public int getNumCuenta() {
        return NumeroCuenta;
    }
    @Override
    public Cliente getClient() {
        return client;
    }
    @Override
    public boolean recibeDinero(int cantidad) {saldo += cantidad; return true;}
    @Override
    public boolean retiraDinero(int monto) {
        if(monto > saldo) {
            return false;
        }saldo -= monto;
        return true;
    }
}
