import java.io.Serializable;
import java.sql.SQLOutput;

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

    private void aumentarDinero(int dinero) {
        dineroDisponible += dinero;
    }

    private void disminuirDinero(int dinero) {
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
    public boolean TransferirDinero(Cliente cliente, int nCuentaOriginaria, int nCuentaDestinaria, int monto, String clave) { //El cliente ingresa al sistema
        //! usa el sistema para poder buscar las cuentas de origen y destino
        Cuenta cuentaOrig = SistemaBE.instancia().buscarCuenta(nCuentaOriginaria);
        if(cuentaOrig == null) {//no la encontro
            System.out.println("La cuenta originaria no existe.");
            return false;
        }
        Cliente clientOrig = cuentaOrig.getClient();
        if(!clientOrig.equals(cliente)){
            System.out.println("No puedes acceder a la cuenta de otro cliente.");
            return false;
        }
        Cuenta cuentaDest = SistemaBE.instancia().buscarCuenta(nCuentaDestinaria);
        if(cuentaDest == null) {//no la encontro
            System.out.println("La cuenta destinaria no existe.");
            return false;
        }
        if(!clientOrig.ClaveCajero().equals(clave)){
            System.out.println("La clave ingresada no es correcta.");
            return false;
        }
        if(!SistemaBE.instancia().Transferir(cuentaOrig, cuentaDest, monto)) {
            System.out.println("La cuenta originaria no tiene saldo suficiente.");
            return false;
        }
        System.out.println("Estimado " + clientOrig.getNombre() + " se ha realizado la transferencia de " + monto + " de la cuenta " + nCuentaOriginaria + " a la cuenta " + nCuentaDestinaria);
        return true;
    }
    public void VerSaldoDeMiCuentaDeAhorro(Cliente c){
        CuentaAhorro CA = SistemaBE.instancia(). buscarCuentaAhorroDelCliente(c);
        if(CA == null){
            System.out.println("No existe cuenta de ahorro para este cliente.");
            return;
        }else{
            System.out.println("El saldo de la cuenta N°" + CA.getNumCuenta() + " es de: " + CA.getSaldo());
        }
    }
    public void VerSaldoDeMiCuentaCorriente(Cliente c){
        CuentaCorriente CC = SistemaBE.instancia(). buscarCuentaCorrienteDelCliente(c);
        if(CC == null){
            System.out.println("No existe cuenta de ahorro para este cliente.");
            return;
        }else{
            System.out.println("El saldo de la cuenta N°" + CC.getNumCuenta() + " es de: " + CC.getSaldo());
        }
    }
    public void VerSaldoDeMiCuentaRUT(Cliente c){
        CuentaRut CR = SistemaBE.instancia().buscarCuentaRutDelCliente(c);
        if(CR == null){
            System.out.println("No existe cuenta de rut para este cliente.");
            return;
        }else{
            System.out.println("El saldo de la cuenta N°" + CR.getNumCuenta() + " es de: " + CR.getSaldo());
        }
    }
}
