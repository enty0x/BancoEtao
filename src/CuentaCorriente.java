import java.io.Serializable;

public class CuentaCorriente implements Cuenta, Serializable {
    private int numero;
    private int saldo;
    private String estado;
    private Cliente cliente;

    public CuentaCorriente(int numero, Cliente c) {
        this.numero = numero;
        this.saldo = 0;
        this.cliente = c;
    }

    //GET AND SET
    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}
    public int getSaldo() {return saldo;}

    @Override
    public int getNumCuenta() {return numero;}
    @Override
    public Cliente getClient() {return cliente;}
    @Override
    public void recibeDinero(int cantidad) {saldo += cantidad;}


}
