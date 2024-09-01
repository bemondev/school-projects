package pdt.cliente.swing.equipo;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Clase que muestra una imagen almacenada en un Blob.
 */
public class BlobImageDisplay extends JFrame {
    // ======================== Atributos ========================
    /**
     * Etiqueta para mostrar la imagen.
     */
    private final JLabel IMAGEN_LABEL;

    // ===================== Constructor =======================

    /**
     * Constructor de la clase.<br>
     * Crea una ventana que muestra una imagen almacenada en un Blob.
     *
     * @param blob Blob que contiene la imagen a mostrar.
     */
    public BlobImageDisplay(Blob blob) {
        setTitle("Image Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        IMAGEN_LABEL = new JLabel();
        add(new JScrollPane(IMAGEN_LABEL), BorderLayout.CENTER);

        displayImage(blob);

        setVisible(true);
    }

    // ======================== Métodos ========================

    /**
     * Método para mostrar una imagen almacenada en un Blob.
     *
     * @param blob Blob que contiene la imagen a mostrar.
     */
    private void displayImage(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream()) {
            Image image = ImageIO.read(inputStream);
            ImageIcon icon = new ImageIcon(image);
            IMAGEN_LABEL.setIcon(icon);
        } catch (SQLException | IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Método para convertir un arreglo de bytes a un Blob.
     *
     * @param byteArray Arreglo de bytes a convertir.
     * @return Blob creado a partir del arreglo de bytes.
     * @throws SQLException Si ocurre un error al crear el Blob.
     */
    public static Blob convertToBlob(byte[] byteArray) throws SQLException {
        return new SerialBlob(byteArray);
    }

}