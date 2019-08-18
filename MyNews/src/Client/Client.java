package Client;

import java.awt.event.ActionEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import Broker.Broker;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;

/**
 *
 * @author Mohamed159588
 */
public class Client {
    static int i;
    public Socket BSocket;
    public int ClientNumber;
    static { i = 0; }
    
    
    public Client(Socket S, int Num) {
        BSocket = S;
        ClientNumber = Num;
    }
    static UnAndSubscribe SObj = new UnAndSubscribe();
    
    //that string will carry the client's name to send it to the broker on unsubscribe
    static String Nx;
    
    /*--------------------------
    ---------All Comments--------
    ----------Are Written By------
    ----------Mohamed159588-------
    ------------------------------*/
    
    public static void main(String[] args) throws IOException {

        //Creating Objects from the GUIs
        ClientGUI1 GUI1 = new ClientGUI1();
        ClientGUI2 GUI2 = new ClientGUI2();
        ClientGUI3 GUI3 = new ClientGUI3();
        
        //Creating Objects for the Buttons of the GUIs
        JButton B1 = GUI1.jButton1;
        JButton B2 = GUI2.jButton1;
        JButton SaveSports = GUI2.jButton4;
        JButton SaveEnt = GUI2.jButton6;
        JButton SavePoli = GUI2.jButton7;
        JButton Saved = GUI2.jButton11;
        JButton SFav = GUI2.jButton9;
        
        
        //check boxes of GUI 
        JCheckBox CheckBox1 = GUI1.jCheckBox1;
        JCheckBox CheckBox2 = GUI1.jCheckBox2;
        JCheckBox CheckBox3 = GUI1.jCheckBox3;
        
        
        //Display the first GUI
        GUI1.setVisible(true);

        //Creating a socket with localhost and port 2000
        Socket cs = new Socket("localhost", 2000);
        
        //creating Objects of OutStream and InStream to receive and send data
        DataOutputStream Coutput = new DataOutputStream(cs.getOutputStream());
        DataInputStream Cinput = new DataInputStream(cs.getInputStream());
        
        ObjectOutputStream ObjOutput = new ObjectOutputStream(cs.getOutputStream());
        
        
        //button1 GUI 1 Press Action - Mohamed159588
        B1.addActionListener((ActionEvent e) -> {
            //Mohamed159588
            if(CheckBox1.isSelected() && CheckBox2.isSelected() && CheckBox3.isSelected())
             {   
                 SObj.setSports(true);
                 SObj.setEntertainment(true);
                 SObj.setPolitical(true);
                 System.out.println("1/2/3");
                 GUI2.jLabel6.setText("Sports");
                 GUI2.jLabel7.setText("Entertainment");
                 GUI2.jLabel8.setText("Political");
             }
             else if(CheckBox1.isSelected() && CheckBox2.isSelected()==false && CheckBox3.isSelected()==false )
             {
                  SObj.setSports(true);
                  SObj.setEntertainment(false);
                  SObj.setPolitical(false);
                  System.out.println("1");
                  GUI2.jLabel6.setText("Sports");
             }
             else if(CheckBox2.isSelected() && CheckBox1.isSelected()==false && CheckBox3.isSelected()==false)
             {
                 SObj.setEntertainment(true);
                 SObj.setSports(false);
                 SObj.setPolitical(false);
                 System.out.println("2");
                 GUI2.jLabel7.setText("Entertainment");
             }
             else if(CheckBox3.isSelected() && CheckBox1.isSelected() == false && CheckBox2.isSelected() == false)
             {
                 SObj.setPolitical(true);
                 SObj.setSports(false);
                 SObj.setEntertainment(false);
                 System.out.println("3");
                 GUI2.jLabel8.setText("Political");
             }
             else if (CheckBox1.isSelected() && CheckBox2.isSelected() && CheckBox3.isSelected() == false)
             {
                 SObj.setSports(true);
                 SObj.setEntertainment(true);
                 SObj.setPolitical(false);
                 System.out.println("1/2");
                 GUI2.jLabel6.setText("Sports");
                 GUI2.jLabel7.setText("Entertainment");
             }
             else if(CheckBox1.isSelected() && CheckBox3.isSelected() && CheckBox2.isSelected()==false)
             {
                 SObj.setSports(true);
                 SObj.setPolitical(true);
                 SObj.setEntertainment(false);
                 System.out.println("1/3");
                 GUI2.jLabel6.setText("Sports");
                 GUI2.jLabel8.setText("Political");
             }
             else if(CheckBox2.isSelected() && CheckBox3.isSelected() && CheckBox1.isSelected()==false)
             {
                 SObj.setSports(false);
                 SObj.setPolitical(true);
                 SObj.setEntertainment(true);
                 System.out.println("2/3");
                 GUI2.jLabel7.setText("Entertainment");
                 GUI2.jLabel8.setText("Political");
             }
            //Get the text in jTextField1 and store it in ClientName - Mohamed159588
            String ClientName = GUI1.jTextField1.getText();
            System.out.println(ClientName);
            Nx = ClientName;
            try 
            {   //Send the clientname string to the Broker
                Coutput.writeUTF(ClientName);
                System.out.println("Client name sent");
                
                System.out.println(SObj.isSports());
                System.out.println(SObj.isEntertainment());
                System.out.println(SObj.isPolitical());
                
                ObjOutput.writeObject(SObj);
                
            } 
            catch (Exception ea) 
            {
                System.out.println("CATCH!!!");
            }
            //Display GUI2 and Dispose GUI1
            GUI2.setVisible(true);
            GUI2.jLabel2.setText("Welcome to MyNews," + ClientName + ".");
            
            GUI1.dispose();
            
        });

        //============================
        //Button2 GUi2 UnAndSubscribe- Mohamed159588
        B2.addActionListener((ActionEvent e) -> {
            try 
            {
                System.out.println(Nx + " Unsubscribe");
                Coutput.writeUTF(Nx);
                System.out.println("unsubscribed successfuly");
                
                //closing the GUI after pressing unsubscribe
                
                GUI2.dispose();
            } 
            catch (Exception ea) 
            {
                System.out.println("F41L3D");
            }

        });
        //============================
        //ArrayList for the News the user would like to save
        ArrayList < String > SavedNews = new ArrayList < > ();
        //SaveButtonSports - Mohamed159588
        SaveSports.addActionListener((ActionEvent e) -> {

            JOptionPane.showMessageDialog(null, "Saved", "Saved!", WIDTH);
            String SavedS = ("Sports :" + GUI2.jTextArea1.getText());
            //Add the string Saved that carry the Savednews to the ArrayList
            SavedNews.add(SavedS);
            System.out.println(SavedS + "SAVED");

        });

        //SaveButtonEntertainment - Mohamed159588
        SaveEnt.addActionListener((ActionEvent e) -> {

            JOptionPane.showMessageDialog(null, "Saved", "Saved!", WIDTH);
            String SavedS = ("Entertainment :" + GUI2.jTextArea2.getText());
            SavedNews.add(SavedS);
            System.out.println(SavedS + "SAVED");

        });

        //SaveButtonPolitical - Mohamed159588
        SavePoli.addActionListener((ActionEvent e) -> {

            JOptionPane.showMessageDialog(null, "Saved", "Saved!", WIDTH);
            String SavedS = ("Political :" + GUI2.jTextArea3.getText());
            SavedNews.add(SavedS);
            System.out.println(SavedS + "SAVED");

        });

        //ShowSaved - Mohamed159588
        Saved.addActionListener((ActionEvent e) -> {
            //Display the GUI3 and write all the saved news ( from the array )
            GUI3.setVisible(true);
            for (int i = 0; i < SavedNews.size(); i++) {
                GUI3.DisplaySaved(SavedNews.get(i));
            }
            //This function clear the Elements After Dispalying it to avoid the repeatation of the same News.
            SavedNews.clear();
        });

        //=========================
        

        //Mohamed159588
        String Category = "";
        while (!("None".equals(Category))) {
            Category = Cinput.readUTF();
            System.out.println(Category);
            String Text = Cinput.readUTF();
            System.out.println(Text);
            if (null == Category) {
                JOptionPane.showMessageDialog(null, "CLOSED", "CLOSED", WIDTH);
            } else switch (Category) {
                case "Sports":
                    GUI2.jTextArea1.setText(Text);
                    break;
                case "Entertainment":
                    GUI2.jTextArea2.setText(Text);
                    break;
                case "Political":
                    GUI2.jTextArea3.setText(Text);
                    break;
                case "Refresh":
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "CLOSED", "CLOSED", WIDTH);
                    break;
            }
        }
        //Mohamed159588 \m/
        
        
        


        //close the connections
        Cinput.close();
        Coutput.close();
        cs.close();
    }

}