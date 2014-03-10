package Components;

import Util.Utils;

import javax.swing.*;
import java.awt.*;

public class EcProrgressBar extends JProgressBar {
    private boolean paint;

    public EcProrgressBar(int x, int y, int width,int height){
        this.setBorder(null);
        this.setBounds(x,y,width,height);
        this.paint=false;
        this.setMinimum(0);
    }

    public void setPaint(boolean b){
        this.paint=b;
    }

    public boolean getPaint(){
        return paint;
    }

    public void updateInfoAndRepaint(int current){
        this.setValue(current);
        this.repaint();
    }

    public void paintComponent(Graphics g)
    {
        if(!paint)return;
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.setBorderPainted(false);
        int value = (int)(getPercentComplete()*100);
        g2.drawImage(new ImageIcon(Utils.getLocalImage("/images/progressBar_bg.png",this)).getImage(), 0, 0, null);
        if(value>0){
            g2.drawImage(new ImageIcon(Utils.getLocalImage("/images/pb_b_l.png",this)).getImage(), 0, 0, null);
            double barWidth=(double)getWidth()/100*value;
            Image barBg = new ImageIcon(Utils.getLocalImage("/images/pb_b.png",this)).getImage();
            for (int i = 0;i<barWidth;i+=4) g2.drawImage(barBg,i+2,0,null);
            if (barWidth>(double)182) g2.drawImage(new ImageIcon(Utils.getLocalImage("/images/pb_b_r.png",this)).getImage(), getWidth()-2, 0, null);
        }
    }
}
