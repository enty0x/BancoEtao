import java.util.ArrayList;

public class Sistema {
    private static Sistema sistema;
    private Sistema() {}
    public static Sistema instancia() {
        if (sistema == null) {
            sistema = new Sistema();
        }
        return sistema;
    }

    ArrayList<SolicitudCH> solicitudes = new ArrayList<SolicitudCH>();
    ArrayList<EjecutivoCH> ejecutivos = new ArrayList<EjecutivoCH>();
    ArrayList<Contrato> contratos = new ArrayList<Contrato>();
    ArrayList<ClienteCH> clientes = new ArrayList<ClienteCH>(); //! OJO, SOLO LOS CLIENTES QUE SI TIENEN CUENTAS
    ArrayList<CuentaAhorro> cuentasAhorro = new ArrayList<CuentaAhorro>();
    EjecutivoCH crearEjecutivo(String nombre, String sucursal){
        EjecutivoCH ejecutivo = new EjecutivoCH(nombre, ejecutivos.size() + 1, sucursal);
        ejecutivos.add(ejecutivo);
        return ejecutivo;
    }
    public void asignarSolicitudCH(SolicitudCH solicitud) {
        solicitud.setID(solicitudes.size() + 1);
        solicitudes.add(solicitud);
    }
    public boolean ValidarSolicitudCH(SolicitudCH solicitudCH) {
        //! considerar que si paso aca, es porque no tenia cuenta de ahorro
        ClienteCH clienteSolicitante = solicitudCH.getCliente();
        // if (EvaluarDocumentosCH(solicitudCH))...;
        //! considerando que cumple todo lo necesario para que sea una solicitud válida
        solicitudCH.setEstado(true); //aprobado
        System.out.println("Sistema ha aprobado Solicitud");

        //generar cuenta de ahorro, su

        Contrato contratoNuevo = nuevoContrato(solicitudCH);
        solicitudCH.setContrato(contratoNuevo);

        return true;
    }
    private Contrato nuevoContrato(SolicitudCH solicitud) {
        Contrato contrato = new Contrato(contratos.size()+1, "CH", solicitud.getCliente(), solicitud.getEjecutivo());
        contratos.add(contrato);
        return contrato;
    }
}
