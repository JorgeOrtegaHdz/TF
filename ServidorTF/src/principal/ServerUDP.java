package principal;

import java.net.*;
import java.io.*;

public class ServerUDP {
    private PanelDibujo pd;
    
    public ServerUDP(PanelDibujo Workarea){
        pd=Workarea;
    }
    
    public void comienzaServicio(){
        DatagramSocket socket;
        try {
            //Creamos el socket
            socket = new DatagramSocket(6000); 
            byte[] mensaje_bytes = new byte[256];
            String mensaje ="";
            mensaje = new String(mensaje_bytes);
            DatagramPacket paquete = new DatagramPacket(mensaje_bytes,256);
            int puerto, pos;
            double x,y;
            InetAddress address;
            // Recibimos el paquete
            socket.receive(paquete);
            // Lo formateamos
            mensaje = new String(mensaje_bytes).trim();
            //Obtenemos IP Y PUERTO
            puerto = paquete.getPort();
            address = paquete.getAddress();
            //System.out.println(" puerto: "+ puerto+" ip: "+address);
            String[] cords = mensaje.split("/");
            pos=Integer.parseInt(cords[0]);
            x = Double.parseDouble(cords[1]);
            
            y = Double.parseDouble(cords[2]);
            pd.a[pos]=(int)y;
            pd.x[pos]=(int)x;
            pd.actualizados=(int)pos;
            //System.out.println("pos="+pos+"  x: "+ (int)x*pd.escalaX+"y: "+(int)y*pd.escalaY);
            socket.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}