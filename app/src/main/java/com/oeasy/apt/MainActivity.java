package com.oeasy.apt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.oeasy.libannotation.OutputModulePackage;

@OutputModulePackage(module = "common", packagePath = "com/oeasy/common")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.e("zqwx", " - "+ Hello.keyAZqwx);

    }
}
