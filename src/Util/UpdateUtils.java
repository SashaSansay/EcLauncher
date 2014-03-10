package Util;

import Components.EcButton;
import Components.EcProrgressBar;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

import Components.EcServerButton;
import com.google.gson.*;

public class UpdateUtils {

    private static EcProrgressBar bar;
    private static EcButton btn;
    private static EcServerButton aquilla;

    public static void getFilesHashAndUpdate(EcProrgressBar bar,EcButton btn,EcServerButton aquilla) throws NoSuchAlgorithmException, IOException {
        UpdateUtils.bar = bar;
        UpdateUtils.btn = btn;
        UpdateUtils.aquilla=aquilla;
        aquilla.setEnabled(false);
        btn.setEnabled(false);
        updateTextButton("Проверка и обновление","Пожалуйста, подождите...");
        String jsonString = Utils.getInfo("hashes");
        if(!jsonString.equals("error")){
            System.out.println(jsonString);
            JsonObject obj= new JsonParser().parse(jsonString).getAsJsonObject();
            String mainPath = Utils.getMainPath();
            System.out.println(obj.entrySet().size());
            bar.setMaximum(obj.entrySet().size());
            HashMap<String,String> thisFiles = Utils.getMD5Files();
            for (Map.Entry<String,JsonElement> entry : obj.entrySet()) {
                String currentFileName = mainPath + entry.getKey().replaceAll("/", Matcher.quoteReplacement(File.separator));
                File currentFile = new File(currentFileName);
                btn.setText2("Проверка: "+currentFileName);
                btn.repaint();
                if(!currentFile.exists()){
                    Path p = Paths.get(currentFileName);
                    new File(p.getParent().toString()).mkdirs();
                    if(!getFile(entry.getKey(),currentFileName)){
                        btn.setText("Ошибка обновления...","Попробуйте позже");
                        btn.repaint();
                        return;
                    }
                }else{
                    String s1 = entry.getValue().toString().replaceAll("\"","");
                    if(s1.length()>32)s1=s1.substring(1,s1.length());
                    String thisHash = Utils.getMD5File(currentFileName);
                    if(!s1.equals(thisHash)){
                        System.out.println(currentFileName+" - BAD HASH! "+thisHash+"!="+s1);
                        currentFile.delete();
                        if(!getFile(entry.getKey(),currentFileName)){
                            btn.setText("Ошибка обновления...","Попробуйте позже");
                            btn.repaint();
                            return;
                        }
                    }else{
                        System.out.println(currentFileName+" - OK! "+thisHash+"=="+s1);
                    }
                }
                thisFiles.remove(entry.getKey().replaceAll("/",Matcher.quoteReplacement(File.separator)));
                updateProgress(1);
            }
            for(Map.Entry<String,String> entry: thisFiles.entrySet()){
                new File(Utils.getMainPath()+entry.getKey()).delete();
            }
            btn.setEnabled(true);
            updateTextButton("Обновлений не найдено!","");
        }
        aquilla.setEnabled(true);
    }

    public static boolean getFile(String file,String path){
        btn.setText2("Downloading: "+file);
        btn.repaint();
        try{
            URL website = new URL("http://download.craft.ec/client"+file);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(path);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            //updateProgress(1);
            System.out.println("Downloaded "+file);
            fos.close();
            return true;
        }
        catch (IOException e){
            btn.setText("Ошибка обновления...");
            btn.repaint();
            btn.setText2("Попробуйте позже");
            btn.repaint();
            btn.setEnabled(true);
            return false;
        }
    }

    private static void updateProgress(int x){
        bar.setValue(bar.getValue()+x);
        bar.repaint();
    }

    private static void updateTextButton(String str){
        btn.setText(str);
        btn.repaint();
    }

    private static void updateTextButton(String str, String str2){
        btn.setText(str);
        btn.setText2(str2);
        btn.repaint();
    }
}
