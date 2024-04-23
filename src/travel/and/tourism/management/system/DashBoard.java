
package travel.and.tourism.management.system;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashBoard extends JFrame {
    DashBoard(){
       // setBounds(0,0,1600,1000);
       setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        
        
        JPanel p1=new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(0,0,102));
        p1.setBounds(0,0,1920,65);
        add(p1);
        
       ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/dashboard.png")) ;
       Image i2=i1.getImage().getScaledInstance(70, 70,Image.SCALE_DEFAULT);
       ImageIcon i3=new ImageIcon(i2);
       JLabel icon=new JLabel(i3);
       icon.setBounds(5, 0, 70, 70);
       p1.add(icon);
       
       JLabel heading=new JLabel("Dashboard");
       heading.setBounds(80, 10, 300, 40);
       heading.setForeground(Color.WHITE);
       heading.setFont(new Font("Tahoma",Font.BOLD,30));
       p1.add(heading);
       
       JPanel p2=new JPanel();
       p2.setLayout(null);
       p2.setBackground(new Color(0,0,102));
       p2.setBounds(0,65,300,950);
       add(p2);
       
       setVisible(true);
       
       JButton addPersonalDetails=new JButton("Add Personal Details");
       addPersonalDetails.setBounds(0, 0, 300, 50);
       addPersonalDetails.setBackground(new Color(0,0,102));
       addPersonalDetails.setForeground(Color.WHITE);
       addPersonalDetails.setFont(new Font("Tahoma",Font.PLAIN,20));
       addPersonalDetails.setMargin(new Insets(0,0,0,60));
       p2.add(addPersonalDetails);
       
       
    
       
       
       
       
    
       
       JButton bookpackages=new JButton("Book Tour");
       bookpackages.setBounds(0, 50, 300, 50);
       bookpackages.setBackground(new Color(0,0,102));
       bookpackages.setForeground(Color.WHITE);
       bookpackages.setFont(new Font("Tahoma",Font.PLAIN,20));
       bookpackages.setMargin(new Insets(0,0,0,120));
       p2.add(bookpackages);
       
       
      bookpackages.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (UserSession.getInstance().getLoggedInUsername() != null) {
            // Nếu người dùng đã đăng nhập, chuyển đến trang TourBooking
            new TourBooking();
        } else {
            // Nếu người dùng chưa đăng nhập, hiển thị cửa sổ đăng nhập
            new Login();
        }
    }
});


    
       
       JButton viewpackages=new JButton("View Booked Tour");
       viewpackages.setBounds(0, 100, 300, 50);
       viewpackages.setBackground(new Color(0,0,102));
       viewpackages.setForeground(Color.WHITE);
       viewpackages.setFont(new Font("Tahoma",Font.PLAIN,20));
       viewpackages.setMargin(new Insets(0,0,0,120));
       p2.add(viewpackages);
       
       
       
      
       JButton destinations=new JButton("Destinations");
       destinations.setBounds(0, 150, 300, 50);
       destinations.setBackground(new Color(0,0,102));
       destinations.setForeground(Color.WHITE);
       destinations.setFont(new Font("Tahoma",Font.PLAIN,20));
       destinations.setMargin(new Insets(0,-150,0,0));
       p2.add(destinations);
       
       JButton payments=new JButton("Payments");
       payments.setBounds(0, 200, 300, 50);
       payments.setBackground(new Color(0,0,102));
       payments.setForeground(Color.WHITE);
       payments.setFont(new Font("Tahoma",Font.PLAIN,20));
       payments.setMargin(new Insets(0,-150,0,0));
       p2.add(payments);
       
       JButton calculators=new JButton("Calculators");
       calculators.setBounds(0, 250, 300, 50);
       calculators.setBackground(new Color(0,0,102));
       calculators.setForeground(Color.WHITE);
       calculators.setFont(new Font("Tahoma",Font.PLAIN,20));
       calculators.setMargin(new Insets(0,-150,0,0));
       p2.add(calculators);
       
     
       
       JButton about=new JButton("About");
       about.setBounds(0, 300, 300, 50);
       about.setBackground(new Color(0,0,102));
       about.setForeground(Color.WHITE);
       about.setFont(new Font("Tahoma",Font.PLAIN,20));
       about.setMargin(new Insets(0,-150,0,0));
       p2.add(about);
    
    
             
       ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
       Image i5=i4.getImage().getScaledInstance(1920, 1050, Image.SCALE_DEFAULT);
       ImageIcon i6=new ImageIcon(i5);
       JLabel image=new JLabel(i6);
       image.setBounds(0,0,1920,1050);
       add(image);
       
       
       
       
            addPersonalDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Tạo một thể hiện của lớp AddCustomer
                AddCustomer addCustomerFrame = new AddCustomer();
                // Hiển thị cửa sổ AddCustomer
                addCustomerFrame.setVisible(true);
            }
        });

       
       
       
       
       
       
       
       
       
       
       
       
       
       
        
        
        
        
        setVisible(true);
       
    }
    
    public static void main(String[] args){
       new DashBoard();
     }

}
