package com.example.jacob_000.assign3;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button createB;
    Button loadB;
    Button clearB;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createB = (Button) findViewById(R.id.createB);
        loadB = (Button) findViewById(R.id.loadB);
        clearB = (Button) findViewById(R.id.clearB);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        final Context context = this;
        final File file = new File(context.getFilesDir(), "test.txt");

        final ArrayList<String> numbers = new ArrayList<String>();

        createB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(0);
                //Context context = this;
                //final file = new File(context.getFilesDir(), "test.txt");
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OutputStream outputFile = new FileOutputStream(file.getPath());
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputFile);
                            for(int i = 1; i <= 10; ++i) {
                                outputStreamWriter.write(Integer.toString(i) + "\n");
                                Thread.sleep(250);
                                progressBar.setProgress(progressBar.getProgress()+10);
                            }

                            outputStreamWriter.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });

        loadB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setProgress(0);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                            String line;

                            while ((line = br.readLine()) != null) {
                                numbers.add(line);
                                Thread.sleep(250);
                                progressBar.setProgress(progressBar.getProgress()+10);
                            }
                            br.close();
                        }
                        //catches exceptions
                        catch(FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        catch(IOException ex) {
                            ex.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });


                ArrayAdapter<String> numAdapter =
                        new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, numbers);

                ListView listView = (ListView) findViewById(R.id.numbersList);
                listView.deferNotifyDataSetChanged();
                listView.setAdapter(numAdapter);
                listView.deferNotifyDataSetChanged();
            }
        });

        clearB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbers.clear();
                progressBar.setProgress(0);

                ArrayAdapter<String> numAdapter =
                        new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, numbers);

                ListView listView = (ListView) findViewById(R.id.numbersList);
                listView.setAdapter(numAdapter);
            }
        });
    }
}
