package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

public class WSClientHandler implements Runnable { //this class is useless for the project, but it wont run without it because the multi client - sevrer communication works both ways
                                                    //and there must be some bufferedreaders and printwriters
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    public WSClientHandler(Socket clientSocket) throws IOException {
        this.client=clientSocket;
        in=new BufferedReader(new InputStreamReader(client.getInputStream()));
        out=new PrintWriter(client.getOutputStream(),true);
    }


    @Override
    public void run() {

        try {
            while (true) {
                String request = in.readLine();
                    out.println(Server.useless_function());

            }
        } catch (IOException e) {
            System.err.println("IO exception in client handler");
            System.err.println(e.getStackTrace());
        } finally {
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
