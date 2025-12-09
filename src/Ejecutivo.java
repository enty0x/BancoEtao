public class Ejecutivo {
    private String nombre;
    private int IDej;
    private String sucursal;
    public Ejecutivo(String nombre, int IDEj, String sucursal) {
        this.nombre = nombre;
        this.IDej = IDEj;
        this.sucursal = sucursal;
    }
    public boolean equals(Ejecutivo ejecutivo){
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
    public boolean nuevaSolicitudCH(Solicitud solicitud) {
        Cliente cliente = solicitud.getCliente();
        //ArrayList<DocumentoCH> docsCliente= cliente.getDocumentos();
        // ! Aunque, por el momento considerare que todo es aprobado, no hara falta usar la funcion
        if(cliente.ContratoCAFirmado){
            return false;
        }//contrato no firmado, hay disponibilidad de crear cuenta de ahorro
        Sistema.instancia().integrarSolicitud(solicitud);
        System.out.println("El ejecutivo " + nombre + " creo la nueva solicitud N°" + solicitud.getID());
        System.out.println("validando en sistema...");
        Sistema.instancia().ValidarSolicitudCA(solicitud);
        if(!solicitud.getEstado()){//si no salio aprobada
            System.out.println("Solicitud ID: "+solicitud.getID()+ " ha sido cerrada.");
            return false;
        }
        solicitud.getContrato().FirmaEjecutivo(this);
        System.out.println("Solicitud ID: "+solicitud.getID()+ " ha sido cerrada.");
        return true;
    }public CuentaAhorro buscarCuentaAhorro(Cliente c){ //! SI EXISTIESE CLASE PLATAFORMAONLINE QUE FUNCIONE COMO EJECUTIVO,
        //!EJECUTIVO SI O SI TENDRA QUE PASARLE Y ASIGNARLE LA CTA DE AHORRO AL CLIENTE
        if(c.ContratoCAFirmado) return Sistema.instancia().buscarCuentaAhorroDelCliente(c);
        //si no entra al if, no cumple por lo que...
        System.out.println("El cliente no posee cuenta de ahorro.");
        return null;
    }


    public boolean nuevaSolicitudCR(Solicitud solicitud) {
        Cliente cliente = solicitud.getCliente();
        //ArrayList<DocumentoCH> docsCliente= cliente.getDocumentos();
        // ! Aunque, por el momento considerare que todo es aprobado, no hara falta usar la funcion
        if(cliente.ContratoCRFirmado){
            return false;
        }//contrato no firmado, hay disponibilidad de crear cuenta rut
        Sistema.instancia().integrarSolicitud(solicitud);
        System.out.println("El ejecutivo " + nombre + " creo la nueva solicitud N°" + solicitud.getID());
        System.out.println("validando en sistema...");
        Sistema.instancia().ValidarSolicitudCA(solicitud);
        if(solicitud.getEstado()){
            return false;
        }
        return true;
    }

    //CUENTA CORRIENTE COSAS PAULA
    public boolean nuevaSolicitudCC(Solicitud solicitud) {
        Cliente cliente = solicitud.getCliente();
        //ArrayList<DocumentoCH> docsCliente= cliente.getDocumentos();
        // ! Aunque, por el momento considerare que todo es aprobado, no hara falta usar la funcion
        if(cliente.ContratoCCFirmado){
            return false;
        }//contrato no firmado, hay disponibilidad de crear cuenta de ahorro
        Sistema.instancia().integrarSolicitud(solicitud);
        System.out.println("El ejecutivo " + nombre + " creo la nueva solicitud N°" + solicitud.getID());
        System.out.println("validando en sistema...");
        Sistema.instancia().ValidarSolicitudCC(solicitud);
        if(!solicitud.getEstado()){//si no salio aprobada
            System.out.println("Solicitud ID: "+solicitud.getID()+ " ha sido cerrada.");
            return false;
        }
        solicitud.getContrato().FirmaEjecutivo(this);
        System.out.println("Solicitud ID: "+solicitud.getID()+ " ha sido cerrada.");
        return true;
    }
    public CuentaCorriente buscarCuentaCorriente(Cliente c){
        if(c.ContratoCCFirmado) return Sistema.instancia().buscarCuentaCorrienteDelCliente(c);
        System.out.println("El cliente no posee cuenta corriente.");
        return null;
    }
}