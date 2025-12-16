import java.util.*;
public class Main{
    public static void main(String[] args) {
        mainprueba();
    }
    public static void mainprueba() {
        SistemaBE sistemaBE = SistemaBE.instancia();
        Scanner sc = new Scanner(System.in);

        Cliente cliente1 = new Cliente("pepe", 218238190, 20);
        Ejecutivo ej1 = sistemaBE.crearEjecutivo("Jose", "Chillan");
        cliente1.SolicitarCuentaAhorro(ej1);
        System.out.println("");
        CajaVecina cv1 = sistemaBE.crearCajaVecina(100000);
        System.out.println("---!PROBANDO DEPOSITOS!---");
        cliente1.Depositar(cv1, 1000, 1);
        cliente1.Depositar(ej1, 1000, 1);
        System.out.println("");
        //para probar
        System.out.println("---!solicita cuenta rut!---");
        cliente1.SolicitarCuentaRut(ej1);
        System.out.println("");
        System.out.println("---!solicita cuenta corriente!---");
        cliente1.SolicitarCuentaCorriente(ej1);
        Scanner sc1 = new Scanner(System.in);
        System.out.println("");
        System.out.println("PROBANDO TRANSFERENCIAS");
        System.out.println("");
        System.out.print("Ingrese el numero de cuenta desde la que desea transferir: ");
        int CuentaOrig = sc1.nextInt();
        System.out.print("Ingrese el numero de cuenta a la que desea transferir: ");
        int CuentaDest = sc1.nextInt();
        System.out.print("Ingrese el monto de lo que desea transferir: ");
        int monto = sc1.nextInt();
        System.out.print("Ingrese su clave de cajero: ");
        String clave = sc1.next();
        cliente1.Transferir(cv1, monto, CuentaOrig, CuentaDest, clave);
    }

}