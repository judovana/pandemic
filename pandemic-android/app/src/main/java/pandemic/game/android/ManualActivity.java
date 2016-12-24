package pandemic.game.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ManualActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        getActionBar().setTitle("Manual (images)");
        //getSupportActionBar().setTitle("Manual (images)");

        ScrollView view = (ScrollView) findViewById(R.id.scrollView);
        LinearLayout lay= (LinearLayout ) findViewById(R.id.linerLayput);

        for(int i = 1; i <= 8; i++){
            Bitmap imgs = BitmapFactory.decodeStream(ManualActivity.class.getResourceAsStream("/pandemic/data/manual/0"+i+".jpg"));
            ImageView imview = new ImageView(this);
            imview.setImageBitmap(imgs);
            lay.addView(imview);
        }


    }
}
