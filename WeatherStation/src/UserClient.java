package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Random;

public class UserClient {

    //interface variables
    private JPanel ucpanel;
    private JSpinner spinner; //spinner for the selection of the weather station
    private JButton displayws; //display ws data button
    private JButton downloadws; //download ws data button
    private JButton displayfield; //display field info button
    private JButton downloadfield; //download field info button
    private JButton listws; //list all the ws
    private JLabel label;

    private int counter=0; //the counter is used to name the files: user 1's file will have _1 at the name's end and user 2's file will have _2.

    //constructors
    public UserClient() {

        listws.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int size;
                size = Server.getWeather_stations().size();
                Server.getWeather_stations().size();
                if(size==0){
                    String message="There are no Weather Stations!";
                    JOptionPane.showMessageDialog(null,message);
                }
                else{
                String message="";
                for (int i=1;i<=size;i++){ //adding each weather staion in the display GUI
                    message=message+"Weather Station "+String.valueOf(i)+"\n";
                }
                JOptionPane.showMessageDialog(null,message);}
            }
        });

        displayfield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Random r = new Random();
                int area = r.nextInt(90)+10; //generate a random number between 10 and 100 for the area
                String crop=getCrop(); //generate with the function a random crop
                String message="Area: "+String.valueOf(area)+" hectares\n"+"Current crop: "+crop;
                JOptionPane.showMessageDialog(null,message);

            }
        });

        downloadfield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Random r = new Random();
                int area = r.nextInt(90)+10;//random area
                String crop=getCrop(); //random crop

                String filename="Field_description_"+String.valueOf(Server.getClients1().size())+".csv"; //setting the file's name
                String path="C:\\Users\\Mircea\\Desktop\\Mirchales\\FIVERR\\WeatherStation\\"+filename; //setting the path
                File file = new File(path); //we make the file and delete it's content
                file.delete();
                if (file.length() == 0) { //if the file is empty, we insert the header using FileWriter
                    try {


                        FileWriter csvWriter = new FileWriter(filename);
                        csvWriter.append("Area (HA)");
                        csvWriter.append(",");
                        csvWriter.append("Current Crop");
                        csvWriter.append("\n");

                        csvWriter.flush();
                        csvWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                try (FileWriter csvWriter = new FileWriter(filename, true)) { //adding the area and crop to the file
                    csvWriter.append(String.valueOf(area));
                    csvWriter.append(",");
                    csvWriter.append(crop);
                    csvWriter.append("\n");

                    csvWriter.flush();
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String message=filename+"  downloaded!";
                JOptionPane.showMessageDialog(null,message);


            }
        });

        displayws.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int value = (Integer) spinner.getValue();


                if(Server.getNws()==0){                             //if there is no ws, error
                    String message="There are no Weather Stations";
                    JOptionPane.showMessageDialog(null,message);
                }
                else if(value>Server.getNws() || value<=0){   //wrong index, error
                    String message="Wrong ID. Choose a number from 1 to "+String.valueOf(Server.getNws());
                    JOptionPane.showMessageDialog(null,message);
                }
                else{ //display the ws's info
                    String message="Weather Station "+String.valueOf(value)+" data:\n"+"GPS Positioning: "+String.valueOf(Server.getWeather_stations().get(value-1).getLatitude())+", "+String.valueOf(Server.getWeather_stations().get(value-1).getLongitude())+"\n"+"Temperature: "+String.valueOf(Server.getWeather_stations().get(value-1).getTemperature())+" °C"+"\n"+"Barometric pressure: "+String.valueOf(Server.getWeather_stations().get(value-1).getBarometric_pressure())+" mbar"+"\n"+"Relative humidity: "+String.valueOf(Server.getWeather_stations().get(value-1).getRelative_humidity())+" %"+"\n"+"Wind force: "+String.valueOf(Server.getWeather_stations().get(value-1).getWind_force())+" km/h";
                    JOptionPane.showMessageDialog(null,message);
                }
            }
        });

        downloadws.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int value = (Integer) spinner.getValue();


                if(Server.getNws()==0){  //same error checks
                    String message="There are no Weather Stations";
                    JOptionPane.showMessageDialog(null,message);
                }
                else if(value>Server.getNws() || value<=0){
                    String message="Wrong ID. Choose a number from 1 to "+String.valueOf(Server.getNws());
                    JOptionPane.showMessageDialog(null,message);
                }
                else{
                    String filename="Weather_info_"+String.valueOf(Server.getClients1().size())+".csv";    //same file editing
                    String path="C:\\Users\\Mircea\\Desktop\\Mirchales\\FIVERR\\WeatherStation\\"+filename;
                    File file = new File(path);
                    file.delete();
                    if (file.length() == 0) {
                        try {


                            FileWriter csvWriter = new FileWriter(filename);  //adding the header
                            csvWriter.append("Latitude");
                            csvWriter.append(",");
                            csvWriter.append("Longitude");
                            csvWriter.append(",");
                            csvWriter.append("Temperature (celsius degrees)");
                            csvWriter.append(",");
                            csvWriter.append("Barometric pressure (mbar)");
                            csvWriter.append(",");
                            csvWriter.append("Relative humidity (%)");
                            csvWriter.append(",");
                            csvWriter.append("Wind force (km/h)");
                            csvWriter.append("\n");

                            csvWriter.flush();
                            csvWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    try (FileWriter csvWriter = new FileWriter(filename, true)) { //adding data
                        csvWriter.append(String.valueOf(Server.getWeather_stations().get(value-1).getLatitude()));
                        csvWriter.append(",");
                        csvWriter.append(String.valueOf(Server.getWeather_stations().get(value-1).getLongitude()));
                        csvWriter.append(",");
                        csvWriter.append(String.valueOf(Server.getWeather_stations().get(value-1).getTemperature()));
                        csvWriter.append(",");
                        csvWriter.append(String.valueOf(Server.getWeather_stations().get(value-1).getBarometric_pressure()));
                        csvWriter.append(",");
                        csvWriter.append(String.valueOf(Server.getWeather_stations().get(value-1).getRelative_humidity()));
                        csvWriter.append(",");
                        csvWriter.append(String.valueOf(Server.getWeather_stations().get(value-1).getWind_force()));
                        csvWriter.append("\n");

                        csvWriter.flush();
                        csvWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String message=filename+"  downloaded!";
                    JOptionPane.showMessageDialog(null,message);
                }
            }
        });

    }


    //server connection data
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9091;

    public static void main(String[] args) throws IOException {
        Socket socket =new Socket(SERVER_IP,SERVER_PORT);

        //useless code
        BufferedReader input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
        String serverResponse = input.readLine();
        System.out.println("[SERVER] "+serverResponse);

        socket.close();
        System.exit(0);
    }

    //setters and getters
    public JPanel getUcpanel() {
        return ucpanel;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    //the function that generates a random crop from the list. you can add more if you want
    protected static String getCrop() {
        String[] crops={"Rice","Wheat","Maize","Cotton","Common bean"};
        String crop=crops[(int) (Math.random()*crops.length)];
        return crop;
    }
}
