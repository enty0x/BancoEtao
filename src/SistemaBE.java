import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class SistemaBE implements Serializable {
    private static SistemaBE sistemaBE;

    private SistemaBE() {
    }

    public static SistemaBE instancia() {
        if (sistemaBE == null) {
            sistemaBE = new SistemaBE();
        }
        return sistemaBE;
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
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumCuenta() == numCuenta) {
                cuenta.recibeDinero(monto);
                return true;
            }
        }
        return false; //no existe tal cuenta
    }

    public boolean Transferir(Cuenta origen, Cuenta destino, int monto) {
        if (origen.retiraDinero(monto)) {
            destino.recibeDinero(monto);
            return true;
        }
        return false;
    }

    //!METODO IMPORTANTE
    private int sigNumCuentaDisponible() {
        return (cuentasRuts.size() + cuentasCorrientes.size() + cuentasAhorro.size() + 1);
    }

    Ejecutivo crearEjecutivo(String nombre, String sucursal) {
        Ejecutivo ejecutivo = new Ejecutivo(nombre, ejecutivos.size() + 1, sucursal);
        ejecutivos.add(ejecutivo);
        return ejecutivo;
    }

    CajaVecina crearCajaVecina(int MontoInicial) {
        CajaVecina cv = new CajaVecina(CajasVecinas.size() + 1, MontoInicial);
        CajasVecinas.add(cv);
        System.out.println("Caja vecina creada exitosamente, ID: " + cv.id());
        return cv;
    }
    //! CUENTA RUT

    public void integrarSolicitud(Solicitud solicitud) {
        solicitud.setID(solicitudes.size() + 1);
        solicitudes.add(solicitud);
    }

    public boolean ValidarSolicitudCR(Solicitud solicitud) {
        Cliente clienteSolicitante = solicitud.getCliente();
        solicitud.setEstado(true); //aprobado
        System.out.println("Sistema ha aprobado Solicitud, generando cuenta corriente y contrato...");

        CuentaRut CR = generarCuentaRut(clienteSolicitante);
        cuentasRuts.add(CR);
        cuentas.add(CR);
        if (!clientes.contains(clienteSolicitante)) clientes.add(clienteSolicitante);

        Contrato contratoNuevo = nuevoContrato(solicitud, "CR"); //tipo CR = Cuenta RUT
        solicitud.setContrato(contratoNuevo);
        //AHORA EJECUTIVO DEBE FIRMAR SU PARTE DEL CONTRATO
        return true;
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
        if (!clientes.contains(clienteSolicitante)) clientes.add(clienteSolicitante);

        Contrato contratoNuevo = nuevoContrato(solicitud, "CA");
        solicitud.setContrato(contratoNuevo);
        //AHORA EJECUTIVO DEBE FIRMAR SU PARTE DEL CONTRATO
        return true;
    }

    public boolean ValidarSolicitudCC(Solicitud solicitud) {
        Cliente clienteSolicitante = solicitud.getCliente();
        solicitud.setEstado(true); //aprobado
        System.out.println("Sistema ha aprobado Solicitud, generando cuenta corriente y contrato...");

        CuentaCorriente CC = generarCuentaCorriente(clienteSolicitante);
        cuentasCorrientes.add(CC);
        cuentas.add(CC);
        if (!clientes.contains(clienteSolicitante)) clientes.add(clienteSolicitante);

        Contrato contratoNuevo = nuevoContrato(solicitud, "CC"); //tipo CC = Cuenta Corriente
        solicitud.setContrato(contratoNuevo);
        //AHORA EJECUTIVO DEBE FIRMAR SU PARTE DEL CONTRATO
        return true;
    }

    private Contrato nuevoContrato(Solicitud solicitud, String tipo) {

        Contrato contrato = new Contrato(contratos.size() + 1, tipo, solicitud.getCliente(), solicitud.getEjecutivo());
        contratos.add(contrato);
        return contrato;
    }

    public CuentaAhorro generarCuentaAhorro(Cliente cliente) {
        CuentaAhorro CA = new CuentaAhorro(cliente, sigNumCuentaDisponible());
        //!VERIFICAR LA EXISTENCIA DE CLAVES PREVIAS
        String claveCaj = cliente.ClaveCajero();
        if (claveCaj == null) {//NO TIENE CLAVES, GENERAR
            Random random = new Random();
            claveCaj = String.valueOf(random.nextInt(10000));
            while (claveCaj.length() < 4) claveCaj = "0" + claveCaj; //arreglo menor, haciendo la clave de 4 digitos
            cliente.setClaves(claveCaj, String.valueOf(cliente.getCedula()));
        }
        System.out.println("Se ha creado una cuenta de ahorro para el cliente " + cliente.getNombre());
        System.out.println("El numero de cuenta es: " + CA.getNumCuenta());
        System.out.println("La clave online de la cuenta es: " + cliente.getCedula() + " (La cedula del cliente).");
        System.out.println("La clave de cajero de la cuenta es: " + claveCaj);
        System.out.println("Puede usarse, cambiar las claves y realizar movimientos despues de firmar el contrato.");
        return CA;
    }

    public CuentaRut generarCuentaRut(Cliente cliente) {
        CuentaRut CR = new CuentaRut(sigNumCuentaDisponible(), cliente);
        //!VERIFICAR LA EXISTENCIA DE CLAVES PREVIAS
        String claveCaj = cliente.ClaveCajero();
        if (claveCaj == null) {//NO TIENE CLAVES, GENERAR
            Random random = new Random();
            claveCaj = String.valueOf(random.nextInt(10000));
            while (claveCaj.length() < 4) claveCaj = "0" + claveCaj; //arreglo menor, haciendo la clave de 4 digitos
            cliente.setClaves(claveCaj, String.valueOf(cliente.getCedula()));
        }
        System.out.println("Se ha creado una cuenta RUT para el cliente " + cliente.getNombre());
        System.out.println("El numero de cuenta es: " + CR.getNumCuenta());
        System.out.println("La clave online de la cuenta es: " + cliente.getCedula() + " (La cedula del cliente).");
        System.out.println("La clave de cajero de la cuenta es: " + claveCaj);
        System.out.println("Puede usarse, cambiar las claves y realizar movimientos despues de firmar el contrato.");
        return CR;
    }

    //MODIFIQUE AQUI PAULA
    public CuentaCorriente generarCuentaCorriente(Cliente cliente) {
        CuentaCorriente CC = new CuentaCorriente(sigNumCuentaDisponible(), cliente);
        //!VERIFICAR LA EXISTENCIA DE CLAVES PREVIAS
        String claveCaj = cliente.ClaveCajero();
        if (claveCaj == null) {//NO TIENE CLAVES, GENERAR
            Random random = new Random();
            claveCaj = String.valueOf(random.nextInt(10000));
            while (claveCaj.length() < 4) claveCaj = "0" + claveCaj; //arreglo menor, haciendo la clave de 4 digitos
            cliente.setClaves(claveCaj, String.valueOf(cliente.getCedula()));
        }
        System.out.println("Se ha creado una cuenta corriente para el cliente " + cliente.getNombre());
        System.out.println("El numero de cuenta es: " + CC.getNumCuenta());
        System.out.println("La clave online de la cuenta es: " + cliente.getCedula() + " (La cedula del cliente).");
        System.out.println("La clave de cajero de la cuenta es: " + claveCaj);
        System.out.println("Puede usarse, cambiar las claves y realizar movimientos despues de firmar el contrato.");
        return CC;
    }

    public CuentaAhorro buscarCuentaAhorroDelCliente(Cliente cliente) {
        for (CuentaAhorro CA : cuentasAhorro) {
            if (CA.getClient().equals(cliente)) {
                return CA;
            }
        }
        return null;
    }

    public CuentaRut buscarCuentaRutDelCliente(Cliente cliente) {
        for (CuentaRut CR : cuentasRuts) {
            if (CR.getClient().equals(cliente)) return CR;
        }
        return null;
    }

    //CUENTA CORRIENTE PAULA
    public CuentaCorriente buscarCuentaCorrienteDelCliente(Cliente cliente) {
        for (CuentaCorriente CC : cuentasCorrientes) {
            if (CC.getClient().equals(cliente)) return CC;
        }
        return null;
    }

    public Cuenta buscarCuenta(int numeroCuenta) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumCuenta() == numeroCuenta) return cuenta;
        }
        return null;
    }
}
