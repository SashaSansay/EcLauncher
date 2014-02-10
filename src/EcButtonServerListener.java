import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcButtonServerListener implements ActionListener {

    private EcLauncher l;

    public EcButtonServerListener(EcLauncher l) {
        this.l = l;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Utils.playSound("click.wav");
    }
}
