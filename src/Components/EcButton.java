package Components;

import Listeners.EcButtonActionListener;
import Util.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class EcButton extends JButton{

    private type type;
    private int x;
    private int y;
    private int sizeX;
    private int sizeY;
    private String text;
    private String text2;
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
        text="";
        text2="";
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
        switch (type){
            case CLOSE: name+="close.png";break;
            case MINIMIZE: name+="minimize.png";break;
            case UPDATE: name+="update_bg.png";break;
        }
        g2.drawImage(new ImageIcon(Utils.getLocalImage(name,this)).getImage(), 0, 0, null);
        if(type== type.UPDATE){
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT,this.getClass().getResourceAsStream("/font/MyriadPro-Regular.otf"));
                font = font.deriveFont(17.0f);
                g2.setColor(Color.decode("#606060"));
                g2.setFont(font);
                String version = Utils.getInfo("launcher");
                if(!version.equals(String.valueOf(l.getVersion()))){
                    if(version.equals("error")){
                        text="Нет связи с сервером";
                        text2="Проверьте ваше соединение";
                    }else{
                        text="Доступно обновление лаучнера!";
                        text2 = "Загрузить сейчас!";
                    }
                }
                if(text.equals("")) text = "Обновления не найдены!";
                FontMetrics fontMetrics = g.getFontMetrics(font);
                //if(fontMetrics.charsWidth(text.toCharArray(),0,text.length())>335) text=text
                g2.drawString(text,348/2-fontMetrics.charsWidth(text.toCharArray(),0,text.length())/2,20);
                if(!text2.equals("")){
                    g2.setColor(Color.decode("#2E8118"));
                    font = font.deriveFont(15.0f);
                    g2.setFont(font);
                    g2.drawString(text2,348/2-fontMetrics.charsWidth(text2.toCharArray(),0,text2.length())/2,40);
                }//348
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

    public void setText(String str){
        this.text=str;
    }

    public void setText(String str,String str2){
        this.text=str;
        this.text2=str2;
    }

    public void setText2(String str){
        this.text2=str;
    }
}
