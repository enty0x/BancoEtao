import java.io.Serializable;

public class CuentaAhorro implements Cuenta, Serializable {
    private int numeroCuenta;
    private int saldo;
    private Cliente client;

    public CuentaAhorro(Cliente cliente, int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0;
        this.client = cliente;
    }
    //modificadores de acceso
    public boolean recibeDinero(int monto) {
        saldo += monto;return true;
    }public boolean retiraDinero(int monto) {
        if(monto > saldo) {
            return false;
        }saldo -= monto;
        return true;
    }

    //gets posibles
    public int getSaldo(){return saldo;}
    public int getNumCuenta(){return numeroCuenta;}
    public Cliente getClient(){return client;}

}
