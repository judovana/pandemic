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
        c1.setText(Roles.knownRoles[0]);
        c2.setText(Roles.knownRoles[1]);
        c3.setText(Roles.knownRoles[2]);
        c4.setText(Roles.knownRoles[3]);

        Button b = (Button) findViewById(R.id.startButton) ;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                myIntent.putExtra(Roles.knownRoles[0], c1.isChecked());
                myIntent.putExtra(Roles.knownRoles[1], c2.isChecked());
                myIntent.putExtra(Roles.knownRoles[2], c3.isChecked());
                myIntent.putExtra(Roles.knownRoles[3], c4.isChecked());
                MainActivity.this.startActivity(myIntent);

            }
        });
    }

}
