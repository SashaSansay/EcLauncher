import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EcServerButton extends JButton{
    private type type;
    private int x;
    private int y;
    private int sizeX;
    private int sizeY;
    private EcLauncher l;
    private int max;
    private int current;
    private int port;
    private int progressWidth;
    private String online;

    public EcServerButton(type type,int x,int y,int sizeX,int sizeY,EcLauncher l){
        this.type=type;
        this.x=x;
        this.y=y;
        this.sizeX=sizeX;
        this.sizeY=sizeY;
        this.l=l;
        this.setBounds(x,y,sizeX,sizeY);
        this.addActionListener(new EcButtonServerListener(l));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        switch (type){
            case AQUILLA: port=25565;
        }
        String[] res = Utils.pollServer("craft.ec",port);
        if(res[0]!=null){
            progressWidth = (int) ((348.0f/100.00f)*(Float.valueOf(res[1])*100.0f/Float.valueOf(res[2])));
            online=res[1]+"/"+res[2];
        }else{
            online="";
        }

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
        String text = "";
        switch (type){
            case AQUILLA: name+="aquilla_bg.png"; port=25565; text="Aquilla";break;
        }
        if(!online.equals("")){
            g2.drawImage(new ImageIcon(name).getImage(), 0, 0, null);
            g2.drawImage(new ImageIcon("images/aquilla_progress.png").getImage(), 3, 3,progressWidth, 48, null);
        }else{
            g2.drawImage(new ImageIcon("images/offline_bg.png").getImage(), 0, 0, null);
        }
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,new File("font/Nautilus.ttf"));
            font = font.deriveFont(25.0f);
            g2.setColor(Color.decode("#007180"));
            g2.setFont(font);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            int textWidth = fontMetrics.charsWidth(text.toCharArray(),0,text.length());
            g2.drawString(text,354/2-textWidth/2+1,29+1);
            g2.setColor(Color.WHITE);
            g2.drawString(text,354/2-textWidth/2,29);
            font=font.deriveFont(12.0f);
            if(!online.equals("")){
                g2.setFont(font);
                g2.setColor(Color.decode("#007180"));
                g2.drawString(online,354/2-fontMetrics.charsWidth(online.toCharArray(),0,online.length())/2+15+1,43+1);
                g2.setColor(Color.WHITE);
                g2.drawString(online,354/2-fontMetrics.charsWidth(online.toCharArray(),0,online.length())/2+15,43);
            }
            else{
                g2.setFont(font);
                g2.setColor(Color.decode("#007180"));
                g2.drawString("Offline",354/2-fontMetrics.charsWidth("Offline".toCharArray(),0,"Offline".length())/2+25+1,43+1);
                g2.setColor(Color.WHITE);
                g2.drawString("Offline",354/2-fontMetrics.charsWidth("Offline".toCharArray(),0,"Offline".length())/2+25,43);
            }
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.setColor(Color.WHITE);
    }

    public type getType() {
        return type;
    }
}
