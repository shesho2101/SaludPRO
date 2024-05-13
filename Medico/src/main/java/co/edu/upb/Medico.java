package co.edu.upb;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Calendar;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import principal.DAO.Abstract.CallDAO;
import principal.dominio.cita.CitaServices;
import principal.dominio.historialClinico.HistorialClinicoServices;
import principal.dominio.medicamento.Medicamento;
import principal.dominio.medicamento.MedicamentoServices;

public class Medico extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelActual; // Panel actual que se va a mostrar
    private JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private int panelPositionX = 770;
    //Base de datos
    private HistorialClinicoServices hcs;
    private CitaServices cs;

    //Historia Clinica
    private JButton btnHistorial;
    private JTextField textFieldDocumentoHis;

    //Calendario
    private JCalendar calendario;
    private JButton btnCalendar;
    private JTable tablaCitas;
    private DefaultTableModel dt;
    private JScrollPane scroll;

    //Reporte
    private JButton btnReportarCita;
    private JTextField textFieldDocumentoRep;
    private JTextField dateRep;

    MedicamentoServices medicamentoServices = new MedicamentoServices();



    public Medico() {
        //Inicializacion base de datos
        this.hcs = new HistorialClinicoServices();
        this.cs = new CitaServices();

        //Historial
        this.btnHistorial = new JButton();
        this.textFieldDocumentoHis = new JTextField();

        //Calendario
        this.calendario = new JCalendar();
        this.btnCalendar = new JButton();
        this.tablaCitas = new JTable();
        this.scroll = new JScrollPane(tablaCitas);
        this.dt = new DefaultTableModel();

        //Reporte
        this.btnReportarCita = new JButton();
        this.dateRep = new JTextField();
        this.textFieldDocumentoRep = new JTextField();

        FrameController.registerFrame("MedicoFrame", this);


        setTitle("IPS Salud Pro - Medico");
        setSize(1600, 900);
        setResizable(false); // Desactivar la capacidad de redimensionamiento
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(150, 198, 198));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        buttons = new JButton[3];

        buttons[0] = createButton("Buscar historia clínica", 640, 320);
        buttons[1] = createButton("Ver agenda", 640, 400);
        buttons[2] = createButton("Reportar paciente", 640, 480);


        // Añadir el botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnCerrarSesion.setBounds(10, 790, 200, 40); // Posición en la parte inferior izquierda
        btnCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarSesion(); // Llama a la función para cerrar sesión
            }
        });
        contentPane.add(btnCerrarSesion);


        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(getClass().getResource("/interfazAzul.jpg")));
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
        addActionHistorial();
        addActionCalendar();
        addActionRep();
    }

    private JButton createButton(final String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(x, y, 260, 50);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!movimiento) {
                    moverBotones();
                }

                switch (text) {
                    case "Buscar historia clínica":
                        crearNuevoPanel(1);
                        break;

                    case "Ver agenda":
                        crearNuevoPanel(2);
                        break;

                    case "Reportar paciente":
                        crearNuevoPanel(3);
                        break;
                }
            }
        });
        contentPane.add(button);
        return button;
    }

    private void moverBotones() {
        if (!movimiento) {
            Timer timer = new Timer(10, new ActionListener() {
                // Posición final donde queremos que se detengan los botones
                final int posicionFinal = 250; // Ajusta este valor a tu posición final deseada
                int deltaX = 33;
                long startTime = System.currentTimeMillis();
                int positionLimit = -33; // Limite de posición para detener el desplazamiento

                public void actionPerformed(ActionEvent e) {
                    for (JButton button : buttons) {
                        // Nueva posición
                        int newPosX = button.getX() - deltaX;

                        // Si la nueva posición es menor o igual a la posición final, detén el desplazamiento
                        if (newPosX <= posicionFinal) {
                            newPosX = posicionFinal; // Asegurar que no se pase de la posición final
                            ((Timer) e.getSource()).stop(); // Detener el Timer
                            movimiento = false; // Indicar que el movimiento ha terminado
                        }

                        // Asignar la nueva posición al botón
                        button.setLocation(newPosX, button.getY());
                    }
                }
            });

            timer.start(); // Iniciar el Timer
            movimiento = true; // Indicar que se está moviendo
        }
    }

    private void crearNuevoPanel(int opcion) {
        if (panelActual != null) {
            limpiarPanel(panelActual);
        }
        // Asegúrate de inicializar nuevoPanel
        if (nuevoPanel == null) {
            nuevoPanel = new JPanel();
        }

        panelActual = new JPanel(); // Crear nuevo panel
        panelActual.setBackground(new Color(250, 250, 250));
        panelActual.setBounds(800, 0, 800, 900);

        contentPane.add(nuevoPanel);
        contentPane.repaint();
        movimiento = true;
    
        switch (opcion) {
            case 1:
                cargarPanelHistoriaClinica();
                break;
    
            case 2:
                cargarPanelVerAgenda();
                break;
    
            case 3:
                cargarPanelReportar();
                break;
    
            default:
                // Manejar otros casos o simplemente no hacer nada
                break;
        }
    }
    
    private void cargarPanelHistoriaClinica() {
        if (movimiento && panelActual != null) {
            limpiarPanel(panelActual);

            calendario.setVisible(false);
            btnCalendar.setVisible(false);
            
            panelActual.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            btnHistorial.setText("Buscar historial");
            btnHistorial.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnHistorial.setBounds(305, 437, 200, 40);
            panelActual.add(btnHistorial);

            JLabel lblDocumento = new JLabel("<html>Documento<p> Paciente<html>");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(340, 321, 120, 45);
            panelActual.add(lblDocumento);

            textFieldDocumentoHis.setBounds(305, 376, 200, 34);
            textFieldDocumentoHis.setFont(new Font("Tahoma", Font.BOLD, 17));
            panelActual.add(textFieldDocumentoHis);

            contentPane.add(panelActual);
            contentPane.setComponentZOrder(panelActual, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }

        }
    }

    private void addActionHistorial() {
        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validar que el documento del paciente esté disponible
                String documentoPaciente = textFieldDocumentoHis.getText();
                if (documentoPaciente == null || documentoPaciente.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, "Por favor, ingrese el documento del paciente.");
                    return;
                }

                // Crear la ventana para mostrar la información del paciente y la tabla de citas
                JFrame frame = new JFrame("Información del Paciente y Citas");
                frame.setSize(1000, 600);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                // Obtener la información del paciente
                try {
                    CallDAO callDAO = new CallDAO();
                    String queryPaciente = "SELECT " +
                            "usuario.nombre, " +
                            "usuario.apellidos, " +
                            "paciente.fecha_nacimiento, " +
                            "paciente.edad, " +
                            "paciente.sexo, " +
                            "paciente.grupoSanguineo, " +
                            "paciente.telefono, " +
                            "paciente.correoElectronico " +
                            "FROM paciente " +
                            "JOIN usuario ON paciente.ID_Paciente = usuario.ID " +
                            "WHERE paciente.ID_Paciente = '" + documentoPaciente + "'";

                    ResultSet rsPaciente = callDAO.consultDataBase(queryPaciente);

                    if (rsPaciente.next()) {
                        String nombre = rsPaciente.getString("nombre") + " " + rsPaciente.getString("apellidos");
                        String fechaNacimiento = rsPaciente.getDate("fecha_nacimiento").toString();
                        int edad = rsPaciente.getInt("edad");
                        String sexo = rsPaciente.getString("sexo");
                        String grupoSanguíneo = rsPaciente.getString("grupoSanguineo");
                        String telefono = rsPaciente.getString("telefono");
                        String correoElectronico = rsPaciente.getString("correoElectronico");

                        // Crear un panel con la información del paciente
                        JPanel panelPaciente = new JPanel();
                        panelPaciente.setLayout(new GridLayout(4, 1));

                        panelPaciente.add(new JLabel("Nombre:"));
                        panelPaciente.add(new JLabel(nombre));

                        panelPaciente.add(new JLabel("Fecha de Nacimiento:"));
                        panelPaciente.add(new JLabel(fechaNacimiento));

                        panelPaciente.add(new JLabel("Edad:"));
                        panelPaciente.add(new JLabel(String.valueOf(edad)));

                        panelPaciente.add(new JLabel("Sexo:"));
                        panelPaciente.add(new JLabel(sexo));

                        panelPaciente.add(new JLabel("Grupo Sanguíneo:"));
                        panelPaciente.add(new JLabel(grupoSanguíneo));

                        panelPaciente.add(new JLabel("Teléfono:"));
                        panelPaciente.add(new JLabel(telefono));

                        panelPaciente.add(new JLabel("Correo Electrónico:"));
                        panelPaciente.add(new JLabel(correoElectronico));

                        frame.getContentPane().add(panelPaciente, BorderLayout.NORTH); // Agregar la info del paciente en la parte superior
                    }

                } catch (Exception ex) {
                    Logger.getLogger(Medico.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frame, "Error al obtener la información del paciente.");
                    return;
                }

                // Crear la tabla con la información de las citas del paciente
                DefaultTableModel modeloTabla = new DefaultTableModel();
                modeloTabla.setColumnIdentifiers(new String[]{
                        "Día", "Médico", "Sede", "Especialidad", "Consultorio"
                });

                JTable tablaCitas = new JTable(modeloTabla);
                JScrollPane scrollPane = new JScrollPane(tablaCitas);

                // Consultar la base de datos para obtener las citas del paciente
                try {
                    String queryCitas = "SELECT " +
                            "cita.fecha, " +
                            "usuario.nombre AS medico, " +
                            "sede.nombre AS sede, " +
                            "medico.Especializacion AS especialidad, " +
                            "cita.NumHab, " +
                            "consultorio.Nombre AS consultorio, " +
                            "cita.fecha AS hora " +
                            "FROM cita " +
                            "JOIN medico ON cita.ID_Medico = medico.ID_Medico " +
                            "JOIN usuario ON medico.ID_Medico = usuario.ID " +
                            "JOIN consultorio ON cita.NumHab = consultorio.NumHab " +
                            "JOIN sede ON consultorio.CodSede = sede.Codigo " +
                            "WHERE cita.ID_Paciente = '" + documentoPaciente + "'";

                    CallDAO callDAO = new CallDAO();
                    ResultSet rs = callDAO.consultDataBase(queryCitas);

                    // Llenar la tabla con los datos obtenidos
                    while (rs.next()) {
                        modeloTabla.addRow(new Object[]{
                                rs.getTimestamp("fecha").toString(), // Día y hora de la cita
                                rs.getString("medico"), // Médico
                                rs.getString("sede"), // Sede
                                rs.getString("especialidad"), // Especialidad
                                rs.getString("consultorio"), // Consultorio
                        });
                    }

                } catch (Exception ex) {
                    Logger.getLogger(Medico.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frame, "Error al obtener las citas del paciente.");
                    return;
                }

                frame.getContentPane().add(scrollPane, BorderLayout.CENTER); // Agregar la tabla al centro
                frame.setVisible(true); // Mostrar la ventana
            }
        });
    }

    private void cargarPanelVerAgenda() {
        if (movimiento && panelActual != null) {
            //movimiento = false;

            limpiarPanel(panelActual);
            createTable();

            calendario.setVisible(true);
            btnCalendar.setVisible(true);

            panelActual.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0


            calendario.setBounds(150,40,500,400);
            panelActual.add(calendario);

            btnCalendar.setText("Mostrar citas");
            btnCalendar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnCalendar.setBounds(305, 450, 200, 40);
            panelActual.add(btnCalendar);

            panelActual.add(scroll);

            // Crear el botón de generar tratamiento
            JButton btnGenerarTratamiento = new JButton("Generar tratamiento");
            btnGenerarTratamiento.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnGenerarTratamiento.setBounds(280, 760, 250, 40);
            panelActual.add(btnGenerarTratamiento);

            // Agregar ActionListener al botón de generar tratamiento
            btnGenerarTratamiento.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Llamar al método para cargar el panel de selección de medicamentos
                    cargarPanelSeleccionMedicamento();
                }
            });




            getContentPane().add(panelActual);
            getContentPane().setComponentZOrder(panelActual, 0);
            panelActual.revalidate();
            panelActual.repaint();

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void createTable(){
        String[] nombreC = {"Numero Cita", "Consultorio", "Fecha", "ID Paciente"};
        dt.setColumnIdentifiers(nombreC);
        tablaCitas.setDefaultEditor(Object.class, null);
        tablaCitas.setModel(dt);
        tablaCitas.getTableHeader().setReorderingAllowed(false);
        scroll.setBounds(225, 500, 350, 250);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scroll.setBackground(Color.blue);
    }


    private void addActionCalendar(){
        btnCalendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Date fechaSelec = calendario.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaSelec);
                
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaFormateada = sdf.format(fechaSelec);
                
                
                if(fechaFormateada != null){
                    try {
                        addCita(fechaFormateada);
                    } catch (Exception ex) {
                        Logger.getLogger(Medico.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }    
            }
        });
    }
    
    public void addCita(String fecha) throws Exception{
        try {
            String sql = "SELECT NumCita, NumHab, fecha, ID_Paciente FROM cita WHERE fecha LIKE '" + fecha + "%'";
            
            CallDAO cbd = new CallDAO();
            
            ResultSet rs = cbd.consultDataBase(sql);
            
           
            if(tablaCitas.getRowCount() != 0){
                dt.setNumRows(0);
            } else{
                while(rs.next()){
                    dt.addRow(new Object[] {rs.getInt(1), rs.getInt(2), rs.getTimestamp(3).toString(), rs.getString(4)});
                }
            }
            
            
            cbd.desconnect();
            
        } catch (Exception e) {
            throw e;
        }
    }

    private void cargarPanelSeleccionMedicamento() {
        // Limpiar el panel actual
        limpiarPanel(panelActual);

        // Crear el panel de selección de medicamentos
        panelActual.setBackground(new Color(7, 29, 68)); // Mismo color que el panel actual
        panelActual.setLayout(null);
        panelActual.setBounds(800, 0, 800, 900); // Mismas dimensiones que el panel actual

        java.util.List<Medicamento> medicamentos = null;
        try {
            medicamentos = medicamentoServices.listMedi();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Crear un array de nombres de medicamentos para usar en el menú desplegable
        String[] nombresMedicamentos = new String[medicamentos.size()];
        for (int i = 0; i < medicamentos.size(); i++) {
            nombresMedicamentos[i] = medicamentos.get(i).getNombre();
        }

        // Crear el menú desplegable con los nombres de los medicamentos
        JComboBox<String> comboBoxMedicamentos = new JComboBox<>(nombresMedicamentos);
        comboBoxMedicamentos.setBounds(310, 350, 200, 40);
        panelActual.add(comboBoxMedicamentos);

        // Crear el botón de aceptar
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnAceptar.setBounds(330, 400, 150, 40);
        panelActual.add(btnAceptar);

        getContentPane().remove(panelActual); // Remover el panel actual
        getContentPane().add(panelActual); // Agregar el nuevo panel
        getContentPane().setComponentZOrder(panelActual, 0);
        panelActual.revalidate();
        panelActual.repaint();

        // Acción del botón de aceptar
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el medicamento seleccionado
                String nombreMedicamentoSeleccionado = (String) comboBoxMedicamentos.getSelectedItem();

                // Realizar la lógica para usar el medicamento seleccionado
                // Por ejemplo, puedes imprimir el nombre del medicamento seleccionado
                JOptionPane.showMessageDialog(null, "Medicamento seleccionado: " + nombreMedicamentoSeleccionado);
            }
        });
    }


    private void cargarPanelReportar() {
        if (movimiento && panelActual != null) {
            limpiarPanel(panelActual);
            calendario.setVisible(false);
            btnCalendar.setVisible(false);

            panelActual.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            btnReportarCita.setText("Reportar cita");
            btnReportarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnReportarCita.setBounds(305, 437, 180, 40);
            panelActual.add(btnReportarCita);

            textFieldDocumentoRep.setBounds(180, 376, 180, 34);
            textFieldDocumentoRep.setFont(new Font("Tahoma", Font.BOLD, 17));
            panelActual.add(textFieldDocumentoRep);
            
            dateRep.setBounds(430, 376, 180, 34);
            dateRep.setFont(new Font("Tahoma", Font.BOLD, 17));
            panelActual.add(dateRep);

            JLabel lblDocumento = new JLabel("<html>Documento<p> Paciente<html>");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(210, 321, 120, 45);
            panelActual.add(lblDocumento);
            
            JLabel lblDocumento2 = new JLabel("Fecha Cita");
            lblDocumento2.setForeground(new Color(255, 255, 255));
            lblDocumento2.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento2.setBounds(465, 321, 120, 45);
            panelActual.add(lblDocumento2);
            
            contentPane.add(panelActual);
            contentPane.setComponentZOrder(panelActual, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }
    
    private void addActionRep(){
        btnReportarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cs.modificarAsistencia(textFieldDocumentoRep.getText(), dateRep.getText(), true);
                } catch (Exception ex) {
                    
                }
            }
        });
    }


    private void cerrarSesion() {
        FrameController.openFrame("LoginFrame");
        FrameController.cerrarSesion(); // Llama al controlador para cerrar sesión

    }
    
    private void limpiarPanel(JPanel panel) {
        if (panel != null) {
            panel.removeAll(); // Eliminar todos los componentes del panel
            panel.repaint();   // Repintar para asegurar que todo esté limpio
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Medico().setVisible(true);
            }
        });
    }

}

