package com.company;



import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Client  implements ActionListener {
    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static Socket s;

    static DataInputStream din;
    static DataOutputStream dout;
    static Box vertical = Box.createVerticalBox();
    boolean typing;

    static JFrame f1 = new JFrame();


    Client() {
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(5, 17, 30, 30);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });


        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(40, 10, 50, 50);
        p1.add(l2);


        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(292, 20, 35, 30);
        p1.add(l5);


        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel l6 = new JLabel(i12);
        l6.setBounds(353, 20, 35, 30);
        p1.add(l6);


        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("com/company/icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel l7 = new JLabel(i15);
        l7.setBounds(413, 20, 13, 25);
        p1.add(l7);

        JLabel l3 = new JLabel("Bindusar");
        l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(110, 15, 100, 18);
        p1.add(l3);

        JLabel l4 = new JLabel("Active Now");
        l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 11));
        l4.setForeground(Color.WHITE);
        l4.setBounds(110, 35, 100, 20);
        p1.add(l4);

        Timer t = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!typing)
                {
                    l4.setText("Active Now");
                }
            }
        });
        t.setInitialDelay(2000);



        a1 = new JPanel();
        //a1.setBounds(3, 73, 444, 438);
        a1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));


        JScrollPane sp = new JScrollPane(a1);
        sp.setBounds(3,73,444,438);
        sp.setBorder(BorderFactory.createEmptyBorder());


        ScrollBarUI ui = new BasicScrollBarUI() {
            protected JButton createDecreaseButton(int orientation){
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor= new Color(7,94,84);
                return button;
            }
            protected JButton createIncreaseButton(int orientation){
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(7,94,84));
                button.setForeground(Color.WHITE);
                this.thumbColor = new Color(7,94,84);
                return button;
            }


        };

        sp.getVerticalScrollBar().setUI(ui);
        f1.add(sp);


        t1 = new JTextField();
        t1.setBounds(3, 512, 327, 35);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                l4.setText("Typing...");
                t.stop();
                typing = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                typing = false;
                if(!t.isRunning())
                {
                    t.start();
                }
            }
        });



        b1 = new JButton("Send");
        b1.setBounds(330, 512, 118, 35);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        f1.add(b1);


        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(450, 550);
        f1.setLocation( 700,150);
        f1.setUndecorated(true);
        f1.setVisible(true);


    }

    public void actionPerformed(ActionEvent ae) {
        try{
            String out = t1.getText();
            sendTextToFile(out);
            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical,BorderLayout.PAGE_START);

            //a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void sendTextToFile(String message) throws FileNotFoundException{
        try(FileWriter f =new FileWriter("chat.txt",true);
            PrintWriter p = new PrintWriter(new BufferedWriter(f));){

            p.println("Bindusar: "+message);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out)
    {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));

        JLabel l1  = new JLabel("<html><p style =\"width :150px\">"+out+"</p></html>");
        l1.setFont(new Font("Helvetica",Font.PLAIN,16));
        l1.setBackground(new Color(37,211,102));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        p3.add(l1);
        p3.add(l2);
        return p3;
    }



    public static void main(String[] args)
    {
        new Client().f1.setVisible(true);


        try{

            s = new Socket("127.0.0.1",6001);
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());


            String msg = "";


            while(true) {
                a1.setLayout(new BorderLayout());
                msg = din.readUTF();
                JPanel p2 = formatLabel(msg);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);
                f1.validate();
            }



        }catch (Exception e){

        }
    }
}
