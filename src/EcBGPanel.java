import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class EcBGPanel extends JPanel {

    private EcLauncher l;

    public EcBGPanel(EcLauncher l){
        this.l=l;
        //EcButtonActionListener listener = new EcButtonActionListener(l);
        this.setLayout(null);
        EcButton closeBtn = new EcButton(type.CLOSE,810,10,10,10,l);
        EcButton minimizeBtn = new EcButton(type.MINIMIZE,790,10,15,10,l);
        EcButton updateBtn = new EcButton(type.UPDATE,37,81,348,58,l);
        EcServerButton aquillaBtn = new EcServerButton(type.AQUILLA,34,149,354,54,l);
        this.add(aquillaBtn);
        this.add(updateBtn);
        this.add(minimizeBtn);
        this.add(closeBtn);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        ImageIcon img = new ImageIcon("images/background.png");
        g.drawImage(img.getImage(), 0, 0, null);
    }
}
