package Util;

import Components.EcButton;
import Components.EcProrgressBar;
import Components.EcServerButton;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class UpdateThread extends Thread {

    private EcProrgressBar bar;
    private EcButton btn;
    private EcServerButton aquilla;

    public UpdateThread(EcProrgressBar bar,EcButton btn,EcServerButton aquillaBtn){
        this.bar=bar;
        this.btn=btn;
        this.aquilla=aquillaBtn;
        btn.setText("test");
        btn.repaint();
    }

    public void run()
    {
        try {
            UpdateUtils.getFilesHashAndUpdate(bar,btn,aquilla);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
