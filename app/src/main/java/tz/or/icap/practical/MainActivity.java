 package tz.or.icap.practical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;




 public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        actionBar.setTitle("QR Code Scanner");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);

        scannerView = findViewById(R.id.scanner_view);
        textResults = findViewById(R.id.textResult);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        scannerView.setResultHandler(MainActivity.this);
                        scannerView.startCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        Toast.makeText(MainActivity.this,
                                "You must accept this permission"
                                , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

     @Override
     protected void onDestroy() {

         scannerView.stopCamera();
         super.onDestroy();
     }


     @Override
     public void onBackPressed() {
         super.onBackPressed();
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {

         switch (item.getItemId()){

             case android.R.id.home:
                 onBackPressed();
                 return true;
         }
         return super.onOptionsItemSelected(item);
     }


     @Override
     public void handleResult(Result rawResult) {
         //Here we can receive the rawResult
         textResults.setText(rawResult.getText());
     }
 }