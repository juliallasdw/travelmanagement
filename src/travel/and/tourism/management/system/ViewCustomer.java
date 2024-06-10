package travel.and.tourism.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ViewCustomer extends JFrame {
    private JTable customerTable;
    private DefaultTableModel model;

    public ViewCustomer() {
        setTitle("View Customers");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600); // Increased width to accommodate new columns
        setLocationRelativeTo(null);

        initComponents();
        fetchAndDisplayCustomers();

        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        customerTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(customerTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm Selected Tour");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmSelectedTour();
            }
        });
        mainPanel.add(confirmButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void fetchAndDisplayCustomers() {
        try {
            Conn c = new Conn();
            Connection connection = c.c;
            String query = "SELECT bt.booking_id, bt.username, bt.tour_id, t.tour_name, bt.booking_date, bt.elder, bt.children, bt.confirmation_status, t.price " +
                           "FROM booked_tours bt JOIN tours t ON bt.tour_id = t.tour_id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            model = new DefaultTableModel();
            model.addColumn("Booking ID");
            model.addColumn("Username");
            model.addColumn("Tour ID");
            model.addColumn("Tour Name");
            model.addColumn("Booking Date");
            model.addColumn("Adults");
            model.addColumn("Children");
            model.addColumn("Total Price");
            model.addColumn("Confirmed");

            while (resultSet.next()) {
                int bookingId = resultSet.getInt("booking_id");
                String username = resultSet.getString("username");
                int tourId = resultSet.getInt("tour_id");
                String tourName = resultSet.getString("tour_name");
                String bookingDate = resultSet.getString("booking_date");
                int adults = resultSet.getInt("elder");
                int children = resultSet.getInt("children");
                boolean confirmationStatus = resultSet.getBoolean("confirmation_status");
                double pricePerAdult = resultSet.getDouble("price");
                double totalPrice = adults * pricePerAdult + children * pricePerAdult * 0.5;

                Vector<Object> row = new Vector<>();
                row.add(bookingId);
                row.add(username);
                row.add(tourId);
                row.add(tourName);
                row.add(bookingDate);
                row.add(adults);
                row.add(children);
                row.add(String.format("$%.2f", totalPrice));
                row.add(confirmationStatus ? "Yes" : "No");

                model.addRow(row);
            }

            customerTable.setModel(model);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch booked customers from database.");
        }
    }

    private void confirmSelectedTour() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            int bookingId = (int) model.getValueAt(selectedRow, 0);
            try {
                Conn c = new Conn();
                Connection connection = c.c;
                String query = "UPDATE booked_tours SET confirmation_status = TRUE WHERE booking_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, bookingId);
                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Tour confirmed successfully!");
                    fetchAndDisplayCustomers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to confirm tour.");
                }

                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to confirm tour.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a tour to confirm.");
        }
    }

    public static void main(String[] args) {
        new ViewCustomer();
    }
}
