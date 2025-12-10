import java.util.*;
public class Main{
    public static void main(String[] args) {
        SistemaBE sistemaBE = SistemaBE.instancia();
        Scanner sc = new Scanner(System.in);

        Cliente cliente1 = new Cliente("pepe", 218238190, 20);
        Ejecutivo ej1 = sistemaBE.crearEjecutivo("Jose", "Chillan");
        cliente1.SolicitarCuentaAhorro(ej1);
        System.out.println("");
        CajaVecina cv1 = sistemaBE.crearCajaVecina(100000);

        cliente1.Depositar(cv1, 10000, 1);
        cliente1.VerMontoCA(cv1);
    }

}