package com.michaelfaith.apt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.michaelfaith.common.Hello;
import com.michaelfaith.apt.R;
import com.michaelfaith.libannotation.OutputModulePackage;

@OutputModulePackage(module = "common", packagePath = "com/michaelfaith/common")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("zqwx", " - "+ Hello.LIB_ALPHA_VALUEAMAO);

    }
}
