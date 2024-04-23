package travel.and.tourism.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoard2 extends JFrame {
    DashBoard2() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(new Color(0, 0, 102));
        headerPanel.setBounds(0, 0, 1920, 65);
        add(headerPanel);

        ImageIcon dashboardIcon = new ImageIcon(ClassLoader.getSystemResource("icons/dashboard.png"));
        Image dashboardImage = dashboardIcon.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon scaledDashboardIcon = new ImageIcon(dashboardImage);
        JLabel dashboardLabel = new JLabel(scaledDashboardIcon);
        dashboardLabel.setBounds(5, 0, 70, 70);
        headerPanel.add(dashboardLabel);

        JLabel headingLabel = new JLabel("Dashboard");
        headingLabel.setBounds(80, 10, 300, 40);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        headerPanel.add(headingLabel);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(0, 0, 102));
        menuPanel.setBounds(0, 65, 300, 950);
        add(menuPanel);

        JButton addPersonalDetailsButton = new JButton("Add Personal Details");
        addPersonalDetailsButton.setBounds(0, 0, 300, 50);
        addPersonalDetailsButton.setBackground(new Color(0, 0, 102));
        addPersonalDetailsButton.setForeground(Color.WHITE);
        addPersonalDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        addPersonalDetailsButton.setMargin(new Insets(0, 0, 0, 60));
        menuPanel.add(addPersonalDetailsButton);

       
        JButton viewCustomersButton = new JButton("View Customer");
        viewCustomersButton.setBounds(0, 50, 300, 50);
        viewCustomersButton.setBackground(new Color(0, 0, 102));
        viewCustomersButton.setForeground(Color.WHITE);
        viewCustomersButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        viewCustomersButton.setMargin(new Insets(0, 0, 0, 110));
        menuPanel.add(viewCustomersButton);

        JButton updateToursButton = new JButton("Update Tours");
        updateToursButton.setBounds(0, 100, 300, 50);
        updateToursButton.setBackground(new Color(0, 0, 102));
        updateToursButton.setForeground(Color.WHITE);
        updateToursButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        updateToursButton.setMargin(new Insets(0, 0, 0, 120));
        menuPanel.add(updateToursButton);

     

        JButton destinationsButton = new JButton("Destinations");
        destinationsButton.setBounds(0, 150, 300, 50);
        destinationsButton.setBackground(new Color(0, 0, 102));
        destinationsButton.setForeground(Color.WHITE);
        destinationsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        destinationsButton.setMargin(new Insets(0, 0, 0, 140));
        menuPanel.add(destinationsButton);

        JButton aboutButton = new JButton("About");
        aboutButton.setBounds(0, 150, 300, 50);
        aboutButton.setBackground(new Color(0, 0, 102));
        aboutButton.setForeground(Color.WHITE);
        aboutButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        aboutButton.setMargin(new Insets(0, 0, 0, 175));
        menuPanel.add(aboutButton);
        
       JButton about=new JButton("About");
       about.setBounds(0, 200, 300, 50);
       about.setBackground(new Color(0,0,102));
       about.setForeground(Color.WHITE);
       about.setFont(new Font("Tahoma",Font.PLAIN,20));
       about.setMargin(new Insets(0,0,0,175));
       menuPanel.add(about);
       
       
       
       
       
        viewCustomersButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (UserSession.getInstance().isLoggedIn()) {
            ViewCustomer viewCustomer = new ViewCustomer();
            viewCustomer.setVisible(true);
        } else {
            // Nếu chưa đăng nhập, hiển thị cửa sổ đăng nhập
            Login login = new Login(); // Tên cửa sổ đăng nhập của bạn
            login.setVisible(true);
        }
    }
});
       
       addPersonalDetailsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (UserSession.getInstance().isLoggedIn()) {
            AddCustomer addCustomer = new AddCustomer();
            addCustomer.setVisible(true);
        } else {
            // Nếu chưa đăng nhập, hiển thị cửa sổ đăng nhập
            Login login = new Login(); // Tên cửa sổ đăng nhập của bạn
            login.setVisible(true);
        }
    }
});
       
      updateToursButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (UserSession.getInstance().isLoggedIn()) {
            // Nếu đã đăng nhập, mở cửa sổ TourManagement
            TourManagement tourManagement = new TourManagement();
            tourManagement.setVisible(true);
        } else {
            // Nếu chưa đăng nhập, hiển thị cửa sổ đăng nhập
            Login login = new Login();
            login.setVisible(true);
        }
    }
});

        ImageIcon backgroundImageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/home4.jpg"));
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1920, 1050, Image.SCALE_DEFAULT);
        ImageIcon scaledBackgroundImageIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImageIcon);
        backgroundLabel.setBounds(0, 0, 1920, 1050);
        add(backgroundLabel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new DashBoard2();
    }
}
