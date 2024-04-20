
package travel.and.tourism.management.system;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;

public class DashBoard2 extends JFrame {
    DashBoard2(){
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
       
       JButton addPersonalDetails=new JButton("Add Personal Details");
       addPersonalDetails.setBounds(0, 0, 300, 50);
       addPersonalDetails.setBackground(new Color(0,0,102));
       addPersonalDetails.setForeground(Color.WHITE);
       addPersonalDetails.setFont(new Font("Tahoma",Font.PLAIN,20));
       addPersonalDetails.setMargin(new Insets(0,0,0,60));
       p2.add(addPersonalDetails);
       
       JButton updatePersonalDetails=new JButton("Update Personal Details");
       updatePersonalDetails.setBounds(0, 50, 300, 50);
       updatePersonalDetails.setBackground(new Color(0,0,102));
       updatePersonalDetails.setForeground(Color.WHITE);
       updatePersonalDetails.setFont(new Font("Tahoma",Font.PLAIN,20));
       updatePersonalDetails.setMargin(new Insets(0,0,0,30));
       p2.add(updatePersonalDetails);
       
       JButton viewPersonalDetails=new JButton("View Details");
       viewPersonalDetails.setBounds(0, 100, 300, 50);
       viewPersonalDetails.setBackground(new Color(0,0,102));
       viewPersonalDetails.setForeground(Color.WHITE);
       viewPersonalDetails.setFont(new Font("Tahoma",Font.PLAIN,20));
       viewPersonalDetails.setMargin(new Insets(0,0,0,130));
       p2.add(viewPersonalDetails);
       
       JButton deletePersonalDetails=new JButton("Delete Personal Details");
       deletePersonalDetails.setBounds(0, 150, 300, 50);
       deletePersonalDetails.setBackground(new Color(0,0,102));
       deletePersonalDetails.setForeground(Color.WHITE);
       deletePersonalDetails.setFont(new Font("Tahoma",Font.PLAIN,20));
       deletePersonalDetails.setMargin(new Insets(0,0,0,40));
       p2.add(deletePersonalDetails);
       
       JButton checkpackages=new JButton("Check Packages");
       checkpackages.setBounds(0, 200, 300, 50);
       checkpackages.setBackground(new Color(0,0,102));
       checkpackages.setForeground(Color.WHITE);
       checkpackages.setFont(new Font("Tahoma",Font.PLAIN,20));
       checkpackages.setMargin(new Insets(0,0,0,110));
       p2.add(checkpackages);
       
       JButton bookpackages=new JButton("Update Packages");
       bookpackages.setBounds(0, 250, 300, 50);
       bookpackages.setBackground(new Color(0,0,102));
       bookpackages.setForeground(Color.WHITE);
       bookpackages.setFont(new Font("Tahoma",Font.PLAIN,20));
       bookpackages.setMargin(new Insets(0,0,0,120));
       p2.add(bookpackages);
       
       JButton viewpackages=new JButton("View Packages");
       viewpackages.setBounds(0, 300, 300, 50);
       viewpackages.setBackground(new Color(0,0,102));
       viewpackages.setForeground(Color.WHITE);
       viewpackages.setFont(new Font("Tahoma",Font.PLAIN,20));
       viewpackages.setMargin(new Insets(0,0,0,120));
       p2.add(viewpackages);
       
       JButton viewhotels=new JButton("View Hotel");
       viewhotels.setBounds(0, 350, 300, 50);
       viewhotels.setBackground(new Color(0,0,102));
       viewhotels.setForeground(Color.WHITE);
       viewhotels.setFont(new Font("Tahoma",Font.PLAIN,20));
       viewhotels.setMargin(new Insets(0,0,0,130));
       p2.add(viewhotels);
       
       JButton bookhotels=new JButton("Update Holtel");
       bookhotels.setBounds(0, 400, 300, 50);
       bookhotels.setBackground(new Color(0,0,102));
       bookhotels.setForeground(Color.WHITE);
       bookhotels.setFont(new Font("Tahoma",Font.PLAIN,20));
       bookhotels.setMargin(new Insets(0,0,0,140));
       p2.add(bookhotels);
       
       JButton viewBookedHotels=new JButton("Check Hotel");
       viewBookedHotels.setBounds(0, 450, 300, 50);
       viewBookedHotels.setBackground(new Color(0,0,102));
       viewBookedHotels.setForeground(Color.WHITE);
       viewBookedHotels.setFont(new Font("Tahoma",Font.PLAIN,20));
       viewBookedHotels.setMargin(new Insets(0,0,0,150));
       p2.add(viewBookedHotels);
       
       
       JButton destinations=new JButton("Destinations");
       destinations.setBounds(0, 500, 300, 50);
       destinations.setBackground(new Color(0,0,102));
       destinations.setForeground(Color.WHITE);
       destinations.setFont(new Font("Tahoma",Font.PLAIN,20));
       destinations.setMargin(new Insets(0,0,0,140));
       p2.add(destinations);
       
       
       
       JButton notepad=new JButton("Notepad");
       notepad.setBounds(0, 550, 300, 50);
       notepad.setBackground(new Color(0,0,102));
       notepad.setForeground(Color.WHITE);
       notepad.setFont(new Font("Tahoma",Font.PLAIN,20));
       notepad.setMargin(new Insets(0,0,0,145));
       p2.add(notepad);
       
       JButton about=new JButton("About");
       about.setBounds(0, 600, 300, 50);
       about.setBackground(new Color(0,0,102));
       about.setForeground(Color.WHITE);
       about.setFont(new Font("Tahoma",Font.PLAIN,20));
       about.setMargin(new Insets(0,0,0,175));
       p2.add(about);
       
       ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/home4.jpg"));
       Image i5=i4.getImage().getScaledInstance(1920, 1050, Image.SCALE_DEFAULT);
       ImageIcon i6=new ImageIcon(i5);
       JLabel image=new JLabel(i6);
       image.setBounds(0,0,1920,1050);
       add(image);
       
       
       
       


       
      

    
  

       
       
       
       
       
       
       
       
       
       
       
       
        
        
        
        
        setVisible(true);
       
    }
    
    public static void main(String[] args){
       new DashBoard2();
     }

}

