package com.example.igym.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.igym.R;

public class webView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        ImageButton btBack = findViewById(R.id.btBack);
        WebView wvView = findViewById(R.id.wvView);

        configureWebView(wvView);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void configureWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.com/maps/dir//BeOne+Fitness+%26+Sport,+Campus+Universitario,+32004+Ourense/data=!4m6!4m5!1m1!4e2!1m2!1m1!1s0xd2ffeb9a19e9375:0xd24206b864592f26?sa=X&ved=2ahUKEwiB9NbJ4paDAxVCif0HHUX4D80Q48ADegQIFhAA");
    }
}
