package co.edu.upb;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

public class FrameController {
    // Mapa para registrar los diferentes frames
    private static Map<String, JFrame> frameMap = new HashMap<>();

    // Registro de un frame con un nombre clave
    public static void registerFrame(String frameName, JFrame frame) {
        frameMap.put(frameName, frame);
    }

    // Cierre de un frame específico
    public static void closeFrame(String frameName) {
        JFrame frame = frameMap.get(frameName);
        if (frame != null) {
            frame.dispose();
        }
    }

    // Apertura de un frame específico
    public static void openFrame(String frameName) {
        JFrame frame = frameMap.get(frameName);
        if (frame != null) {
            frame.setVisible(true);
        }
    }

    // Cerrar sesión y abrir el frame de login
    public static void cerrarSesion() {
        closeAllFrames(); // Cierra todos los frames abiertos
        openFrame("LoginFrame"); // Abre el frame de inicio de sesión
    }

    // Cierre de todos los frames registrados
    private static void closeAllFrames() {
        for (JFrame frame : frameMap.values()) {
            frame.dispose();
        }
    }
}
