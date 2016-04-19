package com.example.geroff.persistentsavetest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivityFile extends Activity {
    private EditText inputText;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveButton = (Button) findViewById(R.id.btn_save);
        inputText = (EditText) findViewById(R.id.et_input);
        String content = load();
        if (!TextUtils.isEmpty(content)){
            inputText.setText(content);
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    FileOutputStream fos = null;
                    BufferedWriter bw = null;
                    try {
                        fos = openFileOutput("data", Context.MODE_PRIVATE);
                        bw = new BufferedWriter(new OutputStreamWriter(fos));
                        bw.write(content);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        try {
                            if (bw != null) {
                                bw.close();
                            }
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(MainActivityFile.this, "save Ok", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String load() {
        FileInputStream fis = null;
        BufferedReader br = null;
        String content = null;
        try {
            fis = openFileInput("data");
            br = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            content = buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fis != null) {
                    fis.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }
}
