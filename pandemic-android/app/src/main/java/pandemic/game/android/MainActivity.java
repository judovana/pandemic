package pandemic.game.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import pandemic.game.roles.Roles;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CheckBox c1 = (CheckBox) findViewById(R.id.checkBox1) ;
        final CheckBox c2 = (CheckBox) findViewById(R.id.checkBox2) ;
        final CheckBox c3 = (CheckBox) findViewById(R.id.checkBox3) ;
        final CheckBox c4 = (CheckBox) findViewById(R.id.checkBox4) ;
        final CheckBox c5 = (CheckBox) findViewById(R.id.checkBox5) ;
        final CheckBox c6 = (CheckBox) findViewById(R.id.checkBox6) ;
        final CheckBox c7 = (CheckBox) findViewById(R.id.checkBox7) ;
        c1.setText(Roles.knownRoles[0]);
        c2.setText(Roles.knownRoles[1]);
        c3.setText(Roles.knownRoles[2]);
        c4.setText(Roles.knownRoles[3]);
        c5.setText(Roles.knownRoles[4]);
        c6.setText(Roles.knownRoles[5]);
        c7.setText(Roles.knownRoles[6]);

        Button b = (Button) findViewById(R.id.startButton) ;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                myIntent.putExtra(Roles.knownRoles[0], c1.isChecked());
                myIntent.putExtra(Roles.knownRoles[1], c2.isChecked());
                myIntent.putExtra(Roles.knownRoles[2], c3.isChecked());
                myIntent.putExtra(Roles.knownRoles[3], c4.isChecked());
                myIntent.putExtra(Roles.knownRoles[4], c5.isChecked());
                myIntent.putExtra(Roles.knownRoles[5], c6.isChecked());
                myIntent.putExtra(Roles.knownRoles[6], c7.isChecked());
                MainActivity.this.startActivity(myIntent);

            }
        });
    }

}
