import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class EcButtonServerListener implements ActionListener {

    private EcLauncher l;

    public EcButtonServerListener(EcLauncher l) {
        this.l = l;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Utils.playSound("click.wav");
        try {
            String version="";
            switch (((EcServerButton)actionEvent.getSource()).getType()){
                case AQUILLA: version="aquilla";break;
                default: version="aquilla";break;
            }
            Utils.startMinecraft(l.getMemory(),version);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
