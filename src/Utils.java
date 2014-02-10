import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Utils {
    public static String[] pollServer(String ip, int port)
    {
        Socket soc = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        try
        {
            soc = new Socket();
            soc.setSoTimeout(3000);
            soc.setTcpNoDelay(true);
            soc.setTrafficClass(18);
            soc.connect(new InetSocketAddress(ip, port), 3000);
            dis = new DataInputStream(soc.getInputStream());
            dos = new DataOutputStream(soc.getOutputStream());
            dos.write(254);

            if (dis.read() != 255)
            {
                throw new IOException("Bad message");
            }

            return readString(dis, 256).split("§");
        } catch (Exception e)
        {
            return new String[] { null, null, null };
        } finally
        {
            try { dis.close();  } catch (Exception e) {}
            try { dos.close();  } catch (Exception e) {}
            try { soc.close();  } catch (Exception e) {}
        }
    }

    public static String readString(DataInputStream is, int d) throws IOException
    {
        short word = is.readShort();
        if (word > d) throw new IOException();
        if (word < 0) throw new IOException();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < word; i++)
        {
            res.append(is.readChar());
        }
        return res.toString();
    }

    public static String getInfo(String info){
        BufferedReader rd = null;
        String res = "";
        String line = "";
        try {
            URL url = new URL("http://forum.craft.ec/script/getinfo.php?q="+info);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while ((line = rd.readLine()) != null) {
                res += line;
            }
            rd.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getMD5File(String path)
    {
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            InputStream is = new FileInputStream(new File(path));
            byte[] buffer = new byte[8192];
            int read = 0;
            while( (read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
            byte[] md5sum = digest.digest();
            BigInteger bigInt = new BigInteger(1, md5sum);
            String output = bigInt.toString(16);
            is.close();
            return output;
        }
        catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String getMainPath(){
        return System.getProperty("user.home")+File.separator+".craftec";
    }

   /* public static String[][] getMD5Mods(String path){
        String mainPath=getMainPath();

    }*/

    public static String getWorkingDirectory(){
        return System.getProperty("user.home")+File.separator+".craftec";
    }

    public static void startMinecraft(String memory,String version,String forge){
        //String  bin = Utils.getWorkingDirectory() + File.separator + cfolder + File.separator + "bin" + File.separator;
        //String  min = Utils.getWorkingDirectory() + File.separator + cfolder;
        //String[] s = LauncherFrame.result.split(":");
        String cp = File.pathSeparator;
        ArrayList<String> params = new ArrayList<String>();
        params.add("java");
        //params.add("-Xmx" + memoryId + "m");
        memory="1024";
        version="1.6.4";
        forge="9.11.1.965";
        //params.add("java -Xms"+memory"+m -Xmx"+memory+"m -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true -Djava.library.path=\".\versions\\"+version+"\natives\" -cp \".\libraries\net\minecraftforge\minecraftforge\\"+forge+"\minecraftforge-%forge%.jar\";\".\libraries\net\minecraft\launchwrapper\%launchwrapper%\launchwrapper-%launchwrapper%.jar";".\libraries\org\ow2\asm\asm-all\4.1\asm-all-4.1.jar";".\libraries\net\sf\jopt-simple\jopt-simple\4.5\jopt-simple-4.5.jar";".\libraries\lzma\lzma\0.0.1\lzma-0.0.1.jar";".\libraries\net\sf\jopt-simple\jopt-simple\4.5\jopt-simple-4.5.jar";".\libraries\com\paulscode\codecjorbis\20101023\codecjorbis-20101023.jar";".\libraries\com\paulscode\codecwav\20101023\codecwav-20101023.jar";".\libraries\com\paulscode\libraryjavasound\20101123\libraryjavasound-20101123.jar";".\libraries\com\paulscode\librarylwjglopenal\20100824\librarylwjglopenal-20100824.jar";".\libraries\com\paulscode\soundsystem\20120107\soundsystem-20120107.jar";".\libraries\org\lwjgl\lwjgl\lwjgl\2.9.0\lwjgl-2.9.0.jar";".\libraries\org\lwjgl\lwjgl\lwjgl_util\2.9.0\lwjgl_util-2.9.0.jar";".\libraries\argo\argo\2.25_fixed\argo-2.25_fixed.jar";".\libraries\org\bouncycastle\bcprov-jdk15on\1.47\bcprov-jdk15on-1.47.jar";".\libraries\com\google\guava\guava\14.0\guava-14.0.jar";".\libraries\org\apache\commons\commons-lang3\3.1\commons-lang3-3.1.jar";".\libraries\commons-io\commons-io\2.4\commons-io-2.4.jar";".\libraries\net\java\jinput\jinput\2.0.5\jinput-2.0.5.jar";".\libraries\net\java\jutils\jutils\1.0.0\jutils-1.0.0.jar";".\libraries\com\google\code\gson\gson\2.2.2\gson-2.2.2.jar";".\versions\%version%\%version%.jar" net.minecraft.launchwrapper.Launch --username %name% --session %session% --version Forge%forge% --gameDir ".\minecraft" --assetsDir ".\assets" --tweakClass cpw.mods.fml.common.launcher.FMLTweaker");
        ProcessBuilder pb = new ProcessBuilder(params);

        System.exit(0);
    }

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sounds/"+url));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}
