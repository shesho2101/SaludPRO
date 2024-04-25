package co.edu.upb;

import principal.dominio.PersonalAtencion.PersonalAtencionServices;
import principal.dominio.medico.Medico;
import principal.dominio.medico.MedicoServices;
import principal.dominio.user.Usuario;
import principal.dominio.user.UsuarioServices;

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

    private UsuarioServices usuarioServices;

    private MedicoServices medicoServices;

    private PersonalAtencionServices personalAtencionServices;

    private Usuario usuario;

    private Medico medico;


    public Administrador() {
        usuarioServices = new UsuarioServices();
        medicoServices = new MedicoServices();
        personalAtencionServices = new PersonalAtencionServices();
        usuario = new Usuario();
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

        // Añadir el botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnCerrarSesion.setBounds(10, 790, 200, 40); // Posición en la parte inferior izquierda
        btnCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //cerrarSesion(); // Llama a la función para cerrar sesión
            }
        });
        contentPane.add(btnCerrarSesion);

        JLabel lblFondo = new JLabel("");
        lblFondo.setIcon(new ImageIcon(getClass().getResource("/interfazBlanca.jpg")));
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
    }

    /*private void cerrarSesion() {
        FrameManager frameManager = new FrameManager();
        FrameManager.cerrarSesion(Administrador.this);
    }*/

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
                        try {
                            crearNuevoPanel(1);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        break;

                    case "Administrar consultorios":
                        try {
                            crearNuevoPanel(2);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        break;

                    case "Administrar usuarios":
                        try {
                            crearNuevoPanel(3);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
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

    private void crearNuevoPanel(int opcion) throws Exception {
        // Limpiar el contenido previo del panel antes de crear algo nuevo
        if (nuevoPanel != null) {
            limpiarPanel(nuevoPanel);
        } else {
            nuevoPanel = new JPanel(); // Crear nuevo panel si no existe
        }

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
            limpiarPanel(nuevoPanel);
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

    private void cargarConsultorios() throws Exception {
        if (movimiento) {
            limpiarPanel(nuevoPanel);

            Medico medico;
            medico = new Medico();
            usuario = medico.getUsr();

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
            for (int i = 0; i == 0; i++) {


                JPanel panelRow = new JPanel();
                panelRow.setVisible(true);
                panelRow.setSize(1050, 30);
                panelRow.setBackground(Color.WHITE);
                jPanelTable.add(panelRow);

                panelRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cursor clickeable

                JLabel nombre = new JLabel(usuario.getNombre());
                nombre.setVisible(true);
                nombre.setSize(250, 25);
                nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(nombre);
                nombre.setLocation(20, 5);

                JLabel documento = new JLabel(medico.getID());
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

                JLabel especialidad = new JLabel(medico.getEspecializacion());
                especialidad.setVisible(true);
                especialidad.setSize(320, 30);
                especialidad.setFont(new Font("Tahoma", Font.PLAIN, 15));
                panelRow.add(especialidad);
                especialidad.setLocation(470, 5);

                JLabel consultorio = new JLabel(String.valueOf(medico.getCons()));
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
            limpiarPanel(nuevoPanel);
            // Crear el panel de usuarios
            JPanel panelCancelarCita = new JPanel();
            panelCancelarCita.setBackground(new Color(7, 29, 68));
            panelCancelarCita.setLayout(null); // Usar null layout
            panelCancelarCita.setBounds(800, 0, 800, 900);

            JPanel jPanelTable = new JPanel();
            jPanelTable.setLayout(null); // Usar null layout
            jPanelTable.setBackground(Color.WHITE);
            jPanelTable.setBounds(40, 168, 700, 400); // Ubicación y tamaño del área para la tabla
            panelCancelarCita.add(jPanelTable);

            // Agregar botones para interacción
            JButton btnAgregar = new JButton("Agregar");
            btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgregar.setBounds(65, 618, 180, 40);
            panelCancelarCita.add(btnAgregar);

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(300, 618, 180, 40);
            panelCancelarCita.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(540, 618, 180, 40);
            panelCancelarCita.add(btnBorrar);

            getContentPane().add(panelCancelarCita);
            getContentPane().setComponentZOrder(panelCancelarCita, 0); // Ubicar el nuevo panel al frente

            // Configurar el título del panel
            JLabel lblNombreCategoria = new JLabel("Administrar Usuarios");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(Color.WHITE);
            lblNombreCategoria.setBounds(285, 96, 210, 40);
            panelCancelarCita.add(lblNombreCategoria);

            // Ejemplo de lista de usuarios
            List<Usuario> usuarios = null;
            try {
                usuarios = usuarioServices.listUsr();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Generación de filas para la tabla
            int y = 10; // Posición inicial para la primera fila
            for (Usuario usuario : usuarios) {
                JPanel panelRow = new JPanel();
                panelRow.setVisible(true);
                panelRow.setSize(700, 30); // Tamaño de cada fila
                panelRow.setBackground(Color.WHITE); // Color de fondo para la fila
                panelRow.setLayout(null); // Posicionamiento libre

                // Etiquetas para mostrar información del usuario
                JLabel nombre = new JLabel(usuario.getNombre());
                nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
                nombre.setBounds(20, 5, 200, 25); // Posición y tamaño
                panelRow.add(nombre);

                JLabel documento = new JLabel(usuario.getId());
                documento.setFont(new Font("Tahoma", Font.PLAIN, 15));
                documento.setBounds(250, 5, 200, 25);
                panelRow.add(documento);

                JLabel cargo = new JLabel(usuario.getCargo());
                cargo.setFont(new Font("Tahoma", Font.PLAIN, 15));
                cargo.setBounds(480, 5, 200, 25); // Posición para "cargo"
                panelRow.add(cargo);

                panelRow.setLocation(0, y); // Posición vertical para cada fila
                y += 40; // Incrementar para la siguiente fila

                jPanelTable.add(panelRow); // Añadir la fila al panel de la tabla
            }

            // Acción del botón "Agregar"
            btnAgregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cargarPanelAgregar(); // Llamar la función para agregar nuevos usuarios
                    limpiarPanel(panelCancelarCita);
                }
            });

            // Acción del botón "Borrar"
            btnBorrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cargarPanelBorrar();
                    limpiarPanel(panelCancelarCita);
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
                        lblNombreMedico.setBounds(310, 30, 180, 34);
                        panelAgregar.add(lblNombreMedico);

                        JTextField textFieldNombreMedico = new JTextField();
                        textFieldNombreMedico.setBounds(310, 80, 180, 30);
                        panelAgregar.add(textFieldNombreMedico);

                        JLabel lblApellidoMedico = new JLabel("Apellido:");
                        lblApellidoMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblApellidoMedico.setForeground(Color.WHITE);
                        lblApellidoMedico.setBounds(310, 130, 180, 34);
                        panelAgregar.add(lblApellidoMedico);

                        JTextField textFieldApellidoMedico = new JTextField();
                        textFieldApellidoMedico.setBounds(310, 180, 180, 30);
                        panelAgregar.add(textFieldApellidoMedico);

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
                                String apellidoMedico = textFieldApellidoMedico.getText();
                                String documentoMedico = new String(passwordFieldDocumentoMedico.getPassword());
                                String especialidad = (String) menuDesplegableOpciones.getSelectedItem();
                                String sede = (String) menuDesplegableSedes.getSelectedItem();
                                int consultorio = Integer.parseInt(textFieldConsultorio.getText());
                                String cargo = "Médico";

                                if (nombreMedico.isEmpty() || apellidoMedico.isEmpty() || documentoMedico.isEmpty() || especialidad.isEmpty() || sede.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    try {
                                        usuarioServices.createUsr(documentoMedico, nombreMedico, apellidoMedico, cargo);
                                    } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                    }

                                    try {
                                        medicoServices.createMed(documentoMedico, especialidad, consultorio);
                                    } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                    }

                                }
                            }
                        });
                        break;
                    case "Agente de Atención al Paciente":
                        limpiarPanel(panelAgregar);

                        JLabel lblNombreAgente = new JLabel("Nombre:");
                        lblNombreAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblNombreAgente.setForeground(Color.WHITE);
                        lblNombreAgente.setBounds(310, 30, 180, 34);
                        panelAgregar.add(lblNombreAgente);

                        JTextField textFieldNombreAgente = new JTextField();
                        textFieldNombreAgente.setBounds(310, 80, 180, 30);
                        panelAgregar.add(textFieldNombreAgente);

                        JLabel lblApellidoAgente = new JLabel("Apellido:");
                        lblApellidoAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblApellidoAgente.setForeground(Color.WHITE);
                        lblApellidoAgente.setBounds(310, 130, 180, 34);
                        panelAgregar.add(lblApellidoAgente);

                        JTextField textFieldApellidoAgente = new JTextField();
                        textFieldApellidoAgente.setBounds(310, 180, 180, 30);
                        panelAgregar.add(textFieldApellidoAgente);

                        JLabel lblDocumentoAgente = new JLabel("Documento:");
                        lblDocumentoAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblDocumentoAgente.setForeground(Color.WHITE);
                        lblDocumentoAgente.setBounds(310, 230, 180, 34);
                        panelAgregar.add(lblDocumentoAgente);

                        JPasswordField passwordFieldDocumentoAgente = new JPasswordField();
                        passwordFieldDocumentoAgente.setBounds(310, 280, 180, 30);
                        panelAgregar.add(passwordFieldDocumentoAgente);

                        JLabel lblSede1 = new JLabel("Sede:");
                        lblSede1.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblSede1.setForeground(Color.WHITE);
                        lblSede1.setBounds(310, 330, 180, 34);
                        panelAgregar.add(lblSede1);

                        String[] sedes1 = {"Bucaramanga", "Floridablanca", "Piedecuesta", "Girón", "Lebrija", "Pamplona", "Rionegro"};
                        JComboBox<String> menuDesplegableSedes1 = new JComboBox<>(sedes1);
                        menuDesplegableSedes1.setModel(new DefaultComboBoxModel<>(sedes1));
                        menuDesplegableSedes1.setBounds(310, 380, 180, 30);
                        panelAgregar.add(menuDesplegableSedes1);

                        JButton btnAgregarAgente = new JButton("Agregar");
                        btnAgregarAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                        btnAgregarAgente.setBounds(310, 430, 180, 40);
                        panelAgregar.add(btnAgregarAgente);
                        btnAgregarAgente.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String nombreAgente = textFieldNombreAgente.getText();
                                String apellidoAgente = textFieldApellidoAgente.getText();
                                String documentoAgente = new String(passwordFieldDocumentoAgente.getPassword());
                                String sede = (String) menuDesplegableSedes1.getSelectedItem();

                                String cargo = "Atención al paciente";

                                if (nombreAgente.isEmpty() || apellidoAgente.isEmpty() || documentoAgente.isEmpty() || sede.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    try {
                                        usuarioServices.createUsr(documentoAgente, nombreAgente, apellidoAgente, cargo);
                                    } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    try {
                                        personalAtencionServices.createPersonalAtencion(documentoAgente, sede);
                                    } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                    }
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

    private void cargarPanelBorrar() {
        JPanel panelBorrar = new JPanel();
        panelBorrar.setBackground(new Color(7, 29, 68));
        panelBorrar.setLayout(null);
        panelBorrar.setBounds(800, 0, 800, 900);

        JLabel lblDocumentoMedico = new JLabel("Documento:");
        lblDocumentoMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblDocumentoMedico.setForeground(Color.WHITE);
        lblDocumentoMedico.setBounds(310, 230, 180, 34);
        panelBorrar.add(lblDocumentoMedico);

        JPasswordField passwordFieldDocumento = new JPasswordField();
        passwordFieldDocumento.setBounds(310, 280, 180, 30);
        panelBorrar.add(passwordFieldDocumento);

        JButton btnEliminarUsuario = new JButton("Eliminar usuario");
        btnEliminarUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnEliminarUsuario.setBounds(290, 330, 240, 40);
        panelBorrar.add(btnEliminarUsuario);

        btnEliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String documento = new String(passwordFieldDocumento.getPassword());
                try {
                    usuario = usuarioServices.searchPerID(documento);
                    if(usuario.getCargo().equals("Médico")){
                        medicoServices.delMed(documento);
                    }
                    if (usuario.getCargo().equals("Atención al paciente")){
                        personalAtencionServices.deletePA(documento);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    usuarioServices.deleteUsr(documento);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        contentPane.add(panelBorrar);
        contentPane.setComponentZOrder(panelBorrar, 0);

        if (nuevoPanel != null) {
            nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
        }
    }

    // Método para limpiar un panel
    private void limpiarPanel(JPanel panel) {
        panel.removeAll();
        panel.revalidate(); // Revalidar el diseño
        panel.repaint();   // Repintar el panel para aplicar cambios
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
