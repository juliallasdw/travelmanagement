package travel.and.tourism.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TourBooking extends JFrame {
    private String username;

    public TourBooking() {
        this.username = UserSession.getInstance().getLoggedInUsername(); // Lấy thông tin người dùng từ UserSession

        setTitle("Tour Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tạo JPanel để chứa hình ảnh và combobox
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());

        // Thêm hình ảnh vào JLabel và đặt ở trên cùng
        JLabel imageLabel = new JLabel(new ImageIcon("province_image.jpg")); // Thay "province_image.jpg" bằng đường dẫn đến hình ảnh thực tế
        imagePanel.add(imageLabel, BorderLayout.NORTH);

        // Tạo combobox chứa danh sách tỉnh
        String[] provinces = {"Hà Nội", "Đà Nẵng", "Đà Lạt", "Quảng Ninh", "Khánh Hòa"};
        JComboBox<String> provinceComboBox = new JComboBox<>(provinces);
        imagePanel.add(provinceComboBox, BorderLayout.CENTER);

        add(imagePanel, BorderLayout.NORTH);

        JPanel tourInfoPanel = new JPanel();
        tourInfoPanel.setLayout(new BoxLayout(tourInfoPanel, BoxLayout.Y_AXIS));
        tourInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel tourLabel = new JLabel("Tour Information");
        tourLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tourInfoPanel.add(tourLabel);

        JLabel tourDescriptionLabel = new JLabel();
        tourInfoPanel.add(tourDescriptionLabel);

        provinceComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProvince = (String) provinceComboBox.getSelectedItem();
                String tourDescription = getTourDescription(selectedProvince);
                tourDescriptionLabel.setText(tourDescription);
            }
        });

        add(tourInfoPanel, BorderLayout.CENTER);

        JButton bookNowButton = new JButton("Book Now");
        bookNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInfo = UserSession.getInstance().getLoggedInUsername();
                JOptionPane.showMessageDialog(TourBooking.this, "Booking successful! Thank you for choosing our tour.");
            }
        });
        add(bookNowButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String getTourDescription(String province) {
        String tourDescription = "";

        switch (province) {
            case "Hà Nội":{
                tourDescription = "Tour Hà Nội: 3 ngày 2 đêm, giá $500";
               JPanel p1 = new JPanel();
                p1.setBackground(new Color(131, 193, 233));
                p1.setBounds(0, 0, 400, 400);
                p1.setLayout(null);
                add(p1);
                
                ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.png"));
                Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
                 ImageIcon i3 = new ImageIcon(i2);
                JLabel image = new JLabel(i3);
                 image.setBounds(100, 120, 200, 200);
                 p1.add(image);}
                break;
            case "Đà Nẵng":
                tourDescription = "Tour Đà Nẵng: 4 ngày 3 đêm, giá $700";
                break;
            case "Đà Lạt":
                tourDescription = "Tour Đà Lạt: 2 ngày 1 đêm, giá $400";
                break;
            case "Quảng Ninh":
                tourDescription = "Tour Quảng Ninh: 5 ngày 4 đêm, giá $900";
                break;
            case "Khánh Hòa":
                tourDescription = "Tour Khánh Hòa: 7 ngày 6 đêm, giá $1200";
                break;
            default:
                tourDescription = "Chưa có tour cho tỉnh này.";
                break;
        }

        return tourDescription;
    }

    public static void main(String[] args) {
        UserSession.getInstance().setLoggedInUsername("your_username_here");
        new TourBooking();
    }
}


