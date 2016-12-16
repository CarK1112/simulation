package lu.btsi.simulationapp.mainframe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.StrictMode;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import lu.btsi.simulationapp.R;

import lu.btsi.simulationapp.server.Load;
import lu.btsi.simulationapp.server.LoadFiles;

public class MainActivity extends AppCompatActivity {

    // Our load and save options
    final Load myLoadCells = new Load();
    final LoadFiles myLoadFiles = new LoadFiles();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // Get the drawpanel
        final DrawSurface drawSurface = (DrawSurface) findViewById(R.id.drawpanel);

        final Spinner spinnerFiles = (Spinner) findViewById(R.id.spinnerFiles);
        final Button startButton = (Button) findViewById(R.id.buttonStart);
        final Button resetButton = (Button) findViewById(R.id.buttonReset);
        // Populate the combobox
        getDataForSpinner(spinnerFiles);

        // Reset
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gather data again, just in case if a file has been deleted
                String buttonText = startButton.getText().toString();
                try {
                    String filename = null;
                    if (spinnerFiles != null && spinnerFiles.getSelectedItem() != null) {
                        filename = (String) spinnerFiles.getSelectedItem();
                        String JSONData = myLoadCells.readData(filename);
                        System.err.println("File name " + filename);
                        drawSurface.setData(JSONData);
                        drawSurface.invalidate();
                        drawSurface.timerSpeed = 1000;
                        int refreshRate = drawSurface.timerSpeed;
                        startButton.setText("Stop");
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Error!");
                        alertDialog.setMessage("No files are available.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
// start
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gather data again, just in case if a file has been deleted
                String buttonText = startButton.getText().toString();
                if (buttonText == "Restart" || buttonText == "Load") {
                    try {
                        String filename = null;
                        if (spinnerFiles != null && spinnerFiles.getSelectedItem() != null) {
                            filename = (String) spinnerFiles.getSelectedItem();
                            String JSONData = myLoadCells.readData(filename);
                            System.err.println("File name " + filename);
                            drawSurface.setData(JSONData);
                            drawSurface.invalidate();
                            drawSurface.timerSpeed = 200;
                            int refreshRate = drawSurface.timerSpeed;
                            drawSurface.startTimer();
                            drawSurface.repaint();
                            startButton.setText("Stop");
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Error!");
                            alertDialog.setMessage("No files are available.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    drawSurface.timer.cancel();
                    startButton.setText("Restart");
                }

            }
        });

    }

    // Gather data from the server
    public void getDataForSpinner(Spinner spinnerFiles) {
        try {
            JSONArray array = new JSONArray(myLoadFiles.readFiles());
            ArrayAdapter<String> adapter;
            List<String> list;

            list = new ArrayList<String>();

            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFiles.setAdapter(adapter);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
