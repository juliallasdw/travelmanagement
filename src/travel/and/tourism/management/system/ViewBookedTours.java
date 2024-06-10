package travel.and.tourism.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ViewBookedTours extends JFrame {
    private JTable bookedToursTable;

    public ViewBookedTours() {
        setTitle("View Booked Tours");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600); // Increased width to accommodate new columns
        setLocationRelativeTo(null);

        initComponents();
        fetchAndDisplayBookedTours();

        setVisible(true);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        bookedToursTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(bookedToursTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void fetchAndDisplayBookedTours() {
        try {
            String username = UserSession.getInstance().getLoggedInUsername();
            if (username == null || username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "User not logged in.");
                return;
            }

            Conn c = new Conn();
            Connection connection = c.c;
            String query = "SELECT bt.tour_id, t.tour_name, bt.booking_date, bt.elder, bt.children, bt.confirmation_status, t.price " +
                           "FROM booked_tours bt JOIN tours t ON bt.tour_id = t.tour_id WHERE bt.username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Tour ID");
            model.addColumn("Tour Name");
            model.addColumn("Booking Date");
            model.addColumn("Adults");
            model.addColumn("Children");
            model.addColumn("Total Price");
            model.addColumn("Confirmed");

            while (resultSet.next()) {
                int tourId = resultSet.getInt("tour_id");
                String tourName = resultSet.getString("tour_name");
                String bookingDate = resultSet.getString("booking_date");
                int adults = resultSet.getInt("elder");
                int children = resultSet.getInt("children");
                boolean confirmationStatus = resultSet.getBoolean("confirmation_status");
                double pricePerAdult = resultSet.getDouble("price");
                double totalPrice = adults * pricePerAdult + children * pricePerAdult * 0.5;

                Vector<Object> row = new Vector<>();
                row.add(tourId);
                row.add(tourName);
                row.add(bookingDate);
                row.add(adults);
                row.add(children);
                row.add(String.format("$%.2f", totalPrice));
                row.add(confirmationStatus ? "Yes" : "No");

                model.addRow(row);
            }

            bookedToursTable.setModel(model);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch booked tours from database.");
        }
    }

    public static void main(String[] args) {
        if (UserSession.getInstance().isLoggedIn()) {
            new ViewBookedTours();
        } else {
            JOptionPane.showMessageDialog(null, "Please log in first.");
        }
    }
}
