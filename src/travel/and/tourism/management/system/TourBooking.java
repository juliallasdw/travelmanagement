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
    private JTextField searchField,locationField;
    private JButton searchButton, viewDetailsButton,locationSearchButton,durationSearchButton;
    private JScrollPane scrollPane;
    private JComboBox<String> durationComboBox;


    public TourBooking() {
        setTitle("Tour Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

       searchField = new JTextField(20);
searchPanel.add(searchField);

searchButton = new JButton("Search by Name");
searchPanel.add(searchButton);

locationField = new JTextField(20);
searchPanel.add(locationField);

locationSearchButton = new JButton("Search by Location");
searchPanel.add(locationSearchButton);

String[] durations = {"1 day 1 night", "2 days 1 night", "3 days 2 nights", "4 days 3 nights", "5 days 4 nights", "a week"};
durationComboBox = new JComboBox<String>(durations);
searchPanel.add(durationComboBox);

durationSearchButton = new JButton("Search by Duration");
searchPanel.add(durationSearchButton);


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

        searchButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String keyword = searchField.getText();
        searchTourByName(keyword);
    }
});

locationSearchButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String location = locationField.getText();
        searchTourByLocation(location);
    }
});

durationSearchButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String duration = (String) durationComboBox.getSelectedItem();
        searchTourByDuration(duration);
    }
});

        // Thêm sự kiện cho nút xem chi tiết
        viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tourTable.getSelectedRow();
                if (selectedRow != -1) {
                    int tourId = (int) tourTable.getValueAt(selectedRow, 0);
                    new TourDetails(tourId);
                } else {
                    JOptionPane.showMessageDialog(TourBooking.this, "Please select a tour to view details.");
                }
            }
        });
        add(viewDetailsButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    
    
    
     private void searchTourByName(String keyword) {
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
    
    
    
    private void searchTourByLocation(String location) {
    try {
        Conn c = new Conn();
        String query = "SELECT * FROM tours WHERE location LIKE ?";
        PreparedStatement ps = c.c.prepareStatement(query);
        ps.setString(1, "%" + location + "%");
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
            String tourLocation = rs.getString("location");

            model.addRow(new Object[]{tourId, tourName, duration, price, tourLocation});
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

private void searchTourByDuration(String duration) {
    try {
        Conn c = new Conn();
        String query = "SELECT * FROM tours WHERE duration = ?";
        PreparedStatement ps = c.c.prepareStatement(query);
        ps.setString(1, duration);
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
            String tourDuration = rs.getString("duration");
            double price = rs.getDouble("price");
            String location = rs.getString("location");

            model.addRow(new Object[]{tourId, tourName, tourDuration, price, location});
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

    public static void main(String[] args) {
        new TourBooking();
    }
}
