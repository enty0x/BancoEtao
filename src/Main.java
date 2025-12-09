import java.util.*;
public class Main{
    public static void main(String[] args) {
        Sistema sistema = Sistema.instancia();
        Scanner sc = new Scanner(System.in);

        Cliente cliente1 = new Cliente("pepe", 218238190, 20);
        Ejecutivo ej1 = sistema.crearEjecutivo("Jose", "Chillan");
        cliente1.SolicitarCuentaAhorro(ej1);
        System.out.println("");
        CajaVecina cv1 = sistema.crearCajaVecina(100000);

        cliente1.Depositar(cv1, 1000, 1);
        System.out.println("aparte");
        //para probar
        cliente1.SolicitarCuentaCorriente(ej1);
        System.out.println("");

    }

}