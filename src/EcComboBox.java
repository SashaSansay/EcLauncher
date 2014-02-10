import javax.swing.*;

public class EcComboBox extends JComboBox {
    public EcComboBox(String[] str,int x, int y, int width,int height){
        super(str);
        this.setBounds(x,y,width,height);
    }
}
