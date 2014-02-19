package Util;

import Components.EcProrgressBar;

public class UpdateThread extends Thread {

    private EcProrgressBar bar;

    public UpdateThread(EcProrgressBar bar){
        this.bar=bar;
    }

    public void run()
    {
        bar.setMaximum(100);
        for(int i = 0; i<100;i++){
            bar.setValue(i);
            bar.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

}
