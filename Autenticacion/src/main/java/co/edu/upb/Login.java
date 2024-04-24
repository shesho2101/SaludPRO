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

        public Login() {
                // Configuración de la ventana
                setTitle("IPS Salud Pro - Inicio de Sesión");
                setBounds(0,0,1600,900);
                setResizable(false); // Desactivar la capacidad de redimensionamiento
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

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

                JLabel lblImagenLogin = new JLabel();
                lblImagenLogin.setIcon(new ImageIcon(getClass().getResource("/login.jpg")));
                lblImagenLogin.setBounds(250, 223, 380, 540);
                mainPanel.add(lblImagenLogin);

                JLabel lblFondoLogin = new JLabel();
                lblFondoLogin.setIcon(new ImageIcon(getClass().getResource("/fondoLogin.jpg")));
                lblFondoLogin.setBounds(0, 0, 1600, 900);
                mainPanel.add(lblFondoLogin);

                btnLogin.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                iniciarSesion();
                        }
                });

                // Hacer visible la ventana
                setVisible(true);
        }

        private void iniciarSesion() {
                String usuario = nombreUsuario.getText();
                String contrasena = new String(passwordField.getPassword());

                if (usuario.trim().isEmpty() || contrasena.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor, rellene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Verificación de credenciales
                UsuarioServices usuarioServices = new UsuarioServices();
                try {
                        Usuario usr = usuarioServices.searchPerID(usuario); // Busca por ID

                        if (usr != null && usr.getNombre().equals(usuario)) {
                                switch (usr.getCargo()) {
                                        case "Administrador":
                                                new Administrador().setVisible(true);
                                                break;

                                        case "Medico":
                                                new Medico().setVisible(true);
                                                break;

                                        case "Agente de Atención al Paciente":
                                                new AgenteAtencionAlPaciente().setVisible(true);
                                                break;

                                        default:
                                                throw new Exception("Rol desconocido");
                                }

                                this.dispose(); // Cerrar la ventana de login

                        } else {
                                System.out.println(usr.getNombre() + usr.getId());
                                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al iniciar sesión", "Error", JOptionPane.ERROR_MESSAGE);
                }
        }

        public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                                new Login();
                        }
                });
        }
}
