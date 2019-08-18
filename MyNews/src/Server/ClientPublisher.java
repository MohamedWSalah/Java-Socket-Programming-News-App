
package Server;

import static Broker.Broker.arInput;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;

/**
 *
 * @author Moon159588
 */

    /*-------------------------
    ---------All Comments-------
    ----------Are Written By-----
    ----------Mohamed159588-------
    ------------------------------*/
public class ClientPublisher implements Runnable {

    Socket cSocket;
    public ClientPublisher(Socket cSocket) throws IOException {
        this.cSocket = cSocket;
    };
    
    static ClientPublisherGUI GUI = new ClientPublisherGUI();
    static JButton Button = GUI.jButton1;
    
    @Override
    public void run() {

        try {

            DataOutputStream Soutput = new DataOutputStream(cSocket.getOutputStream());
            DataInputStream Sinput = new DataInputStream(cSocket.getInputStream());
            //read the name entered by the client
            String CN = Sinput.readUTF();
            GUI.ConnectedClients(CN);
            System.out.println("R3C31V3D |" + CN);
            
            
            //Get the Category and the text from the ClientPublisher GUI
            //The GUI send the info to the ClientPublisher class by a socket
            ServerSocket X = new ServerSocket(5000);
            String Category = "";
            while (!Category.equals("None")) 
            {
                
                Socket A = X.accept();
                DataInputStream IN = new DataInputStream(A.getInputStream());
                Category = IN.readUTF();
                String Text = IN.readUTF();
                System.out.println(Category +" "+ Text + " Got from GUI !!!");
                
                //check if there is any incoming data on Sinput datastream
                    if(Sinput.available()!=0)
                   {
                        System.out.println("Unsubscriber");
                        String UnClientName = Sinput.readUTF();
                        System.out.println(UnClientName+" Read ");
                        GUI.DisconncetedClients(UnClientName);
                        System.out.println(UnClientName+" UnSubscribed :D");
                   }
                   
                
                
                Soutput.writeUTF(Category);
                System.out.println("Category Sent to broker");
                Soutput.writeUTF(Text);
                System.out.println("Text Sent to brokter");
                
            }
            
        } 
        catch (Exception e) 
        {
            System.out.println("1_W4NN4_3ND_M3");
        }
    }




    public static void main(String[] args) throws IOException {
        GUI.setVisible(true);
        
         
         
        //=============================================
        ServerSocket X = new ServerSocket(3000);

        while (true) {

            Socket A = X.accept();
            
            Thread Client = new Thread(new ClientPublisher(A));
            Client.start();

        }
        
       
    }

}