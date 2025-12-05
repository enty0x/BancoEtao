import java.util.ArrayList;
public class ClienteCH {
    private String nombre;
    private int Cedula; //Rut o DNI (Sin puntos ni guion)
    //! -K ES -0
    private int edad;
    private ArrayList<DocumentoCH> documentos;

    //Atributos de la clase ClienteCH
    public boolean ContratoCHFirmado;
    private CuentaAhorro CUENTAAHORRO;

    public ClienteCH(String nombre, int cedula, int edad) {
        this.nombre = nombre;
        this.Cedula = cedula;
        this.edad = edad;
        documentos = new ArrayList<DocumentoCH>();

        ContratoCHFirmado = false;
        CUENTAAHORRO = null;
    }
    public String getNombre() {
        return nombre;
    }
    public void agregarDoc(DocumentoCH doc){
        documentos.add(doc);
    }
    public ArrayList<DocumentoCH> getDocumentos(){
        return documentos;
    }
    public void SolicitarCuentaAhorro(EjecutivoCH ejecutivo){ //
        SolicitudCH solicitud = new SolicitudCH(this, ejecutivo);
        if(ejecutivo.nuevaSolicitudCH(solicitud)){ //Si es True, la cuenta fue creada y hay que marcar el contrato como firmado.

        }else{ //
            System.out.println("No se pudo abrir cuenta de ahorro");
            if(ContratoCHFirmado){
                System.out.println("Razon: Ya existe una existente.");
            }else{
                System.out.println("Razon: No cumple los requisitos.");
            }
        }
    }
}