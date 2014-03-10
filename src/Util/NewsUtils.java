package Util;

import Components.EcNewsPanel;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class NewsUtils {

    private static EcNewsPanel pan;

    public static void getNews(EcNewsPanel pan) throws IOException {
        String url =  "http://forum.craft.ec/categories/%D0%BD%D0%BE%D0%B2%D0%BE%D1%81%D1%82%D0%B8/feed.rss";
        SyndFeed feed = null;
        InputStream is = null;
        try{
            URLConnection openConnection = new URL(url).openConnection();
            is = new URL(url).openConnection().getInputStream();
            if("gzip".equals(openConnection.getContentEncoding())){
                is = new GZIPInputStream(is);
            }
            InputSource source = new InputSource(is);
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(source);
            List<SyndEntryImpl> entries = feed.getEntries();
            int size = entries.size();
            String[] title = new String[size];
            String[] desc = new String[size];
            String[] link = new String[size];
            int i = 0;
            for(SyndEntryImpl entry : entries){
                title[i]=entry.getTitle();
                desc[i]=entry.getDescription().getValue();
                link[i]=entry.getLink();
                i++;
                System.out.println(entry.getTitle());
                System.out.println(entry.getDescription().getValue());
                System.out.println(entry.getLink());
            }
            pan.setItems(title,desc,link);
            pan.repaint();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if( is != null) is.close();
        }

    }
}
