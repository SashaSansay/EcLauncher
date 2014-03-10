package Components;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import Listeners.*;
import Util.NewsThread;
import Util.UpdateThread;
import Util.UpdateUtils;
import Util.Utils;
import Workers.UpdateWorker;

public class EcBGPanel extends JPanel {

    private EcLauncher l;
    private EcTextField memory;
    private Font font;
    private EcProrgressBar bar;
    private EcServerButton aquillaBtn;
    private EcNewsPanel newsPanel;

    public EcBGPanel(EcLauncher l) throws Exception {
        this.l=l;
        this.setLayout(null);
        EcButton closeBtn = new EcButton(type.CLOSE,810,10,10,10,l);
        EcButton minimizeBtn = new EcButton(type.MINIMIZE,790,10,15,10,l);
        EcButton updateBtn = new EcButton(type.UPDATE,37,81,348,58,l);
        aquillaBtn = new EcServerButton(type.AQUILLA,34,149,354,54,l);
        memory = new EcTextField("1024",35,438,274,25);
        font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/font/Nautilus.ttf"));
        bar = new EcProrgressBar(527,457,184,6);
        newsPanel = new EcNewsPanel(425,61,387,416);
        new UpdateThread(bar,updateBtn,aquillaBtn).start();
        new NewsThread(newsPanel).start();
        bar.setPaint(true);
        this.add(newsPanel);
        this.add(memory);
        this.add(aquillaBtn);
        this.add(updateBtn);
        this.add(minimizeBtn);
        this.add(closeBtn);
        this.add(bar);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon img = new ImageIcon(Utils.getLocalImage("/images/background.png",this));
        g2.drawImage(img.getImage(), 0, 0, null);
        font = font.deriveFont(19.0f);
        String text = "craft.ec";
        FontMetrics fontMetrics = g.getFontMetrics(font);
        int textWidth = fontMetrics.charsWidth(text.toCharArray(),0,text.length());
        g2.setColor(Color.decode("#363636"));
        g2.setFont(font);
        g2.drawString("craft.ec",830/2-textWidth/2,23);
        g2.drawString("mb",315,455);

    }

    public String getMemory(){
        return memory.getText();
    }

    public EcProrgressBar getBar(){
        return this.bar;
    }

    public EcServerButton getAquilla(){
        return aquillaBtn;
    }
}
