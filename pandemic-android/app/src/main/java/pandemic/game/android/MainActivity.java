package pandemic.game.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import pandemic.game.roles.Roles;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CheckBox random = (CheckBox) findViewById(R.id.randomBeggining);
        final TextView epidemyCards = (TextView) findViewById(R.id.epidemyCounter);
        final Button plus = (Button) findViewById(R.id.buttonPlus);
        final Button minus = (Button) findViewById(R.id.butonMinus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epidemyCards.setText("" + (Integer.valueOf(epidemyCards.getText().toString()) + 1));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epidemyCards.setText("" + (Integer.valueOf(epidemyCards.getText().toString()) - 1));
            }
        });


        final CheckBox c1 = (CheckBox) findViewById(R.id.checkBox1);
        final CheckBox c2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox c3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox c4 = (CheckBox) findViewById(R.id.checkBox4);
        final CheckBox c5 = (CheckBox) findViewById(R.id.checkBox5);
        final CheckBox c6 = (CheckBox) findViewById(R.id.checkBox6);
        final CheckBox c7 = (CheckBox) findViewById(R.id.checkBox7);
        c1.setText(Roles.knownRoles[0]);
        c2.setText(Roles.knownRoles[1]);
        c3.setText(Roles.knownRoles[2]);
        c4.setText(Roles.knownRoles[3]);
        c5.setText(Roles.knownRoles[4]);
        c6.setText(Roles.knownRoles[5]);
        c7.setText(Roles.knownRoles[6]);
        final Button b = (Button) findViewById(R.id.startButton);
        final Button b2 = (Button) findViewById(R.id.button2);
        final Button b3 = (Button) findViewById(R.id.button3);
        if (GameActivity.board != null) {
            c1.setEnabled(false);
            c2.setEnabled(false);
            c3.setEnabled(false);
            c4.setEnabled(false);
            c5.setEnabled(false);
            c6.setEnabled(false);
            c7.setEnabled(false);
            b.setText("Continue");

        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setEnabled(false);
                c2.setEnabled(false);
                c3.setEnabled(false);
                c4.setEnabled(false);
                c5.setEnabled(false);
                c6.setEnabled(false);
                c7.setEnabled(false);
                b.setText("Continue");
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                myIntent.putExtra(Roles.knownRoles[0], c1.isChecked());
                myIntent.putExtra(Roles.knownRoles[1], c2.isChecked());
                myIntent.putExtra(Roles.knownRoles[2], c3.isChecked());
                myIntent.putExtra(Roles.knownRoles[3], c4.isChecked());
                myIntent.putExtra(Roles.knownRoles[4], c5.isChecked());
                myIntent.putExtra(Roles.knownRoles[5], c6.isChecked());
                myIntent.putExtra(Roles.knownRoles[6], c7.isChecked());
                myIntent.putExtra("random", random.isChecked());
                myIntent.putExtra("epidemies", Integer.valueOf(epidemyCards.getText().toString()));
                MainActivity.this.startActivity(myIntent);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ManualActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream is = MainActivity.this.getClass().getResourceAsStream("/pandemic/data/manual.pdf");
                    File somePath = Environment.getExternalStorageDirectory();
                    File file = new File(somePath, "pandemic-manual.pdf");
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    Uri path = Uri.fromFile(file);
                    Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                    pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    pdfOpenintent.setDataAndType(path, "application/pdf");
                    startActivity(pdfOpenintent);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // set title
                    alertDialogBuilder.setTitle("Error");
                    alertDialogBuilder.setCancelable(true);
                    // set dialog message
                    alertDialogBuilder
                            .setMessage(ex.getMessage());
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

}
