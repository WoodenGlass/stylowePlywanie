package dobrowol.styloweplywanie.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewVersionChecker extends AsyncTask<Void,Void, Void> {
    //HERE DECLARE THE VARIABLES YOU USE FOR PARSING
    private Element overview=null;
    private  Element featureList=null;
    private Elements features=null;
    private Element paragraph =null;
    private String newVersion = null;
    private String oldVersion = null;
    private Context context;

    public NewVersionChecker(String oldVersion, Context context)
    {
        this.oldVersion = oldVersion;
        this.context = context;
    }
    @Override
    protected Void doInBackground(Void... arg0) {

        Document doc = null;
            try {
            doc = Jsoup
                    .connect(
                            "https://play.google.com/store/apps/details?id="
                                    + "dobrowol.styloweplywanie" + "&hl=en")
                    .timeout(30000)
                    .userAgent(
                            "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com").get();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (doc != null) {
            Element version = doc.select("div[itemprop=softwareVersion]").first();
            if (version != null) {
                newVersion = version.ownText();
            }
        }

        // Get the paragraph element
        // article.setText(paragraph.text()); I comment this out because you cannot update ui from non-ui thread, since doInBackground is running on background thread.


        return null;
    }
    @Override
    protected void onPostExecute(Void result)
    {

        if (newVersion != null && !oldVersion.equals(newVersion)) {
            Intent startIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=dobrowol.styloweplywanie"));
            context.startActivity(startIntent);
        }
    }

}
