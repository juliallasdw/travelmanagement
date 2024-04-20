package travel.and.tourism.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;

public class TourManagement extends JFrame {
    private JTextField tfTourName, tfDuration, tfPrice, tfLocation;
    private JButton btnAdd, btnUpdate, btnDelete;
     private JTable listOfTours;
     public int tourId;
   
    public TourManagement() {
        setTitle("Tour Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel lblTourName = new JLabel("Tour Name:");
        tfTourName = new JTextField();
        add(lblTourName);
        add(tfTourName);

        JLabel lblDuration = new JLabel("Duration:");
        tfDuration = new JTextField();
        add(lblDuration);
        add(tfDuration);

        JLabel lblPrice = new JLabel("Price:");
        tfPrice = new JTextField();
        add(lblPrice);
        add(tfPrice);

        JLabel lblLocation = new JLabel("Location:");
        tfLocation = new JTextField();
        add(lblLocation);
        add(tfLocation);
        
        listOfTours = new JTable();
        // Thiết lập model và dữ liệu cho bảng listOfTours
        
        // Thêm sự kiện khi người dùng chọn một tour từ danh sách
        listOfTours.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listOfTours.getSelectedRow();
                tourId = Integer.parseInt(listOfTours.getValueAt(row, 0).toString());
            }
        });

        btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý thêm tour
                String tourName = tfTourName.getText();
                String duration = tfDuration.getText();
                double price = Double.parseDouble(tfPrice.getText());
                String location = tfLocation.getText();
                addTour(tourName, duration, price, location);
            }
        });
        add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        String tourName = tfTourName.getText();
        String duration = tfDuration.getText();
        double price = Double.parseDouble(tfPrice.getText());
        String location = tfLocation.getText();

        // Thực hiện cập nhật tour trong cơ sở dữ liệu
        boolean updated = updateTour(tourId,tourName, duration, price, location);
        if (updated) {
            JOptionPane.showMessageDialog(TourManagement.this, "Tour updated successfully!");
        } else {
            JOptionPane.showMessageDialog(TourManagement.this, "Failed to update tour.");
        }
    }
});
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý xóa tour
                // Code xử lý xóa tour ở đây
            }
        });
        add(btnDelete);
    }

    private void addTour(String tourName, String duration, double price, String location) {
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
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add tour.");
        }
    }

    
    private boolean updateTour(int tourID, String tourName, String duration, double price, String location) {
    try {
        Conn c = new Conn();
        String query = "UPDATE tours SET tour_name = ?, duration = ?, price = ?, location = ? WHERE tour_id = ?";
        PreparedStatement ps = c.c.prepareStatement(query);
        ps.setString(1, tourName);
        ps.setString(2, duration);
        ps.setDouble(3, price);
        ps.setString(4, location);
        ps.setInt(5, tourID); // Đặt giá trị của tourId
        int result = ps.executeUpdate();
        c.c.close();
        JOptionPane.showMessageDialog(this, "Tour updated successfully!");
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to update tour.");
        return false;
    }
    }
    
    public static void main(String[] args) {
        new TourManagement();
    }
}

