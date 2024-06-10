package travel.and.tourism.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TourDetails extends JFrame {
    private JLabel lblTourName, lblDuration, lblPrice, lblLocation, lblImage, lblTotalPrice;
    private JTextArea txtDescription;
    private JTextField tfAdults, tfChildren;
    private JButton btnBook, btnPrevImage, btnNextImage;
    private int tourId;
    private ArrayList<String> imageBase64List;
    private int currentImageIndex;
    private double pricePerAdult;

    public TourDetails(int tourId) {
        this.tourId = tourId;
        imageBase64List = new ArrayList<>();
        currentImageIndex = 0;

        setTitle("Tour Details");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create information panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize components
        lblTourName = new JLabel();
        lblDuration = new JLabel();
        lblPrice = new JLabel();
        lblLocation = new JLabel();
        lblImage = new JLabel();
        txtDescription = new JTextArea(5, 20);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(txtDescription);
        btnBook = new JButton("Book");
        btnPrevImage = new JButton("Previous");
        btnNextImage = new JButton("Next");
        lblTotalPrice = new JLabel("Total Price: $0.00");

        // Add components to info panel
        addLabelAndComponent(infoPanel, "Tour Name:", lblTourName, gbc, 0);
        addLabelAndComponent(infoPanel, "Duration:", lblDuration, gbc, 1);
        addLabelAndComponent(infoPanel, "Price (per adult):", lblPrice, gbc, 2);
        addLabelAndComponent(infoPanel, "Location:", lblLocation, gbc, 3);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        infoPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(descriptionScrollPane, gbc);

        // Add image label to info panel
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        lblImage.setPreferredSize(new Dimension(400, 300));
        infoPanel.add(lblImage, gbc);

        // Add image navigation buttons
        JPanel imageNavPanel = new JPanel(new FlowLayout());
        imageNavPanel.add(btnPrevImage);
        imageNavPanel.add(btnNextImage);

        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        infoPanel.add(imageNavPanel, gbc);

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Create user input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.insets = new Insets(5, 5, 5, 5);
        gbcInput.fill = GridBagConstraints.HORIZONTAL;

        tfAdults = new JTextField(5);
        tfChildren = new JTextField(5);

        addLabelAndComponent(inputPanel, "Number of Adults:", tfAdults, gbcInput, 0);
        addLabelAndComponent(inputPanel, "Number of Children:", tfChildren, gbcInput, 1);
        
        gbcInput.gridx = 0;
        gbcInput.gridy = 2;
        gbcInput.gridwidth = 2;
        inputPanel.add(lblTotalPrice, gbcInput);

        // Add booking button at the bottom of input panel
        gbcInput.gridx = 0;
        gbcInput.gridy = 3;
        gbcInput.gridwidth = 2;
        gbcInput.anchor = GridBagConstraints.CENTER;
        inputPanel.add(btnBook, gbcInput);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Load tour details
        loadTourDetails(tourId);

        // Add action listeners for navigation buttons
        btnPrevImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!imageBase64List.isEmpty()) {
                    currentImageIndex = (currentImageIndex - 1 + imageBase64List.size()) % imageBase64List.size();
                    displayImage(imageBase64List.get(currentImageIndex));
                }
            }
        });

        btnNextImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!imageBase64List.isEmpty()) {
                    currentImageIndex = (currentImageIndex + 1) % imageBase64List.size();
                    displayImage(imageBase64List.get(currentImageIndex));
                }
            }
        });

        // Add action listener for book button
        btnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTour();
            }
        });

        tfAdults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalPrice();
            }
        });

        tfChildren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalPrice();
            }
        });

        setVisible(true);
    }

    private void addLabelAndComponent(JPanel panel, String labelText, JComponent component, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private void loadTourDetails(int tourId) {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM tours WHERE tour_id = ?";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setInt(1, tourId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblTourName.setText(rs.getString("tour_name"));
                lblDuration.setText(rs.getString("duration"));
                pricePerAdult = rs.getDouble("price");
                lblPrice.setText(String.format("$%.2f", pricePerAdult));
                lblLocation.setText(rs.getString("location"));
                txtDescription.setText(rs.getString("tour_description"));

                String image1 = rs.getString("image1");
                String image2 = rs.getString("image2");
                String image3 = rs.getString("image3");

                if (image1 != null) imageBase64List.add(image1);
                if (image2 != null) imageBase64List.add(image2);
                if (image3 != null) imageBase64List.add(image3);

                if (!imageBase64List.isEmpty()) {
                    displayImage(imageBase64List.get(currentImageIndex));
                }
            }

            rs.close();
            ps.close();
            c.c.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load tour details.");
        }
    }

    private void displayImage(String imageBase64) {
        try {
            if (imageBase64 != null && !imageBase64.isEmpty()) {
                byte[] imageData = Base64.getDecoder().decode(imageBase64);
                ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
                BufferedImage image = ImageIO.read(bais);

                ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(400, 300, Image.SCALE_SMOOTH));
                lblImage.setIcon(imageIcon);
            } else {
                lblImage.setIcon(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to display image.");
        }
    }

    private void calculateTotalPrice() {
        try {
            int adults = Integer.parseInt(tfAdults.getText());
            int children = Integer.parseInt(tfChildren.getText());
            double totalPrice = adults * pricePerAdult + children * pricePerAdult * 0.5;
            lblTotalPrice.setText(String.format("Total Price: $%.2f", totalPrice));
        } catch (NumberFormatException e) {
            lblTotalPrice.setText("Total Price: $0.00");
        }
    }

    private void bookTour() {
        try {
            int adults = Integer.parseInt(tfAdults.getText());
            int children = Integer.parseInt(tfChildren.getText());
            
            calculateTotalPrice();

            Conn c = new Conn();
            String query = "INSERT INTO booked_tours (tour_id, tour_name, username, elder, children) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setInt(1, tourId);
            ps.setString(2, lblTourName.getText());
            ps.setString(3, UserSession.getInstance().getLoggedInUsername());
            ps.setInt(4, adults);
            ps.setInt(5, children);
            int result = ps.executeUpdate();
            c.c.close();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Tour booked successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to book tour.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for adults and children.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to book tour.");
        }
    }
}
