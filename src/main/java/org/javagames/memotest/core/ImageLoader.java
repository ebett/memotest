package org.javagames.memotest.core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;

public class ImageLoader {

  private static final String[] CARDS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "J", "Q", "K"};

  private static final String[] PALOS = {"C", "S", "H", "D"};

  private static final String FILE_EXT = ".png";

  private Map<String, ImageIcon> cache = new HashMap<>();

  ImageIcon loadIcon(String filename) {
    return cache.computeIfAbsent(filename,
        (fname) -> new ImageIcon(ImageLoader.class.getResource(fname)));
  }

  public List<ImageIcon> loadIcons() {
    List<ImageIcon> icons = new ArrayList<>(CARDS.length * PALOS.length);
    for (String card: CARDS) {
      for (String p : PALOS) {
        final String filename = "/images/" + card + p + FILE_EXT;
        icons.add(loadIcon(filename));
      }
    }
    return icons;
  }

  public static void downloadCardImages() {
    final String baseUri = "https://deckofcardsapi.com/static/img/";
    HttpClient client = HttpClient.newBuilder().followRedirects(Redirect.ALWAYS).build();

    for (String card: CARDS) {
      for (String p :PALOS) {
        String uri = baseUri +  card + p + FILE_EXT;
        System.out.println("uri = " + uri);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .GET()
            .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            .build();

        try (InputStream is = client.sendAsync(request, BodyHandlers.ofInputStream())
            .thenApply(HttpResponse::body).join();
            FileOutputStream out = new FileOutputStream("./images/"+ card + p + ".png")) {
          is.transferTo(out);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
