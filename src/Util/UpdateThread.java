package Util;

import Components.EcProrgressBar;

import java.io.File;
import java.util.Arrays;

public class UpdateThread extends Thread {

    private EcProrgressBar bar;

    public UpdateThread(EcProrgressBar bar){
        this.bar=bar;
    }

    public void run()
    {
        String answer = Utils.getInfo("hashes");
        //String normalizeAnswer=answer.replaceAll("/", File.separator);
        //System.out.println(normalizeAnswer);
        if(answer.isEmpty()) return;
        System.out.println(answer);
        String[] hashes=answer.split(";");
        String[] thisHashes=Utils.getMD5Mods().split(";");
        for(int i=0;i<thisHashes.length/2;i++){
            if(Arrays.asList().contains(""))
        }
        bar.setMaximum(100);
        for(int i = 0; i<100;i++){
            bar.setValue(i);
            bar.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
