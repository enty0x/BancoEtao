import java.io.Serializable;
import java.time.LocalDate;

public class Documento implements Serializable {
    private String IdDoc;
    private String TipoDoc;
    private LocalDate FechaVencimiento;
    public Documento(String IdDoc, String Tipo) {
        this.IdDoc = IdDoc;
        this.TipoDoc = Tipo;
        this.FechaVencimiento = null; //No tiene vencimiento, ejemplo: Documentacion de fondos
    }
    public Documento(String IdDoc, String Tipo, LocalDate FechaVencimiento) {
        this.IdDoc = IdDoc;
        this.TipoDoc = Tipo;
        this.FechaVencimiento = FechaVencimiento;
    }
    boolean Vencido(){
        if (FechaVencimiento == null) {
            return false;
        }
        return FechaVencimiento.isBefore(LocalDate.now());
    }
    public String getIdDoc() {
        return IdDoc;
    }
    public String getTipoDoc() {
        return TipoDoc;
    }
    public LocalDate getFechaVencimiento() { //Como LocalDate, no como String
        return FechaVencimiento;
    }
}