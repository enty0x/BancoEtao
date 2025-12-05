public interface Cuenta {
    public int getSaldo();
    public int getNumCuenta();
    public Cliente getClient();
    public void recibeDinero(int cantidad);
}
