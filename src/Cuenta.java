public interface Cuenta {
    public int getSaldo();
    public int getNumCuenta();
    public Cliente getClient();
    public boolean recibeDinero(int cantidad);
    public boolean retiraDinero(int cantidad);
}