public class CuentaAhorro {
    private int numeroCuenta;
    private int saldo;
    private String ClaveOnline;
    private int ClaveCajero;
    private ClienteCH client;

    public CuentaAhorro(ClienteCH cliente, int numeroCuenta, String claveOnline, int claveCajero) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0;
        ClaveOnline = claveOnline;
        ClaveCajero = claveCajero;
        this.client = cliente;
    }
}
