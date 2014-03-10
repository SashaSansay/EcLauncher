package Components;

import Util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class EcTextField extends JTextField{
    private Font font;
    private EcTextField text;

    public EcTextField(String text,int x, int y,int width,int height){
        //super(text);
        this.setText(text);
        setBounds(x,y,width,height);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/font/LucidaSansRegular.ttf"));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setEditable(false);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                char c = keyEvent.getKeyChar();
                EcTextField f = (EcTextField)keyEvent.getSource();
                if((c==8)&&(!f.getText().equals("")))f.setText(f.getText().substring(0, f.getText().length()-1));
                if((c>47)&&(c<58)) f.setText(f.getText()+c);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBorder(null);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(new ImageIcon(Utils.getLocalImage("/images/memory_bg.png",this)).getImage(),0,0,null);
        font = font.deriveFont(11.0f);
        String text = getText();
        FontMetrics fontMetrics = g.getFontMetrics(font);
        //int textWidth = fontMetrics.charsWidth(text.toCharArray(),0,text.length());
        g2.setColor(Color.decode("#363636"));
        g2.setFont(font);
        g2.drawString(text,5,17);
        /*Caret caret = this.getCaret();
        if(caret.isVisible()){
            this.Care
        } */
    }
}
