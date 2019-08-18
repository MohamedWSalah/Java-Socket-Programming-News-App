/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Broker;

import Client.Client;
import Client.UnAndSubscribe;
import Server.ClientPublisherGUI;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Mohamed159588
 */


    /*--------------------------
    ---------All Comments--------
    ----------Are Written By------
    ----------Mohamed159588-------
    ------------------------------*/
public class Broker implements Runnable {
    //Create vector to carry the Connected clients number
    static Vector <Client> ar = new Vector < > ();
    //Create vector to carry the OutStream Connections associated with the same client number
    static public Vector <DataOutputStream> arOutput = new Vector < > ();
    static public Vector <DataInputStream> arInput=new Vector < > ();
    static public Vector <UnAndSubscribe> UnV = new Vector < > ();
    static public Vector <String> Names = new Vector < > ();
    
    static int i;
    Socket cSocket;
    static {
        i = 0;
    }
    public Broker(Socket cSocket) {
        this.cSocket = cSocket;
    }
    
    
    

    @Override
    public void run() {
        try {
            String Category = "";
            String Text;
            String Name;
            String U;

            //Client Socket
            DataOutputStream out = new DataOutputStream(cSocket.getOutputStream());
            DataInputStream in = new DataInputStream(cSocket.getInputStream());
            
            ObjectInputStream ObjInput = new ObjectInputStream(cSocket.getInputStream());
            
            //Server Socket
            Socket s1 = new Socket("localhost", 3000);
            DataOutputStream output = new DataOutputStream(s1.getOutputStream());
            DataInputStream input = new DataInputStream(s1.getInputStream());
            
            //====
           
            //Read the name from the client and send it to the MainServer
            Name = in .readUTF();
            System.out.println(Name);
            output.writeUTF(Name);
            Names.add(Name);
            
            UnV.add((UnAndSubscribe)ObjInput.readObject());
            
           
           
            //Mohamed159588
            while (!(Category.equals("None"))) 
            {
                Category = input.readUTF();
                System.out.println(Category);
                Text = input.readUTF();
                System.out.println(Text);
                System.out.println("Client and category printed");
                
                
                System.out.println("============================");
                /*Loop on the clients and send them the Category + text using
                 the OutStream associated to each Client from the Vector*/
                for (int it = 0; it < Broker.ar.size(); it++) 
                    {
                        //available functions check if there is any data that could be read using arInput datastream
                        if(arInput.get(it).available()!=0)
                        {
                            String X =arInput.get(it).readUTF();
                            System.out.println("need to remove " + X);
                            int Index=Names.indexOf(X);
                            System.out.println(Index + " is the number of the client index in vector " + X);
                            ar.remove(Index);
                            arOutput.remove(Index);
                            arInput.remove(Index);
                            UnV.remove(Index);
                            Names.remove(Index);
                            output.writeUTF(X);
                            System.out.println("REMOVED");
                            i--;
                            System.out.println("Clients Connected : " +i);
                        }
                            System.out.println(UnV.get(it).isSports());
                            System.out.println(UnV.get(it).isEntertainment());
                            System.out.println(UnV.get(it).isPolitical());
                            System.out.println("============================");
                            
                            if(UnV.get(it).isSports()==true && UnV.get(it).isEntertainment()==true && UnV.get(it).isPolitical()== true)
                            {
                                arOutput.get(it).writeUTF(Category);
                                arOutput.get(it).writeUTF(Text);

                                System.out.println(Category + " "+ Text +" Sent to " +Names.get(it));
                                System.out.println("1/2/3");
                            }
                            else if (UnV.get(it).isSports()==true && UnV.get(it).isEntertainment()==false && UnV.get(it).isPolitical()==false)
                            {
                                if(Category.equals("Entertainment") || Category.equals("Political"))
                                {
                                    System.out.println("Entertainment and Politcal topics are blocked by the user " + Names.get(it));
                                }
                                else
                                {
                                    arOutput.get(it).writeUTF(Category);
                                    arOutput.get(it).writeUTF(Text);

                                    System.out.println(Category + " "+ Text +" Sent to " +Names.get(it));
                                    System.out.println("1 Only");
                                }
                            }
                            else if(UnV.get(it).isSports()==false && UnV.get(it).isEntertainment()==true && UnV.get(it).isPolitical()==false)
                            {
                               if(Category.equals("Sports") || Category.equals("Political"))
                                {
                                    System.out.println("Sports and Political topic are blocked by the subscriber " + Names.get(it));
                                }
                               else
                               {
                                    arOutput.get(it).writeUTF(Category);
                                    arOutput.get(it).writeUTF(Text);

                                    System.out.println(Category + " "+ Text +" Sent to " +Names.get(it)); 
                                    System.out.println("2 Only");
                               }
                                
                            }
                            else if(UnV.get(it).isSports()==false && UnV.get(it).isEntertainment()==false && UnV.get(it).isPolitical()==true)
                            {
                               if(Category.equals("Sports") || Category.equals("Entertainment"))
                                {
                                    System.out.println("Sports and Entertainment topic are blocked by the subscriber " + Names.get(it));
                                }
                               else
                               {   
                                    arOutput.get(it).writeUTF(Category);
                                    arOutput.get(it).writeUTF(Text);

                                    System.out.println(Category + " "+ Text +" Sent to " +Names.get(it));
                                    System.out.println("3 Only");
                               }
                            }
                            else if(UnV.get(it).isSports()==true && UnV.get(it).isEntertainment()==true && UnV.get(it).isPolitical()==false)
                            {
                               if(Category.equals("Political"))
                                {
                                    System.out.println("Political topic is blocked by the subscriber " + Names.get(it));
                                }
                               else
                               {
                                    arOutput.get(it).writeUTF(Category);
                                    arOutput.get(it).writeUTF(Text);

                                    System.out.println(Category + " "+ Text +" Sent to " +Names.get(it));
                                    System.out.println("1/2");
                               }
                            }
                            else if(UnV.get(it).isSports()==true && UnV.get(it).isEntertainment()==false && UnV.get(it).isPolitical()==true)
                            {
                                if(Category.equals("Entertainment"))
                                {
                                   System.out.println("Entertainment topic is blocked by the subscriber " + Names.get(it)); 
                                }
                                else
                                {
                                    arOutput.get(it).writeUTF(Category);
                                    arOutput.get(it).writeUTF(Text);

                                    System.out.println(Category + " "+ Text +" Sent to " +Names.get(it));
                                    System.out.println("1/3");
                                }
                            }
                            else if(UnV.get(it).isSports()==false && UnV.get(it).isEntertainment()==true && UnV.get(it).isPolitical()==true)
                            {
                               if(Category.equals("Sports"))
                                {
                                   System.out.println("Sports topic is blocked by the subscriber " + Names.get(it)); 
                                } 
                               else
                               {
                                    arOutput.get(it).writeUTF(Category);
                                    arOutput.get(it).writeUTF(Text);
                                    System.out.println(Category + " "+ Text +" Sent to " +Names.get(it));
                                    System.out.println("2/3");
                               }
                            }
                         
            }
            }
            
            //Close the Connections
            
            out.close();
            input.close();
            output.close();
            s1.close();
            cSocket.close();

        }
        catch (Exception e) 
        {
            System.out.println("1_W4NN4_3ND_M3");
        }
    }



    public static void main(String[] args) throws IOException {

       
        ServerSocket sSocket = new ServerSocket(2000);

        while (true) {

            Socket S = sSocket.accept();
            //Add the new client to the array list after accepeting its connection
            ar.add(new Client(S, i));
            //Create a OutStream assoicated to the new client
            arOutput.add(new DataOutputStream(S.getOutputStream()));
            arInput.add(new DataInputStream(S.getInputStream()));
            i++;
            
            System.out.println("Clients Connected : " +i);
            Thread Client = new Thread(new Broker(S));
            Client.start();



        }

    }
}