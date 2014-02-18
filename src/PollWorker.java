import javax.swing.*;

public class PollWorker extends SwingWorker<Integer, Integer>{

    private EcServerButton btn;

    public PollWorker(EcServerButton btn){
        this.btn=btn;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        int port = 0;
        switch (btn.getType()){
            case AQUILLA: port = 25565;break;
            default: port = 25565; break;
        }
        String[] res = Utils.pollServer("craft.ec",port);
        String online;
        int progressWidth = 0;
        if(res[0]!=null){
            progressWidth = (int) ((348.0f / 100.00f) * (Float.valueOf(res[1]) * 100.0f / Float.valueOf(res[2])));
            online=res[1]+"/"+res[2];
        }else{
            online="";
        }
        btn.updateInfoAndRepaint(progressWidth,online);
        return null;
    }
}
