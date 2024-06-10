package travel.and.tourism.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Vector;
import javax.imageio.ImageIO;

public class TourManagement extends JFrame {
    private JTextField tfTourName, tfDuration, tfPrice, tfLocation, tfDescription;
    private JButton btnAdd, btnUpdate, btnDelete, btnUpload1, btnUpload2, btnUpload3;
    private JLabel lblImage1, lblImage2, lblImage3;
    private JTable listOfTours;
    private int tourId;
    private String imageBase64_1, imageBase64_2, imageBase64_3;
    private JComboBox<String> durationComboBox;

    public TourManagement() {
        setTitle("Tour Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        initComponents();
        fetchAndDisplayTours();

        setVisible(true);
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add labels and text fields
        addLabelAndTextField(inputPanel, gbc, "Tour Name:", tfTourName = new JTextField(20), 0);
        addLabelAndTextField(inputPanel, gbc, "Price:", tfPrice = new JTextField(20), 2);
        addLabelAndTextField(inputPanel, gbc, "Location:", tfLocation = new JTextField(20), 3);
        addLabelAndTextField(inputPanel, gbc, "Description:", tfDescription = new JTextField(20), 4);

        String[] durations = {"1 day 1 night", "2 days 1 night", "3 days 2 nights", "4 days 3 nights", "5 days 4 nights", "a week"};
        durationComboBox = new JComboBox<>(durations);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPanel.add(durationComboBox, gbc);

        // Add upload buttons
        btnUpload1 = new JButton("Upload Image 1");
        btnUpload2 = new JButton("Upload Image 2");
        btnUpload3 = new JButton("Upload Image 3");

        lblImage1 = new JLabel();
        lblImage2 = new JLabel();
        lblImage3 = new JLabel();

        gbc.gridy = 5;
        gbc.gridwidth = 1;
        inputPanel.add(btnUpload1, gbc);
        gbc.gridx = 1;
        inputPanel.add(lblImage1, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(btnUpload2, gbc);
        gbc.gridx = 1;
        inputPanel.add(lblImage2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        inputPanel.add(btnUpload3, gbc);
        gbc.gridx = 1;
        inputPanel.add(lblImage3, gbc);

        // Add action buttons
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        // Set layout and add components
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.WEST);
        add(new JScrollPane(listOfTours = new JTable()), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        btnUpload1.addActionListener(e -> uploadImage(lblImage1, 1));
        btnUpload2.addActionListener(e -> uploadImage(lblImage2, 2));
        btnUpload3.addActionListener(e -> uploadImage(lblImage3, 3));

        btnAdd.addActionListener(e -> addTour());
        btnUpdate.addActionListener(e -> updateTour());
        btnDelete.addActionListener(e -> deleteTour());

        listOfTours.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listOfTours.getSelectedRow();
                if (row != -1) {
                    tourId = Integer.parseInt(listOfTours.getValueAt(row, 0).toString());
                    tfTourName.setText(listOfTours.getValueAt(row, 1).toString());
                    durationComboBox.setSelectedItem(listOfTours.getValueAt(row, 2).toString());
                    tfPrice.setText(listOfTours.getValueAt(row, 3).toString());
                    tfLocation.setText(listOfTours.getValueAt(row, 4).toString());
                    tfDescription.setText(listOfTours.getValueAt(row, 5).toString());

                    imageBase64_1 = listOfTours.getValueAt(row, 6).toString();
                    displayImage(imageBase64_1, lblImage1);
                    imageBase64_2 = listOfTours.getValueAt(row, 7).toString();
                    displayImage(imageBase64_2, lblImage2);
                    imageBase64_3 = listOfTours.getValueAt(row, 8).toString();
                    displayImage(imageBase64_3, lblImage3);
                }
            }
        });
    }

