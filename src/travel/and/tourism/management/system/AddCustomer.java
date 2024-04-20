package travel.and.tourism.management.system;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class AddCustomer extends JFrame {
    JButton submit, back;
    JTextField tfIDNumber, tfName, tfCountry, tfAddress, tfPhone, tfEmail;
    JRadioButton male, female;
    JComboBox<String> idComboBox;
    boolean infoAdded = false;

    AddCustomer() {
        setSize(800, 600);
        setLocation(300, 100);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(131, 193, 233));
        p1.setBounds(0, 0, 800, 60);
        p1.setLayout(null);
        add(p1);

        JLabel lblTitle = new JLabel("Add Customer");
        lblTitle.setBounds(320, 15, 200, 30);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        p1.add(lblTitle);

        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBounds(50, 100, 700, 450);
        add(p2);

        JLabel lblIDType = new JLabel("ID type:");
        lblIDType.setBounds(60, 30, 120, 25);
        lblIDType.setFont(new Font("Tahoma", Font.PLAIN, 20));
        p2.add(lblIDType);
        
       
     
        JLabel lblIDNumber = new JLabel("ID number:");
        lblIDNumber.setBounds(60, 70, 120, 25);
        lblIDNumber.setFont(new Font("Tahoma", Font.PLAIN, 20));
        p2.add(lblIDNumber);

        tfIDNumber = new JTextField();
        tfIDNumber.setBounds(200, 70, 200, 25);
        p2.add(tfIDNumber);

        String[] idOptions = {"Passport", "Driver License"};
        idComboBox = new JComboBox<>(idOptions);
        idComboBox.setBounds(200, 30, 200, 25);
        p2.add(idComboBox);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(60, 110, 120, 25);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        p2.add(lblName);

        tfName = new JTextField();
        tfName.setBounds(200, 110, 200, 25);
        p2.add(tfName);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(60, 150, 120, 25);
        lblGender.setFont(new Font("Tahoma", Font.PLAIN, 20));
        p2.add(lblGender);

        male = new JRadioButton("Male");
        male.setBounds(200, 150, 100, 25);
        male.setBackground(Color.WHITE);
        p2.add(male);

        female = new JRadioButton("Female");
        female.setBounds(300, 150, 100, 25);
        female.setBackground(Color.WHITE);
        p2.add(female);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        JLabel lblCountry = new JLabel("Country:");
        lblCountry.setBounds(60, 190, 120, 25);
        lblCountry.setFont(new Font("Tahoma", Font.PLAIN, 20));
        p2.add(lblCountry);

        tfCountry = new JTextField();
        tfCountry.setBounds(200, 190, 200, 25);
        p2.add(tfCountry);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(60, 230, 120, 25);
        lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        p2.add(lblAddress);

        tfAddress = new JTextField();
        tfAddress.setBounds(200, 230, 200, 25);
        p2.add(tfAddress);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(60, 270, 120, 25);
        lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
        p2.add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(200, 270, 200, 25);
        p2.add(tfPhone);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(60, 310, 120, 25);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
        p2.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(200, 310, 200, 25);
        p2.add(tfEmail);

        submit = new JButton("Submit");
        submit.setBounds(200, 380, 100, 30);
        submit.setBackground(new Color(133, 193, 233));
        submit.setForeground(Color.WHITE);
        submit.setBorder(new LineBorder(new Color(133, 193, 233)));
        p2.add(submit);

        back = new JButton("Back");
        back.setBounds(350, 380, 100, 30);
        back.setBackground(new Color(133, 193, 233));
        back.setForeground(Color.WHITE);
        back.setBorder(new LineBorder(new Color(133, 193, 233)));
        p2.add(back);

        submit.addActionListener(e -> {
             String username = ""; // Khởi tạo biến username

    // Kiểm tra xem session đã đăng nhập hay chưa
    if (UserSession.getInstance().isLoggedIn()) {
        // Nếu đã đăng nhập, lấy username từ session
        username = UserSession.getInstance().getLoggedInUsername();
    } else {
      
        new Login().setVisible(true); // Hiển thị trang đăng nhập
        return; // Dừng việc thực hiện phương thức submitActionPerformed()
    }
           
            String idType = (String) idComboBox.getSelectedItem();
            String idNumber = tfIDNumber.getText();
            String name = tfName.getText();
            String gender = male.isSelected() ? "Male" : "Female";
            String country = tfCountry.getText();
            String address = tfAddress.getText();
            String phone = tfPhone.getText();
            String email = tfEmail.getText();
            showCustomerInformation( idType, idNumber, name, gender, country, address, phone, email);

            // Thêm giá trị vào cơ sở dữ liệu
            String query = "INSERT INTO customers (username, id_type, id_number, name, gender, country, customer_address, phone, email) " +
                    "VALUES ('" + username + "','" + idType + "', '" + idNumber + "', '" + name + "', '" + gender + "', '" + country + "', '" +
                    address + "', '" + phone + "', '" + email + "')";
            try {
                Conn c = new Conn();
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(this, "Customer information added successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        back.addActionListener(e -> {
            new DashBoard();
            dispose();
        });

        setVisible(true);
    }

    private void showCustomerInformation(String idType, String idNumber, String name, String gender, String country, String address, String phone, String email) {
        JFrame customerInfoFrame = new JFrame("Customer Information");
        customerInfoFrame.setSize(400, 400);
        customerInfoFrame.setLocationRelativeTo(null);
        customerInfoFrame.setLayout(new GridLayout(8, 2));

        JLabel[] labels = {
            new JLabel("ID Type:"), new JLabel(idType),
            new JLabel("ID Number:"), new JLabel(idNumber),
            new JLabel("Name:"), new JLabel(name),
            new JLabel("Gender:"), new JLabel(gender),
            new JLabel("Country:"), new JLabel(country),
            new JLabel("Address:"), new JLabel(address),
            new JLabel("Phone:"), new JLabel(phone),
            new JLabel("Email:"), new JLabel(email)
        };

        for (JLabel label : labels) {
            customerInfoFrame.add(label);
        }

        customerInfoFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new AddCustomer();
    }
}
