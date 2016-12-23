package pandemic.game.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.InfecetionRate;
import pandemic.game.roles.Roles;

public class OtherActions extends Activity {

    public static Roles roles;
    public static Deck deck;
    public static GameActivity game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_actions);
        getActionBar().setTitle("Other actions");
        //getSupportActionBar().setTitle("Hello world App");

        Button nextPlayer = (Button) findViewById(R.id.nextPlayer);
        nextPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OtherActions.this.setVisible(false);
                roles.setNextPlayer();
                //TODO remove this chaos methodsonce proper gamepaly is in place
                InfecetionRate.self.chaos();
                game.board.drawBoard();
                OtherActions.this.finish();
            }
        });

    }
}
