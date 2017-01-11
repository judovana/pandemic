package pandemic.game.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.File;

import pandemic.game.board.Board;
import pandemic.game.roles.Roles;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CheckBox random = (CheckBox) findViewById(R.id.randomBeggining);
        final CheckBox symetric = (CheckBox) findViewById(R.id.symetric);
        final TextView epidemyCards = (TextView) findViewById(R.id.epidemyCounter);
        final Button plus1 = (Button) findViewById(R.id.buttonPlus);
        final Button minus1 = (Button) findViewById(R.id.butonMinus);
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epidemyCards.setText("" + (Integer.valueOf(epidemyCards.getText().toString()) + 1));
            }
        });
        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epidemyCards.setText("" + (Integer.valueOf(epidemyCards.getText().toString()) - 1));
            }
        });

        final TextView longing = (TextView) findViewById(R.id.longingCounter);
        final Button plus2 = (Button) findViewById(R.id.buttonPlus52);
        final Button minus2 = (Button) findViewById(R.id.butonMinus51);
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                longing.setText("" + (Integer.valueOf(longing.getText().toString()) + 1));
            }
        });
        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                longing.setText("" + (Integer.valueOf(longing.getText().toString()) - 1));
            }
        });


        final CheckBox c1 = (CheckBox) findViewById(R.id.checkBox1);
        final CheckBox c2 = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox c3 = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox c4 = (CheckBox) findViewById(R.id.checkBox4);
        final CheckBox c5 = (CheckBox) findViewById(R.id.checkBox5);
        final CheckBox c6 = (CheckBox) findViewById(R.id.checkBox6);
        final CheckBox c7 = (CheckBox) findViewById(R.id.checkBox7);
        final CheckBox c8 = (CheckBox) findViewById(R.id.checkBox8);
        final CheckBox c9 = (CheckBox) findViewById(R.id.checkBox9);
        final CheckBox c10 = (CheckBox) findViewById(R.id.checkBox10);
        final CheckBox c11 = (CheckBox) findViewById(R.id.checkBox11);
        c1.setText(Roles.knownRoles[0]);
        c2.setText(Roles.knownRoles[1]);
        c3.setText(Roles.knownRoles[2]);
        c4.setText(Roles.knownRoles[3]);
        c5.setText(Roles.knownRoles[4]);
        c6.setText(Roles.knownRoles[5]);
        c7.setText(Roles.knownRoles[6]);
        c8.setText(Roles.knownRoles[7]);
        c9.setText(Roles.knownRoles[8]);
        c10.setText(Roles.knownRoles[9]);
        c11.setText(Roles.knownRoles[10]);

        View.OnClickListener desc = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                CheckBox ch = (CheckBox)v;
                if (ch.isChecked()) {
                    MainActivity.this.setTitle(Roles.rolesInstances.get(ch.getText()).getDescription());
                } else {
                    MainActivity.this.setTitle("pandemic");
                }
            }
        };
        c1.setOnClickListener(desc);
        c2.setOnClickListener(desc);
        c3.setOnClickListener(desc);
        c4.setOnClickListener(desc);
        c5.setOnClickListener(desc);
        c6.setOnClickListener(desc);
        c7.setOnClickListener(desc);
        c8.setOnClickListener(desc);
        c9.setOnClickListener(desc);
        c10.setOnClickListener(desc);
        c11.setOnClickListener(desc);


        final Button b = (Button) findViewById(R.id.startButton);
        final Button bEn1 = (Button) findViewById(R.id.buttonEN1);
        final Button bEn2 = (Button) findViewById(R.id.buttonEN2);
        final Button bCz1 = (Button) findViewById(R.id.buttonCZ1);
        final Button bCz2 = (Button) findViewById(R.id.buttonCZ2);
        if (GameActivity.board != null) {
            c1.setEnabled(false);
            c2.setEnabled(false);
            c3.setEnabled(false);
            c4.setEnabled(false);
            c5.setEnabled(false);
            c6.setEnabled(false);
            c7.setEnabled(false);
            c8.setEnabled(false);
            c9.setEnabled(false);
            c11.setEnabled(false);
            c11.setEnabled(false);
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
                c8.setEnabled(false);
                c9.setEnabled(false);
                c10.setEnabled(false);
                c11.setEnabled(false);
                b.setText("Continue");
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                myIntent.putExtra(Roles.knownRoles[0], c1.isChecked());
                myIntent.putExtra(Roles.knownRoles[1], c2.isChecked());
                myIntent.putExtra(Roles.knownRoles[2], c3.isChecked());
                myIntent.putExtra(Roles.knownRoles[3], c4.isChecked());
                myIntent.putExtra(Roles.knownRoles[4], c5.isChecked());
                myIntent.putExtra(Roles.knownRoles[5], c6.isChecked());
                myIntent.putExtra(Roles.knownRoles[6], c7.isChecked());
                myIntent.putExtra(Roles.knownRoles[7], c8.isChecked());
                myIntent.putExtra(Roles.knownRoles[8], c9.isChecked());
                myIntent.putExtra(Roles.knownRoles[9], c10.isChecked());
                myIntent.putExtra(Roles.knownRoles[10], c11.isChecked());
                myIntent.putExtra("random", random.isChecked());
                myIntent.putExtra("epidemies", Integer.valueOf(epidemyCards.getText().toString()));
                myIntent.putExtra("symetric", symetric.isChecked());
                myIntent.putExtra("longing", Integer.valueOf(longing.getText().toString()));
                MainActivity.this.startActivity(myIntent);

            }
        });
        bEn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ManualActivity.class);
                ManualActivity.type = Board.MANUAL;
                MainActivity.this.startActivity(myIntent);
            }
        });
        bCz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ManualActivity.class);
                ManualActivity.type = Board.MANUALCZ;
                MainActivity.this.startActivity(myIntent);
            }
        });
        bEn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryPdf(Board.MANUAL);
            }
        });
        bCz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryPdf(Board.MANUALCZ);
            }
        });
    }

    public  void tryPdf(String s) {
        try {

            File somePath = Environment.getExternalStorageDirectory();
            File file = new File(somePath, Board.MANUALPDF);
            Board.exportManual(file, s);
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
}
