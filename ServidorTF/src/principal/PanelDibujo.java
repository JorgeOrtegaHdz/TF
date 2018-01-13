package principal;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Graphics;

public class PanelDibujo extends JPanel implements Runnable{
    public int altura,ancho,origen[]=new int[2];
    public int escalaX=200,escalaY=200;
    public int factor=5;
    public int a[]=new int[1000];
    public int x[]=new int[1000];
    Thread conexion;
    public int actualizados;
    
    public PanelDibujo(){
        conexion=new Thread(this);
        conexion.start();
        borraBuffer();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ancho=getWidth();
        altura=getHeight();
        origen[0]=ancho/2;
        origen[1]=altura/2;
        dibujaEjes(g);
        //creaFuncion(10);
        int i=0;
        while(x[i]<ancho && a[i]<altura){
            //g.drawLine(origen[0]+x[i],origen[1]-a[i],origen[0]+x[i+1],origen[1]-a[i+1]);
            //g.drawLine(origen[0]-x[i],origen[1]-a[i],origen[0]-x[i+1],origen[1]-a[i+1]);
            if(x[i]>=0){
                g.drawLine(origen[0]+x[i],origen[1]-a[i],origen[0]+x[i+1],origen[1]-a[i+1]);
            }else{
                g.drawLine(origen[0]+x[i],origen[1]-a[i],origen[0]+x[i+1],origen[1]-a[i+1]);
                System.out.println("x="+(origen[0]+x[i])/escalaX+"y="+(origen[1]-a[i])/escalaY);
            }
            i++;
        }
    }
    private void dibujaEjes(Graphics g){
        int j=0;
        //Asigno color rojo al pintar los ejes
        g.setColor(Color.red);
        //Eje y
        g.drawLine(ancho/2, 0, ancho/2, altura);
        g.drawLine(ancho/2,0,(ancho/2)-10,10);
        g.drawLine(ancho/2,0,(ancho/2)+10,10);
        //Eje x
        g.drawLine(0, altura/2, ancho, altura/2);
        g.drawLine(ancho-10, (altura/2)-10, ancho, altura/2);
        g.drawLine(ancho-10, (altura/2)+10, ancho, altura/2);
        //Etiquetas
        g.setColor(Color.black);
        g.drawString("X", ancho-10, altura/2+30);
        g.drawString("Y", ancho/2+30, 20);
        //Escala eje x
        for(int i=origen[0];i<=origen[0]*2;i+=escalaX){
            g.drawLine(i,origen[1]-1,i,origen[1]+1);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString(""+j, i, (altura/2)+10);
            j++;
        }
        j=0;
        for(int i=origen[0];i>=0;i-=escalaX){
            g.drawLine(i,origen[1]-1,i,origen[1]+1);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString(""+j, i, (altura/2)+10);
            j++;
        }
        //Escala eje y
        j=1;
        for(int i=origen[1]+escalaY;i<=origen[1]*2;i+=escalaY){
            g.drawLine(origen[0]-1,i,origen[0]+1,i);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString(""+j, (ancho/2)-10, i);
            j++;
        }
        j=1;
        for(int i=origen[1]-escalaY;i>=0;i-=escalaY){
            g.drawLine(origen[0]-1,i,origen[0]+1,i);
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString(""+j, (ancho/2)+10, i);
            j++;
        }
    }
    
    public float eleva(float num,int exp){
        for(int i=0;i<exp-1;i++){
            num*=num;
        }
        return num;
    }
    
    private void borraBuffer(){
        int k=0,h=0;
        for(int i=0;i<1000;i++){
            a[i]=k;
            x[i]=h;
            h+=factor;
        }
    }
    
    private void creaFuncion(int fin){
        float j=0;
        for(int i=0;i<fin;i++){
            a[i]=(int)(eleva(j/escalaX,2)*escalaY);
            x[i]=(int)j;
            j+=factor;
            if(x[i]%escalaX==0){
                System.out.println("a["+i+"]="+a[i]/escalaY+"  ,  "+"x="+x[i]/escalaX);
            }
        }
    }

    @Override
    public void run() {
        try{
            /*for(int i=0;i<100;i++){
                creaFuncion(i);
                repaint();
                Thread.sleep(500);
            }*/
            for(;;){
                ServerUDP s=new ServerUDP(this);
                s.comienzaServicio();
                repaint();
                //Thread.sleep(500);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
