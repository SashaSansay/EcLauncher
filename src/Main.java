import Components.EcLauncher;
import Util.UpdateUtils;
import Util.Utils;
import Workers.UpdateWorker;

public class Main {
    public static void main(String[] args) throws Exception {
        //System.out.println(System.getProperty("user.home"));
        //System.out.println(Utils.getInfo("hashes"));
        //System.out.println(Utils.getInfo("launcher"));
        UpdateUtils.getFilesHashAndUpdate(null);
        new EcLauncher();
    }
}
