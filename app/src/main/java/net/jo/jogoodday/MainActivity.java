package net.jo.jogoodday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button getBtn;
    private TextView result;
//    public String site = "http://www.cwb.gov.tw/rss/forecast/36_01.xml"; // RSS
    public String site = "http://www.cwb.gov.tw/V7/forecast/taiwan/Taipei_City.htm";
    public String site2 = "http://www.cwb.gov.tw/V7/forecast/week/week.htm";
    private String xmlSrc = "http://opendata.cwb.gov.tw/opendataapi?dataid=F-C0032-001&authorizationkey=CWB-ED9E9133-DEEF-441E-92CF-B9385E470053";
    private WebView webView;
    private String outerHtml;
    private String outerHtml2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView = (WebView) findViewById(R.id.webview);


        getWebsite();



    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("tag", "_______________RUN");
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect(site).get();
//                    String s1 = doc.select("div#forecast0 h3").outerHtml();
                    Elements elements2 = doc.select("div#box8 > table");
//                    elements2.select("html table:nth-of-type(2)").remove();
//                    outerHtml2 = elements2.outerHtml();



                    Document weekDoc = Jsoup.connect(site2).get();
                    Elements elements = weekDoc.select("table.BoxTableInside tr:nth-of-type(1), table.BoxTableInside tr:nth-of-type(4), table.BoxTableInside tr:nth-of-type(5)");
                    elements.select("th[scope='row']").remove();
                    elements.select("th:nth-of-type(2)").remove();

                    outerHtml2 = "<table>" + elements + "</table>";

                    // 組合兩個table
                    outerHtml2 = elements2.outerHtml() + "<br>" + outerHtml2;


                    String css = "<head><style>\n" +
                            "body {\n" +
//                            "    background-color: linen;\n" +
                            "}\n" +
                            "\n" +
                            "table, td {\n" +
                            "    border: 1px solid black;\n" +
//                            "    margin-left: 40px;\n" +
                            "} \n" +
                            "</style></head>\n";

                    // 修正圖片網址
                    String sss = outerHtml2.replaceAll("../../", "http://www.cwb.gov.tw/V7/");
                    // 加上CSS
                    outerHtml2 = css+sss;
//                    Log.d("ss ", "__________" + outerHtml2);


                    String title = doc.title();
                    Elements links = doc.select("a[href]");
                    Elements ths = doc.select("th");

                    builder.append(title).append("\n");

                    for (Element th : ths) {
                        builder.append("\n").append("TH : ").append(th.attr("class"))
                                .append("\n").append("Text : ").append(th.text());
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        webView.loadData(outerHtml, "text/html", "UTF-8");
                        webView.loadDataWithBaseURL("file:///android_asset/.", outerHtml2, "text/html", "UTF-8", null);
//                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }




}
