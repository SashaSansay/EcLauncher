import Components.EcLauncher;
import Util.UpdateUtils;
import Util.Utils;
import Workers.UpdateWorker;

public class Main {
    public static void main(String[] args) throws Exception {
        //System.out.println(System.getProperty("user.home"));
        //System.out.println(Utils.getInfo("hashes"));
        //System.out.println(Utils.getInfo("launcher"));
<<<<<<< HEAD
        UpdateUtils.getFilesHashAndUpdate(null);
=======
>>>>>>> a7488d499527f9c53e1c5b3f0124f224125e3165
        new EcLauncher();
    }
}
