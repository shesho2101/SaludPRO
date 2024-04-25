package co.edu.upb;

import principal.dominio.user.Usuario;
import principal.dominio.user.UsuarioServices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
        private JTextField nombreUsuario;
        private JPasswordField passwordField;
        private UsuarioServices usuarioServices; // Servicio para interactuar con la base de datos de usuarios

        public Login() {
                // Configuración de la ventana
                setTitle("IPS Salud Pro - Inicio de Sesión");
                setBounds(0, 0, 1600, 900);
                setResizable(false); // Desactivar la capacidad de redimensionamiento
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

                // Inicializar el servicio de usuarios
                usuarioServices = new UsuarioServices();

                // Creación de paneles
                JPanel mainPanel = new JPanel();
                mainPanel.setBackground(new Color(150, 198, 198));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                // Diseño y estilos adicionales
                getContentPane().add(mainPanel);
                mainPanel.setLayout(null);

                JLabel lblUsuario = new JLabel("Usuario");
                lblUsuario.setForeground(new Color(7, 29, 68));
                lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblUsuario.setBounds(334, 339, 100, 25);
                mainPanel.add(lblUsuario);

                JLabel lblPassword = new JLabel("Contraseña");
                lblPassword.setForeground(new Color(7, 29, 68));
                lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblPassword.setBounds(334, 415, 127, 25);
                mainPanel.add(lblPassword);

                nombreUsuario = new JTextField();
                nombreUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
                nombreUsuario.setBounds(334, 374, 213, 31);
                mainPanel.add(nombreUsuario);

                passwordField = new JPasswordField();
                passwordField.setFont(new Font("Tahoma", Font.PLAIN, 15));
                passwordField.setBounds(334, 450, 213, 31);
                mainPanel.add(passwordField);

                JButton btnLogin = new JButton("Iniciar sesión");
                btnLogin.setBackground(new Color(7, 29, 68));
                btnLogin.setForeground(new Color(255, 255, 255));
                btnLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
                btnLogin.setBounds(355, 505, 170, 50);
                mainPanel.add(btnLogin);

                // Agregar evento de clic para el botón de iniciar sesión
                btnLogin.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // Recuperar valores de entrada
                                String nombre = nombreUsuario.getText();
                                String password = new String(passwordField.getPassword());

                                // Validar la sesión
                                validarSesion(nombre, password);
                        }
                });

                // Imágenes y otros elementos visuales
                JLabel lblImagenLogin = new JLabel(new ImageIcon(getClass().getResource("/login.jpg")));
                lblImagenLogin.setBounds(250, 223, 380, 540);
                mainPanel.add(lblImagenLogin);

                JLabel lblFondoLogin = new JLabel(new ImageIcon(getClass().getResource("/fondoLogin.jpg")));
                lblFondoLogin.setBounds(0, 0, 1600, 900);
                mainPanel.add(lblFondoLogin);

                // Hacer visible la ventana
                setVisible(true);
        }

        // Método para validar la sesión
        public void validarSesion(String nombreUsuario, String password) {
                try {
                        // Buscar al usuario por ID (password)
                        Usuario usuario = usuarioServices.searchPerID(password);

                        if (usuario != null) {
                                if (usuario.getNombre().equals(nombreUsuario)) {
                                        // Verifica el cargo del usuario y redirige a la ventana adecuada
                                        String cargo = usuario.getCargo();
                                        System.out.println(cargo);

                                        switch (cargo) {
                                                case "Administrador":
                                                        // Ventana del administrador
                                                        Administrador ventanaAdmin = new Administrador();
                                                        ventanaAdmin.setVisible(true);
                                                        break;

                                                case "Médico":
                                                        // Ventana del médico
                                                        co.edu.upb.Medico ventanaMedico = new Medico();
                                                        ventanaMedico.setVisible(true);
                                                        break;

                                                case "PA":
                                                        // Ventana del agente de atención al paciente
                                                        co.edu.upb.AgenteAtencionAlPaciente ventanaAgente = new AgenteAtencionAlPaciente();
                                                        ventanaAgente.setVisible(true);
                                                        break;

                                                default:
                                                        JOptionPane.showMessageDialog(
                                                                this,
                                                                "El cargo del usuario no es reconocido.",
                                                                "Error",
                                                                JOptionPane.ERROR_MESSAGE
                                                        );
                                                        break;
                                        }

                                        // Cerrar la ventana de login después de redirigir
                                        dispose();
                                } else {
                                        // Usuario o contraseña incorrectos
                                        JOptionPane.showMessageDialog(
                                                this,
                                                "Usuario o contraseña incorrectos.",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE
                                        );
                                }
                        } else {
                                // Usuario no encontrado
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Usuario no encontrado.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                        }
                } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(
                                this,
                                "Error inesperado al validar sesión.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                }
        }

        // Método principal para ejecutar la aplicación
        public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                                new Login();
                        }
                });
        }
}
