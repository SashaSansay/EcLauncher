import java.io.*;
import java.net.*;

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
}
