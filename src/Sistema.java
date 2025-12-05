import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Sistema {
    private static Sistema sistema;
    private Sistema() {}
    public static Sistema instancia() {
        if (sistema == null) {
            sistema = new Sistema();
        }
        return sistema;
    }

    ArrayList<Solicitud> solicitudes = new ArrayList<Solicitud>();
    ArrayList<Ejecutivo> ejecutivos = new ArrayList<Ejecutivo>();
    ArrayList<Contrato> contratos = new ArrayList<Contrato>();
    ArrayList<Cliente> clientes = new ArrayList<Cliente>(); //! OJO, SOLO LOS CLIENTES QUE SI TIENEN CUENTAS
    ArrayList<CajaVecina> CajasVecinas = new ArrayList<CajaVecina>();

    //CUENTAS
    ArrayList<CuentaRut> cuentasRuts = new ArrayList<CuentaRut>();
    ArrayList<CuentaAhorro> cuentasAhorro = new ArrayList<CuentaAhorro>();
    ArrayList<CuentaCorriente> cuentasCorrientes = new ArrayList<CuentaCorriente>();
    ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>(); //aqui van todas, ideal para hacer recorridos.
    public boolean Depositar(int numCuenta, int monto) {
        for(Cuenta cuenta : cuentas) {
            if (cuenta.getNumCuenta() == numCuenta) {
                cuenta.recibeDinero(monto);
                return true;
            }
        }
        return false; //no existe tal cuenta
    }
    //!METODO IMPORTANTE
    private int sigNumCuentaDisponible(){return (cuentasRuts.size() + cuentasCorrientes.size() + cuentasAhorro.size() + 1);}

    Ejecutivo crearEjecutivo(String nombre, String sucursal){
        Ejecutivo ejecutivo = new Ejecutivo(nombre, ejecutivos.size() + 1, sucursal);
        ejecutivos.add(ejecutivo);
        return ejecutivo;
    }
    CajaVecina crearCajaVecina(int MontoInicial){
        CajaVecina cv = new CajaVecina(CajasVecinas.size() + 1, MontoInicial);
        CajasVecinas.add(cv);
        System.out.println("Caja vecina creada exitosamente, ID: " + cv.id());
        return cv;
    }
    //! CUENTA RUT
    public CuentaRut activaCuentaRut(CuentaRut cuentaRut) {
        if(cuentaRut == null) { return null; }
        if(cuentaRut.getEstado().equals("ACTIVA")) {
            System.out.println("La cuenta ya esta activa");
        }
        else {
            cuentaRut.setEstado("ACTIVA");
        }
        System.out.println("La cuenta ha sido activada exitosamente");
        cuentasRuts.add(cuentaRut);
        return cuentaRut;
    }
    //! CUENTA CORRIENTE
    public CuentaCorriente activaCuentaCorriente(CuentaCorriente cuentaCorriente) {
        if(cuentaCorriente == null) { return null; }
        if(cuentaCorriente.getEstado().equals("ACTIVA")) {
            System.out.println("La cuenta ya esta activa");
        }
        else {
            cuentaCorriente.setEstado("ACTIVA");
        }
        System.out.println("La cuenta ha sido activada exitosamente");
        cuentasCorrientes.add(cuentaCorriente);
        return cuentaCorriente;
    }
    //! CUENTA AHORRO
    public void integrarSolicitud(Solicitud solicitud) {
        solicitud.setID(solicitudes.size() + 1);
        solicitudes.add(solicitud);
    }
    public boolean ValidarSolicitudCA(Solicitud solicitud) {
        //! considerar que si paso aca, es porque no tenia cuenta de ahorro
        Cliente clienteSolicitante = solicitud.getCliente();
        // if (EvaluarDocumentosCH(solicitudCH))...;
        //! considerando que cumple todo lo necesario para que sea una solicitud válida
        solicitud.setEstado(true); //aprobado
        System.out.println("Sistema ha aprobado Solicitud, generando cuenta de ahorro y contrato...");

        //generar cuenta de ahorro, su claveOnline sera el ID del cliente, la claveCajero sera una clave random de 4 digitos
        CuentaAhorro CA = generarCuentaAhorro(clienteSolicitante);
        cuentasAhorro.add(CA);
        cuentas.add(CA);
        if(!clientes.contains(clienteSolicitante))clientes.add(clienteSolicitante);

        Contrato contratoNuevo = nuevoContrato(solicitud, "CA");
        solicitud.setContrato(contratoNuevo);
        //AHORA EJECUTIVO DEBE FIRMAR SU PARTE DEL CONTRATO
        return true;
    }
    private Contrato nuevoContrato(Solicitud solicitud, String tipo) {

        Contrato contrato = new Contrato(contratos.size()+1, tipo, solicitud.getCliente(), solicitud.getEjecutivo());
        contratos.add(contrato);
        return contrato;
    }

    public CuentaAhorro generarCuentaAhorro(Cliente cliente) {
        Random random = new Random();
        String claveCaj = String.valueOf(random.nextInt(10000));
        while(claveCaj.length() < 4)claveCaj = "0" + claveCaj; //arreglo menor, haciendo la clave de 4 digitos

        CuentaAhorro CA = new CuentaAhorro(cliente,sigNumCuentaDisponible(),String.valueOf(cliente.getCedula()),claveCaj);
        System.out.println("Se ha creado una cuenta de ahorro para el cliente " + cliente.getNombre());
        System.out.println("El numero de cuenta es: " + CA.getNumCuenta());
        System.out.println("La clave online de la cuenta es: " + cliente.getCedula()+ " (La cedula del cliente).");
        System.out.println("La clave de cajero de la cuenta es: " + claveCaj);
        System.out.println("Puede usarse, cambiar las claves y realizar movimientos despues de firmar el contrato.");
        return CA;
    }

    public CuentaAhorro buscarCuentaAhorroDelCliente(Cliente cliente) {
        for (CuentaAhorro CA : cuentasAhorro) {
            if (CA.getClient().equals(cliente)) {
                return CA;
            }
        }
        return null;//nunca deberia llegar aca por las
    }
}
