package Workers;

import Components.EcProrgressBar;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class UpdateWorker extends SwingWorker<Integer, Integer>{
    EcProrgressBar bar;

    public UpdateWorker(EcProrgressBar bar){
        this.bar=bar;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        bar.setMaximum(100);
        for(int i=0;i<5;i++){
            bar.updateInfoAndRepaint(i);
            TimeUnit.MILLISECONDS.sleep(300);
        }
        return null;
    }

    public void startBg() throws Exception {
        doInBackground();

    }
}
