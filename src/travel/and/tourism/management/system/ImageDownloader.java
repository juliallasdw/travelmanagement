/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package travel.and.tourism.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.sql.ResultSet;

public class ImageDownloader extends JFrame {
    private JLabel imageLabel;
    private JButton ShowImageButton;
    private static byte[] imageData;

    public ImageDownloader() {
        setTitle("Show Image");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        // Tạo các thành phần giao diện
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        ShowImageButton = new JButton("Show Image");

        // Thêm các sự kiện cho các nút
        ShowImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showImage();
                } catch (SQLException ex) {
                    Logger.getLogger(ImageDownloader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Sử dụng layout BorderLayout
        setLayout(new BorderLayout());

        // Thêm các thành phần vào giao diện
        add(imageLabel, BorderLayout.CENTER);
        add(ShowImageButton, BorderLayout.NORTH);

        setVisible(true);
    }

    private void showImage() throws SQLException {
        try {
            Conn c = new Conn();
            String query = "SELECT image_data FROM images WHERE id = ?"; // Giả sử bạn muốn lấy ảnh mới nhất
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String base64Image = rs.getString("image_data");
                byte[] GetImageData = Base64.getDecoder().decode(base64Image);
//                JOptionPane.showMessageDialog(this, base64Image);
                ByteArrayInputStream bais = new ByteArrayInputStream(GetImageData);
                BufferedImage image = ImageIO.read(bais);
//                JOptionPane.showMessageDialog(this, image);

                ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(300, 300, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
            } else {
                JOptionPane.showMessageDialog(this, "No image found in the database.");
            }
            c.c.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ImageDownloader();
            }
        });
    }
}
