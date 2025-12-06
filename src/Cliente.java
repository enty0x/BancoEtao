import java.util.ArrayList;
public class Cliente {
    private String nombre;
    private int Cedula; //Rut o DNI (Sin puntos ni guion)
    //! -K ES -0
    private int edad;
    private ArrayList<Documento> documentos;

    //Atributos sobre Cuentas
    //cuenta ahorro
    public boolean ContratoCAFirmado;
    private CuentaAhorro CUENTAAHORRO;
    //cuenta rut
    public boolean ContratoCRFirmado;
    private CuentaRut CUENTARUT;
    //cuenta corriente
    public boolean ContratoCCFirmado;
    private CuentaCorriente CUENTACORRIENTE;

    public Cliente(String nombre, int cedula, int edad) {
        this.nombre = nombre;
        this.Cedula = cedula;
        this.edad = edad;
        documentos = new ArrayList<Documento>();

        ContratoCAFirmado = false;
        CUENTAAHORRO = null;
    }

    public void SolicitarCuentaAhorro(Ejecutivo ejecutivo){ //
        Solicitud solicitud = new Solicitud(this, ejecutivo);
        boolean exito = ejecutivo.nuevaSolicitudCH(solicitud);
        if(exito){ //Si es True, la cuenta fue creada y hay que marcar el contrato como firmado.
            if(solicitud.getEstado()){//true = contrato firmado por ejecutivo, hay cuenta de ahorro yupiii
                solicitud.getContrato().FirmaCliente(this);
                ContratoCAFirmado = true;
                CUENTAAHORRO = ejecutivo.buscarCuentaAhorro(this);
                System.out.println("El cliente " + nombre + " ahora posee una cuenta de ahorro.");
            }//!aun no existe la posiblidad de no tener documentos validos, por lo que else no existe.
        }else{ //
            System.out.println("No se pudo abrir cuenta de ahorro");
            if(ContratoCAFirmado){
                System.out.println("Razon: Ya existe una existente.");
            }else{
                System.out.println("Razon: No cumple los requisitos.");
            }
        }
    }
    //CUENTA CORRIENTE Paula
    public void SolicitarCuentaCorriente(Ejecutivo ejecutivo){
        Solicitud solicitud = new Solicitud(this, ejecutivo);
        boolean exito = ejecutivo.nuevaSolicitudCC(solicitud);
        if(exito){
            if(solicitud.getEstado()){
                solicitud.getContrato().FirmaCliente(this);
                ContratoCCFirmado = true;
                CUENTACORRIENTE = ejecutivo.buscarCuentaCorriente(this);
                System.out.println("El cliente "+nombre + " ahora posee una cuenta corriente");
            }
        }else{
            System.out.println("No se pudo abrir cuenta corriente");
            if(ContratoCRFirmado){
                System.out.println("Razon: Ya existe una existente.");
            }else{
                System.out.println("Razon: No cumple los requisitos.");
            }
        }
    }

    public void Depositar(CajaVecina cv, int monto, int ncuenta){
        if(cv.DepositarACuenta(monto, ncuenta)){
            // saltardefelicidad()
        }else{
            // llorar()
        }
    }

    //!extras
    public String getNombre() {
        return nombre;
    }
    public int getCedula() {return Cedula;}
    public void agregarDoc(Documento doc){
        documentos.add(doc);
    }
    public ArrayList<Documento> getDocumentos(){
        return documentos;
    }
    public boolean equals(Cliente cliente){
        return (this.Cedula == cliente.Cedula);
    }
}