import java.time.LocalDate;

public class SolicitudCH {
    private int ID;
    private boolean Estado;
    private LocalDate fecha;

    private ClienteCH cliente;
    private Contrato contrato;
    private EjecutivoCH ejecutivo;

    public SolicitudCH(ClienteCH cliente, EjecutivoCH ejecutivo) { //version primitiva creada por el cliente
        this.Estado = false;
        this.fecha = LocalDate.now();
        this.cliente = cliente;
        this.ejecutivo = ejecutivo;
    }
    //|---------ESTO LO MANEJA SISTEMA---------|
    public void setID(int ID) { //metodo para asignar un ID, asignado por sistema.
        this.ID = ID;
    }
    public void setEstado(boolean Estado) { //metodo para asignar un estado, asignado por sistema.
        this.Estado = Estado;
    }public void setContrato(Contrato contrato) { //metodo para asignar un contrato, asignado por sistema.
        this.contrato = contrato;
    }

    public int getID() {
        return ID;
    }public boolean getEstado() {
        return Estado;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public ClienteCH getCliente() {
        return cliente;
    }
    public EjecutivoCH getEjecutivo() {
        return ejecutivo;
    }
    public Contrato getContrato() {
        return contrato;
    }
}