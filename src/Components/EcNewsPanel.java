package Components;

import Util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class EcNewsPanel extends JPanel {

    private String[] titles;
    private String[] descriptions;
    private String[] links;

    public EcNewsPanel(int x,int y,int width,int height){
        this.setBounds(x,y,width,height);
        titles=null;
        descriptions=null;
        links=null;
    }

    public void setItems(String[] titles,String[] descriptions,String[] links){
        this.titles=titles;
        this.descriptions=descriptions;
        this.links=links;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(new ImageIcon(Utils.getLocalImage("/images/news_bg.png",this)).getImage(), 0, 0, null);
        Font font=null;
        Font font2=null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/font/Nautilus.ttf"));
            font2 = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/font/LucidaSansRegular.ttf"));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(titles!=null&&titles.length!=0){
            int size = titles.length;
            int k=50;
            font = font.deriveFont(20.0f);
            font2 = font2.deriveFont(14.0f);
            size = (size>3) ? 3 : size;
            for(int i=0;i<size;i++){
                g2.setFont(font);
                g2.setColor(Color.decode("#434343"));
                g2.drawString(titles[i],30,k);
                g2.setFont(font2);
                g2.setColor(Color.decode("#606060"));
                g2.drawString(descriptions[i],40,k+20);
                k+=70;
            }
        }

    }
}
