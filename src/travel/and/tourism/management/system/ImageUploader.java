package travel.and.tourism.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImageUploader extends JFrame {
    private JLabel imageLabel;
    private JButton chooseImageButton, saveImageButton;
    private File selectedFile;
    private static String imageDataBase64;

    public ImageUploader() {
        setTitle("Image Uploader");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        // Tạo các thành phần giao diện
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseImageButton = new JButton("Choose Image");
        saveImageButton = new JButton("Save Image");

        // Thêm các sự kiện cho các nút
        chooseImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseImage();
            }
        });

        saveImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });

        // Sử dụng layout BorderLayout
        setLayout(new BorderLayout());

        // Thêm các thành phần vào giao diện
        add(imageLabel, BorderLayout.CENTER);
        add(chooseImageButton, BorderLayout.NORTH);
        add(saveImageButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            displayImage(selectedFile);
        }
    }

    private void displayImage(File file) {
        try {
            Image image = ImageIO.read(file);
            image = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);

            BufferedImage resizedImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);
            byte[] imageData = baos.toByteArray();
            imageDataBase64 = Base64.getEncoder().encodeToString(imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getImageDataBase64() {
        return imageDataBase64;
    }

    private void saveImage() {
        if (selectedFile != null) {
            JOptionPane.showMessageDialog(this, "Image selected and converted to Base64!");
        } else {
            JOptionPane.showMessageDialog(this, "Please choose an image first.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ImageUploader();
            }
        });
    }
}
