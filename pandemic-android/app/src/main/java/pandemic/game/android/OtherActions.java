package pandemic.game.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.InfecetionRate;
import pandemic.game.roles.Role;
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
        ScrollView mainScroll= (ScrollView) findViewById(R.id.mainScroll);
        mainScroll.removeAllViews();
        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        mainScroll.addView(main);

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

        TextView t = new TextView(this);
        t.setText(roles.getCurrentPlayer().getName());
        ViewGroup.LayoutParams LLParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        t.setLayoutParams(LLParams);
        main.addView(t);
        for (Role r : roles.getPlayersInCity(roles.getCurrentPlayer().getCity())){
            if (r != roles.getCurrentPlayer()) {
                t = new TextView(this);
                t.setText(r.getName());
                LLParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setLayoutParams(LLParams);
                main.addView(t);
            }

        }

    }
}
