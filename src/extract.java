import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class extract {
    public static void main(String[] args) {
        String filename = "src/weblist.txt";
        List<String> urls = readUrlsFromFile(filename);
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/xchange-result.txt"))) {
            for (String url : urls) {
                try {
                    // get
                    Document document = Jsoup.connect(url).get();

                    // ganti
                    Element h1 = document.selectFirst("h1");
                    Element element = document.selectFirst("div.company-public-profile__intro__taglist__tag:nth-child(1) > span:nth-child(2)");

                    // export
                    if (h1 != null && element !=null) {
                        String line = h1.text() + " | " + element.text() + System.lineSeparator();
                        bw.write(line);
                        System.out.println(line + "sukses menulis xchange-result.txt");
                    }
                } catch (IOException e) {
                    System.out.println("Kosong: " + url);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readUrlsFromFile(String filename) {
        List<String> urls = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                urls.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urls;
    }
}
