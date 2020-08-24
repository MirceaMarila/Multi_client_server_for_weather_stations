package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WeatherStationClient {
    //interface data
    private JPanel wscpanel;
    private JTextField lont; //text field for longitude
    private JTextField latt; //text field for latitude
    private JTextField temt; //text field for temperature
    private JTextField bart; //text field for barometric pressure
    private JTextField humt; //text field for humidity
    private JTextField wint; //text field for wind force
    private JLabel lon; //labels
    private JLabel lat;
    private JLabel tem;
    private JLabel bar;
    private JLabel hum;
    private JLabel win;
    private JLabel wsid;
    private JButton submit; //the submit button
    private JSpinner spinner; //the spinner that selects the number of the weather station that we want to modify its data


    //interface elements programmig in constructor
    public WeatherStationClient(){
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int value = (Integer) spinner.getValue(); //get the value of the spinner


                if(Server.getNws()==0){  //if there are no ws, error
                    String message="There are no Weather Stations";
                    JOptionPane.showMessageDialog(null,message);
                }
                else if(value>Server.getNws() || value<=0){ //if the number is incorrect, error
                    String message="Wrong ID. Choose a number from 1 to "+String.valueOf(Server.getNws());
                    JOptionPane.showMessageDialog(null,message);
                }
                else if(lont.getText().isEmpty()||latt.getText().isEmpty()||temt.getText().isEmpty()||bart.getText().isEmpty()||humt.getText().isEmpty()||wint.getText().isEmpty()){
                    String message="Please complete each field!"; //if at least one field is empty, error
                    JOptionPane.showMessageDialog(null,message);
                }
                else {
                    String message="Done!";
                    JOptionPane.showMessageDialog(null,message);

                    //now we update the selected weather station's data
                    Server.getWeather_stations().get(value-1).setLongitude(Double.parseDouble(lont.getText()));
                    Server.getWeather_stations().get(value-1).setLatitude(Double.parseDouble(latt.getText()));
                    Server.getWeather_stations().get(value-1).setTemperature(Double.parseDouble(temt.getText()));
                    Server.getWeather_stations().get(value-1).setBarometric_pressure(Double.parseDouble(bart.getText()));
                    Server.getWeather_stations().get(value-1).setRelative_humidity(Double.parseDouble(humt.getText()));
                    Server.getWeather_stations().get(value-1).setWind_force(Double.parseDouble(humt.getText()));


                }



            }
        });

    }


    //server connection data
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9090;

    public static void main(String[] args) throws IOException {
        Socket socket =new Socket(SERVER_IP,SERVER_PORT);

        //useless variables again, but essential for the compilator
        BufferedReader input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader keyboard=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
        out.println(""); // the server's variable "input" receives this "out" because the PrintWriter sends the String and the BufferedReader receives it.
        String serverResponse = input.readLine();
        System.out.println("[SERVER] "+serverResponse);
        //end of useless code

        socket.close();
        System.exit(0);
    }

    public JPanel getWscpanel() {
        return wscpanel;
    }
}
