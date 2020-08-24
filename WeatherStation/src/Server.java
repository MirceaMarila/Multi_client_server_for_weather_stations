package src;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends javax.swing.JFrame {

    //2 ports for 2 different types of clients
    private static final int PORT=9090;
    private static final int PORT1=9091;

    private static ArrayList<WeatherStation> weather_stations=new ArrayList<>(); //the proper list of weatherstations
    private static int nws=0; //size of this list


    //the lists of clients that are connected to the server (max 5 for each type of client)
    //if you want more than 5 clients of each type to connect in the same time, you cand modify the 5 BUT YOU HAVE TO MODIFY THE NUMBER OF THREADS IN MAIN ALSO ( while(i<5) )
    private static ArrayList<WSClientHandler> clients=new ArrayList<>();
    private static ExecutorService pool= Executors.newFixedThreadPool(5);

    private static ArrayList<UClientHandler> clients1=new ArrayList<>();
    private static ExecutorService pool1= Executors.newFixedThreadPool(5);


    //interface elements programming must be in class constructor
    public Server() {
        initComponents();
        ServerSocket listener= null;
        try {
            listener = new ServerSocket(PORT); //the server is waiting for a client to connect to that PORT

        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerSocket listener1= null;


        try {
            listener1 = new ServerSocket(PORT1);

        } catch (IOException e) {
            e.printStackTrace();
        }


        AddWS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                WeatherStation ws=new WeatherStation();
                ws.setId(weather_stations.size()+1);

                //setting random values
                Random r = new Random();
                ws.setLatitude(r.nextInt(180) - 90);
                ws.setLongitude(r.nextInt(360) - 180);
                ws.setTemperature(r.nextInt(40));
                ws.setBarometric_pressure(r.nextInt(71)+1013);
                ws.setRelative_humidity(r.nextInt(100));
                ws.setWind_force(r.nextInt(118));
                weather_stations.add(ws);//here the new WS is added to the arraylist
                setNws(weather_stations.size());
                int l=weather_stations.size();

                //display a message
                String message="Weather Station successfully added with id: "+String.valueOf(l)+"\n"+"GPS Positioning: "+String.valueOf(ws.getLatitude())+", "+String.valueOf(ws.getLongitude())+"\n"+"Temperature: "+String.valueOf(ws.getTemperature())+" °C"+"\n"+"Barometric pressure: "+String.valueOf(ws.getBarometric_pressure())+" mbar"+"\n"+"Relative humidity: "+String.valueOf(ws.getRelative_humidity())+" %"+"\n"+"Wind force: "+String.valueOf(ws.getWind_force())+" km/h";
                JOptionPane.showMessageDialog(null,message);

            }
        });

        ServerSocket finalListener = listener;
        ConWSC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                Socket client = null;
                try {
                    client = finalListener.accept();  //the client is connected to the server


                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("[SERVER] Connected to WSclient");
                WSClientHandler clientThread= null;
                try {
                    clientThread = new WSClientHandler(client);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                clients.add(clientThread); //we add the new client to the arraylist
                pool.execute(clientThread);


                //this part of code is useless, but it wont run without it
                //i learned to make multi-client interactive sessions. practically, the client can request something and the server will send that to him.
                //but for this project, it's not the case. i tried a lot of other ways to do the multi-client connection but only this one worked..
                //so there is some useless code here and there but without it, it wont run.
                BufferedReader input= null;
                try {
                    input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String data = null;
                try {
                    data = input.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //end of useless code


                //opening the weather station client GUI
                JFrame frame=new JFrame();
                frame.setContentPane(new WeatherStationClient().getWscpanel());
                frame.setSize(800,500);
                frame.setVisible(true);


            }
        });

        ServerSocket finalListener1 = listener1;
        LogUC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(ucnamet.getText().isEmpty()||ucpasst.getText().isEmpty()){  //if one of the fields is emplty ->error
                    String message="Please complete each field!";
                    JOptionPane.showMessageDialog(null, message);
                }
                else if(check_database(ucnamet.getText(),ucpasst.getText())==0) //if the credentials dont match with the ones in database ->error
                {
                    String message="Invalid name or password!";
                    JOptionPane.showMessageDialog(null, message);
                }
                else if(check_database(ucnamet.getText(),ucpasst.getText())==1) {

                    Socket client1 = null;
                    try {
                        client1 = finalListener1.accept(); //the server accepts the client
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[SERVER] Connected to Uclient");
                    UClientHandler clientThread1 = null;
                    try {
                        clientThread1 = new UClientHandler(client1);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    clients1.add(clientThread1); //the new client is added to the arraylist
                    pool1.execute(clientThread1);

                    //opening the GUI for the connected user client
                    JFrame frame=new JFrame();
                    frame.setContentPane(new UserClient().getUcpanel());
                    frame.setSize(800,500);
                    frame.setVisible(true);
                }

            }

        });

        RegUC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //opening the GUI to register the user
                JFrame frame=new JFrame();
                frame.setContentPane(new RegisterUC().getRegpanel());
                frame.setSize(800,500);
                frame.setVisible(true);
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddWS = new javax.swing.JButton();
        ConWSC = new javax.swing.JButton();
        RegUC = new javax.swing.JButton();
        LogUC = new javax.swing.JButton();
        ucnamet = new javax.swing.JTextField();
        ucpasst = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        AddWS.setText("jButton1");

        ConWSC.setText("jButton1");

        RegUC.setText("jButton1");

        LogUC.setText("jButton1");

        ucnamet.setText("jTextField1");

        ucpasst.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ucpasst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ucnamet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LogUC)
                    .addComponent(RegUC)
                    .addComponent(ConWSC)
                    .addComponent(AddWS))
                .addContainerGap(171, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddWS)
                .addGap(18, 18, 18)
                .addComponent(ConWSC)
                .addGap(18, 18, 18)
                .addComponent(RegUC)
                .addGap(18, 18, 18)
                .addComponent(LogUC)
                .addGap(18, 18, 18)
                .addComponent(ucnamet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ucpasst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        //here you can modify if you want more than 5 clients of each type to be connected in the same time
        //basically, we need to run the clients main's in separate threads because when the server will accept one of them (by pushing the connect as WSC button, for example),
        //the client will connect automatic to the server
        int i=0;
        while(i<5) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        WeatherStationClient.main(args);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            };

            thread.start();

            Thread thread1 = new Thread() {
                public void run() {
                    try {
                        UserClient.main(args);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            thread1.start();
        i=i+1;
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }
     //this function is useless but it helps the project to run correctly
    protected static String useless_function() {

        return "";
    }

//setters and getters
    public static ArrayList<WSClientHandler> getClients() {
        return clients;
    }

    public static void setClients(ArrayList<WSClientHandler> clients) {
        Server.clients = clients;
    }

    public static ArrayList<UClientHandler> getClients1() {
        return clients1;
    }

    public static void setClients1(ArrayList<UClientHandler> clients1) {
        Server.clients1 = clients1;
    }

    public static int getNws() {
        return nws;
    }

    public void setNws(int nws) {
        this.nws = nws;
    }

    public static ArrayList<WeatherStation> getWeather_stations() {
        return weather_stations;
    }

    public static void setWeather_stations(ArrayList<WeatherStation> weather_stations) {
        Server.weather_stations = weather_stations;
    }

    //the function that checks if the name and the password that were introduced in the field are in the databse
    public int check_database(String name, String password){
        String csvFile = "C:\\Users\\Mircea\\Desktop\\Mirchales\\FIVERR\\WeatherStation\\User_DB.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int flag=0;

        try {

            br = new BufferedReader(new FileReader(csvFile)); //read from the file
            while ((line = br.readLine()) != null) { //take each line

                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                if(data[0].equals(name)&&data[1].equals(password)){
                    flag=1; //if there is a match, turn the flag 1
                }


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(flag==1)
            return 1;
        else
            return 0;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddWS;
    private javax.swing.JButton ConWSC;
    private javax.swing.JButton LogUC;
    private javax.swing.JButton RegUC;
    private javax.swing.JTextField ucnamet;
    private javax.swing.JTextField ucpasst;
    // End of variables declaration//GEN-END:variables
}
