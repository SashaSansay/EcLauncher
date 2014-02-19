package Util;

import Components.EcProrgressBar;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class UpdateUtils {

    public static void updateAndRepaint(EcProrgressBar bar) throws InvocationTargetException, InterruptedException {
        bar.setMaximum(100000);
        for(int i=0;i<100000;i++){
            bar.setValue(i);
            bar.repaint();
        }
    }
}
