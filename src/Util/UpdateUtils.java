package Util;

import Components.EcProrgressBar;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.*;

public class UpdateUtils {

    public static void getFilesHashAndUpdate(EcProrgressBar bar) throws IOException {
        String jsonString = Utils.getInfo("hashes");
        JsonObject obj= new JsonParser().parse(jsonString).getAsJsonObject();
        String mainPath = Utils.getMainPath();
        for (Map.Entry<String,JsonElement> entry : obj.entrySet()) {
            System.out.println(entry.getValue());
            Utils.getMD5File(mainPath + entry.getKey());
        }
    }

    public static FileOutputStream getFile(String url,String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        FileOutputStream out = new FileOutputStream(path);
        byte data[] = new byte[1024];
        int count;
        while((count = in.read(data,0,1024)) != -1)
        {
            out.write(data, 0, count);
        }
        return out;
    }
}
