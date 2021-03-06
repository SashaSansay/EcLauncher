package Components;

import Util.Utils;
import Workers.PollWorker;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import Listeners.EcButtonServerListener;

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

    public EcServerButton(type type,int x,int y,int sizeX,int sizeY,EcLauncher l) throws Exception {
        this.type=type;
        this.x=x;
        this.y=y;
        this.sizeX=sizeX;
        this.sizeY=sizeY;
        this.l=l;
        this.setBounds(x,y,sizeX,sizeY);
        this.addActionListener(new EcButtonServerListener(l));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        new PollWorker(this).startBg();
    }

    public void updateInfoAndRepaint(int progressWidth,String online){
        this.progressWidth=progressWidth;
        this.online=online;
        this.repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.setBorderPainted(false);
        String name="/images/";
        String text = "";
        switch (type){
            case AQUILLA: name+="aquilla_bg.png"; port=25565; text="Aquilla";break;
        }
        if(!online.equals("")){
            g2.drawImage(new ImageIcon(Utils.getLocalImage(name,this)).getImage(), 0, 0, null);
            g2.drawImage(new ImageIcon(Utils.getLocalImage("/images/aquilla_progress.png",this)).getImage(), 3, 3,progressWidth, 48, null);
        }else{
            g2.drawImage(new ImageIcon(Utils.getLocalImage("/images/offline_bg.png",this)).getImage(), 0, 0, null);
        }
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,this.getClass().getResourceAsStream("/font/Nautilus.ttf"));
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
