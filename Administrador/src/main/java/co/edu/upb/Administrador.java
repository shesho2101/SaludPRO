package co.edu.upb;

import principal.dominio.PersonalAtencion.PersonalAtencionServices;
import principal.dominio.consultorio.Consultorio;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.medicamento.Medicamento;
import principal.dominio.medicamento.MedicamentoServices;
import principal.dominio.medico.Medico;
import principal.dominio.medico.MedicoServices;
import principal.dominio.paciente.GrupoSanguineo;
import principal.dominio.paciente.Paciente;
import principal.dominio.paciente.PacienteServices;
import principal.dominio.sede.SedeServices;
import principal.dominio.user.Usuario;
import principal.dominio.user.UsuarioServices;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class Administrador extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelActual; // Panel actual que se va a mostrar
    private JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private int panelPositionX = 770;
    private UsuarioServices usuarioServices;
    private MedicoServices medicoServices;
    private SedeServices sedeServices;
    private ConsultorioServices consultorioServices;
    private PersonalAtencionServices personalAtencionServices;
    private Usuario usuario;
    private MedicamentoServices medicamentoServices;
    private PacienteServices pacienteServices;

    public Administrador() {
        usuarioServices = new UsuarioServices();
        medicoServices = new MedicoServices();
        consultorioServices = new ConsultorioServices();
        personalAtencionServices = new PersonalAtencionServices();
        usuario = new Usuario();
        medicamentoServices = new MedicamentoServices();
        sedeServices = new SedeServices();
        pacienteServices = new PacienteServices();

        FrameController.registerFrame("AdministradorFrame", this);

        setTitle("IPS Salud Pro - Administrador");
        setSize(1600, 900);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(150, 198, 198));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        buttons = new JButton[4];

        buttons[0] = createButton("Inventario medicamentos", 610, 320);
        buttons[1] = createButton("Administrar consultorios", 610, 400);
        buttons[2] = createButton("Administrar usuarios", 610, 480);
        buttons[3] = createButton("Administrar pacientes", 610, 560);


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
        lblFondo.setIcon(new ImageIcon(getClass().getResource("/interfazBlanca.jpg")));
        lblFondo.setBounds(0, 0, 1600, 900);
        contentPane.add(lblFondo);
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

                    case "Administrar pacientes":
                        try {
                            crearNuevoPanel(4); // Panel para administrar pacientes
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

    private void cerrarSesion() {
        FrameController.openFrame("LoginFrame");
        FrameController.cerrarSesion(); // Llama al controlador para cerrar sesión

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

    private void crearNuevoPanel(int opcion) throws Exception {
        if (panelActual != null) {
            limpiarPanel(panelActual);
        }

        panelActual = new JPanel(); // Crear nuevo panel
        panelActual.setBackground(new Color(250, 250, 250));
        panelActual.setBounds(800, 0, 800, 900);

        contentPane.add(panelActual);
        contentPane.repaint(); // Repintar para reflejar el nuevo panel

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

            case 4:
                cargarPacientes(); // Llamada al método para cargar pacientes
                break;

            default:
                // Manejar otros casos o simplemente no hacer nada
                break;
        }
    }

    private void cargarInventario() {
        if (movimiento && panelActual != null) {
            limpiarPanel(panelActual);
            panelActual.setBackground(new Color(7, 29, 63));
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);

            JLabel lblNombreCategoria = new JLabel("Inventario medicamentos");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(new Color(255, 255, 255));
            lblNombreCategoria.setBounds(257, 96, 267, 40);
            panelActual.add(lblNombreCategoria);

            JPanel jPanelTable = new JPanel();
            jPanelTable.setLayout(null); // Usar null layout
            jPanelTable.setBackground(Color.WHITE);
            jPanelTable.setBounds(40, 168, 700, 400); // Ubicación y tamaño del área para la tabla
            panelActual.add(jPanelTable);

            // Ejemplo de lista de usuarios
            List<Medicamento> medicamentos = null;
            try {
                medicamentos = medicamentoServices.listMedi();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Generación de filas para la tabla
            int y = 10; // Posición inicial para la primera fila
            for (Medicamento medicamento : medicamentos) {
                JPanel panelRow = new JPanel();
                panelRow.setVisible(true);
                panelRow.setSize(700, 30); // Tamaño de cada fila
                panelRow.setBackground(Color.WHITE); // Color de fondo para la fila
                panelRow.setLayout(null); // Posicionamiento libre

                // Etiquetas para mostrar información del usuario
                JLabel codigo = new JLabel(String.valueOf(medicamento.getCodigo()));
                codigo.setFont(new Font("Tahoma", Font.PLAIN, 15));
                codigo.setBounds(20, 5, 200, 25); // Posición y tamaño
                panelRow.add(codigo);

                JLabel nombre = new JLabel(medicamento.getNombre());
                nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
                nombre.setBounds(250, 5, 200, 25);
                panelRow.add(nombre);

                JLabel cantidad = new JLabel(String.valueOf(medicamento.getCantidad()));
                cantidad.setFont(new Font("Tahoma", Font.PLAIN, 15));
                cantidad.setBounds(530, 5, 200, 25); // Posición para "cargo"
                panelRow.add(cantidad);

                panelRow.setLocation(0, y); // Posición vertical para cada fila
                y += 40; // Incrementar para la siguiente fila

                jPanelTable.add(panelRow); // Añadir la fila al panel de la tabla
            }

            getContentPane().add(panelActual);
            getContentPane().setComponentZOrder(panelActual, 0);

            JButton btnAgendarCita = new JButton("Agregar");
            btnAgendarCita.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgendarCita.setBounds(65, 618, 180, 40);
            panelActual.add(btnAgendarCita);

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(300, 618, 180, 40);
            panelActual.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(540, 618, 180, 40);
            panelActual.add(btnBorrar);

            // Botón Agregar
            btnAgendarCita.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Limpia el panel actual antes de agregar el contenido
                    limpiarPanel(panelActual);

                    panelActual.setBackground(new Color(7, 29, 68));
                    panelActual.setLayout(null);

                    JLabel lblNombre = new JLabel("Nombre:");
                    lblNombre.setFont(new Font("Tahoma", Font.BOLD, 20));
                    lblNombre.setForeground(Color.WHITE);
                    lblNombre.setBounds(310, 80, 180, 30);
                    panelActual.add(lblNombre);

                    JTextField txtNombre = new JTextField();
                    txtNombre.setBounds(310, 120, 180, 30);
                    panelActual.add(txtNombre);

                    JLabel lblCantidad = new JLabel("Cantidad:");
                    lblCantidad.setFont(new Font("Tahoma", Font.BOLD, 20));
                    lblCantidad.setForeground(Color.WHITE);
                    lblCantidad.setBounds(310, 160, 180, 30);
                    panelActual.add(lblCantidad);

                    JTextField txtCantidad = new JTextField();
                    txtCantidad.setBounds(310, 200, 180, 30);
                    panelActual.add(txtCantidad);

                    JButton btnGuardar = new JButton("Guardar");
                    btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 20));
                    btnGuardar.setBounds(310, 260, 180, 40);
                    panelActual.add(btnGuardar);

                    btnGuardar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String nombre = txtNombre.getText();
                            String cantidad = txtCantidad.getText();

                            if (nombre.isEmpty() || cantidad.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            try {
                                medicamentoServices.createMedicamento(nombre, Integer.parseInt(cantidad));
                                JOptionPane.showMessageDialog(null, "Medicamento agregado exitosamente.");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error al agregar el medicamento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    panelActual.revalidate();
                    panelActual.repaint();
                }
            });

            // Botón Modificar
            btnModificar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    limpiarPanel(panelActual);

                    panelActual.setBackground(new Color(7, 29, 68));
                    panelActual.setLayout(null);

                    JLabel lblCodigo = new JLabel("Código del medicamento:");
                    lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 20));
                    lblCodigo.setForeground(Color.WHITE);
                    lblCodigo.setBounds(270, 80, 280, 30);
                    panelActual.add(lblCodigo);

                    JTextField txtCodigo = new JTextField();
                    txtCodigo.setBounds(310, 120, 180, 30);
                    panelActual.add(txtCodigo);

                    JButton btnBuscar = new JButton("Buscar");
                    btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 20));
                    btnBuscar.setBounds(310, 180, 180, 40);
                    panelActual.add(btnBuscar);

                    btnBuscar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String codigo = txtCodigo.getText();

                            if (codigo.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Por favor, ingrese el código del medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            try {
                                Medicamento medicamento = medicamentoServices.searchPerCod(Integer.parseInt(codigo));
                                if (medicamento == null) {
                                    JOptionPane.showMessageDialog(null, "No se encontró el medicamento.");
                                    return;
                                }

                                // Modificar detalles del medicamento
                                JTextField txtNuevoNombre = new JTextField(medicamento.getNombre());
                                txtNuevoNombre.setBounds(310, 260, 180, 30);
                                panelActual.add(txtNuevoNombre);

                                JTextField txtNuevaCantidad = new JTextField(String.valueOf(medicamento.getCantidad()));
                                txtNuevaCantidad.setBounds(310, 320, 180, 30);
                                panelActual.add(txtNuevaCantidad);

                                JButton btnGuardarCambios = new JButton("Guardar Cambios");
                                btnGuardarCambios.setFont(new Font("Tahoma", Font.BOLD, 20));
                                btnGuardarCambios.setBounds(275, 380, 250, 40);
                                panelActual.add(btnGuardarCambios);

                                btnGuardarCambios.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        medicamento.setNombre(txtNuevoNombre.getText());
                                        medicamento.setCantidad(Integer.parseInt(txtNuevaCantidad.getText()));

                                        try {
                                            medicamentoServices.updateMedicamentoCantidad(Integer.parseInt(txtCodigo.getText()), Integer.parseInt(txtNuevaCantidad.getText()));
                                            JOptionPane.showMessageDialog(null, "Cambios guardados exitosamente.");
                                        } catch (Exception ex) {
                                            JOptionPane.showMessageDialog(null, "Error al guardar cambios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                });

                                panelActual.revalidate();
                                panelActual.repaint();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error al buscar el medicamento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                }
            });

            // Botón Borrar
            btnBorrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    limpiarPanel(panelActual);

                    panelActual.setBackground(new Color(7, 29, 68));
                    panelActual.setLayout(null);

                    JLabel lblCodigo = new JLabel("Código del medicamento:");
                    lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 20));
                    lblCodigo.setForeground(Color.WHITE);
                    lblCodigo.setBounds(270, 80, 280, 30);
                    panelActual.add(lblCodigo);

                    JTextField txtCodigo = new JTextField();
                    txtCodigo.setBounds(310, 120, 180, 30);
                    panelActual.add(txtCodigo);

                    JButton btnBorrarMedicamento = new JButton("Borrar");
                    btnBorrarMedicamento.setFont(new Font("Tahoma", Font.BOLD, 20));
                    btnBorrarMedicamento.setBounds(310, 180, 180, 40);
                    panelActual.add(btnBorrarMedicamento);

                    btnBorrarMedicamento.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String codigo = txtCodigo.getText();

                            if (codigo.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Por favor, ingrese el código del medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            try {
                                medicamentoServices.delMed(Integer.parseInt(codigo));
                                JOptionPane.showMessageDialog(null, "Medicamento eliminado exitosamente.");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error al eliminar el medicamento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    panelActual.revalidate();
                    panelActual.repaint();
                }
            });


            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void cargarConsultorios() throws Exception {
        if (panelActual != null) {
            limpiarPanel(panelActual);
        }

        panelActual = new JPanel();
        panelActual.setBackground(new Color(7, 29, 68));
        panelActual.setLayout(null);
        panelActual.setBounds(800, 0, 800, 900);

        JLabel lblTitulo = new JLabel("Administrar Consultorios");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(285, 96, 250, 40);
        panelActual.add(lblTitulo);

        JPanel jPanelTable = new JPanel();
        jPanelTable.setLayout(null);
        jPanelTable.setBackground(Color.WHITE);
        jPanelTable.setBounds(40, 168, 700, 400);
        panelActual.add(jPanelTable);

        List<Consultorio> consultorios = consultorioServices.listCons();

        int y = 10;
        for (Consultorio consultorio : consultorios) {
            JPanel panelRow = new JPanel();
            panelRow.setVisible(true);
            panelRow.setSize(700, 30);
            panelRow.setBackground(Color.WHITE);
            panelRow.setLayout(null);

            JLabel numHab = new JLabel(String.valueOf(consultorio.getNumHab()));
            numHab.setFont(new Font("Tahoma", Font.PLAIN, 15));
            numHab.setBounds(20, 5, 250, 25);
            panelRow.add(numHab);

            JLabel sede = new JLabel(consultorio.getSede().getNombre());
            sede.setFont(new Font("Tahoma", Font.PLAIN, 15));
            sede.setBounds(280, 5, 200, 25);
            panelRow.add(sede);

            JLabel nombre = new JLabel(consultorio.getNombre());
            nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
            nombre.setBounds(540, 5, 200, 25);
            panelRow.add(nombre);

            panelRow.setLocation(0, y);
            y += 40;

            jPanelTable.add(panelRow);
        }

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnAgregar.setBounds(65, 618, 180, 40);
        panelActual.add(btnAgregar);

        JButton btnModificar = new JButton("Modificar");
        btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnModificar.setBounds(300, 618, 180, 40);
        panelActual.add(btnModificar);

        JButton btnBorrar = new JButton("Borrar");
        btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnBorrar.setBounds(540, 618, 180, 40);
        panelActual.add(btnBorrar);

        btnAgregar.addActionListener(e -> cargarPanelAgregarConsultorio());

        btnModificar.addActionListener(e -> cargarPanelModificarConsultorio());

        btnBorrar.addActionListener(e -> cargarPanelBorrarConsultorio());

        contentPane.add(panelActual);
        contentPane.setComponentZOrder(panelActual, 0);
    }

    private void cargarPanelAgregarConsultorio() {
        limpiarPanel(panelActual);
        panelActual.setBackground(new Color(7, 29, 68));
        panelActual.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre del Consultorio:");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(310, 30, 250, 34);
        panelActual.add(lblNombre);

        JTextField textFieldNombre = new JTextField();
        textFieldNombre.setBounds(310, 80, 250, 30);
        panelActual.add(textFieldNombre);

        JLabel lblSede = new JLabel("Sede del Consultorio:");
        lblSede.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblSede.setForeground(Color.WHITE);
        lblSede.setBounds(310, 130, 250, 34);
        panelActual.add(lblSede);

        JTextField textFieldSede = new JTextField();
        textFieldSede.setBounds(310, 180, 250, 30);
        panelActual.add(textFieldSede);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnGuardar.setBounds(310, 230, 180, 40);
        panelActual.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            String nombre = textFieldNombre.getText();
            String sede = textFieldSede.getText();

            if (nombre.isEmpty() || sede.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int codSede = sedeServices.searchPerNombre(sede).getCod(); // Buscar el código de la sede por su nombre
                consultorioServices.saveConsultorio(nombre, codSede); // Guardar el nuevo consultorio
                JOptionPane.showMessageDialog(null, "Consultorio agregado exitosamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al agregar el consultorio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelActual.revalidate();
        panelActual.repaint();
    }

    private void cargarPanelModificarConsultorio() {
        limpiarPanel(panelActual);
        panelActual.setBackground(new Color(7, 29, 68));
        panelActual.setLayout(null);

        JLabel lblNumHab = new JLabel("Número de Habitación:");
        lblNumHab.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNumHab.setForeground(Color.WHITE);
        lblNumHab.setBounds(310, 30, 250, 34);
        panelActual.add(lblNumHab);

        JTextField textFieldNumHab = new JTextField();
        textFieldNumHab.setBounds(310, 80, 250, 30);
        panelActual.add(textFieldNumHab);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnBuscar.setBounds(310, 120, 180, 40);
        panelActual.add(btnBuscar);

        btnBuscar.addActionListener(e -> {
            String numHab = textFieldNumHab.getText();

            if (numHab.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese el número de habitación.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Consultorio consultorio = consultorioServices.searchPerCod(Integer.parseInt(numHab));

                if (consultorio == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró el consultorio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                limpiarPanel(panelActual);

                JLabel lblNuevoNombre = new JLabel("Nuevo Nombre:");
                lblNuevoNombre.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblNuevoNombre.setForeground(Color.WHITE);
                lblNuevoNombre.setBounds(310, 180, 250, 34);
                panelActual.add(lblNuevoNombre);

                JTextField textFieldNuevoNombre = new JTextField(consultorio.getNombre());
                textFieldNuevoNombre.setBounds(310, 230, 250, 30);
                panelActual.add(textFieldNuevoNombre);

                JButton btnGuardar = new JButton("Guardar Cambios");
                btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 20));
                btnGuardar.setBounds(310, 280, 250, 40);
                panelActual.add(btnGuardar);

                btnGuardar.addActionListener(a -> {
                    String nuevoNombre = textFieldNuevoNombre.getText();

                    if (nuevoNombre.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        consultorioServices.modificarConsultorio(consultorio.getNumHab(), nuevoNombre);
                        JOptionPane.showMessageDialog(null, "Cambios guardados exitosamente.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al guardar cambios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                panelActual.revalidate();
                panelActual.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar el consultorio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelActual.revalidate();
        panelActual.repaint();
    }

    private void cargarPanelBorrarConsultorio() {
        limpiarPanel(panelActual);
        panelActual.setBackground(new Color(7, 29, 68));
        panelActual.setLayout(null);

        JLabel lblNumHab = new JLabel("Número de Habitación:");
        lblNumHab.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNumHab.setForeground(Color.WHITE);
        lblNumHab.setBounds(310, 30, 250, 34);
        panelActual.add(lblNumHab);

        JTextField textFieldNumHab = new JTextField();
        textFieldNumHab.setBounds(310, 80, 250, 30);
        panelActual.add(textFieldNumHab);

        JButton btnBorrarConsultorio = new JButton("Borrar Consultorio");
        btnBorrarConsultorio.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnBorrarConsultorio.setBounds(310, 120, 250, 40);
        panelActual.add(btnBorrarConsultorio);

        btnBorrarConsultorio.addActionListener(e -> {
            String numHab = textFieldNumHab.getText();

            if (numHab.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese el número de habitación.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                consultorioServices.deleteConsultorio(Integer.parseInt(numHab));
                JOptionPane.showMessageDialog(null, "Consultorio borrado exitosamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al borrar el consultorio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelActual.revalidate();
        panelActual.repaint();
    }

    private void cargarUsuarios() {
        if (movimiento && panelActual != null) {
            limpiarPanel(panelActual);
            // Crear el panel de usuarios
            panelActual.setBackground(new Color(7, 29, 68));
            panelActual.setLayout(null); // Usar null layout
            panelActual.setBounds(800, 0, 800, 900);

            JPanel jPanelTable = new JPanel();
            jPanelTable.setLayout(null); // Usar null layout
            jPanelTable.setBackground(Color.WHITE);
            jPanelTable.setBounds(40, 168, 700, 400); // Ubicación y tamaño del área para la tabla
            panelActual.add(jPanelTable);

            // Agregar botones para interacción
            JButton btnAgregar = new JButton("Agregar");
            btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgregar.setBounds(65, 618, 180, 40);
            panelActual.add(btnAgregar);

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(300, 618, 180, 40);
            panelActual.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(540, 618, 180, 40);
            panelActual.add(btnBorrar);

            getContentPane().add(panelActual);
            getContentPane().setComponentZOrder(panelActual, 0); // Ubicar el nuevo panel al frente

            // Configurar el título del panel
            JLabel lblNombreCategoria = new JLabel("Administrar Usuarios");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(Color.WHITE);
            lblNombreCategoria.setBounds(275, 96, 260, 40);
            panelActual.add(lblNombreCategoria);

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
                if(!Objects.equals(usuario.getCargo(), "Paciente")){
                    JPanel panelRow = new JPanel();
                    panelRow.setVisible(true);
                    panelRow.setSize(700, 30); // Tamaño de cada fila
                    panelRow.setBackground(Color.WHITE); // Color de fondo para la fila
                    panelRow.setLayout(null); // Posicionamiento libre

                    // Etiquetas para mostrar información del usuario
                    JLabel nombre = new JLabel(usuario.getNombre() +" "+ usuario.getApellidos());
                    nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
                    nombre.setBounds(20, 5, 200, 25); // Posición y tamaño
                    panelRow.add(nombre);

                    JLabel documento = new JLabel(usuario.getId());
                    documento.setFont(new Font("Tahoma", Font.PLAIN, 15));
                    documento.setBounds(250, 5, 200, 25);
                    panelRow.add(documento);

                    JLabel cargo = new JLabel(usuario.getCargo());
                    cargo.setFont(new Font("Tahoma", Font.PLAIN, 15));
                    cargo.setBounds(450, 5, 250, 25); // Posición para "cargo"
                    panelRow.add(cargo);

                    panelRow.setLocation(0, y); // Posición vertical para cada fila
                    y += 40; // Incrementar para la siguiente fila

                    jPanelTable.add(panelRow); // Añadir la fila al panel de la tabla
                }
            }

            // Acción del botón "Agregar"
            btnAgregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    limpiarPanel(panelActual);
                    cargarPanelAgregar(); // Llamar la función para agregar nuevos usuarios
                }
            });

            btnModificar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    limpiarPanel(panelActual);
                    cargarPanelModificarUsuario(); // Llamar la función para agregar nuevos usuarios
                }
            });


            // Acción del botón "Borrar"
            btnBorrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    limpiarPanel(panelActual);
                    cargarPanelBorrar();
                }
            });

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void cargarPanelAgregar() {
            panelActual.setBackground(new Color(7, 29, 68));
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);

            JLabel lblTipo = new JLabel("Tipo:");
            lblTipo.setForeground(new Color(255, 255, 255));
            lblTipo.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblTipo.setBounds(310, 340, 180, 34);
            panelActual.add(lblTipo);

            JComboBox<String> comboBoxTipo = new JComboBox<>();
            comboBoxTipo.addItem("Médico");
            comboBoxTipo.addItem("Agente de Atención al Paciente");
            comboBoxTipo.setBounds(310, 380, 180, 34);
            panelActual.add(comboBoxTipo);

            JButton btnAgregar = new JButton("Seleccionar Tipo");
            btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgregar.setBounds(300, 450, 210, 40);
            panelActual.add(btnAgregar);

            panelActual.revalidate();
            panelActual.repaint();

            btnAgregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String tipoSeleccionado = (String) comboBoxTipo.getSelectedItem();
                    switch (Objects.requireNonNull(tipoSeleccionado)) {
                        case "Médico":
                            limpiarPanel(panelActual);

                            JLabel lblNombreMedico = new JLabel("Nombre:");
                            lblNombreMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblNombreMedico.setForeground(Color.WHITE);
                            lblNombreMedico.setBounds(310, 30, 180, 34);
                            panelActual.add(lblNombreMedico);

                            JTextField textFieldNombreMedico = new JTextField();
                            textFieldNombreMedico.setBounds(310, 80, 180, 30);
                            panelActual.add(textFieldNombreMedico);

                            JLabel lblApellidoMedico = new JLabel("Apellido:");
                            lblApellidoMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblApellidoMedico.setForeground(Color.WHITE);
                            lblApellidoMedico.setBounds(310, 130, 180, 34);
                            panelActual.add(lblApellidoMedico);

                            JTextField textFieldApellidoMedico = new JTextField();
                            textFieldApellidoMedico.setBounds(310, 180, 180, 30);
                            panelActual.add(textFieldApellidoMedico);

                            JLabel lblDocumentoMedico = new JLabel("Documento:");
                            lblDocumentoMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblDocumentoMedico.setForeground(Color.WHITE);
                            lblDocumentoMedico.setBounds(310, 230, 180, 34);
                            panelActual.add(lblDocumentoMedico);

                            JPasswordField passwordFieldDocumentoMedico = new JPasswordField();
                            passwordFieldDocumentoMedico.setBounds(310, 280, 180, 30);
                            panelActual.add(passwordFieldDocumentoMedico);

                            JLabel lblEspecialidad = new JLabel("Especialidad:");
                            lblEspecialidad.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblEspecialidad.setForeground(Color.WHITE);
                            lblEspecialidad.setBounds(310, 330, 180, 34);
                            panelActual.add(lblEspecialidad);

                            String[] opciones = {"Medicina familiar", "Fisioterapia", "Medicina interna", "Psicología"};
                            JComboBox<String> menuDesplegableOpciones = new JComboBox<>(opciones);
                            menuDesplegableOpciones.setModel(new DefaultComboBoxModel<>(opciones));
                            menuDesplegableOpciones.setBounds(310, 380, 180, 30);
                            panelActual.add(menuDesplegableOpciones);

                            JLabel lblSede = new JLabel("Sede:");
                            lblSede.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblSede.setForeground(Color.WHITE);
                            lblSede.setBounds(310, 430, 180, 34);
                            panelActual.add(lblSede);

                            String[] sedes = {"Bucaramanga", "Floridablanca", "Piedecuesta", "Girón", "Lebrija", "Pamplona", "Rionegro"};
                            JComboBox<String> menuDesplegableSedes = new JComboBox<>(sedes);
                            menuDesplegableSedes.setModel(new DefaultComboBoxModel<>(sedes));
                            menuDesplegableSedes.setBounds(310, 480, 180, 30);
                            panelActual.add(menuDesplegableSedes);

                            JLabel lblConsultorio = new JLabel("Consultorio:");
                            lblConsultorio.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblConsultorio.setForeground(Color.WHITE);
                            lblConsultorio.setBounds(310, 530, 180, 34);
                            panelActual.add(lblConsultorio);

                            JTextField textFieldConsultorio = new JTextField();
                            textFieldConsultorio.setBounds(310, 580, 180, 30);
                            panelActual.add(textFieldConsultorio);

                            JButton btnAgregarMedico = new JButton("Agregar Médico");
                            btnAgregarMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
                            btnAgregarMedico.setBounds(300, 764, 210, 40);
                            panelActual.add(btnAgregarMedico);
                            btnAgregarMedico.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // Obtener valores de los campos de texto
                                    String nombreMedico = textFieldNombreMedico.getText();
                                    String apellidoMedico = textFieldApellidoMedico.getText();
                                    String documentoMedico = new String(passwordFieldDocumentoMedico.getPassword());
                                    String especialidad = (String) menuDesplegableOpciones.getSelectedItem();
                                    String sede = (String) menuDesplegableSedes.getSelectedItem();
                                    String textoConsultorio = textFieldConsultorio.getText(); // Obteniendo el valor del consultorio

                                    // Verificar que los campos no estén vacíos
                                    if (nombreMedico.isEmpty() || apellidoMedico.isEmpty() || documentoMedico.isEmpty() || especialidad.isEmpty() || sede.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                                        return; // Detener el proceso si hay campos vacíos
                                    }

                                    // Agregar el médico si todas las validaciones son correctas
                                    try {
                                        usuarioServices.createUsr(documentoMedico, nombreMedico, apellidoMedico, "Médico");

                                        medicoServices.createMed(documentoMedico, especialidad, consultorioServices.searchPerSede(sedeServices.searchPerNombre(sede).getCod(), textoConsultorio).getNumHab());
                                        JOptionPane.showMessageDialog(null, "Médico agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(null, "Error al crear el médico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            });

                            break;
                        case "Agente de Atención al Paciente":
                            limpiarPanel(panelActual);

                            JLabel lblNombreAgente = new JLabel("Nombre:");
                            lblNombreAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblNombreAgente.setForeground(Color.WHITE);
                            lblNombreAgente.setBounds(310, 30, 180, 34);
                            panelActual.add(lblNombreAgente);

                            JTextField textFieldNombreAgente = new JTextField();
                            textFieldNombreAgente.setBounds(310, 80, 180, 30);
                            panelActual.add(textFieldNombreAgente);

                            JLabel lblApellidoAgente = new JLabel("Apellido:");
                            lblApellidoAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblApellidoAgente.setForeground(Color.WHITE);
                            lblApellidoAgente.setBounds(310, 130, 180, 34);
                            panelActual.add(lblApellidoAgente);

                            JTextField textFieldApellidoAgente = new JTextField();
                            textFieldApellidoAgente.setBounds(310, 180, 180, 30);
                            panelActual.add(textFieldApellidoAgente);

                            JLabel lblDocumentoAgente = new JLabel("Documento:");
                            lblDocumentoAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblDocumentoAgente.setForeground(Color.WHITE);
                            lblDocumentoAgente.setBounds(310, 230, 180, 34);
                            panelActual.add(lblDocumentoAgente);

                            JTextField passwordFieldDocumentoAgente = new JTextField();
                            passwordFieldDocumentoAgente.setBounds(310, 280, 180, 30);
                            panelActual.add(passwordFieldDocumentoAgente);

                            JLabel lblSede1 = new JLabel("Sede:");
                            lblSede1.setFont(new Font("Tahoma", Font.BOLD, 20));
                            lblSede1.setForeground(Color.WHITE);
                            lblSede1.setBounds(310, 330, 180, 34);
                            panelActual.add(lblSede1);

                            String[] sedes1 = {"Bucaramanga", "Floridablanca", "Piedecuesta", "Girón", "Lebrija", "Pamplona", "Rionegro"};
                            JComboBox<String> menuDesplegableSedes1 = new JComboBox<>(sedes1);
                            menuDesplegableSedes1.setModel(new DefaultComboBoxModel<>(sedes1));
                            menuDesplegableSedes1.setBounds(310, 380, 180, 30);
                            panelActual.add(menuDesplegableSedes1);

                            JButton btnAgregarAgente = new JButton("Agregar");
                            btnAgregarAgente.setFont(new Font("Tahoma", Font.BOLD, 20));
                            btnAgregarAgente.setBounds(310, 430, 180, 40);
                            panelActual.add(btnAgregarAgente);
                            btnAgregarAgente.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String nombreAgente = textFieldNombreAgente.getText();
                                    String apellidoAgente = textFieldApellidoAgente.getText();
                                    String documentoAgente = new String(passwordFieldDocumentoAgente.getText());
                                    String sede = (String) menuDesplegableSedes1.getSelectedItem();

                                    String cargo = "Agente de Atención al Paciente";

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
                                            JOptionPane.showMessageDialog(null, "Agente de Atención al Paciente agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

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

            contentPane.add(panelActual);
            contentPane.setComponentZOrder(panelActual, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
    }

    private void cargarPanelModificarUsuario() {
        limpiarPanel(panelActual);
        panelActual.setBackground(new Color(7, 29, 68));
        panelActual.setLayout(null);

        // Campo para buscar el usuario por ID
        JLabel lblDocumento = new JLabel("Documento del usuario:");
        lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblDocumento.setForeground(Color.WHITE);
        lblDocumento.setBounds(285, 30, 250, 34);
        panelActual.add(lblDocumento);

        JTextField textFieldDocumento = new JTextField();
        textFieldDocumento.setBounds(310, 80, 180, 30);
        panelActual.add(textFieldDocumento);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnBuscar.setBounds(310, 120, 180, 40);
        panelActual.add(btnBuscar);

        btnBuscar.addActionListener(e -> {
            String documento = textFieldDocumento.getText();

            if (documento.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese el documento del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Usuario usuario = usuarioServices.searchPerID(documento);

                if (usuario == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                limpiarPanel(panelActual);

                // Campos para modificar detalles del usuario
                JLabel lblNuevoNombre = new JLabel("Nuevo nombre:");
                lblNuevoNombre.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblNuevoNombre.setForeground(Color.WHITE);
                lblNuevoNombre.setBounds(310, 180, 180, 34);
                panelActual.add(lblNuevoNombre);

                JTextField textFieldNuevoNombre = new JTextField(usuario.getNombre());
                textFieldNuevoNombre.setBounds(310, 230, 180, 30);
                panelActual.add(textFieldNuevoNombre);

                JLabel lblNuevoApellido = new JLabel("Nuevo apellido:");
                lblNuevoApellido.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblNuevoApellido.setForeground(Color.WHITE);
                lblNuevoApellido.setBounds(310, 280, 180, 34);
                panelActual.add(lblNuevoApellido);

                JTextField textFieldNuevoApellido = new JTextField(usuario.getApellidos());
                textFieldNuevoApellido.setBounds(310, 330, 180, 30);
                panelActual.add(textFieldNuevoApellido);

                JLabel lblNuevoCargo = new JLabel("Nuevo cargo:");
                lblNuevoCargo.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblNuevoCargo.setForeground(Color.WHITE);
                lblNuevoCargo.setBounds(310, 380, 180, 34);
                panelActual.add(lblNuevoCargo);

                // Menú desplegable para seleccionar el cargo
                JComboBox<String> comboBoxNuevoCargo = new JComboBox<>(new String[]{"Médico", "Agente de Atención al Paciente"});
                comboBoxNuevoCargo.setSelectedItem(usuario.getCargo()); // Seleccionar el cargo actual
                comboBoxNuevoCargo.setBounds(310, 430, 180, 30);
                panelActual.add(comboBoxNuevoCargo);

                JButton btnGuardarCambios = new JButton("Guardar Cambios");
                btnGuardarCambios.setFont(new Font("Tahoma", Font.BOLD, 20));
                btnGuardarCambios.setBounds(280, 480, 230, 40);
                panelActual.add(btnGuardarCambios);

                btnGuardarCambios.addActionListener(a -> {
                    String nuevoNombre = textFieldNuevoNombre.getText();
                    String nuevoApellido = textFieldNuevoApellido.getText();
                    String nuevoCargo = (String) comboBoxNuevoCargo.getSelectedItem(); // Obtener el cargo del menú desplegable

                    if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevoCargo.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        usuario.setNombre(nuevoNombre);
                        usuario.setApellidos(nuevoApellido);
                        usuario.setCargo(nuevoCargo);

                        usuarioServices.modificarUsuario(usuario); // Guardar cambios

                        JOptionPane.showMessageDialog(null, "Cambios guardados exitosamente.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al guardar cambios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                panelActual.revalidate();
                panelActual.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelActual.revalidate();
        panelActual.repaint();
    }

    private void cargarPanelBorrar() {
            limpiarPanel(panelActual);
            panelActual.setBackground(new Color(7, 29, 68));
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);

            JLabel lblDocumentoMedico = new JLabel("Documento:");
            lblDocumentoMedico.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblDocumentoMedico.setForeground(Color.WHITE);
            lblDocumentoMedico.setBounds(310, 230, 180, 34);
            panelActual.add(lblDocumentoMedico);

            JTextField passwordFieldDocumento = new JTextField();
            passwordFieldDocumento.setBounds(310, 280, 180, 30);
            panelActual.add(passwordFieldDocumento);

            JButton btnEliminarUsuario = new JButton("Eliminar usuario");
            btnEliminarUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnEliminarUsuario.setBounds(290, 330, 220, 40);
            panelActual.add(btnEliminarUsuario);

            btnEliminarUsuario.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String documento = new String(passwordFieldDocumento.getText());
                    try {
                        usuario = usuarioServices.searchPerID(documento);
                        if (usuario.getCargo().equals("Médico")) {
                            medicoServices.delMed(documento);
                            JOptionPane.showMessageDialog(null, "Médico borrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        }
                        if (usuario.getCargo().equals("Agente de Atención al Paciente")) {
                            personalAtencionServices.deletePA(documento);
                            JOptionPane.showMessageDialog(null, "Agente de Atención al Paciente borrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        }
                        if (usuario.getCargo().equals("Administrador")) {
                            personalAtencionServices.deletePA(documento);
                            JOptionPane.showMessageDialog(null, "No es posible eliminar el administrador", "Error", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        if (usuario.getCargo().equals("Administrador")) {
                            //nada
                        }
                        else{
                            usuarioServices.deleteUsr(documento);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            contentPane.add(panelActual);
            contentPane.setComponentZOrder(panelActual, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
    }

    private void cargarPacientes() {
        if (movimiento && panelActual != null) {
            limpiarPanel(panelActual); // Limpia el panel existente
            panelActual.setBackground(new Color(7, 29, 68));
            panelActual.setLayout(null); // Usar null layout
            panelActual.setBounds(800, 0, 800, 900);

            // Título
            JLabel lblTitulo = new JLabel("Administrar Pacientes");
            lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblTitulo.setForeground(Color.WHITE);
            lblTitulo.setBounds(285, 96, 250, 40);
            panelActual.add(lblTitulo);

            // Tabla de pacientes
            JPanel jPanelTable = new JPanel();
            jPanelTable.setLayout(null); // Usar null layout
            jPanelTable.setBackground(Color.WHITE);
            jPanelTable.setBounds(40, 168, 700, 400); // Ubicación y tamaño del área para la tabla
            panelActual.add(jPanelTable);

            // Aquí agregarías tu lógica para cargar la lista de pacientes desde tus servicios
            List<Paciente> pacientes = null;
            try {
                pacientes = pacienteServices.listPac(); // Obtener la lista de pacientes
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            int y = 10; // Posición inicial para la primera fila
            for (Paciente paciente : pacientes) {
                JPanel panelRow = new JPanel();
                panelRow.setVisible(true);
                panelRow.setSize(700, 30); // Tamaño de cada fila
                panelRow.setBackground(Color.WHITE); // Color de fondo para la fila
                panelRow.setLayout(null); // Posicionamiento libre

                JLabel nombre = new JLabel(paciente.getUsr().getNombre());
                nombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
                nombre.setBounds(20, 5, 200, 25);
                panelRow.add(nombre);

                JLabel documento = new JLabel(paciente.getID());
                documento.setFont(new Font("Tahoma", Font.PLAIN, 15));
                documento.setBounds(250, 5, 200, 25);
                panelRow.add(documento);

                JLabel edad = new JLabel(String.valueOf(paciente.getFechaNacimiento()));
                edad.setFont(new Font("Tahoma", Font.PLAIN, 15));
                edad.setBounds(450, 5, 200, 25); // Posición para "edad"
                panelRow.add(edad);

                JLabel dato = new JLabel(String.valueOf(paciente.getTelefono()));
                dato.setFont(new Font("Tahoma", Font.PLAIN, 15));
                dato.setBounds(620, 5, 200, 25); // Posición para "edad"
                panelRow.add(dato);

                panelRow.setLocation(0, y); // Posición vertical para cada fila
                y += 40; // Incrementar para la siguiente fila

                jPanelTable.add(panelRow); // Añadir la fila al panel de la tabla
            }

            // Botones para Agregar, Modificar y Borrar Pacientes
            JButton btnAgregar = new JButton("Agregar");
            btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnAgregar.setBounds(65, 618, 180, 40);
            panelActual.add(btnAgregar);

            JButton btnModificar = new JButton("Modificar");
            btnModificar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnModificar.setBounds(300, 618, 180, 40);
            panelActual.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(540, 618, 180, 40);
            panelActual.add(btnBorrar);

            // Agregar acciones para estos botones
            btnAgregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Aquí se implementaría el panel para agregar un nuevo paciente
                    cargarPanelAgregarPaciente();
                }
            });

            btnModificar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Aquí se implementaría el panel para modificar un paciente existente
                    cargarPanelModificarPaciente();
                }
            });

            btnBorrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Aquí se implementaría el panel para borrar un paciente
                    cargarPanelBorrarPaciente();
                }
            });

            contentPane.add(panelActual);
            contentPane.setComponentZOrder(panelActual, 0); // Ubicar el nuevo panel al frente
        }
    }

    private void cargarPanelAgregarPaciente() {
        limpiarPanel(panelActual); // Limpia el panel existente
        panelActual.setBackground(new Color(7, 29, 68)); // Color de fondo
        panelActual.setLayout(null); // Layout absoluto para control de posiciones

        // Campos para recoger información del paciente
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(310, 30, 180, 34); // Posición y tamaño
        panelActual.add(lblNombre);

        JTextField textFieldNombre = new JTextField();
        textFieldNombre.setBounds(310, 80, 180, 30); // Posición y tamaño
        panelActual.add(textFieldNombre);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblApellidos.setForeground(Color.WHITE);
        lblApellidos.setBounds(310, 130, 180, 34); // Posición y tamaño
        panelActual.add(lblApellidos);

        JTextField textFieldApellidos = new JTextField();
        textFieldApellidos.setBounds(310, 180, 180, 30); // Posición y tamaño
        panelActual.add(textFieldApellidos);

        JLabel lblDocumento = new JLabel("Documento:");
        lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblDocumento.setForeground(Color.WHITE);
        lblDocumento.setBounds(310, 230, 180, 34); // Posición y tamaño
        panelActual.add(lblDocumento);

        JTextField textFieldDocumento = new JTextField();
        textFieldDocumento.setBounds(310, 280, 180, 30); // Posición y tamaño
        panelActual.add(textFieldDocumento);

        JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento (YYYY-MM-DD):");
        lblFechaNacimiento.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblFechaNacimiento.setForeground(Color.WHITE);
        lblFechaNacimiento.setBounds(310, 330, 380, 34); // Posición y tamaño
        panelActual.add(lblFechaNacimiento);

        JTextField textFieldFechaNacimiento = new JTextField();
        textFieldFechaNacimiento.setBounds(310, 380, 180, 30); // Posición y tamaño
        panelActual.add(textFieldFechaNacimiento);

        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblSexo.setForeground(Color.WHITE);
        lblSexo.setBounds(310, 430, 180, 34); // Posición y tamaño
        panelActual.add(lblSexo);

        JComboBox<String> comboBoxSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        comboBoxSexo.setBounds(310, 480, 180, 30); // Posición y tamaño
        panelActual.add(comboBoxSexo);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTelefono.setForeground(Color.WHITE);
        lblTelefono.setBounds(310, 530, 180, 34); // Posición y tamaño
        panelActual.add(lblTelefono);

        JTextField textFieldTelefono = new JTextField();
        textFieldTelefono.setBounds(310, 580, 180, 30); // Posición y tamaño
        panelActual.add(textFieldTelefono);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(310, 630, 180, 34); // Posición y tamaño
        panelActual.add(lblCorreo);

        JTextField textFieldCorreo = new JTextField();
        textFieldCorreo.setBounds(310, 680, 180, 30); // Posición y tamaño
        panelActual.add(textFieldCorreo);

        JLabel lblGrupoSanguineo = new JLabel("Grupo Sanguíneo:");
        lblGrupoSanguineo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblGrupoSanguineo.setForeground(Color.WHITE);
        lblGrupoSanguineo.setBounds(310, 730, 250, 34); // Posición y tamaño
        panelActual.add(lblGrupoSanguineo);

        JComboBox<String> comboBoxGrupoSanguineo = new JComboBox<>(new String[]{"A_POSITIVO", "O_POSITIVO", "B_POSITIVO", "AB_POSITIVO", "A_NEGATIVO", "O_NEGATIVO", "B_NEGATIVO", "AB_NEGATIVO"});
        comboBoxGrupoSanguineo.setBounds(310, 780, 180, 30); // Posición y tamaño
        panelActual.add(comboBoxGrupoSanguineo);

        JButton btnAgregarPaciente = new JButton("Agregar Paciente");
        btnAgregarPaciente.setBounds(310, 830, 180, 40); // Posición y tamaño
        panelActual.add(btnAgregarPaciente);

// Lógica para agregar paciente
        btnAgregarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textFieldNombre.getText();
                String apellidos = textFieldApellidos.getText();
                String documento = textFieldDocumento.getText();
                String fechaNacimiento = textFieldFechaNacimiento.getText();
                String sexo = comboBoxSexo.getSelectedItem().toString();
                String telefono = textFieldTelefono.getText();
                String correo = textFieldCorreo.getText();
                String grupoSanguineo = comboBoxGrupoSanguineo.getSelectedItem().toString();

                if (nombre.isEmpty() || documento.isEmpty() || fechaNacimiento.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    usuarioServices.createUsr(documento, nombre, apellidos, "Paciente");
                    pacienteServices.createPac(documento, fechaNacimiento, sexo, telefono, correo, GrupoSanguineo.valueOf(grupoSanguineo));

                    JOptionPane.showMessageDialog(null, "Paciente agregado exitosamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar el paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelActual.revalidate();
        panelActual.repaint(); // Repintar
    }

    private void cargarPanelModificarPaciente() {
        limpiarPanel(panelActual); // Limpia el panel para reutilizarlo
        panelActual.setBackground(new Color(7, 29, 68));
        panelActual.setLayout(null);

        // Campo para buscar el paciente por ID
        JLabel lblDocumento = new JLabel("Documento del paciente:");
        lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblDocumento.setForeground(Color.WHITE);
        lblDocumento.setBounds(310, 30, 250, 34); // Posición y tamaño
        panelActual.add(lblDocumento);

        JTextField textFieldDocumento = new JTextField();
        textFieldDocumento.setBounds(310, 80, 180, 30); // Posición y tamaño
        panelActual.add(textFieldDocumento);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnBuscar.setBounds(310, 120, 180, 40); // Posición y tamaño
        panelActual.add(btnBuscar);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String documento = textFieldDocumento.getText();

                if (documento.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese el documento del paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Paciente paciente = pacienteServices.searchPerId(documento);

                    if (paciente == null) {
                        JOptionPane.showMessageDialog(null, "No se encontró el paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    limpiarPanel(panelActual); // Limpia el panel para mostrar la información del paciente

                    JLabel lblNombre = new JLabel("Nuevo nombre:");
                    lblNombre.setFont(new Font("Tahoma", Font.BOLD, 20));
                    lblNombre.setForeground(Color.WHITE);
                    lblNombre.setBounds(310, 180, 180, 34); // Posición y tamaño
                    panelActual.add(lblNombre);

                    JTextField textFieldNuevoNombre = new JTextField(paciente.getUsr().getNombre());
                    textFieldNuevoNombre.setBounds(310, 230, 180, 30); // Posición y tamaño
                    panelActual.add(textFieldNuevoNombre);

                    JLabel lblCorreo = new JLabel("Nuevo correo:");
                    lblCorreo.setFont(new Font("Tahoma", Font.BOLD, 20));
                    lblCorreo.setForeground(Color.WHITE);
                    lblCorreo.setBounds(310, 280, 180, 34); // Posición y tamaño
                    panelActual.add(lblCorreo);

                    JTextField textFieldNuevoCorreo = new JTextField(paciente.getCorreo());
                    textFieldNuevoCorreo.setBounds(310, 330, 180, 30); // Posición y tamaño
                    panelActual.add(textFieldNuevoCorreo);

                    JLabel lblTelefono = new JLabel("Nuevo teléfono:");
                    lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 20));
                    lblTelefono.setForeground(Color.WHITE);
                    lblTelefono.setBounds(310, 380, 180, 34); // Posición y tamaño
                    panelActual.add(lblTelefono);

                    JTextField textFieldNuevoTelefono = new JTextField(paciente.getTelefono());
                    textFieldNuevoTelefono.setBounds(310, 430, 180, 30); // Posición y tamaño
                    panelActual.add(textFieldNuevoTelefono);

                    JButton btnGuardarCambios = new JButton("Guardar Cambios");
                    btnGuardarCambios.setBounds(310, 480, 180, 40); // Posición y tamaño
                    btnGuardarCambios.setFont(new Font("Tahoma", Font.BOLD, 20));
                    panelActual.add(btnGuardarCambios);

                    btnGuardarCambios.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String nuevoNombre = textFieldNuevoNombre.getText();
                            String nuevoCorreo = textFieldNuevoCorreo.getText();
                            String nuevoTelefono = textFieldNuevoTelefono.getText();

                            if (nuevoNombre.isEmpty() || nuevoCorreo.isEmpty() || nuevoTelefono.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            try {
                                paciente.getUsr().setNombre(nuevoNombre);
                                paciente.setCorreo(nuevoCorreo);
                                paciente.setTelefono(nuevoTelefono);

                                pacienteServices.modPaciente(paciente); // Llama al servicio para guardar los cambios

                                JOptionPane.showMessageDialog(null, "Cambios guardados exitosamente.");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, "Error al guardar cambios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    panelActual.revalidate();
                    panelActual.repaint();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar el paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelActual.revalidate();
        panelActual.repaint();
    }

    private void cargarPanelBorrarPaciente() {
        limpiarPanel(panelActual); // Limpia el panel
        panelActual.setBackground(new Color(7, 29, 68));
        panelActual.setLayout(null);

        JLabel lblDocumento = new JLabel("Documento del paciente:");
        lblDocumento.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblDocumento.setForeground(Color.WHITE);
        lblDocumento.setBounds(305, 400, 250, 34);
        panelActual.add(lblDocumento);

        JTextField textFieldDocumento = new JTextField();
        textFieldDocumento.setBounds(330, 450, 180, 30);
        panelActual.add(textFieldDocumento);

        JButton btnBorrarPaciente = new JButton("Borrar Paciente");
        btnBorrarPaciente.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnBorrarPaciente.setBounds(305, 500, 235, 40);
        panelActual.add(btnBorrarPaciente);

        btnBorrarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String documento = textFieldDocumento.getText();

                if (documento.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese el documento del paciente.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    pacienteServices.delPac(documento);
                    usuarioServices.deleteUsr(documento);
                    JOptionPane.showMessageDialog(null, "Paciente borrado exitosamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al borrar el paciente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelActual.revalidate();
        panelActual.repaint();
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
                new Administrador().setVisible(true);
            }
        });
    }
}
