package Listeners;

import Components.EcButton;
import Components.EcLauncher;
import Components.type;
import Util.UpdateThread;
import Util.Utils;

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
            case CLOSE: l.setVisible(false); System.exit(0); break;
            case MINIMIZE: l.setState(JFrame.ICONIFIED); break;
            case UPDATE: if(btn.isEnabled()) new UpdateThread(l.getBar(),btn,l.getAquilla()).start();break;
            default: break;
        }
    }
}
