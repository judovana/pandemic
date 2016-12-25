package pandemic.game.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.InfecetionRate;
import pandemic.game.cards.PlayerCard;
import pandemic.game.roles.Role;
import pandemic.game.roles.Roles;

public class OtherActions extends Activity {

    public static Roles roles;
    public static Deck deck;
    public static GameActivity game;

    private static final ViewGroup.LayoutParams VparamsMW1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private static final LinearLayout.LayoutParams LparamsWW2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_actions);
        getActionBar().setTitle("Other actions");
        //getSupportActionBar().setTitle("Hello world App");

        Button nextPlayer = (Button) findViewById(R.id.nextPlayer);
        ScrollView mainScroll = (ScrollView) findViewById(R.id.mainScroll);
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


        CurrentPlayerView cp = new CurrentPlayerView(roles.getCurrentPlayer(), this);
        main.addView(cp);

        for (Role r : roles.getPlayersInCity(roles.getCurrentPlayer().getCity())) {
            if (r != roles.getCurrentPlayer()) {
                OtherPlayerView op = new OtherPlayerView(r, roles.getCurrentPlayer(),this);
                main.addView(op);
            }

        }

    }

    private class PlayerView extends LinearLayout {

        protected final Role thisPlayer;
        //android is to dummy so we need to duplicate variable here
        protected final Context mContext;
        protected List<CheckboxWithCard> checks;

        public PlayerView(Role thisPlayer, Context parent) {
            super(parent);
            this.mContext = parent;
            this.thisPlayer = thisPlayer;
            this.setOrientation(LinearLayout.HORIZONTAL);
            this.setLayoutParams(VparamsMW1);
        }

        protected TextView setName() {
            TextView t = new TextView(mContext);
            t.setText(thisPlayer.getName());
            t.setLayoutParams(LparamsWW2);
            this.addView(t);
            return t;
        }

        protected Button addDropCardsButon() {
            return addButon("Drop cards");
        }
        protected Button addButon(String title) {
            Button b = new Button(mContext);
            b.setText(title);
            b.setLayoutParams(LparamsWW2);
            this.addView(b);
            return b;
        }

        protected void addCards() {
            LinearLayout cards = new LinearLayout(mContext);
            cards.setOrientation(LinearLayout.VERTICAL);
            cards.setLayoutParams(LparamsWW2);
            checks = init(cards, thisPlayer.getCardsInHand());
            this.addView(cards);
        }

    }

    private class CurrentPlayerView extends PlayerView {

        public CurrentPlayerView(Role thisPlayer, Context parent) {
            super(thisPlayer, parent);
            setName();
            addButon("Build station");
            addButon("Cure disease");
            addButon("Invent Cure");
            addCards();
            addDropCardsButon();

        }
    }

    private class OtherPlayerView extends PlayerView {
        protected final Role currentPlayer;

        public OtherPlayerView(Role thisPlayer, Role currentPlayer, Context parent) {
            super(thisPlayer, parent);
            this.currentPlayer = currentPlayer;
            Button tab = addButon("*******");
            setName();
            tab.setEnabled(false);
            addButon("Give to " + currentPlayer.getName());
            addButon("Take from " + currentPlayer.getName());
            addCards();
            addDropCardsButon();
        }
    }

    private static class CheckboxWithCard extends CheckBox {

        private static final ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        public final PlayerCard card;

        CheckboxWithCard(Context c, PlayerCard pc) {
            super(c);
            this.card = pc;
            this.setLayoutParams(l);
            this.setText(pc.getCity().getName());
            this.setBackgroundColor((Integer) (pc.getCity().getColor().getOriginal()));
            this.setTextColor(getContrastColor((Integer) (pc.getCity().getColor().getOriginal())));
        }

        public PlayerCard getCard() {
            return card;
        }

    }

    private List<CheckboxWithCard> init(LinearLayout cards, List<PlayerCard> cardsInHand) {
        List<CheckboxWithCard> result = new ArrayList<CheckboxWithCard>(cardsInHand.size());

        for (PlayerCard c : cardsInHand) {
            CheckboxWithCard ta = new CheckboxWithCard(OtherActions.this, c);
            result.add(ta);
            cards.addView(ta);
        }
        return result;

    }

    ;


    public static int getContrastColor(int color) {
        int r = 255 - Color.red(color);
        int g = 255 - Color.green(color);
        int b = 255 - Color.blue(color);
        return Color.rgb(r, g, b);
    }

}
