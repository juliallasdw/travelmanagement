package travel.and.tourism.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class TourBooking extends JFrame {
    private JTable tourTable;
    private JTextField searchField;
    private JButton searchButton, bookButton;
    private JScrollPane scrollPane;

    public TourBooking() {
        setTitle("Tour Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchField = new JTextField(20);
        searchPanel.add(searchField);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Hiển thị danh sách tour
        tourTable = new JTable();
        scrollPane = new JScrollPane(tourTable);
        add(scrollPane, BorderLayout.CENTER);

        // Tải danh sách tour khi khởi động
        loadTourList();

        // Thêm sự kiện cho nút tìm kiếm
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                searchTour(keyword);
            }
        });

        // Thêm sự kiện cho nút book
        bookButton = new JButton("Book Now");
       bookButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = tourTable.getSelectedRow();
        if (selectedRow != -1) {
            int tourId = (int) tourTable.getValueAt(selectedRow, 0);
            String tourName = (String) tourTable.getValueAt(selectedRow, 1);
            bookTour(tourId, tourName);
        } else {
            JOptionPane.showMessageDialog(TourBooking.this, "Please select a tour to book.");
        }
    }
});
add(bookButton, BorderLayout.SOUTH);


        setVisible(true);
    }

    private void loadTourList() {
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
                // Lấy thông tin của tour từ cơ sở dữ liệu và thêm vào model
                int tourId = rs.getInt("tour_id");
                String tourName = rs.getString("tour_name");
                String duration = rs.getString("duration");
                double price = rs.getDouble("price");
                String location = rs.getString("location");

                model.addRow(new Object[]{tourId, tourName, duration, price, location});
            }

            tourTable.setModel(model);

            rs.close();
            ps.close();
            c.c.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load tour list from database.");
        }
    }

    private void searchTour(String keyword) {
        try {
        Conn c = new Conn();
        String query = "SELECT * FROM tours WHERE tour_name LIKE ?";
        PreparedStatement ps = c.c.prepareStatement(query);
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Tour ID");
        model.addColumn("Tour Name");
        model.addColumn("Duration");
        model.addColumn("Price");
        model.addColumn("Location");

        while (rs.next()) {
            // Lấy thông tin của tour từ cơ sở dữ liệu và thêm vào model
            int tourId = rs.getInt("tour_id");
            String tourName = rs.getString("tour_name");
            String duration = rs.getString("duration");
            double price = rs.getDouble("price");
            String location = rs.getString("location");

            model.addRow(new Object[]{tourId, tourName, duration, price, location});
        }

        tourTable.setModel(model);

        rs.close();
        ps.close();
        c.c.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to search for tours.");
    }
    }

    private void bookTour(int tourId,String tourName) {
       try {
        Conn c = new Conn();
        String query = "INSERT INTO booked_tours (tour_id, tour_name, username) VALUES (?, ?, ?)";
        PreparedStatement ps = c.c.prepareStatement(query);
        ps.setInt(1, tourId);
        ps.setString(2, tourName);
        ps.setString(3, UserSession.getInstance().getLoggedInUsername());
        int result = ps.executeUpdate();
        c.c.close();

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Tour booked successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to book tour.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to book tour.");
    }
    }

    public static void main(String[] args) {
        new TourBooking();
    }
}

