
package travel.and.tourism.management.system;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.*;
public class Signup extends JFrame implements ActionListener {
    
    JButton create,back;
    JTextField tfname,tfusername,tfpassword,tfanswer;
    Choice security,roleChoice;
    Signup(){
        setBounds(350, 200, 900, 360);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
               
        JPanel p2= new JPanel();
        p2.setBackground(new Color(133,193,233));
        p2.setBounds(0,0,500,400);
        p2.setLayout(null);
        add(p2);
        
      
        
        JLabel lblusername=new JLabel("Username");
        lblusername.setBounds(50, 20, 125, 25);
        lblusername.setFont(new Font("Tahoma",Font.BOLD,14));
        p2.add(lblusername);
        
        tfusername=new JTextField();
        tfusername.setBounds(190, 20, 180, 25);
        tfusername.setBorder(BorderFactory.createEmptyBorder());
        p2.add(tfusername);
        
      
        JLabel lblname=new JLabel("name");
        lblname.setBounds(50, 60, 125, 25);
        lblname.setFont(new Font("Tahoma",Font.BOLD,14));
        p2.add(lblname);
        
        tfname=new JTextField();
        tfname.setBounds(190, 60, 180, 25);
        tfname.setBorder(BorderFactory.createEmptyBorder());
        p2.add(tfname);
        
        JLabel lblpassword=new JLabel("Password");
        lblpassword.setBounds(50, 100, 125, 25);
        lblpassword.setFont(new Font("Tahoma",Font.BOLD,14));
        p2.add(lblpassword);
        
        tfpassword=new JTextField();
        tfpassword.setBounds(190, 100, 180, 25);
        tfpassword.setBorder(BorderFactory.createEmptyBorder());
        p2.add(tfpassword);
        
        JLabel lblsecurity=new JLabel("Security Question");
        lblsecurity.setBounds(50, 140, 125, 25);
        lblsecurity.setFont(new Font("Tahoma",Font.BOLD,14));
        p2.add(lblsecurity);
        
        
        
        
        security = new Choice();
        security.add("Fav game");
        security.add("Fav song");
        security.add("Fav movie");
        security.add("Fav number");
        security.setBounds(190, 140, 180, 25);
        p2.add(security);
        
        JLabel lblanswer=new JLabel(" Answer");
        lblanswer.setBounds(50, 180, 125, 25);
        lblanswer.setFont(new Font("Tahoma",Font.BOLD,14));
        p2.add(lblanswer);
        
        tfanswer= new JTextField();
        tfanswer.setBounds(190, 180, 180, 25);
        tfanswer.setBorder(BorderFactory.createEmptyBorder());
        p2.add(tfanswer);
        
        create = new JButton("Create");
        create.setForeground(new Color(133,193,233));
        create.setBackground(Color.WHITE);
        create.setFont(new Font("Tahoma",Font.BOLD,14));
        create.setBounds(80, 250, 100, 30);
        create.addActionListener(this);
        
        p2.add(create);
        
        back = new JButton("Back");
        back.setForeground(new Color(133,193,233));
        back.setBackground(Color.WHITE);
        back.setFont(new Font("Tahoma",Font.BOLD,14));
        back.setBounds(250, 250, 100, 30);
        back.addActionListener(this);
        p2.add(back);
        
        JLabel lblrole = new JLabel("Select Role");
        lblrole.setBounds(50, 220, 125, 25);
        lblrole.setFont(new Font("Tahoma", Font.BOLD, 14));
        p2.add(lblrole);

        roleChoice = new Choice();
        roleChoice.add("User");
        roleChoice.add("Manager");
        roleChoice.setBounds(190, 220, 180, 25);
        p2.add(roleChoice);
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/signup.png"));
        Image i2=i1.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT );
        ImageIcon i3=new ImageIcon(i2);
        JLabel image=new JLabel(i3);
        image.setBounds(580, 50, 250, 250);
        add(image);

        
        
        setVisible(true);
                
                
        
    }
   @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            String username = tfusername.getText();
            String name = tfname.getText();
            String password = tfpassword.getText();
            String question = security.getSelectedItem();
            String answer = tfanswer.getText();
            String role = roleChoice.getSelectedItem(); // Lấy vai trò được chọn

            // Thêm giá trị vai trò vào truy vấn SQL
            String query = "INSERT INTO account (username, name, password, security, answer, role) VALUES ('" + username + "', '" + name + "', '" + password + "', '" + question + "', '" + answer + "', '" + role + "')";
            try {
                Conn c = new Conn();
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Account Created Successfully");
                setVisible(false);
                new Login();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
