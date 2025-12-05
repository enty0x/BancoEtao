import java.io.Serializable;

public class CuentaAhorro implements Cuenta, Serializable {
    private int numeroCuenta;
    private int saldo;
    private String ClaveOnline;
    private String ClaveCajero; // SOLO 4 DIGITOS, COMO STRING, PORQ EN INT EL 0 PRINCIPAL DE UN "0581" SALE COMO "581"
    private Cliente client;

    public CuentaAhorro(Cliente cliente, int numeroCuenta, String claveOnline, String claveCajero) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0;
        ClaveOnline = claveOnline;
        ClaveCajero = claveCajero;
        this.client = cliente;
    }
    //modificadores de acceso
    public void recibeDinero(int monto) {
        saldo += monto;
    }public void retiraDinero(int monto) {
        if(monto > saldo) {
            System.out.println("No hay suficiente saldo");
            return;
        }saldo -= monto;
    }//sets administrativos, usados desde sistema, llamados por cliente
    //! SISTEMA VALIDA ANTES QUE LOS ARGUMENTOS SEAN VALIDOS.
    public void setClaveOnline(String claveOnline) {ClaveOnline = claveOnline;}
    public void setClaveCajero(String claveCajero) {ClaveCajero = claveCajero;}

    //gets posibles
    public int getSaldo(){return saldo;}
    public int getNumCuenta(){return numeroCuenta;}
    public Cliente getClient(){return client;}

}
