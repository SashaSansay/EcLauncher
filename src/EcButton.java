import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class EcButton extends JButton{

    private type type;
    private int x;
    private int y;
    private int sizeX;
    private int sizeY;
    private EcLauncher l;

    public EcButton(type type,int x,int y,int sizeX,int sizeY,EcLauncher l){
        this.type=type;
        this.x=x;
        this.y=y;
        this.sizeX=sizeX;
        this.sizeY=sizeY;
        this.l=l;
        this.setBounds(x,y,sizeX,sizeY);
        this.addActionListener(new EcButtonActionListener(l));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.setBorderPainted(false);
        String name="images/";
        switch (type){
            case CLOSE: name+="close.png";break;
            case MINIMIZE: name+="minimize.png";break;
            case UPDATE: name+="update_bg.png";break;
        }
        g2.drawImage(new ImageIcon(name).getImage(), 0, 0, null);
        if(type== type.UPDATE){
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT,new File("font/MyriadPro-Regular.otf"));
                font = font.deriveFont(17.0f);
                g2.setColor(Color.decode("#606060"));
                g2.setFont(font);
                String text = "Доступно обновление!";
                if(!Utils.getInfo("launcher").equals(String.valueOf(l.getVersion()))) text="Доступно обновление лаучнера!";
                String text2 = "Загрузить сейчас!";
                FontMetrics fontMetrics = g.getFontMetrics(font);
                g2.drawString(text,348/2-fontMetrics.charsWidth(text.toCharArray(),0,text.length())/2,20);
                g2.setColor(Color.decode("#2E8118"));
                font = font.deriveFont(15.0f);
                g2.setFont(font );
                g2.drawString(text2,348/2-fontMetrics.charsWidth(text2.toCharArray(),0,text2.length())/2,40);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public type getType() {
        return type;
    }
}
