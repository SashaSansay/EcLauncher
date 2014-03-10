package Util;

import Components.EcNewsPanel;

import java.io.IOException;

public class NewsThread extends Thread {
    private EcNewsPanel pan;

    public NewsThread(EcNewsPanel pan){
        this.pan=pan;
    }

    public void run()
    {
        try {
            NewsUtils.getNews(pan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
