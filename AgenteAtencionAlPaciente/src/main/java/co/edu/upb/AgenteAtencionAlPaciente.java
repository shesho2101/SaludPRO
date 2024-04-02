package co.edu.upb;

import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import javax.swing.*;

public class AgenteAtencionAlPaciente extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private final int panelPositionX = 770;
    private JLabel lblFechaSeleccionada;
    private JLabel lblHoraSeleccionada;
    private final Administrador administrador = new Administrador();
    private final ArrayList<Medico> listaMedicosAdministrador = administrador.getListaMedico();
    private final ArrayList<Medico> listaMedicosPorEspecialidad = new ArrayList<>();
    private final ArrayList<Cita> listaCitas = new ArrayList<>();

    public AgenteAtencionAlPaciente() {
        setTitle("IPS Salud Pro - Agente atención al paciente");
        setSize(1600, 900);
        setResizable(false); // Desactivar la capacidad de redimensionamiento
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(150, 198, 198));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        buttons = new JButton[4];

        buttons[0] = createButton("Agendar cita", 660, 290);
        buttons[1] = createButton("Reprogramar cita", 660, 370);
        buttons[2] = createButton("Cancelar cita", 660, 450);
        buttons[3] = createButton("Activar cita", 660, 530);

        JLabel lblFondo = new JLabel("");
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/interfazBlanca.jpg")));
        lblFondo.setIcon(icon);
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
        listaMedicosAdministrador.add(new Medico("Juan", "1", "Fisioterapia", "Bucaramanga", "2101", "Medico"));
        listaMedicosAdministrador.add(new Medico("Sergio", "2", "Psicología", "Pamplona", "202", "Medico"));
    }

    private JButton createButton(final String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(x, y, 220, 50);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!movimiento) {
                    moverBotones();
                }

                switch (text) {
                    case "Agendar cita":
                        crearNuevoPanel(1);
                        break;

                    case "Reprogramar cita":
                        crearNuevoPanel(2);
                        break;

                    case "Cancelar cita":
                        crearNuevoPanel(3);
                        break;

                    case "Activar cita":
                        crearNuevoPanel(4);
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
                final int deltaX = 30;
                final int duration = 215;
                final long startTime = System.currentTimeMillis();

                public void actionPerformed(ActionEvent e) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime;

                    if (elapsedTime > duration) {
                        ((Timer) e.getSource()).stop();
                    } else {
                        moveButtonsToLeft();
                    }
                }
            });

            timer.start();
            movimiento = true;
        }
    }

    private void moveButtonsToLeft() {
        for (JButton button : buttons) {
            button.setLocation(button.getX() - 40, button.getY());
        }
    }

    private void crearNuevoPanel(int opcion) {
        nuevoPanel = new JPanel();
        nuevoPanel.setBackground(new Color(250, 250, 250));
        nuevoPanel.setBounds(panelPositionX, 0, 800, 900);

        contentPane.add(nuevoPanel);
        contentPane.repaint();
        movimiento = true;

        switch (opcion) {
            case 1:
                cargarPanelAgendarCita();
                break;

            case 2:
                cargarPanelAgendarCita();
                break;

            case 3:
                cargarPanelCancelarCita();
                break;

            case 4:
                cargarPanelActivarCita();
                break;

            default:
                // Manejar otros casos o simplemente no hacer nada
                break;
        }
    }

    private void cargarPanelAgendarCita() {
        if (movimiento) {
            JTextField textFieldApellido;
            JTextField textFieldNombre;
            JTextField textFieldDocumento;

            JPanel panelAgendarCita = new JPanel();
            panelAgendarCita.setBackground(new Color(7, 29, 68));
            panelAgendarCita.setLayout(null);
            panelAgendarCita.setBounds(800, 0, 800, 900);

            JButton btnSeleccionarFecha = new JButton("Seleccionar Fecha");
            btnSeleccionarFecha.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnSeleccionarFecha.setBounds(275, 710, 250, 40);
            btnSeleccionarFecha.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Mostrar el selector de fecha
                    JCalendarFull jCalendarFull = new JCalendarFull(AgenteAtencionAlPaciente.this);
                    jCalendarFull.setVisible(true);

                    // Obtener la fecha seleccionada del diálogo
                    Calendar fechaSeleccionada = jCalendarFull.getSelectedDate();
                    if (fechaSeleccionada != null) {
                        int dia = fechaSeleccionada.get(Calendar.DAY_OF_MONTH);
                        int mes = fechaSeleccionada.get(Calendar.MONTH) + 1; // Sumar 1 porque Calendar.MONTH es base 0
                        int year = fechaSeleccionada.get(Calendar.YEAR);
                        String fecha = String.format("%02d/%02d/%04d", dia, mes, year);

                        // Actualizar los JLabels con la fecha seleccionada
                        lblFechaSeleccionada.setText("Fecha: " + fecha);
                        lblHoraSeleccionada.setText("Hora: " + jCalendarFull.getHoraCompleta());
                    }
                }
            });

            panelAgendarCita.add(btnSeleccionarFecha);

            String[] sedes = {"Bucaramanga", "Floridablanca", "Piedecuesta", "Girón", "Lebrija", "Pamplona", "Rionegro"};
            String[] opciones = {"Medicina familiar", "Fisioterapia", "Medina interna", "Psicología"};

            JComboBox<String> menuDesplegableSedes = new JComboBox<>(sedes);
            menuDesplegableSedes.setModel(new DefaultComboBoxModel<>(sedes));
            menuDesplegableSedes.setBounds(310, 405, 180, 30);
            panelAgendarCita.add(menuDesplegableSedes);

            JComboBox<String> menuDesplegableOpciones = new JComboBox<>(opciones);
            menuDesplegableOpciones.setModel(new DefaultComboBoxModel<>(opciones));
            menuDesplegableOpciones.setBounds(310, 500, 180, 30);
            panelAgendarCita.add(menuDesplegableOpciones);

            textFieldNombre = new JTextField();
            textFieldNombre.setBounds(310, 108, 180, 34);
            panelAgendarCita.add(textFieldNombre);

            textFieldApellido = new JTextField();
            textFieldApellido.setBounds(310, 207, 180, 34);
            panelAgendarCita.add(textFieldApellido);

            textFieldDocumento = new JTextField();
            textFieldDocumento.setBounds(310, 306, 180, 34);
            panelAgendarCita.add(textFieldDocumento);

            JLabel lblNombre = new JLabel("Nombre");
            lblNombre.setForeground(new Color(255, 255, 255));
            lblNombre.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombre.setBounds(310, 53, 180, 45);
            panelAgendarCita.add(lblNombre);

            JLabel lblApellido = new JLabel("Apellido");
            lblApellido.setForeground(new Color(255, 255, 255));
            lblApellido.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblApellido.setBounds(310, 152, 180, 45);
            panelAgendarCita.add(lblApellido);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(310, 251, 180, 45);
            panelAgendarCita.add(lblDocumento);

            JLabel lblEspecialidad = new JLabel("Especialidad");
            lblEspecialidad.setForeground(new Color(255, 255, 255));
            lblEspecialidad.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblEspecialidad.setBounds(310, 445, 180, 45);
            panelAgendarCita.add(lblEspecialidad);

            JLabel lblCiudad = new JLabel("Ciudad");
            lblCiudad.setForeground(new Color(255, 255, 255));
            lblCiudad.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblCiudad.setBounds(310, 350, 180, 45);
            panelAgendarCita.add(lblCiudad);

            lblFechaSeleccionada = new JLabel("Fecha: ");
            lblFechaSeleccionada.setForeground(new Color(255, 255, 255));
            lblFechaSeleccionada.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblFechaSeleccionada.setBounds(310, 669, 300, 45);
            panelAgendarCita.add(lblFechaSeleccionada);

            lblHoraSeleccionada = new JLabel("Hora: ");
            lblHoraSeleccionada.setForeground(new Color(255, 255, 255));
            lblHoraSeleccionada.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblHoraSeleccionada.setBounds(310, 614, 300, 45);
            panelAgendarCita.add(lblHoraSeleccionada);

            JLabel lblMedico = new JLabel("Médico:");
            lblMedico.setForeground(new Color(255, 255, 255));
            lblMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblMedico.setBounds(310, 540, 180, 45);
            panelAgendarCita.add(lblMedico);

            JComboBox<String> menuDesplegableMedicos = new JComboBox<>();
            menuDesplegableMedicos.setModel(new DefaultComboBoxModel<>());
            menuDesplegableMedicos.setBounds(310, 590, 180, 30);
            panelAgendarCita.add(menuDesplegableMedicos);

            menuDesplegableOpciones.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String especialidadSeleccionada = (String) menuDesplegableOpciones.getSelectedItem();
                        String sedeSeleccionada = (String) menuDesplegableSedes.getSelectedItem();
                        actualizarListaMedicosPorEspecialidadYCiudad(especialidadSeleccionada, sedeSeleccionada);
                        actualizarMenuDesplegableMedicos(menuDesplegableMedicos);
                    }
                }
            });

            menuDesplegableSedes.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String especialidadSeleccionada = (String) menuDesplegableOpciones.getSelectedItem();
                        String sedeSeleccionada = (String) menuDesplegableSedes.getSelectedItem();
                        actualizarListaMedicosPorEspecialidadYCiudad(especialidadSeleccionada, sedeSeleccionada);
                        actualizarMenuDesplegableMedicos(menuDesplegableMedicos);
                    }
                }
            });

            panelAgendarCita.add(menuDesplegableMedicos);

            panelAgendarCita.add(menuDesplegableMedicos);

            JButton btnAgendarCita = new JButton("Agendar Cita");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(320, 764, 180, 40);
            panelAgendarCita.add(btnAgendarCita);
            btnAgendarCita.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!movimiento) {
                        moverBotones();
                    }

                    // Obtener datos del paciente
                    String nombre = textFieldNombre.getText();
                    String apellido = textFieldApellido.getText();
                    String documento = textFieldDocumento.getText();

                    // Obtener datos de la cita (fecha y hora)
                    String fecha = lblFechaSeleccionada.getText();
                    String hora = lblHoraSeleccionada.getText();

                    // Verificar si todos los campos están llenos
                    if (nombre.isEmpty() || apellido.isEmpty() || documento.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Crear instancias de Paciente, Medico y Consultorio
                        Paciente paciente = new Paciente();
                        paciente.setNombre(nombre);
                        paciente.setApellido(apellido);
                        paciente.setDocumentoIdentidad(documento);

                        String tipoConsulta = (String) menuDesplegableOpciones.getSelectedItem();
                        String sede = (String) menuDesplegableSedes.getSelectedItem();
                        String medico = (String) menuDesplegableMedicos.getSelectedItem();

                        Medico medicoSeleccionado = null;
                        
                        for (Medico medico1 : listaMedicosPorEspecialidad) {
                            if (medico1.getNombre().equals(medico) && medico1.getSede().equals(sede) && medico1.getEspecialidad().equals(tipoConsulta)){
                                medicoSeleccionado = medico1;
                            }
                        }

                        // Crear la cita
                        Cita cita = new Cita(sede, fecha, hora, medicoSeleccionado, paciente, tipoConsulta);

                        listaCitas.add(cita);
                        
                        // Por ejemplo, puedes imprimir los datos de la cita en la consola
                        /*System.out.println("Cita agendada:");
                        System.out.println("Sede: " + cita.getSede());
                        System.out.println(cita.getFecha());
                        System.out.println(cita.getHora());
                        System.out.println("Médico: " + cita.getMedico().getNombre());
                        System.out.println("Consultorio: " + cita.getMedico().getConsultorio());
                        System.out.println("Paciente: " + cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellido());
                        System.out.println("Consulta: " + cita.getEspecialidad());*/

                        for (Cita citas : listaCitas) {
                            System.out.println("Cita agendada:");
                            System.out.println("Sede: " + citas.getSede());
                            System.out.println(citas.getFecha());
                            System.out.println(citas.getHora());
                            System.out.println("Médico: " + citas.getMedico().getNombre());
                            System.out.println("Consultorio: " + citas.getMedico().getConsultorio());
                            System.out.println("Paciente: " + citas.getPaciente().getNombre() + " " + citas.getPaciente().getApellido());
                            System.out.println("Consulta: " + citas.getEspecialidad());
                            System.out.println("");
                        }
                    }
                }
            });


            contentPane.add(panelAgendarCita);
            contentPane.setComponentZOrder(panelAgendarCita, 0);
        }
    }

    private void cargarPanelCancelarCita() {
        if (movimiento) {
            JTextField textFieldDocumento;

            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68));
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(338, 150, 120, 45);
            panelCancelarCita.add(lblDocumento);

            textFieldDocumento = new JTextField();
            textFieldDocumento.setBounds(305, 210, 180, 34);
            panelCancelarCita.add(textFieldDocumento);

            JButton btnBuscarCita = new JButton("Buscar cita");
            btnBuscarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBuscarCita.setBounds(305, 270, 180, 40);
            panelCancelarCita.add(btnBuscarCita);

            JPanel jPanelTable = new JPanel();
            jPanelTable.setLayout(null); // Usar null layout
            jPanelTable.setBackground(Color.WHITE);
            jPanelTable.setBounds(40, 340, 700, 400);
            btnBuscarCita.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener el texto ingresado en el campo de texto del documento
                    String documento = textFieldDocumento.getText().trim();

                    // Verificar si el campo de texto no está vacío
                    if (!documento.isEmpty()) {
                        // Limpiar la tabla antes de agregar nuevas citas
                        jPanelTable.removeAll();

                        // Generación de la tabla de citas
                        int y = 10;
                        for (int i = 0; i < listaCitas.size(); i++) {
                            Cita cita = listaCitas.get(i);
                            // Verificar si la cita coincide con el documento ingresado
                            if (cita.getPaciente().getDocumentoIdentidad().equals(documento)) {
                                JPanel panelRow = new JPanel();
                                panelRow.setVisible(true);
                                panelRow.setSize(700, 30);
                                panelRow.setBackground(Color.WHITE);
                                panelRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cambiar el cursor al pasar sobre el panel
                                jPanelTable.add(panelRow);

                                panelRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                                JLabel sede = new JLabel(cita.getSede());
                                sede.setVisible(true);
                                sede.setSize(120, 25);
                                sede.setFont(new Font("Tahoma", Font.PLAIN, 15));
                                panelRow.add(sede);
                                sede.setLocation(20, y);

                                JLabel fecha = new JLabel(cita.getFecha());
                                fecha.setVisible(true);
                                fecha.setSize(120, 30);
                                fecha.setFont(new Font("Tahoma", Font.PLAIN, 15));
                                panelRow.add(fecha);
                                fecha.setLocation(150, y);

                                JLabel hora = new JLabel(cita.getHora());
                                hora.setVisible(true);
                                hora.setSize(120, 30);
                                hora.setFont(new Font("Tahoma", Font.PLAIN, 15));
                                panelRow.add(hora);
                                hora.setLocation(280, y);

                                JLabel medico = new JLabel(cita.getMedico().getNombre());
                                medico.setVisible(true);
                                medico.setSize(250, 30);
                                medico.setFont(new Font("Tahoma", Font.PLAIN, 15));
                                panelRow.add(medico);
                                medico.setLocation(410, y);

                                JLabel paciente = new JLabel(cita.getPaciente().getNombre());
                                paciente.setVisible(true);
                                paciente.setSize(200, 30);
                                paciente.setFont(new Font("Tahoma", Font.PLAIN, 15));
                                panelRow.add(paciente);
                                paciente.setLocation(670, y);

                                panelRow.setLocation(0, y);
                                y += 40;
                            }
                        }

                        // Actualizar la tabla
                        jPanelTable.revalidate();
                        jPanelTable.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un número de documento.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            panelCancelarCita.add(jPanelTable);

            // Botón para eliminar cita
            JButton btnEliminarCita = new JButton("Eliminar cita");
            btnEliminarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnEliminarCita.setBounds(305, 750, 180, 40);
            panelCancelarCita.add(btnEliminarCita);

            contentPane.add(panelCancelarCita);
            contentPane.setComponentZOrder(panelCancelarCita, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void cargarPanelActivarCita() {
        if (movimiento) {
            //movimiento = false;
            JTextField textFieldDocumento;

            JPanel panelActivarCita = new JPanel();
            panelActivarCita.setBackground(new Color(7, 29, 68)); // Cambiado a fondo claro
            panelActivarCita.setLayout(null);
            panelActivarCita.setBounds(800, 0, 800, 900);  // Cambiado el límite inferior a 0

            JButton btnAgendarCita = new JButton("Buscar cita");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(305, 437, 180, 40);
            panelActivarCita.add(btnAgendarCita);

            textFieldDocumento = new JTextField();
            textFieldDocumento.setBounds(305, 376, 180, 34);
            panelActivarCita.add(textFieldDocumento);

            JLabel lblDocumento = new JLabel("Documento");
            lblDocumento.setForeground(new Color(255, 255, 255));
            lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumento.setBounds(338, 321, 120, 45);
            panelActivarCita.add(lblDocumento);

            contentPane.add(panelActivarCita);
            contentPane.setComponentZOrder(panelActivarCita, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void actualizarListaMedicosPorEspecialidad(String especialidad) {
        listaMedicosPorEspecialidad.clear();
        for (Medico medico : listaMedicosAdministrador) {
            if (medico.getEspecialidad().equals(especialidad)) {
                listaMedicosPorEspecialidad.add(medico);
            }
        }
    }

    private void actualizarMenuDesplegableMedicos(JComboBox<String> menuDesplegableMedicos) {
        String[] nombresMedicos = new String[listaMedicosPorEspecialidad.size()];
        for (int i = 0; i < listaMedicosPorEspecialidad.size(); i++) {
            nombresMedicos[i] = listaMedicosPorEspecialidad.get(i).getNombre();
        }
        menuDesplegableMedicos.setModel(new DefaultComboBoxModel<>(nombresMedicos));
    }

    private void actualizarListaMedicosPorEspecialidadYCiudad(String especialidad, String ciudad) {
        listaMedicosPorEspecialidad.clear();
        for (Medico medico : listaMedicosAdministrador) {
            if (medico.getEspecialidad().equals(especialidad) && medico.getSede().equals(ciudad)) {
                listaMedicosPorEspecialidad.add(medico);
            }
        }
    }


    private void limpiarPanel(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            panel.remove(component);
        }
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AgenteAtencionAlPaciente().setVisible(true);
            }
        });
    }
}
