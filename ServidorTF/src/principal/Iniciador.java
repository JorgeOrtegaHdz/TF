package principal;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//Este es el principal el que inicia el graficador y se hizo en netbeans

public class Iniciador {
    public static void main(String[] args) {
        PanelDibujo panel=new PanelDibujo();
        JFrame aplicacion=new JFrame();
        aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aplicacion.add(panel);
        aplicacion.setSize(1280, 760);
        //aplicacion.setSize(4000, 600);
        aplicacion.setVisible(true);
    }
    
}
