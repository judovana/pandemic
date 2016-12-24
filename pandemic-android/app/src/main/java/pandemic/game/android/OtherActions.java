package pandemic.game.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.InfecetionRate;
import pandemic.game.cards.Card;
import pandemic.game.cards.PlayerCard;
import pandemic.game.roles.Role;
import pandemic.game.roles.Roles;

public class OtherActions extends Activity {

    public static Roles roles;
    public static Deck deck;
    public static GameActivity game;


    private class CardListAdapter extends ArrayAdapter<PlayerCard>{

        final private List<PlayerCard> oo;

        public CardListAdapter(Context context,int resource, List<PlayerCard> objects) {
            super(context, resource, objects);
            this.oo=objects;
        }

        int selected=-1;
        public View getView(int position, View convertView, ViewGroup parent) {
            CheckBox ta = new CheckBox(OtherActions.this);
            ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ta.setLayoutParams(l);
            ta.setText(oo.get(position).getCity().getName());
            ta.setBackgroundColor((Integer)(oo.get(position).getCity().getColor().getOriginal()));
            ta.setTextColor(getContrastColor((Integer)(oo.get(position).getCity().getColor().getOriginal())));
            return ta;
        }

    };

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


        ViewGroup.LayoutParams VparamsMW1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams VparamsWW1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams LparamsWW2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams LparamsMW2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout line = new LinearLayout(this);
        line.setOrientation(LinearLayout.HORIZONTAL);
        line.setLayoutParams(VparamsMW1);


        TextView t = new TextView(this);
        t.setText(roles.getCurrentPlayer().getName());
        t.setLayoutParams(LparamsWW2);
        line.addView(t);

        Button b = new Button(this);
        b.setText("Build station");
        t.setLayoutParams(LparamsWW2);
        line.addView(b);

        b = new Button(this);
        b.setText("Cure disease");
        t.setLayoutParams(LparamsWW2);
        line.addView(b);

        b = new Button(this);
        b.setText("Invent Cure");
        t.setLayoutParams(LparamsWW2);
        line.addView(b);

        ListView cards = new ListView(this);
        cards.setAdapter(new CardListAdapter(this, android.R.layout.simple_list_item_1, roles.getCurrentPlayer().getCardsInHand()));
        cards.setLayoutParams(LparamsWW2);
        setListViewHeightBasedOnChildren(cards);
        line.addView(cards);

        b = new Button(this);
        b.setText("Drop cards");
        t.setLayoutParams(LparamsWW2);
        line.addView(b);

        main.addView(line);

        for (Role r : roles.getPlayersInCity(roles.getCurrentPlayer().getCity())){
            if (r != roles.getCurrentPlayer()) {
                line = new LinearLayout(this);
                line.setOrientation(LinearLayout.HORIZONTAL);
                line.setLayoutParams(VparamsMW1);

                b = new Button(this);
                b.setText("*******");
                b.setLayoutParams(LparamsWW2);
                b.setEnabled(false);
                line.addView(b);

                t = new TextView(this);
                t.setText(r.getName());
                t.setLayoutParams(LparamsWW2);
                line.addView(t);

                b = new Button(this);
                b.setText("Give to "+ roles.getCurrentPlayer().getName());
                t.setLayoutParams(LparamsWW2);
                line.addView(b);

                b = new Button(this);
                b.setText("Take from "+ roles.getCurrentPlayer().getName());
                t.setLayoutParams(LparamsWW2);
                line.addView(b);


                cards = new ListView(this);
                cards.setAdapter(new CardListAdapter(this, android.R.layout.simple_list_item_1, r.getCardsInHand()));
                cards.setLayoutParams(LparamsWW2);
                setListViewHeightBasedOnChildren(cards);
                line.addView(cards);

                b = new Button(this);
                b.setText("Drop cards");
                t.setLayoutParams(LparamsWW2);
                line.addView(b);


                main.addView(line);
            }

        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static int getContrastColor(int color) {
        int r = 255-Color.red(color);
        int g = 255-Color.green(color);
        int b = 255-Color.blue(color);
        return Color.rgb(r,g,b);
    }

}
