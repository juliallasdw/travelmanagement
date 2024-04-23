package travel.and.tourism.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ViewCustomer extends JFrame {
    private JTable customerTable;

    public ViewCustomer() {
        setTitle("View Customers");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
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

        add(mainPanel);
    }

    private void fetchAndDisplayCustomers() {
       try {
        Conn c = new Conn();
        Connection connection = c.c;
        String query = "SELECT bt.username, bt.tour_id, t.tour_name, bt.booking_date FROM booked_tours bt JOIN tours t ON bt.tour_id = t.tour_id";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Username");
        model.addColumn("Tour ID");
        model.addColumn("Tour Name");
        model.addColumn("Booking Date");

        while (resultSet.next()) {
            String username = resultSet.getString("username");
            int tourId = resultSet.getInt("tour_id");
            String tourName = resultSet.getString("tour_name");
            String bookingDate = resultSet.getString("booking_date");

            Vector<String> row = new Vector<>();
            row.add(username);
            row.add(String.valueOf(tourId));
            row.add(tourName);
            row.add(bookingDate);

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

    public static void main(String[] args) {
        new ViewCustomer();
    }
}

