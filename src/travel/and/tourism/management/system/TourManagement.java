package travel.and.tourism.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TourManagement extends JFrame {
    private JTextField tfTourName, tfDuration, tfPrice, tfLocation;
    private JButton btnAdd, btnUpdate, btnDelete;
    private JTable listOfTours;
    private int tourId;

    public TourManagement() {
        setTitle("Tour Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        initComponents();
        fetchAndDisplayTours();
        setVisible(true);
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblTourName = new JLabel("Tour Name:");
        tfTourName = new JTextField(20);
        inputPanel.add(lblTourName, gbc);
        gbc.gridx++;
        inputPanel.add(tfTourName, gbc);
        gbc.gridy++;

        JLabel lblDuration = new JLabel("Duration:");
        tfDuration = new JTextField(20);
        gbc.gridx = 0;
        inputPanel.add(lblDuration, gbc);
        gbc.gridx++;
        inputPanel.add(tfDuration, gbc);
        gbc.gridy++;

        JLabel lblPrice = new JLabel("Price:");
        tfPrice = new JTextField(20);
        gbc.gridx = 0;
        inputPanel.add(lblPrice, gbc);
        gbc.gridx++;
        inputPanel.add(tfPrice, gbc);
        gbc.gridy++;

        JLabel lblLocation = new JLabel("Location:");
        tfLocation = new JTextField(20);
        gbc.gridx = 0;
        inputPanel.add(lblLocation, gbc);
        gbc.gridx++;
        inputPanel.add(tfLocation, gbc);
        gbc.gridy++;

        btnAdd = new JButton("Add");
        inputPanel.add(btnAdd, gbc);
        gbc.gridy++;

        btnUpdate = new JButton("Update");
        inputPanel.add(btnUpdate, gbc);
        gbc.gridy++;

        btnDelete = new JButton("Delete");
        inputPanel.add(btnDelete, gbc);

        listOfTours = new JTable();
        add(new JScrollPane(listOfTours), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.WEST);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTour();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTour();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTour();
            }
        });

        listOfTours.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listOfTours.getSelectedRow();
                if (row != -1) {
                    tourId = Integer.parseInt(listOfTours.getValueAt(row, 0).toString());
                    tfTourName.setText(listOfTours.getValueAt(row, 1).toString());
                    tfDuration.setText(listOfTours.getValueAt(row, 2).toString());
                    tfPrice.setText(listOfTours.getValueAt(row, 3).toString());
                    tfLocation.setText(listOfTours.getValueAt(row, 4).toString());
                }
            }
        });
    }

    private void fetchAndDisplayTours() {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM tours";
            PreparedStatement ps = c.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Tour ID");
            model.addColumn("Tour Name");
            model.addColumn("Duration");
            model.addColumn("Price");
            model.addColumn("Location");

            while (rs.next()) {
                int tourId = rs.getInt("tour_id");
                String tourName = rs.getString("tour_name");
                String duration = rs.getString("duration");
                double price = rs.getDouble("price");
                String location = rs.getString("location");

                model.addRow(new Object[]{tourId, tourName, duration, price, location});
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

    private void addTour() {
        String tourName = tfTourName.getText();
        String duration = tfDuration.getText();
        double price = Double.parseDouble(tfPrice.getText());
        String location = tfLocation.getText();
        if (!tourName.isEmpty() && !duration.isEmpty() && !location.isEmpty()) {
            try {
                Conn c = new Conn();
                String query = "INSERT INTO tours (tour_name, duration, price, location) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, tourName);
                ps.setString(2, duration);
                ps.setDouble(3, price);
                ps.setString(4, location);
                int result = ps.executeUpdate();
                c.c.close();
                JOptionPane.showMessageDialog(this, "Tour added successfully!");
                fetchAndDisplayTours();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to add tour.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.");
        }
    }

    private void updateTour() {
        String tourName = tfTourName.getText();
        String duration = tfDuration.getText();
        double price = Double.parseDouble(tfPrice.getText());
        String location = tfLocation.getText();
        if (!tourName.isEmpty() && !duration.isEmpty() && !location.isEmpty()) {
            try {
                Conn c = new Conn();
                String query = "UPDATE tours SET tour_name = ?, duration = ?, price = ?, location = ? WHERE tour_id = ?";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, tourName);
                ps.setString(2, duration);
                ps.setDouble(3, price);
                ps.setString(4, location);
                ps.setInt(5, tourId);
                int result = ps.executeUpdate();
                c.c.close();
                JOptionPane.showMessageDialog(this, "Tour updated successfully!");
                fetchAndDisplayTours();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to update tour.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.");
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
        new TourManagement();
    }
}

