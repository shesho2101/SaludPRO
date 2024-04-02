package co.edu.upb;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Administrador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private int panelPositionX = 770;
    private ArrayList<Trabajador> listaTrabajadores;
    private ArrayList<Medico> listaMedico;

    public Administrador() {
        setTitle("IPS Salud Pro - Administrador");
        setSize(1600, 900);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(150, 198, 198));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        buttons = new JButton[3];

        buttons[0] = createButton("Inventario medicamentos", 610, 320);
        buttons[1] = createButton("Administrar consultorios", 610, 400);
        buttons[2] = createButton("Administrar usuarios", 610, 480);

        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(getClass().getResource("/interfazBlanca.jpg")));
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);

        listaTrabajadores = new ArrayList<>();
        listaMedico = new ArrayList<>();
    }

    private JButton createButton(final String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(x, y, 300, 50);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!movimiento) {
                    moverBotones();
                }

                switch (text) {
                    case "Inventario medicamentos":
                        crearNuevoPanel(1);
                        break;

                    case "Administrar consultorios":
                        crearNuevoPanel(2);
                        break;

                    case "Administrar usuarios":
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
                int deltaX = 25;
                int duration = 250;
                long startTime = System.currentTimeMillis();

                public void actionPerformed(ActionEvent e) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime;

                    if (elapsedTime > duration) {
                        ((Timer) e.getSource()).stop();
                    } else {
                        moveButtonsToLeft(deltaX);
                    }
                }
            });

            timer.start();
            movimiento = true;
        }
    }

    private void moveButtonsToLeft(int deltaX) {
        for (JButton button : buttons) {
            button.setLocation(button.getX() - deltaX, button.getY());
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
                cargarInventario();
                break;

            case 2:
                cargarConsultorios();
                break;

            case 3:
                cargarUsuarios();
                break;

            default:
                // Manejar otros casos o simplemente no hacer nada
                break;
        }
    }

    private void cargarInventario() {
        if (movimiento) {
            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 63));
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);

            JPanel panelMedicamentos = new JPanel();
            panelMedicamentos.setFont(new Font("Tahoma", Font.PLAIN, 15));
            panelMedicamentos.setBounds(40, 168, 700, 400);
            panelMedicamentos.setFocusable(false);
            panelCancelarCita.add(panelMedicamentos);
            panelMedicamentos.setLayout(null);

            getContentPane().add(panelCancelarCita);
            getContentPane().setComponentZOrder(panelCancelarCita, 0);

            JButton btnAgendarCita = new JButton("Agregar");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(65, 618, 180, 40);
            panelCancelarCita.add(btnAgendarCita);

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(300, 618, 180, 40);
            panelCancelarCita.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(540, 618, 180, 40);
            panelCancelarCita.add(btnBorrar);

            JLabel lblNombreCategoria = new JLabel("Inventario medicamentos");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(new Color(255, 255, 255));
            lblNombreCategoria.setBounds(257, 96, 267, 40);
            panelCancelarCita.add(lblNombreCategoria);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }

        }
    }

    private void cargarConsultorios() {
        if (movimiento) {
            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68));
            panelCancelarCita.setLayout(null);
            panelCancelarCita.setBounds(800, 0, 800, 900);

            JPanel jPanelTable = new JPanel();
            jPanelTable.setLayout(null); // Usar null layout
            jPanelTable.setBackground(Color.WHITE);
            jPanelTable.setBounds(40, 168, 700, 400);
            panelCancelarCita.add(jPanelTable);

            // Generación de la tabla de trabajadores
            int y = 10;
            for (int i = 0; i < listaMedico.size(); i++) {
                Medico medico = listaMedico.get(i);

                JPanel panelRow = new JPanel();
                panelRow.setVisible(true);
                panelRow.setSize(1050, 30);
                panelRow.setBackground(Color.WHITE);
                jPanelTable.add(panelRow);

                panelRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cursor clickeable

                JLabel nombre = new JLabel(medico.getNombre());
                nombre.setVisible(true);
                nombre.setSize(250, 25);
                nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(nombre);
                nombre.setLocation(20, 5);

                JLabel documento = new JLabel(medico.getDocumento());
                documento.setVisible(true);
                documento.setSize(140, 30);
                documento.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(documento);
                documento.setLocation(170, 5);

                JLabel sede = new JLabel(medico.getSede());
                sede.setVisible(true);
                sede.setSize(320, 30);
                sede.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(sede);
                sede.setLocation(320, 5);

                JLabel especialidad = new JLabel(medico.getEspecialidad());
                especialidad.setVisible(true);
                especialidad.setSize(320, 30);
                especialidad.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(especialidad);
                especialidad.setLocation(470, 5);

                JLabel consultorio = new JLabel(medico.getConsultorio());
                consultorio.setVisible(true);
                consultorio.setSize(320, 30);
                consultorio.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(consultorio);
                consultorio.setLocation(620, 5);

                panelRow.setLocation(0, y); // Establecer la posición vertical
                y += 40; // Incrementar la posición vertical para la próxima fila
            }

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(210, 618, 180, 40);
            panelCancelarCita.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(430, 618, 180, 40);
            panelCancelarCita.add(btnBorrar);

            getContentPane().add(panelCancelarCita);
            getContentPane().setComponentZOrder(panelCancelarCita, 0);

            JLabel lblNombreCategoria = new JLabel("Control consultorios");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(new Color(255, 255, 255));
            lblNombreCategoria.setBounds(289, 96, 202, 40);
            panelCancelarCita.add(lblNombreCategoria);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }


    private void cargarUsuarios() {
        if (movimiento) {

            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68));
            panelCancelarCita.setLayout(null); // Usar null layout en lugar de BorderLayout
            panelCancelarCita.setBounds(800, 0, 800, 900);

            JPanel jPanelTable = new JPanel();
            jPanelTable.setLayout(null); // Usar null layout
            jPanelTable.setBackground(Color.WHITE);
            jPanelTable.setBounds(40, 168, 700, 400);
            panelCancelarCita.add(jPanelTable);

            JButton btnAgendarCita = new JButton("Agregar");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(65, 618, 180, 40);
            panelCancelarCita.add(btnAgendarCita);

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(300, 618, 180, 40);
            panelCancelarCita.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(540, 618, 180, 40);
            panelCancelarCita.add(btnBorrar);

            getContentPane().add(panelCancelarCita);
            getContentPane().setComponentZOrder(panelCancelarCita, 0);

            JLabel lblNombreCategoria = new JLabel("Administrar usuarios");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(new Color(255, 255, 255));
            lblNombreCategoria.setBounds(285, 96, 210, 40);
            panelCancelarCita.add(lblNombreCategoria);

            // Generación de la tabla de trabajadores
            int y = 10;
            for (int i = 0; i < listaTrabajadores.size(); i++) {
                Trabajador trabajador = listaTrabajadores.get(i);

                JPanel panelRow = new JPanel();
                panelRow.setVisible(true);
                panelRow.setSize(1050, 30);
                panelRow.setBackground(Color.WHITE);
                jPanelTable.add(panelRow);

                panelRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cursor clickeable

                JLabel nombre = new JLabel(trabajador.getNombre());
                nombre.setVisible(true);
                nombre.setSize(250, 25);
                nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(nombre);
                nombre.setLocation(20, 5);

                JLabel documento = new JLabel(trabajador.getDocumento());
                documento.setVisible(true);
                documento.setSize(140, 30);
                documento.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(documento);
                documento.setLocation(250, 5);

                JLabel cargo = new JLabel(trabajador.getCargo());
                cargo.setVisible(true);
                cargo.setSize(320, 30);
                cargo.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(cargo);
                cargo.setLocation(500, 5);

                panelRow.setLocation(0, y); // Establecer la posición vertical
                y += 40; // Incrementar la posición vertical para la próxima fila
            }

            btnAgendarCita.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getContentPane().remove(panelCancelarCita);
                    getContentPane().repaint();
                    cargarPanelAgregar();
                }
            });

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void cargarPanelAgregar() {
        JPanel panelAgregar = new JPanel();
        panelAgregar.setBackground(new Color(7, 29, 68));
        panelAgregar.setLayout(null);
        panelAgregar.setBounds(800, 0, 800, 900);

        JComboBox<String> comboBoxTipo = new JComboBox<>();
        comboBoxTipo.addItem("Médico");
        comboBoxTipo.addItem("Agente de Atención al Paciente");
        comboBoxTipo.setBounds(310, 380, 180, 34);
        panelAgregar.add(comboBoxTipo);

        JButton btnAgregar = new JButton("Seleccionar Tipo");
        btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnAgregar.setBounds(300, 450, 210, 40);
        panelAgregar.add(btnAgregar);
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoSeleccionado = (String) comboBoxTipo.getSelectedItem();
                switch (Objects.requireNonNull(tipoSeleccionado)) {
                    case "Médico":
                        limpiarPanel(panelAgregar);

                        JLabel lblNombreMedico = new JLabel("Nombre:");
                        lblNombreMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblNombreMedico.setForeground(Color.WHITE);
                        lblNombreMedico.setBounds(310, 130, 180, 34);
                        panelAgregar.add(lblNombreMedico);

                        JTextField textFieldNombreMedico = new JTextField();
                        textFieldNombreMedico.setBounds(310, 180, 180, 30);
                        panelAgregar.add(textFieldNombreMedico);

                        JLabel lblDocumentoMedico = new JLabel("Documento:");
                        lblDocumentoMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblDocumentoMedico.setForeground(Color.WHITE);
                        lblDocumentoMedico.setBounds(310, 230, 180, 34);
                        panelAgregar.add(lblDocumentoMedico);

                        JPasswordField passwordFieldDocumentoMedico = new JPasswordField();
                        passwordFieldDocumentoMedico.setBounds(310, 280, 180, 30);
                        panelAgregar.add(passwordFieldDocumentoMedico);

                        JLabel lblEspecialidad = new JLabel("Especialidad:");
                        lblEspecialidad.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblEspecialidad.setForeground(Color.WHITE);
                        lblEspecialidad.setBounds(310, 330, 180, 34);
                        panelAgregar.add(lblEspecialidad);

                        String[] opciones = {"Medicina familiar", "Fisioterapia", "Medicina interna", "Psicología"};
                        JComboBox<String> menuDesplegableOpciones = new JComboBox<>(opciones);
                        menuDesplegableOpciones.setModel(new DefaultComboBoxModel<>(opciones));
                        menuDesplegableOpciones.setBounds(310, 380, 180, 30);
                        panelAgregar.add(menuDesplegableOpciones);

                        JLabel lblSede = new JLabel("Sede:");
                        lblSede.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblSede.setForeground(Color.WHITE);
                        lblSede.setBounds(310, 430, 180, 34);
                        panelAgregar.add(lblSede);

                        String[] sedes = {"Bucaramanga", "Floridablanca", "Piedecuesta", "Girón", "Lebrija", "Pamplona", "Rionegro"};
                        JComboBox<String> menuDesplegableSedes = new JComboBox<>(sedes);
                        menuDesplegableSedes.setModel(new DefaultComboBoxModel<>(sedes));
                        menuDesplegableSedes.setBounds(310, 480, 180, 30);
                        panelAgregar.add(menuDesplegableSedes);

                        JLabel lblConsultorio = new JLabel("Consultorio:");
                        lblConsultorio.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblConsultorio.setForeground(Color.WHITE);
                        lblConsultorio.setBounds(310, 530, 180, 34);
                        panelAgregar.add(lblConsultorio);

                        JTextField textFieldConsultorio = new JTextField();
                        textFieldConsultorio.setBounds(310, 580, 180, 30);
                        panelAgregar.add(textFieldConsultorio);

                        JButton btnAgregarMedico = new JButton("Agregar Médico");
                        btnAgregarMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
                        btnAgregarMedico.setBounds(320, 764, 180, 40);
                        panelAgregar.add(btnAgregarMedico);
                        btnAgregarMedico.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nombreMedico = textFieldNombreMedico.getText();
                                String documentoMedico = new String(passwordFieldDocumentoMedico.getPassword());
                                String especialidad = (String) menuDesplegableOpciones.getSelectedItem();
                                String sede = (String) menuDesplegableSedes.getSelectedItem();
                                String consultorio = textFieldConsultorio.getText();
                                String cargo = "Medico";

                                if (nombreMedico.isEmpty() || documentoMedico.isEmpty() || sede.isEmpty() || consultorio.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    // Crear un nuevo objeto Trabajador y agregarlo a la lista
                                    Trabajador nuevoMedico = new Trabajador(nombreMedico, documentoMedico, cargo);
                                    listaTrabajadores.add(nuevoMedico);

                                    Medico medico = new Medico(nombreMedico, documentoMedico, especialidad, sede, consultorio, cargo);
                                    listaMedico.add(medico);

                                    // Imprimir los datos del médico (puedes eliminar estas líneas si no las necesitas)
                                    System.out.println("Médico agregado:");
                                    System.out.println("Nombre: " + nombreMedico);
                                    System.out.println("Documento: " + documentoMedico);
                                    System.out.println("Especialidad: " + especialidad);
                                    System.out.println("Sede: " + sede);
                                    System.out.println("Consultorio: " + consultorio);
                                }
                            }
                        });
                        break;
                    case "Agente de Atención al Paciente":
                        limpiarPanel(panelAgregar);

                        JLabel lblNombreAgente = new JLabel("Nombre:");
                        lblNombreAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblNombreAgente.setForeground(Color.WHITE);
                        lblNombreAgente.setBounds(310, 130, 180, 34);
                        panelAgregar.add(lblNombreAgente);

                        JTextField textFieldNombreAgente = new JTextField();
                        textFieldNombreAgente.setBounds(310, 180, 180, 30);
                        panelAgregar.add(textFieldNombreAgente);

                        JLabel lblDocumentoAgente = new JLabel("Documento:");
                        lblDocumentoAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblDocumentoAgente.setForeground(Color.WHITE);
                        lblDocumentoAgente.setBounds(310, 230, 180, 34);
                        panelAgregar.add(lblDocumentoAgente);

                        JPasswordField passwordFieldDocumentoAgente = new JPasswordField();
                        passwordFieldDocumentoAgente.setBounds(310, 280, 180, 30);
                        panelAgregar.add(passwordFieldDocumentoAgente);

                        JButton btnAgregarAgente = new JButton("Agregar");
                        btnAgregarAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                        btnAgregarAgente.setBounds(310, 330, 180, 40);
                        panelAgregar.add(btnAgregarAgente);
                        btnAgregarAgente.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nombreAgente = textFieldNombreAgente.getText();
                                String documentoAgente = new String(passwordFieldDocumentoAgente.getPassword());

                                if (nombreAgente.isEmpty() || documentoAgente.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    // Crear un nuevo objeto Trabajador y agregarlo a la lista
                                    Trabajador nuevoAgente = new Trabajador(nombreAgente, documentoAgente, "Agente de Atención al Paciente");
                                    listaTrabajadores.add(nuevoAgente);

                                    // Imprimir los datos del agente (puedes eliminar estas líneas si no las necesitas)
                                    System.out.println("Agente de Atención al Paciente agregado:");
                                    System.out.println("Nombre: " + nombreAgente);
                                    System.out.println("Documento: " + documentoAgente);
                                }
                            }
                        });
                        break;
                }
            }
        });

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setForeground(new Color(255, 255, 255));
        lblTipo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTipo.setBounds(310, 340, 180, 34);
        panelAgregar.add(lblTipo);

        contentPane.add(panelAgregar);
        contentPane.setComponentZOrder(panelAgregar, 0);

        if (nuevoPanel != null) {
            nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
        }
    }


    private void limpiarPanel(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    public ArrayList<Medico> getListaMedico() {
        return listaMedico;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Administrador().setVisible(true);
            }
        });
    }
}
