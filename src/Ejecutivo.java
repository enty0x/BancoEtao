import java.io.Serializable;

public class Ejecutivo implements Serializable {
    private String nombre;
    private int IDej;
    private String sucursal;

    public Ejecutivo(String nombre, int IDEj, String sucursal) {
        this.nombre = nombre;
        this.IDej = IDEj;
        this.sucursal = sucursal;
    }

    public boolean equals(Ejecutivo ejecutivo) {
        return (this.IDej == ejecutivo.getIDej());
    }

    public String getNombre() {
        return nombre;
    }

    public int getIDej() {
        return IDej;
    }

    public String getSucursal() {
        return sucursal;
    }

    public boolean DepositarACuenta(int dinero, int numeroCuenta) {
        if (SistemaBE.instancia().Depositar(numeroCuenta, dinero)) {
            System.out.println("Se ha realizado el deposito de " + dinero + " en la cuenta " + numeroCuenta);
            return true;
        } else {
            System.out.println("No existe la cuenta.");
            return false;
        }
    }

    public boolean nuevaSolicitudCA(Solicitud solicitud) {
        Cliente cliente = solicitud.getCliente();
        //ArrayList<DocumentoCH> docsCliente= cliente.getDocumentos();
        // ! Aunque, por el momento considerare que todo es aprobado, no hara falta usar la funcion
        if (cliente.ContratoCAFirmado) {
            return false;
        }
        if (cliente.getEdad() < 18) {
            return false;
        }
        //contrato no firmado, hay disponibilidad de crear cuenta de ahorro
        SistemaBE.instancia().integrarSolicitud(solicitud);
        System.out.println("El ejecutivo " + nombre + " creo la nueva solicitud N°" + solicitud.getID());
        System.out.println("validando en sistema...");
        SistemaBE.instancia().ValidarSolicitudCA(solicitud);
        if (!solicitud.getEstado()) {//si no salio aprobada
            System.out.println("Solicitud ID: " + solicitud.getID() + " ha sido cerrada.");
            return false;
        }
        solicitud.getContrato().FirmaEjecutivo(this);
        System.out.println("Solicitud ID: " + solicitud.getID() + " ha sido cerrada.");
        return true;
    }

    public CuentaAhorro buscarCuentaAhorro(Cliente c) { //! SI EXISTIESE CLASE PLATAFORMAONLINE QUE FUNCIONE COMO EJECUTIVO,
        //!EJECUTIVO SI O SI TENDRA QUE PASARLE Y ASIGNARLE LA CTA DE AHORRO AL CLIENTE
        if (c.ContratoCAFirmado) return SistemaBE.instancia().buscarCuentaAhorroDelCliente(c);
        //si no entra al if, no cumple por lo que...
        System.out.println("El cliente no posee cuenta de ahorro.");
        return null;
    }


    public boolean nuevaSolicitudCR(Solicitud solicitud) {
        Cliente cliente = solicitud.getCliente();
        //ArrayList<DocumentoCH> docsCliente= cliente.getDocumentos();
        // ! Aunque, por el momento considerare que todo es aprobado, no hara falta usar la funcion
        if (cliente.ContratoCRFirmado) {
            return false;
        }//contrato no firmado, hay disponibilidad de crear cuenta rut
        SistemaBE.instancia().integrarSolicitud(solicitud);
        System.out.println("El ejecutivo " + nombre + " creo la nueva solicitud N°" + solicitud.getID());
        System.out.println("validando en sistema...");
        SistemaBE.instancia().ValidarSolicitudCR(solicitud);
        if (solicitud.getEstado()) {
            return false;
        }
        return true;
    }

    //CUENTA CORRIENTE COSAS PAULA
    public boolean nuevaSolicitudCC(Solicitud solicitud) {
        Cliente cliente = solicitud.getCliente();
        //ArrayList<DocumentoCH> docsCliente= cliente.getDocumentos();
        // ! Aunque, por el momento considerare que todo es aprobado, no hara falta usar la funcion
        if (cliente.ContratoCCFirmado) {
            return false;
        }
        if (cliente.getEdad() < 18) {
            return false;
        }//contrato no firmado, hay disponibilidad de crear cuenta corriente
        SistemaBE.instancia().integrarSolicitud(solicitud);
        System.out.println("El ejecutivo " + nombre + " creo la nueva solicitud N°" + solicitud.getID());
        System.out.println("validando en sistema...");
        SistemaBE.instancia().ValidarSolicitudCC(solicitud);
        if (!solicitud.getEstado()) {//si no salio aprobada
            System.out.println("Solicitud ID: " + solicitud.getID() + " ha sido cerrada.");
            return false;
        }
        solicitud.getContrato().FirmaEjecutivo(this);
        System.out.println("Solicitud ID: " + solicitud.getID() + " ha sido cerrada.");
        return true;
    }

    public CuentaCorriente buscarCuentaCorriente(Cliente c) {
        if (c.ContratoCCFirmado) return SistemaBE.instancia().buscarCuentaCorrienteDelCliente(c);
        System.out.println("El cliente no posee cuenta corriente.");
        return null;
    }
    public CuentaRut buscarCuentaRut(Cliente c) {
        if (c.ContratoCRFirmado) return SistemaBE.instancia().buscarCuentaRutDelCliente(c);
        System.out.println("El cliente no posee cuenta rut.");
        return null;
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
    public void VerSaldoDeMiCuentaRUT(Cliente c){
        CuentaRut CR = SistemaBE.instancia().buscarCuentaRutDelCliente(c);
    }
}