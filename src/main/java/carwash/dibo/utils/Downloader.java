package carwash.dibo.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Downloader {

    public static String downloadText(String url) throws IOException {
        return downloadText(url, "utf-8");
    }

    public static String downloadText(String url, String encoding) throws IOException {
        url = url.replace(" ", "%20");

        InputStream in = new URL(url).openStream();
        try {
            return IOUtils.toString(in, encoding);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
