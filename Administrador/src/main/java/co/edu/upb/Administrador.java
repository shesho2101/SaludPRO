package co.edu.upb;

import principal.dominio.PersonalAtencion.PersonalAtencionServices;
import principal.dominio.consultorio.Consultorio;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.medicamento.Medicamento;
import principal.dominio.medicamento.MedicamentoServices;
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

    private JPanel panelActual; // Panel actual que se va a mostrar

    private JButton[] buttons;
    private boolean movimiento = false;
    private JPanel nuevoPanel;
    private int panelPositionX = 770;

    private UsuarioServices usuarioServices;

    private MedicoServices medicoServices;

    private ConsultorioServices cs;

    private PersonalAtencionServices personalAtencionServices;

    private Usuario usuario;

    private MedicamentoServices medicamentoServices;


    public Administrador() {
        usuarioServices = new UsuarioServices();
        medicoServices = new MedicoServices();
        cs = new ConsultorioServices();
        personalAtencionServices = new PersonalAtencionServices();
        usuario = new Usuario();
        medicamentoServices = new MedicamentoServices();

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
                int deltaX = 33;
                int duration = 600;
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

            default:
                // Manejar otros casos o simplemente no hacer nada
                break;
        }
    }

    private void cargarInventario() {
        if (movimiento && panelActual != null) {
            limpiarPanel(nuevoPanel);
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
                    lblCodigo.setBounds(310, 80, 180, 30);
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
                                btnGuardarCambios.setBounds(310, 380, 180, 40);
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
                    lblCodigo.setBounds(310, 80, 180, 30);
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
        if (movimiento && panelActual != null) {
            limpiarPanel(nuevoPanel);

            Medico medico;
            medico = new Medico();
            usuario = medico.getUsr();

            panelActual.setBackground(new Color(7, 29, 68));
            panelActual.setLayout(null);
            panelActual.setBounds(800, 0, 800, 900);

            JPanel jPanelTable = new JPanel();
            jPanelTable.setLayout(null); // Usar null layout
            jPanelTable.setBackground(Color.WHITE);
            jPanelTable.setBounds(40, 168, 700, 400);
            panelActual.add(jPanelTable);

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

                JLabel sede = new JLabel(medico.getCons().getSede().getNombre());
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
            panelActual.add(btnModificar);

            JButton btnBorrar = new JButton("Borrar");
            btnBorrar.setFont(new Font("Tahoma", Font.BOLD, 20));
            btnBorrar.setBounds(430, 618, 180, 40);
            panelActual.add(btnBorrar);

            getContentPane().add(panelActual);
            getContentPane().setComponentZOrder(panelActual, 0);

            JLabel lblNombreCategoria = new JLabel("Control consultorios");
            lblNombreCategoria.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNombreCategoria.setForeground(new Color(255, 255, 255));
            lblNombreCategoria.setBounds(289, 96, 202, 40);
            panelActual.add(lblNombreCategoria);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    private void cargarUsuarios() {
        if (movimiento && panelActual != null) {
            limpiarPanel(nuevoPanel);
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
            lblNombreCategoria.setBounds(285, 96, 210, 40);
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
                cargo.setBounds(450, 5, 250, 25); // Posición para "cargo"
                panelRow.add(cargo);

                panelRow.setLocation(0, y); // Posición vertical para cada fila
                y += 40; // Incrementar para la siguiente fila

                jPanelTable.add(panelRow); // Añadir la fila al panel de la tabla
            }

            // Acción del botón "Agregar"
            btnAgregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    limpiarPanel(panelActual);
                    cargarPanelAgregar(); // Llamar la función para agregar nuevos usuarios
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
        if (movimiento && panelActual != null) {
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
                            btnAgregarMedico.setBounds(320, 764, 180, 40);
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
                                        //usuarioServices.createUsr(documentoMedico, nombreMedico, apellidoMedico, "Médico");
                                        //medicoServices.createMed(documentoMedico, especialidad, textoConsultorio);
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
    }

    private void cargarPanelBorrar() {
        if (movimiento && panelActual != null) {
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
            btnEliminarUsuario.setBounds(290, 330, 240, 40);
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

            contentPane.add(panelActual);
            contentPane.setComponentZOrder(panelActual, 0);

            if (nuevoPanel != null) {
                nuevoPanel.setLocation(panelPositionX, nuevoPanel.getY());
            }
        }
    }

    // Método para limpiar el panel actual antes de agregar uno nuevo
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
