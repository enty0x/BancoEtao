import java.io.Serializable;

public class CajaVecina implements Serializable {
    private int IDCV;
    private int dineroDisponible;

    public CajaVecina(int IDCV, int dineroDisponible) {
        this.IDCV = IDCV;
        this.dineroDisponible = dineroDisponible;
    }

    public int getDineroDisponible() {
        return dineroDisponible;
    }

    public int id() {
        return IDCV;
    }

    public void aumentarDinero(int dinero) {
        dineroDisponible += dinero;
    }

    public void disminuirDinero(int dinero) {
        dineroDisponible -= dinero;
    }

    public boolean DepositarACuenta(int dinero, int numeroCuenta) {
        if (SistemaBE.instancia().Depositar(numeroCuenta, dinero)) {
            System.out.println("Se ha realizado el deposito de " + dinero + " en la cuenta " + numeroCuenta);
            aumentarDinero(dinero); //dinero ingresado a la caja vecina
            return true;
        } else {
            System.out.println("No existe la cuenta.");
            return false;
        }
    }
    public void VerSaldoDeMiCuentaDeAhorro(Cliente c){
        CuentaAhorro CA = SistemaBE.instancia(). buscarCuentaAhorroDelCliente(c);
        if(CA == null){
            System.out.println("No existe cuenta de ahorro para este cliente.");
            return;
        }else{
            System.out.printf("El saldo de la cuenta N°" + CA.getNumCuenta() + " es de: " + CA.getSaldo());
        }
    }
    public void VerSaldoDeMiCuentaCorriente(Cliente c){
        CuentaCorriente CC = SistemaBE.instancia(). buscarCuentaCorrienteDelCliente(c);
        if(CC == null){
            System.out.println("No existe cuenta de ahorro para este cliente.");
            return;
        }else{
            System.out.printf("El saldo de la cuenta N°" + CC.getNumCuenta() + " es de: " + CC.getSaldo());
        }
    }
}
