package Util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Components.EcLauncher;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Utils {
    private static List<File> modsList=new ArrayList<File>();

    public static synchronized String[] pollServer(String ip, int port)
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

    public static void checkAndUpdate(){

    }

    public static String getInfo(String info){
        BufferedReader rd = null;
        String res = "";
        String line = "";
        try {
            URL url = new URL("http://s.craft.ec/getinfo.php?q="+info);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "craft.ec");
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
        //return  System.getProperty("user.home")+File.separator+"AppData"+File.separator+"Roaming"+File.separator+".minecraft";
    }

    public static String getMD5Mods(){
        String mainPath=getMainPath()+File.separator+"mods"+File.separator;
        //String[][][] mods = new String[0][1][1];
        listFiles(mainPath);
        String mods="";
        Iterator itr = modsList.iterator();
        int i=0;
        while (itr.hasNext()){
            //mods=Arrays.copyOf(mods,mods.length+1);
            File current = (File) itr.next();
            if(!current.getAbsolutePath().equals(getMainPath()+File.separator+"mods"+File.separator+"rei_minimap"+File.separator+"keyconfig.txt"))
            mods+=current.getAbsolutePath().replace(getMainPath()+File.separator,"")+";"+getMD5File(current.getAbsolutePath())+";";
        }
        System.out.println(mods);
        return mods;
    }

    public static void listFiles(String path){
        File root = new File(path);
        File[] list = root.listFiles();
        if (list == null) return;
        for ( File f : list ) {
            if ( f.isDirectory() ) {
                listFiles(f.getAbsolutePath());
            }
            else {
                modsList.add(f);
            }
        }
    }

    public static void startMinecraft(String memory,String version) throws FileNotFoundException{
        try
        {
            String path = getMainPath()+File.separator;
            String versionDir = new File(path,"versions"+File.separator+version).getAbsolutePath()+File.separator;
            String assetsDir = new File(path, "assets").getAbsolutePath() + File.separator;
            List<String> params = new ArrayList<String>();
            if (Utils.getPlatform() == 2) params.add("javaw"); else params.add("java");
            params.add("-Xincgc");
            params.add("-Xmx"+memory+"m");
            params.add("-Djava.library.path=\"" + versionDir + "natives\"");
            JsonParser parser = new JsonParser();
            JsonObject elem = parser.parse(
                    new InputStreamReader(new FileInputStream(versionDir + version+".json"))).getAsJsonObject();
            JsonArray libraries = elem.get("libraries").getAsJsonArray();
            params.add("-cp");
            StringBuilder path1 = new StringBuilder();
            for (JsonElement lib : libraries) {
                String[] vars = lib.getAsJsonObject().get("name").getAsString()
                        .split(":");
                String libPath = path + "libraries/"
                        + vars[0].replaceAll("\\.", "/") + "/" + vars[1] + "/"
                        + vars[2] + "/" + vars[1] + "-" + vars[2] + ".jar";
                path1.append(libPath + ";");
                JsonElement natives = lib.getAsJsonObject().get("natives");
                if (natives != null) {
                    String os = "windows";
                    if (Utils.getPlatform() == 2) {
                        os = "windows";
                    } else if (Utils.getPlatform() == 3) {
                        os = "osx";
                    } else if (Utils.getPlatform() == 0) {
                        os = "linux";
                    }

                    File nativesZip = new File(path + "libraries/"
                            + vars[0].replaceAll("\\.", "/") + "/" + vars[1] + "/"
                            + vars[2] + "/" + vars[1] + "-" + vars[2] + "-"
                            + "natives-" + os + ".jar");
                    Zipper.unzipFolder(nativesZip, new File(versionDir, "natives"));
                }
            }
            path1.append(versionDir + version+".jar");
            params.add("\"" + path1.toString() + "\"");
            params.add(elem.get("mainClass").getAsString());
            params.add(elem.get("minecraftArguments").getAsString()
                    .replace("${auth_player_name}", "SashaSansay")
                    .replace("${auth_session}", "Hello World! ^^")
                    .replace("${version_name}", version)
                    .replace("${game_directory}", path)
                    .replace("${game_assets}", assetsDir)
                    .replace("${auth_uuid}", "1")
                    .replace("${auth_access_token}", "1")
                    .replace("${auth_uuid}", "Hello World Again! ^^"));
            StringBuilder sb = new StringBuilder();
            for (String s : params) {
                //System.out.print(s + " ");
                sb.append(s + " ");
            }
            Runtime.getRuntime().exec(sb.toString());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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

    public static int getPlatform()
    {
        String osName = System.getProperty("os.name").toLowerCase();

        if(osName.contains("win")) return 2;
        if(osName.contains("mac")) return 3;
        if(osName.contains("solaris")) return 1;
        if(osName.contains("sunos")) return 1;
        if(osName.contains("linux")) return 0;
        if(osName.contains("unix")) return 0;

        return 4;
    }

    public static String getMemory(EcLauncher l){
        return l.getMemory();
    }

    public static void openURL(String url)
    {
        try
        {
            Object o = Class.forName("java.awt.Desktop").getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            o.getClass().getMethod("browse", new Class[] { URI.class }).invoke(o, new Object[] { new URI(url)});
        } catch (Throwable e) {}
    }
}
