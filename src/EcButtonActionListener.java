import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcButtonActionListener implements ActionListener {

    private EcLauncher l;

    public EcButtonActionListener(EcLauncher l){
        this.l=l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Utils.playSound("click.wav");
        EcButton btn = (EcButton) e.getSource();
        type type = btn.getType();
        switch (type){
            case CLOSE: l.setVisible(false); l.dispose(); break;
            case MINIMIZE: l.setState(JFrame.ICONIFIED); break;
            case UPDATE: break;
            default: break;
        }
    }
}
