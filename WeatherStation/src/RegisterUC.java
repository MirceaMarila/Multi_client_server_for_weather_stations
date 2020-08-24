import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RegisterUC {    //this class is auxiliary and is used as a GUI for the registration part
    private JTextField namet;
    private JTextField passt;
    private JButton register;
    private JLabel name;
    private JLabel password;
    private JPanel regpanel;


    public RegisterUC(){
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(namet.getText().isEmpty()||passt.getText().isEmpty()){   //if one field is empty, error
                    String message="Please complete each field!";
                    JOptionPane.showMessageDialog(null,message);
                }
                else {


                    File file = new File("C:\\Users\\Mircea\\Desktop\\Mirchales\\FIVERR\\WeatherStation\\User_DB.csv"); //here we create the csv databse

                    if (check_duplicate(namet.getText(), passt.getText()) == 1) { //prevent duplicates
                        String message = "Already in database!";
                        JOptionPane.showMessageDialog(null, message);
                    } else {

                        if (file.length() == 0) {
                            try {


                                FileWriter csvWriter = new FileWriter("User_DB.csv");
                                csvWriter.append("Name");
                                csvWriter.append(",");              //set header
                                csvWriter.append("Password");
                                csvWriter.append("\n");

                                csvWriter.flush();
                                csvWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                        try (FileWriter csvWriter = new FileWriter("User_DB.csv", true)) {
                            csvWriter.append(namet.getText());
                            csvWriter.append(",");
                            csvWriter.append(passt.getText());   //add users to the csv file
                            csvWriter.append("\n");

                            csvWriter.flush();
                            csvWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String message = "Registered!";
                        JOptionPane.showMessageDialog(null, message);

                    }
                }
            }
        });
    }

    //panel getter
    public JPanel getRegpanel() {
        return regpanel;
    }

    //fucntion to check if someone with that name and password is already in the database
    public int check_duplicate(String name, String password){
        String csvFile = "C:\\Users\\Mircea\\Desktop\\Mirchales\\FIVERR\\WeatherStation\\User_DB.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int flag=0;

        try {

            br = new BufferedReader(new FileReader(csvFile)); //open the csv file
            while ((line = br.readLine()) != null) {  //read every line

                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                if(data[0].equals(name)&&data[1].equals(password)){
                    flag=1;  //if the name and password are already in the databse, set flag to 1
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
}