    private void addLabelAndTextField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(textField, gbc);
    }

    private void fetchAndDisplayTours() {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM tours WHERE manager_username = ?";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, UserSession.getInstance().getLoggedInUsername());
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Tour ID");
            model.addColumn("Tour Name");
            model.addColumn("Duration");
            model.addColumn("Price");
            model.addColumn("Location");
            model.addColumn("Description");
            model.addColumn("Image 1");
            model.addColumn("Image 2");
            model.addColumn("Image 3");

            while (rs.next()) {
                int tourId = rs.getInt("tour_id");
                String tourName = rs.getString("tour_name");
                String duration = rs.getString("duration");
                String price = rs.getString("price");
                String location = rs.getString("location");
                String description = rs.getString("tour_description");
                String image1 = rs.getString("image1");
                String image2 = rs.getString("image2");
                String image3 = rs.getString("image3");

                Vector<Object> row = new Vector<>();
                row.add(tourId);
                row.add(tourName);
                row.add(duration);
                row.add(price);
                row.add(location);
                row.add(description);
                row.add(image1);
                row.add(image2);
                row.add(image3);

                model.addRow(row);
            }

            listOfTours.setModel(model);
            rs.close();
            ps.close();
            c.c.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch tours from database.");
        }
    }

    private void uploadImage(JLabel label, int imageNumber) {
        ImageUploader uploader = new ImageUploader();
        uploader.setVisible(true);
        uploader.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                String imageDataBase64 = ImageUploader.getImageDataBase64();
                if (imageNumber == 1) {
                    imageBase64_1 = imageDataBase64;
                    displayImage(imageBase64_1, label);
                } else if (imageNumber == 2) {
                    imageBase64_2 = imageDataBase64;
                    displayImage(imageBase64_2, label);
                } else if (imageNumber == 3) {
                    imageBase64_3 = imageDataBase64;
                    displayImage(imageBase64_3, label);
                }
            }
        });
    }

    private void displayImage(String imageBase64, JLabel label) {
        try {
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                byte[] imageData = Base64.getDecoder().decode(imageBase64);
                ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
                BufferedImage image = ImageIO.read(bais);

                ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                label.setIcon(imageIcon);
            } else {
                label.setIcon(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to display image.");
        }
    }

    private void addTour() {
        String tourName = tfTourName.getText();
        String duration = (String) durationComboBox.getSelectedItem();
        String price = tfPrice.getText();
        String location = tfLocation.getText();
        String description = tfDescription.getText();
        String managerUsername = UserSession.getInstance().getLoggedInUsername();

        if (!tourName.isEmpty() && !duration.isEmpty() && !price.isEmpty() && !location.isEmpty() && managerUsername != null) {
            try {
                Conn c = new Conn();
                String query = "INSERT INTO tours (tour_name, duration, price, location, tour_description, image1, image2, image3, manager_username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, tourName);
                ps.setString(2, duration);
                ps.setString(3, price);
                ps.setString(4, location);
                ps.setString(5, description);
                ps.setString(6, imageBase64_1);
                ps.setString(7, imageBase64_2);
                ps.setString(8, imageBase64_3);
                ps.setString(9, managerUsername);
                int result = ps.executeUpdate();
                c.c.close();
                JOptionPane.showMessageDialog(this, "Tour added successfully!");
                fetchAndDisplayTours();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to add tour.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields and upload images.");
        }
    }

    private void updateTour() {
        String tourName = tfTourName.getText();
        String duration = (String) durationComboBox.getSelectedItem();
        String price = tfPrice.getText();
        String location = tfLocation.getText();
        String description = tfDescription.getText();

        if (!tourName.isEmpty() && !duration.isEmpty() && !price.isEmpty() && !location.isEmpty()) {
            try {
                Conn c = new Conn();
                String query = "UPDATE tours SET tour_name = ?, duration = ?, price = ?, location = ?, tour_description = ?, image1 = ?, image2 = ?, image3 = ? WHERE tour_id = ?";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, tourName);
                ps.setString(2, duration);
                ps.setString(3, price);
                ps.setString(4, location);
                ps.setString(5, description);
                ps.setString(6, imageBase64_1);
                ps.setString(7, imageBase64_2);
                ps.setString(8, imageBase64_3);
                ps.setInt(9, tourId);
                int result = ps.executeUpdate();
                c.c.close();
                JOptionPane.showMessageDialog(this, "Tour updated successfully!");
                fetchAndDisplayTours();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to update tour.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields and upload images.");
        }
    }

    private void deleteTour() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this tour?");
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Conn c = new Conn();
                String query = "DELETE FROM tours WHERE tour_id = ?";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setInt(1, tourId);
                int result = ps.executeUpdate();
                c.c.close();
                JOptionPane.showMessageDialog(this, "Tour deleted successfully!");
                fetchAndDisplayTours();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to delete tour.");
            }
        }
    }

    public static void main(String[] args) {
        if (UserSession.getInstance().isLoggedIn() && "manager".equals(UserSession.getInstance().getRole())) {
            new TourManagement();
        } else {
            JOptionPane.showMessageDialog(null, "Please log in as a manager to access this page.");
        }
    }
}
