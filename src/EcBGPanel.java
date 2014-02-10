import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EcBGPanel extends JPanel {

    private EcLauncher l;
    private String[] memoryList = {"128","256","512","1024","2048"};

    public EcBGPanel(EcLauncher l){
        this.l=l;
        //EcButtonActionListener listener = new EcButtonActionListener(l);
        this.setLayout(null);
        EcButton closeBtn = new EcButton(type.CLOSE,810,10,10,10,l);
        EcButton minimizeBtn = new EcButton(type.MINIMIZE,790,10,15,10,l);
        EcButton updateBtn = new EcButton(type.UPDATE,37,81,348,58,l);
        EcServerButton aquillaBtn = new EcServerButton(type.AQUILLA,34,149,354,54,l);
        //EcComboBox memoryBox = new EcComboBox(memoryList,20,400,100,30);
        EcTextField memory = new EcTextField("1024",35,438,352,25);
        this.add(memory);
        //this.add(memoryBox);
        this.add(aquillaBtn);
        this.add(updateBtn);
        this.add(minimizeBtn);
        this.add(closeBtn);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon img = new ImageIcon("images/background.png");
        g2.drawImage(img.getImage(), 0, 0, null);
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("font/Nautilus.ttf"));
            font = font.deriveFont(19.0f);
            String text = "craft.ec";
            FontMetrics fontMetrics = g.getFontMetrics(font);
            int textWidth = fontMetrics.charsWidth(text.toCharArray(),0,text.length());
            g2.setColor(Color.decode("#363636"));
            g2.setFont(font);
            g2.drawString("craft.ec",830/2-textWidth/2,23);

        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
