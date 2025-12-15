import java.io.Serializable;

public class CuentaCorriente implements Cuenta, Serializable {
    private int numero;
    private int saldo;
    private Cliente cliente;

    public CuentaCorriente(int numero, Cliente c) {
        this.numero = numero;
        this.saldo = 0;
        this.cliente = c;
    }
    @Override
    public int getSaldo() {return saldo;}
    @Override
    public int getNumCuenta() {return numero;}
    @Override
    public Cliente getClient() {return cliente;}
    @Override
    public boolean recibeDinero(int cantidad) {saldo += cantidad;return true;}
    @Override
    public boolean retiraDinero(int monto) {
        if(monto > saldo) {
            return false;
        }saldo -= monto;
        return true;
    }
}