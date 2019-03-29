package de.baumann.browser.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


import java.io.File;

import de.baumann.browser.Ninja.R;
import lib.assetmanagers.CopyFiles;
import lib.gradlefile.Gradlejs;

/**
 * Created by LESA on 2018/1/15.
 */

public class Settings_Aria2Activity extends AppCompatActivity implements View.OnClickListener{

    public final static String DATA_HOST = "HOSTDATA";
    public final static String DATA_PORT = "PORTDATA";
    public final static String DATA_TOKEN = "TOKENDATA";
    public final static String DATA_ABLESSL = "ABLESSL";

    public final static String DATA_SAVE_FILE ="aria2config";

    private EditText host;
    private EditText port;
    private EditText token;
    private Button save;
    private CheckBox aCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.setting_aria2);
        host = findViewById(R.id.aria2_editText_host);
        port = findViewById(R.id.aria2_editText_port);
        token = findViewById(R.id.aria2_editText_token);
        aCheckBox = findViewById(R.id.aria2_checkBox);

        SharedPreferences settings = getSharedPreferences(DATA_SAVE_FILE, 0);
        host.setText(settings.getString(DATA_HOST,"localhost"));
        port.setText(settings.getString(DATA_PORT,"6800"));
        token.setText(settings.getString(DATA_TOKEN,""));
        aCheckBox.setChecked(settings.getBoolean(DATA_ABLESSL,false));

        save = findViewById(R.id.aria2_button_save);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.aria2_button_save){
            SharedPreferences settings = getSharedPreferences(DATA_SAVE_FILE, 0);
            SharedPreferences.Editor editor = settings.edit();

            editor.putString(DATA_HOST,host.getText().toString());
            editor.putString(DATA_PORT,port.getText().toString());
            editor.putString(DATA_TOKEN,token.getText().toString());
            editor.putBoolean(DATA_ABLESSL,aCheckBox.isChecked());
            editor.commit();

            File externalFilesDir = getExternalFilesDir(null);
            File uidir = new File(externalFilesDir+"/ui");
            if (!uidir.exists()){
                uidir.mkdir();
                CopyFiles.copyFilesFassets(this,"webui-aria2",""+uidir);
            }
            Gradlejs.built(""+uidir,host.getText().toString(),port.getText().toString(),token.getText().toString());


        }
    }
}
