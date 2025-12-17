import java.io.*;

public class Persistencia {

    private static final String ARCHIVO = "banco_datos.txt";

    // --- GUARDAR ---
    public void guardar(SistemaBE sistema) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            
            // 1. EJECUTIVOS
            for (Ejecutivo e : sistema.ejecutivos) {
                writer.write("EJECUTIVO;" + e.getIDej() + ";" + e.getNombre() + ";" + e.getSucursal());
                writer.newLine();
            }

            // 2. CAJAS VECINAS
            for (CajaVecina cv : sistema.CajasVecinas) {
                writer.write("CAJA;" + cv.id() + ";" + cv.getDineroDisponible());
                writer.newLine();
            }

            // 3. CLIENTES (Guardamos nombre, rut, edad y claves)
            for (Cliente c : sistema.clientes) {
                // Manejo de nulos para claves
                String cOnline = (c.ClaveOnline() == null) ? "null" : c.ClaveOnline();
                String cCajero = (c.ClaveCajero() == null) ? "null" : c.ClaveCajero();
                
                writer.write("CLIENTE;" + c.getNombre() + ";" + c.getCedula() + ";" + c.getEdad() + ";" + cOnline + ";" + cCajero);
                writer.newLine();
            }

            // 4. CUENTAS
            for (Cliente c : sistema.clientes) {
                // CTA AHORRO
                CuentaAhorro ca = sistema.buscarCuentaAhorroDelCliente(c);
                if (ca != null) {
                    writer.write("CTA_AHORRO;" + c.getCedula() + ";" + ca.getNumCuenta() + ";" + ca.getSaldo());
                    writer.newLine();
                }

                // CTA CORRIENTE
                CuentaCorriente cc = sistema.buscarCuentaCorrienteDelCliente(c);
                if (cc != null) {
                    writer.write("CTA_CORRIENTE;" + c.getCedula() + ";" + cc.getNumCuenta() + ";" + cc.getSaldo());
                    writer.newLine();
                }

                // CTA RUT (Sin estado, como acordamos)
                CuentaRut cr = sistema.buscarCuentaRutDelCliente(c);
                if (cr != null) {
                    writer.write("CTA_RUT;" + c.getCedula() + ";" + cr.getNumCuenta() + ";" + cr.getSaldo());
                    writer.newLine();
                }
            }
            
            System.out.println("SISTEMA: Datos guardados correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    // --- CARGAR ---
    public SistemaBE cargar() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return null; 

        SistemaBE sistema = SistemaBE.instancia();
        limpiarSistema(sistema); // Borramos memoria para llenar con el archivo

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if(partes.length < 2) continue;
                
                String tipo = partes[0];

                switch (tipo) {
                    case "EJECUTIVO": // EJECUTIVO;Id;Nombre;Sucursal
                        // Tu constructor: Ejecutivo(String nombre, int IDEj, String sucursal)
                        Ejecutivo eje = new Ejecutivo(partes[2], Integer.parseInt(partes[1]), partes[3]);
                        sistema.ejecutivos.add(eje);
                        break;

                    case "CAJA": // CAJA;Id;Dinero
                        // Tu constructor: CajaVecina(int IDCV, int dineroDisponible)
                        CajaVecina cv = new CajaVecina(Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
                        sistema.CajasVecinas.add(cv);
                        break;

                    case "CLIENTE": // CLIENTE;Nombre;Rut;Edad;ClaveOnline;ClaveCajero
                        // Tu constructor: Cliente(String nombre, int cedula, int edad)
                        Cliente cli = new Cliente(partes[1], Integer.parseInt(partes[2]), Integer.parseInt(partes[3]));
                        
                        // Recuperar claves si existen en el archivo
                        if (partes.length >= 6) {
                            String cOnline = partes[4].equals("null") ? null : partes[4];
                            String cCajero = partes[5].equals("null") ? null : partes[5];
                            cli.setClaves(cCajero, cOnline);
                        }
                        sistema.clientes.add(cli);
                        break;

                    case "CTA_AHORRO": // CTA_AHORRO;RutCliente;NumCuenta;Saldo
                        int rutCA = Integer.parseInt(partes[1]);
                        Cliente duenoCA = buscarClientePorRut(sistema, rutCA);
                        if (duenoCA != null) {
                            // Tu constructor: CuentaAhorro(Cliente cliente, int numeroCuenta)
                            CuentaAhorro ca = new CuentaAhorro(duenoCA, Integer.parseInt(partes[2]));
                            ca.recibeDinero(Integer.parseInt(partes[3])); // Restauramos saldo
                            
                            sistema.cuentasAhorro.add(ca);
                            sistema.cuentas.add(ca);
                            duenoCA.ContratoCAFirmado = true;
                        }
                        break;

                    case "CTA_CORRIENTE": // CTA_CORRIENTE;RutCliente;NumCuenta;Saldo
                        int rutCC = Integer.parseInt(partes[1]);
                        Cliente duenoCC = buscarClientePorRut(sistema, rutCC);
                        if (duenoCC != null) {
                            // Tu constructor: CuentaCorriente(int numero, Cliente c) <--- OJO: ORDEN INVERSO A AHORRO
                            CuentaCorriente cc = new CuentaCorriente(Integer.parseInt(partes[2]), duenoCC);
                            cc.recibeDinero(Integer.parseInt(partes[3]));
                            
                            sistema.cuentasCorrientes.add(cc);
                            sistema.cuentas.add(cc);
                            duenoCC.ContratoCCFirmado = true;
                        }
                        break;
                        
                    case "CTA_RUT": // CTA_RUT;RutCliente;NumCuenta;Saldo
                        int rutCR = Integer.parseInt(partes[1]);
                        Cliente duenoCR = buscarClientePorRut(sistema, rutCR);
                        if (duenoCR != null) {
                            // Tu constructor: CuentaRut(int NumeroCuenta, Cliente c)
                            CuentaRut cr = new CuentaRut(Integer.parseInt(partes[2]), duenoCR);
                            cr.recibeDinero(Integer.parseInt(partes[3]));
                            
                            sistema.cuentasRuts.add(cr);
                            sistema.cuentas.add(cr);
                            duenoCR.ContratoCRFirmado = true;
                        }
                        break;
                }
            }
            System.out.println("SISTEMA: Datos recuperados exitosamente.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar archivo: " + e.getMessage());
        }
        return sistema;
    }

    // --- MÉTODOS AUXILIARES ---
    private Cliente buscarClientePorRut(SistemaBE sis, int rut) {
        for (Cliente c : sis.clientes) {
            if (c.getCedula() == rut) return c;
        }
        return null;
    }

    private void limpiarSistema(SistemaBE sis) {
        sis.clientes.clear();
        sis.ejecutivos.clear();
        sis.CajasVecinas.clear();
        sis.cuentas.clear();
        sis.cuentasAhorro.clear();
        sis.cuentasCorrientes.clear();
        sis.cuentasRuts.clear();
        sis.solicitudes.clear();
        sis.contratos.clear();
    }
}