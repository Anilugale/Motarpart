package app.motaroart.com.motarpart.services;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;

import app.motaroart.com.motarpart.R;

public class ImageViewer extends Activity {
    ImageView image_viewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        ActionBar actionBar=getActionBar();
        if(actionBar!=null)
        {
            actionBar.hide();
        }

        String image_url=getIntent().getExtras().getString("imageURL","");
        image_viewer = (ImageView) findViewById(R.id.image_viewer);
        new GetImage().execute(image_url);
    }

    class GetImage extends AsyncTask<String,Void,Void>
    {
        Bitmap mIcon_val;
        @Override
        protected Void doInBackground(String... strings) {
            try {

                URL newurl = new URL(strings[0]);
                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mIcon_val!=null) {
                ProgressBar loader_img = (ProgressBar) findViewById(R.id.loader_img);
                loader_img.setVisibility(View.GONE);
                image_viewer.setImageBitmap(mIcon_val);
                image_viewer.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(aVoid);
        }
    }



}
