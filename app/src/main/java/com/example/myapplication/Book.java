package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Book extends AppCompatActivity {
    private Button startReading;
    private TextView introduction;
    private File bookFile;
    private Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        introduction = findViewById(R.id.introduction);
        introduction.setText("作者：张志春\n\n");
        introduction.append("\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"本书是易学研究界第一次从理论和实践两个方面彻底揭开了奇门遁甲之谜。\n\n");
        introduction.append("\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"本书展示了来源于军事上的排兵布阵的这种时空数理模型，时至今日，仍然具有深刻的哲学内涵和广泛的实用价值。是一部以学论术，以术证学，雅俗共赏的易学研究新著。\n\n");
        introduction.append("\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"奇门遁学，古称“帝王之学”，是中国传统文化中最具神秘色彩的易学数术之一。\n\n");
        introduction.append("\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"\u0020"+"本书由思维科学入手，从理论和应用两个方面，彻底揭开了它的神秘面纱，是一部以学论术，以术证学，学术结合，雅俗共赏的易学研究新著。");


        if (ContextCompat.checkSelfPermission(Book.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Book.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1
            );
        }


        startReading = findViewById(R.id.bookStart);
        startReading.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                File fileBrochure = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "神奇之门.pdf");

                if (!fileBrochure.exists()) {
                    CopyAssetsbrochure();
                }

                /** PDF reader code */
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "神奇之门.pdf");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider",file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    getApplicationContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(SecondActivity.this, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CopyAssetsbrochure() {

        try
        {
            AssetManager assetManager = getAssets();
            String[] files = null;
            InputStream in = null;
            OutputStream out = null;
            files = assetManager.list("book");
            in = assetManager.open("book/神奇之门.pdf");
            out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/" + files[0]);
            copyFile(in, out);

            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;

        }
        catch (IOException e)
        {
            Log.e("1tag"+e.getClass(), e.getMessage());
        }

    }


    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024*30];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }






}
