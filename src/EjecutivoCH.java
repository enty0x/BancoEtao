public class EjecutivoCH {
    private String nombre;
    private int IDej;
    private String sucursal;
    public EjecutivoCH(String nombre, int IDEj, String sucursal) {
        this.nombre = nombre;
        this.IDej = IDEj;
        this.sucursal = sucursal;
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
    public boolean nuevaSolicitudCH(SolicitudCH solicitud) {
        ClienteCH cliente = solicitud.getCliente();
        //ArrayList<DocumentoCH> docsCliente= cliente.getDocumentos();
        // ! Aunque, por el momento considerare que todo es aprobado, no hara falta usar la funcion
        if(cliente.ContratoCHFirmado){
            return false;
        }//contrato no firmado, hay disponibilidad de crear cuenta de ahorro
        Sistema.instancia().asignarSolicitudCH(solicitud);
        System.out.println("El ejecutivo " + nombre + " creo la nueva solicitud N°" + solicitud.getID());
        System.out.println("validando en sistema...");
        Sistema.instancia().ValidarSolicitudCH(solicitud);
        if(solicitud.getEstado()){
            return false;
        }
        return true;
    }
}