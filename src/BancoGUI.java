import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.OutputStream;
import java.io.PrintStream;

public class BancoGUI extends JFrame {

    private JComboBox<String> comboClientes;
    private JComboBox<String> comboEjecutivos;
    private JComboBox<String> comboCajas;
    
    // Tabla y Modelo
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    
    // Barra de estado inferior
    private JLabel lblEstado;
    
    // Referencia al sistema
    private SistemaBE sistema;

    // --- CAMPOS DE TEXTO y COMBOS ---
    // Pestaña Creación
    private JTextField txtNombreCli, txtRutCli, txtEdadCli;
    
    // Pestaña Caja Vecina (Depósitos)
    private JTextField txtMontoDep;
    private JComboBox<String> comboCtaDestDep;
    
    // Pestaña Caja Vecina (Transferencias)
    private JComboBox<String> comboCtaOrigen;
    private JComboBox<String> comboCtaDestino;
    private JTextField txtMontoTrans, txtClave;

    public BancoGUI() {
        // 1. Cargar datos
        Persistencia p = new Persistencia();
        SistemaBE datosCargados = p.cargar();
        sistema = SistemaBE.instancia();

        setTitle("Banco Etao - Sistema de Gestión Completo");
        setSize(1200, 750);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarYSalir();
            }
        });

        setLayout(new BorderLayout());

        // --- PANEL SUPERIOR: CREACIÓN ---
        JPanel panelCreacion = new JPanel(new GridLayout(1, 3, 10, 10));
        panelCreacion.setBorder(BorderFactory.createTitledBorder("Gestión Inicial"));

        JButton btnCrearEjecutivo = new JButton("1. Crear Ejecutivo");
        btnCrearEjecutivo.addActionListener(e -> crearEjecutivo(true));
        panelCreacion.add(btnCrearEjecutivo);

        JButton btnCrearCaja = new JButton("2. Crear Caja Vecina");
        btnCrearCaja.addActionListener(e -> crearCajaVecina());
        panelCreacion.add(btnCrearCaja);

        JPanel panelCliente = new JPanel(new FlowLayout());
        txtNombreCli = new JTextField(8);
        txtRutCli = new JTextField(7);
        txtEdadCli = new JTextField(3);
        
        JButton btnCrearCliente = new JButton("3. Crear");
        btnCrearCliente.addActionListener(e -> crearCliente());
        
        panelCliente.add(new JLabel("Nom:")); panelCliente.add(txtNombreCli);
        panelCliente.add(new JLabel("Rut:")); panelCliente.add(txtRutCli);
        panelCliente.add(new JLabel("Edad:")); panelCliente.add(txtEdadCli);
        panelCliente.add(btnCrearCliente);
        panelCreacion.add(panelCliente);

        add(panelCreacion, BorderLayout.NORTH);

        // --- PANEL CENTRAL: PESTAÑAS ---
        JTabbedPane tabs = new JTabbedPane();

        // Pestaña 1: Solicitudes
        JPanel tabSolicitudes = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        comboClientes = new JComboBox<>();
        comboClientes.addActionListener(e -> actualizarComboOrigen());

        comboEjecutivos = new JComboBox<>();
        
        gbc.gridx=0; gbc.gridy=0; tabSolicitudes.add(new JLabel("Cliente:"), gbc);
        gbc.gridx=1; gbc.gridy=0; tabSolicitudes.add(comboClientes, gbc);
        
        gbc.gridx=0; gbc.gridy=1; tabSolicitudes.add(new JLabel("Ejecutivo:"), gbc);
        gbc.gridx=1; gbc.gridy=1; tabSolicitudes.add(comboEjecutivos, gbc);
        
        JButton btnSolCA = new JButton("Solicitar Cuenta Ahorro");
        btnSolCA.setBackground(new Color(220, 240, 255));
        btnSolCA.addActionListener(e -> solicitarCuenta("AHORRO"));
        
        JButton btnSolCC = new JButton("Solicitar Cuenta Corriente");
        btnSolCC.setBackground(new Color(255, 240, 220));
        btnSolCC.addActionListener(e -> solicitarCuenta("CORRIENTE"));

        JButton btnSolCR = new JButton("Solicitar Cuenta RUT");
        btnSolCR.setBackground(new Color(220, 255, 220));
        btnSolCR.addActionListener(e -> solicitarCuenta("RUT"));

        gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=2; tabSolicitudes.add(btnSolCA, gbc);
        gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2; tabSolicitudes.add(btnSolCC, gbc);
        gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2; tabSolicitudes.add(btnSolCR, gbc);

        tabs.addTab("Mesón de Atención", tabSolicitudes);

        // Pestaña 2: Caja Vecina
        JPanel tabCaja = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // --- Columna Izquierda: Depósitos ---
        JPanel panelDepositos = new JPanel(new GridLayout(5, 1, 5, 5));
        panelDepositos.setBorder(BorderFactory.createTitledBorder("Depósitos"));
        
        comboCajas = new JComboBox<>();
        txtMontoDep = new JTextField();
        comboCtaDestDep = new JComboBox<>(); 
        
        JButton btnDepositar = new JButton("Realizar Depósito");
        btnDepositar.addActionListener(e -> realizarDeposito());
        
        panelDepositos.add(new JLabel("Seleccionar Caja:")); panelDepositos.add(comboCajas);
        panelDepositos.add(new JLabel("Monto:")); panelDepositos.add(txtMontoDep);
        panelDepositos.add(new JLabel("Cta Destino:")); panelDepositos.add(comboCtaDestDep); 
        panelDepositos.add(new JLabel("")); panelDepositos.add(btnDepositar);

        // --- Columna Derecha: Transferencias ---
        JPanel panelTransfer = new JPanel(new GridLayout(6, 2, 5, 5));
        panelTransfer.setBorder(BorderFactory.createTitledBorder("Transferencias"));
        
        comboCtaOrigen = new JComboBox<>();
        comboCtaDestino = new JComboBox<>();
        
        txtMontoTrans = new JTextField();
        txtClave = new JTextField();
        JButton btnTransferir = new JButton("Transferir");
        
        btnTransferir.addActionListener(e -> realizarTransferencia());
        
        panelTransfer.add(new JLabel("Cta Origen:")); panelTransfer.add(comboCtaOrigen);
        panelTransfer.add(new JLabel("Cta Destino:")); panelTransfer.add(comboCtaDestino);
        panelTransfer.add(new JLabel("Monto:")); panelTransfer.add(txtMontoTrans);
        panelTransfer.add(new JLabel("Clave Cajero:")); panelTransfer.add(txtClave);
        panelTransfer.add(new JLabel("")); panelTransfer.add(btnTransferir);
        panelTransfer.add(new JLabel("Nota:")); 
        panelTransfer.add(new JLabel("<html><size='2'>Cta Origen depende del<br>Cliente en Pestaña 1.</html>"));

        tabCaja.add(panelDepositos);
        tabCaja.add(panelTransfer);

        tabs.addTab("Caja Vecina", tabCaja);

        // Pestaña 3: Tabla
        JPanel tabLista = new JPanel(new BorderLayout());
        String[] columnas = {
            "Nombre", "RUT", "Edad", 
            "Clave Online", "Clave Cajero", 
            "Cta. Ahorro ($)", "Cta. Corriente ($)", "Cta. RUT ($)"
        };
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaClientes);
        JButton btnRefrescar = new JButton("🔄 Actualizar Tabla");
        btnRefrescar.addActionListener(e -> cargarDatosTabla());
        tabLista.add(scrollTabla, BorderLayout.CENTER);
        tabLista.add(btnRefrescar, BorderLayout.SOUTH);
        
        tabs.addTab("Base de Clientes y Saldos", tabLista);

        add(tabs, BorderLayout.CENTER);

        // --- PANEL INFERIOR ---
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEstado.setBorder(BorderFactory.createEtchedBorder());
        panelEstado.setBackground(Color.LIGHT_GRAY);
        lblEstado = new JLabel("Estado: Sistema iniciado.");
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelEstado.add(lblEstado);
        add(panelEstado, BorderLayout.SOUTH);

        capturarSalidaSistema();
        actualizarCombosGlobales(); // Carga inicial
        cargarDatosTabla();
        
        if (sistema.ejecutivos.isEmpty()) crearEjecutivo(false);
    }

    // --- MÉTODOS DE CREACIÓN ---

    private void crearCliente() {
        String nombre = txtNombreCli.getText().trim();
        String rutStr = txtRutCli.getText().trim();
        String edadStr = txtEdadCli.getText().trim();

        if (nombre.isEmpty() || rutStr.isEmpty() || edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: Se deben llenar los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int rut = Integer.parseInt(rutStr);
            int edad = Integer.parseInt(edadStr);
            
            if (edad < 18) {
                JOptionPane.showMessageDialog(this, "Error: El cliente debe ser mayor de 18 años.", "Edad Inválida", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Cliente c : sistema.clientes) {
                if (c.getCedula() == rut) {
                    JOptionPane.showMessageDialog(this, "Ya existe un cliente con ese RUT.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            Cliente nuevo = new Cliente(nombre, rut, edad);
            sistema.clientes.add(nuevo); 
            actualizarCombosGlobales(); 
            cargarDatosTabla();
            JOptionPane.showMessageDialog(this, "Cliente creado correctamente.");
            
            txtNombreCli.setText("");
            txtRutCli.setText("");
            txtEdadCli.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "RUT y Edad deben ser números.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- LÓGICA DE TRANSACCIONES ---

    private void realizarDeposito() {
        String montoStr = txtMontoDep.getText().trim();
        String seleccionDestino = (String) comboCtaDestDep.getSelectedItem(); 

        if (montoStr.isEmpty() || seleccionDestino == null) {
            JOptionPane.showMessageDialog(this, "Error: Se deben llenar los campos.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int monto = Integer.parseInt(montoStr);
            
            // --- VALIDACIÓN DE MONTO NEGATIVO O CERO ---
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "Error: El monto debe ser mayor a 0.", "Monto Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int nCuenta = extraerNumeroCuenta(seleccionDestino);
            CajaVecina cv = obtenerCajaDelCombo();
            
            if (cv != null) {
                if(cv.DepositarACuenta(monto, nCuenta)) {
                    JOptionPane.showMessageDialog(this, "Depósito exitoso.");
                    cargarDatosTabla();
                    actualizarCombosGlobales(); 
                    txtMontoDep.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: No se pudo realizar el depósito.", "Fallo", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error: Seleccione una Caja Vecina.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Monto inválido (Solo números).", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarTransferencia() {
        String montoStr = txtMontoTrans.getText().trim();
        String clave = txtClave.getText().trim();
        
        String seleccionOrigen = (String) comboCtaOrigen.getSelectedItem();
        String seleccionDestino = (String) comboCtaDestino.getSelectedItem();

        if (seleccionOrigen == null || seleccionDestino == null || montoStr.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: Se deben llenar los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int nOrigen = extraerNumeroCuenta(seleccionOrigen);
            int nDest = extraerNumeroCuenta(seleccionDestino);
            int monto = Integer.parseInt(montoStr);
            
            // --- VALIDACIÓN DE MONTO NEGATIVO O CERO ---
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "Error: El monto debe ser mayor a 0.", "Monto Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Cliente clienteActual = obtenerClienteDelCombo();
            CajaVecina cv = obtenerCajaDelCombo();
            
            if (clienteActual == null || cv == null) {
                JOptionPane.showMessageDialog(this, "Error interno: Cliente o Caja no seleccionados.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (cv.TransferirDinero(clienteActual, nOrigen, nDest, monto, clave)) {
                JOptionPane.showMessageDialog(this, "Transferencia Exitosa!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosTabla();
                txtMontoTrans.setText("");
                txtClave.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Transferencia Fallida.\nVerifique saldo, clave o pertenencia de la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Formato de número inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al procesar la cuenta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Helper para sacar el ID de "N° 123 - Nombre..."
    private int extraerNumeroCuenta(String textoCombo) {
        String[] partes = textoCombo.split(" ");
        return Integer.parseInt(partes[1]);
    }

    // --- LÓGICA DE SOLICITUDES ---
    private void solicitarCuenta(String tipo) {
        Cliente cliente = obtenerClienteDelCombo();
        Ejecutivo ejecutivo = obtenerEjecutivoDelCombo();

        if (cliente == null || ejecutivo == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar Cliente y Ejecutivo.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exito = false;
        if (tipo.equals("AHORRO")) {
            if (!cliente.ContratoCAFirmado) {
                cliente.SolicitarCuentaAhorro(ejecutivo);
                exito = cliente.ContratoCAFirmado;
            } else {
                JOptionPane.showMessageDialog(this, "Aviso: El cliente YA TIENE Cuenta Ahorro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else if (tipo.equals("CORRIENTE")) {
            if (!cliente.ContratoCCFirmado) {
                cliente.SolicitarCuentaCorriente(ejecutivo);
                exito = cliente.ContratoCCFirmado;
            } else {
                JOptionPane.showMessageDialog(this, "Aviso: El cliente YA TIENE Cuenta Corriente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else if (tipo.equals("RUT")) {
            if (!cliente.ContratoCRFirmado) {
                cliente.SolicitarCuentaRut(ejecutivo);
                exito = cliente.ContratoCRFirmado;
            } else {
                JOptionPane.showMessageDialog(this, "Aviso: El cliente YA TIENE Cuenta RUT.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        if (exito) {
            String mensaje = "¡Cuenta creada exitosamente!\n\n" +
                             "Cliente: " + cliente.getNombre() + "\n" +
                             "Clave Online: " + cliente.ClaveOnline() + "\n" +
                             "Clave Cajero: " + cliente.ClaveCajero();
            
            JOptionPane.showMessageDialog(this, mensaje, "Éxito - Guarde sus claves", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosTabla();
            actualizarCombosGlobales(); 
        } else {
            if (cliente.getEdad() < 18) {
                JOptionPane.showMessageDialog(this, "Denegada: El cliente es MENOR DE EDAD.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "La solicitud fue rechazada por el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- GESTIÓN DE COMBOS INTELIGENTES ---

    private void actualizarCombosGlobales() {
        comboEjecutivos.removeAllItems();
        for (Ejecutivo e : sistema.ejecutivos) comboEjecutivos.addItem(e.getNombre() + " (ID:" + e.getIDej() + ")");
        
        comboCajas.removeAllItems();
        for (CajaVecina cv : sistema.CajasVecinas) comboCajas.addItem("Caja N° " + cv.id());
        
        Object seleccionPrevia = comboClientes.getSelectedItem();
        
        ActionListener[] listeners = comboClientes.getActionListeners();
        for(ActionListener al : listeners) comboClientes.removeActionListener(al);
        
        comboClientes.removeAllItems();
        for (Cliente c : sistema.clientes) comboClientes.addItem(c.getNombre() + " (" + c.getCedula() + ")");
        
        if (seleccionPrevia != null) {
            for(int i=0; i<comboClientes.getItemCount(); i++) {
                if(comboClientes.getItemAt(i).equals(seleccionPrevia)) {
                    comboClientes.setSelectedIndex(i);
                    break;
                }
            }
        }
        for(ActionListener al : listeners) comboClientes.addActionListener(al);

        actualizarCombosDestino(); 
        actualizarComboOrigen();
    }

    private void actualizarCombosDestino() {
        comboCtaDestino.removeAllItems();
        comboCtaDestDep.removeAllItems(); 
        
        for (Cuenta c : sistema.cuentas) {
            String tipo = "Cta";
            if (c instanceof CuentaRut) tipo = "RUT";
            else if (c instanceof CuentaAhorro) tipo = "Ahorro";
            else if (c instanceof CuentaCorriente) tipo = "Corriente";
            
            String item = "N° " + c.getNumCuenta() + " - " + c.getClient().getNombre() + " (" + tipo + ")";
            
            comboCtaDestino.addItem(item);
            comboCtaDestDep.addItem(item); 
        }
    }

    private void actualizarComboOrigen() {
        comboCtaOrigen.removeAllItems();
        Cliente clienteActual = obtenerClienteDelCombo();
        if (clienteActual == null) return;
        
        String nombre = clienteActual.getNombre();

        CuentaAhorro ca = sistema.buscarCuentaAhorroDelCliente(clienteActual);
        if (ca != null) comboCtaOrigen.addItem("N° " + ca.getNumCuenta() + " - " + nombre + " (Ahorro)");
        
        CuentaCorriente cc = sistema.buscarCuentaCorrienteDelCliente(clienteActual);
        if (cc != null) comboCtaOrigen.addItem("N° " + cc.getNumCuenta() + " - " + nombre + " (Corriente)");
        
        CuentaRut cr = sistema.buscarCuentaRutDelCliente(clienteActual);
        if (cr != null) comboCtaOrigen.addItem("N° " + cr.getNumCuenta() + " - " + nombre + " (RUT)");
    }

    // --- HELPERS BÁSICOS ---
    
    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); 
        for (Cliente c : sistema.clientes) {
            String ahorroInfo = "---";
            String corrienteInfo = "---";
            String rutInfo = "---";
            
            CuentaAhorro ca = sistema.buscarCuentaAhorroDelCliente(c);
            if (ca != null) ahorroInfo = "$" + ca.getSaldo() + " (N°" + ca.getNumCuenta() + ")";
            CuentaCorriente cc = sistema.buscarCuentaCorrienteDelCliente(c);
            if (cc != null) corrienteInfo = "$" + cc.getSaldo() + " (N°" + cc.getNumCuenta() + ")";
            CuentaRut cr = sistema.buscarCuentaRutDelCliente(c);
            if (cr != null) rutInfo = "$" + cr.getSaldo() + " (N°" + cr.getNumCuenta() + ")";
            
            String cOnline = (c.ClaveOnline() != null) ? c.ClaveOnline() : "---";
            String cCajero = (c.ClaveCajero() != null) ? c.ClaveCajero() : "---";

            modeloTabla.addRow(new Object[]{
                c.getNombre(), c.getCedula(), c.getEdad(), 
                cOnline, cCajero, 
                ahorroInfo, corrienteInfo, rutInfo
            });
        }
    }
    
    private void crearEjecutivo(boolean mostrarMensaje) {
        String nombre = "Ejecutivo " + (sistema.ejecutivos.size() + 1);
        sistema.crearEjecutivo(nombre, "Sucursal Central");
        actualizarCombosGlobales();
        if(mostrarMensaje) JOptionPane.showMessageDialog(this, "Ejecutivo creado: " + nombre);
    }
    private void crearCajaVecina() {
        sistema.crearCajaVecina(500000);
        actualizarCombosGlobales();
        JOptionPane.showMessageDialog(this, "Caja Vecina creada.");
    }
    private void guardarYSalir() {
        if (JOptionPane.showConfirmDialog(this, "¿Guardar cambios antes de salir?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
            new Persistencia().guardar(sistema);
            JOptionPane.showMessageDialog(this, "Datos guardados.");
            System.exit(0);
        } else {
            System.exit(0);
        }
    }
    
    private Cliente obtenerClienteDelCombo() {
        if (comboClientes.getSelectedIndex() < 0) return null;
        return sistema.clientes.get(comboClientes.getSelectedIndex());
    }
    private Ejecutivo obtenerEjecutivoDelCombo() {
        if (comboEjecutivos.getSelectedIndex() < 0) return null;
        return sistema.ejecutivos.get(comboEjecutivos.getSelectedIndex());
    }
    private CajaVecina obtenerCajaDelCombo() {
        if (comboCajas.getSelectedIndex() < 0) return null;
        return sistema.CajasVecinas.get(comboCajas.getSelectedIndex());
    }
    private void capturarSalidaSistema() {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override public void write(int b) { }
            @Override public void write(byte[] b, int off, int len) {
                String msj = new String(b, off, len).trim();
                if (!msj.isEmpty()) SwingUtilities.invokeLater(() -> lblEstado.setText("Mensaje: " + msj));
            }
        });
        System.setOut(printStream);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BancoGUI().setVisible(true));
    }
}