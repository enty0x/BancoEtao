import java.io.Serializable;
import java.util.ArrayList;
public class Cliente implements Serializable {
    private String nombre;
    private int Cedula; //Rut o DNI (Sin puntos ni guion)
    //! -K ES -0
    private int edad;
    private ArrayList<Documento> documentos;

    //Atributos sobre Cuentas
    //!ESTAS CLAVES SERAN PARA LOS TIPOS 3 DE CUENTA
    private String ClaveOnline;
    private String ClaveCajero; // SOLO 4 DIGITOS, COMO STRING, PORQ EN INT EL 0 PRINCIPAL DE UN "0581" SALE COMO "581"
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

        ClaveCajero = null;
        ClaveOnline = null;

        ContratoCAFirmado = false;
        CUENTAAHORRO = null;
        ContratoCRFirmado = false;
        CUENTARUT = null;
        ContratoCCFirmado = false;
        CUENTACORRIENTE = null;
    }
    //!PARA LAS CLAVES:
    public void setClaves(String claveCajero, String claveOnline){
        ClaveCajero = claveCajero;
        ClaveOnline = claveOnline;
    }
    public String ClaveOnline(){
        return ClaveOnline;
    }public String ClaveCajero(){
        return ClaveCajero;
    }

    public void SolicitarCuentaAhorro(Ejecutivo ejecutivo){ //
        Solicitud solicitud = new Solicitud(this, ejecutivo);
        if(ejecutivo.nuevaSolicitudCH(solicitud)){ //Si es True, la cuenta fue creada y hay que marcar el contrato como firmado.
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

    public void Depositar(CajaVecina cv, int monto, int ncuenta){
        if(cv.DepositarACuenta(monto, ncuenta)){
            // saltardefelicidad()
        }else{
            // llorar()
        }
    }public void VerMontoCA(CajaVecina cv){
        cv.VerSaldoDeMiCuentaDeAhorro(this);
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
    public int getEdad() {
        return  this.edad;
    }
}