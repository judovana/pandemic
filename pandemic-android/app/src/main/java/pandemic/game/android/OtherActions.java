package pandemic.game.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pandemic.game.board.parts.Deck;
import pandemic.game.board.parts.Drugs;
import pandemic.game.board.parts.tokens.Cubes;
import pandemic.game.cards.PlayerCard;
import pandemic.game.roles.Role;
import pandemic.game.roles.Roles;
import pandemic.game.roles.implementations.OperationExpert;
import pandemic.game.roles.implementations.Researcher;

public class OtherActions extends Activity {

    public static Roles roles;
    public static Deck deck;
    public static GameActivity game;

    private final String CD = "Cure disease";

    private static final ViewGroup.LayoutParams VparamsMW1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private static final LinearLayout.LayoutParams LparamsWW2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    private void enableOE(Button station) {
        if (roles.getCurrentPlayer() instanceof OperationExpert && !roles.getCurrentPlayer().getCity().haveStation()) {
            station.setEnabled(true);
        } else {
            station.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_actions);
        this.setTitle(roles.getCurrentPlayer().getTitle());

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
                game.board.drawBoard();
                OtherActions.this.finish();
            }
        });


        CurrentPlayerView cp = new CurrentPlayerView(roles.getCurrentPlayer(), this);
        main.addView(cp);

        List<OtherPlayerView> others = new ArrayList<OtherPlayerView>(7);
        for (Role r : roles.getPlayersInCity(roles.getCurrentPlayer().getCity())) {
            if (r != roles.getCurrentPlayer()) {
                OtherPlayerView op = new OtherPlayerView(r, roles.getCurrentPlayer(), this);
                main.addView(op);
                others.add(op);
                op.setListener(cp, others);
            }

        }
        cp.setListener(others);

    }

    private abstract class PlayerView extends LinearLayout {

        protected final Role thisPlayer;
        //android is to dummy so we need to duplicate variable here
        protected final Context mContext;
        protected List<CheckboxWithCard> checks;
        protected LinearLayout cards;
        protected Button drop;

        public void diableAll(){
            drop.setEnabled(false);
        }

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
            Button b = addButon("Drop card(s)");
            b.setEnabled(false);
            return b;
        }

        protected Button addButon(String title) {
            Button b = new Button(mContext);
            b.setText(title);
            b.setLayoutParams(LparamsWW2);
            this.addView(b);
            return b;
        }

        protected void addCards() {
            cards = new LinearLayout(mContext);
            cards.setOrientation(LinearLayout.VERTICAL);
            cards.setLayoutParams(LparamsWW2);
            checks = init(cards, thisPlayer.getCardsInHand());
            this.addView(cards);
        }

        public Set<j2a.Color> getSelectedColors() {
            List<PlayerCard> pc = getSelectedCards();
            Set<j2a.Color> r = new HashSet<j2a.Color>(pc.size());
            for (PlayerCard card : pc) {
                r.add(card.getCity().getColor());
            }
            return r;
        }

        public List<PlayerCard> getSelectedCards() {
            List<PlayerCard> result = new ArrayList<PlayerCard>(checks.size());
            for (CheckboxWithCard ch : checks) {
                if (ch.isChecked()) {
                    result.add(ch.getCard());
                }
            }
            return result;
        }

        protected void setCheckListener(CompoundButton.OnCheckedChangeListener ocl) {
            for (CheckboxWithCard ch : checks) {
                ch.setOnCheckedChangeListener(ocl);
            }
        }

        public abstract void resetListeners();

        protected OnClickListener createDropListener() {
            return new OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<PlayerCard> l = getSelectedCards();
                    for (PlayerCard c : l) {
                        thisPlayer.discardCard(c);
                        deck.returnCard(c);
                        diableAll();
                    }
                    resetCheckBoxes();

                }
            };
        }

        public void resetCheckBoxes(){
            cards.removeAllViews();
            checks=init(cards, thisPlayer.getCardsInHand());
            resetListeners();

        }
    }

    private class OtherPlayerView extends PlayerView {
        protected final Role currentPlayer;
        private final Button giveto;
        private final Button taketo;
        private CurrentPlayerView cpv;
        private List<OtherPlayerView> opvs;

        public void diableAll(){
            super.diableAll();
            giveto.setEnabled(false);
            taketo.setEnabled(false);
        }

        public OtherPlayerView(Role thisPlayer, Role currentPlayer, Context parent) {
            super(thisPlayer, parent);
            this.currentPlayer = currentPlayer;
            //Button tab = addButon("*******");
            //tab.setEnabled(false);
            setName();
            giveto = addButon("Give to " + currentPlayer.getName());
            taketo = addButon("Take from " + currentPlayer.getName());
            addCards();
            drop = addDropCardsButon();
            drop.setOnClickListener(createDropListener());
            diableAll();
        }

        @Override
        public void resetListeners() {
            setListener(cpv, opvs);
        }

        public void setListener(CurrentPlayerView ccpv, List<OtherPlayerView> others ) {
            this.cpv=ccpv;
            this.opvs=others;
            setCheckListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (getSelectedCards().size() == 1 && (getSelectedCards().get(0).getCity().equals(thisPlayer.getCity()) || thisPlayer instanceof Researcher)) {
                        giveto.setEnabled(true);
                    } else {
                        giveto.setEnabled(false);
                    }
                    if (getSelectedCards().size() >= 1) {
                        drop.setEnabled(true);
                    } else {
                        drop.setEnabled(false);
                    }
                }
            });
            taketo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cpv.getSelectedCards().size() != 1) {
                        throw new RuntimeException("only one can be selected");
                    }
                    PlayerCard c = cpv.getSelectedCards().get(0);
                    currentPlayer.giveTo(c,thisPlayer);
                    OtherActions.this.setTitle(roles.getCurrentPlayer().getTitle());
                    for (OtherPlayerView other : opvs) {
                        other.diableAll();
                    }
                    resetCheckBoxes();
                    cpv.resetCheckBoxes();
                    cpv.diableAll();
                }
            });

            giveto.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getSelectedCards().size() != 1) {
                        throw new RuntimeException("only one can be selected");
                    }
                    PlayerCard c = getSelectedCards().get(0);
                    currentPlayer.takeFrom(c,thisPlayer);
                    OtherActions.this.setTitle(roles.getCurrentPlayer().getTitle());
                    for (OtherPlayerView other : opvs) {
                        other.diableAll();
                    }
                    resetCheckBoxes();
                    cpv.resetCheckBoxes();
                    cpv.diableAll();
                }
            });
        }

        public void setTake(boolean b) {
            taketo.setEnabled(b);
        }

    }

    private class CurrentPlayerView extends PlayerView {

        private final Button invent;
        final Button bs;
        private List<OtherPlayerView> others;

        @Override
        public void resetListeners() {
            setListener(others);
        }

        public void diableAll(){
            super.diableAll();
            enableOE(bs);
            invent.setEnabled(false);
        }

        public void setListener(final List<OtherPlayerView> others) {
            this.others=others;
            setCheckListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (getSelectedCards().size() == roles.cardsToCure() && getSelectedColors().size() == 1 && thisPlayer.getCity().haveStation()) {
                        invent.setEnabled(true);
                    } else {
                        invent.setEnabled(false);
                    }
                    if (getSelectedCards().size() >= 1) {
                        drop.setEnabled(true);
                    } else {
                        drop.setEnabled(false);
                    }
                    enableOE(bs);
                    if (getSelectedCards().size() == 1 && getSelectedCards().get(0).getCity().equals(thisPlayer.getCity())) {
                        if (!roles.getCurrentPlayer().getCity().haveStation()) {
                            bs.setEnabled(true);
                        }
                    }
                    if (getSelectedCards().size() == 1 && (getSelectedCards().get(0).getCity().equals(thisPlayer.getCity()) || thisPlayer instanceof  Researcher)) {
                        for (OtherPlayerView oo : others) {
                            oo.setTake(true);
                        }
                    } else {
                        for (OtherPlayerView oo : others) {
                            oo.setTake(false);
                        }
                    }
                }
            });
        }

        public CurrentPlayerView(final Role thisPlayer, Context parent) {
            super(thisPlayer, parent);
            setName();
            bs = addButon("Build station");
            enableOE(bs);
            bs.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (roles.getCurrentPlayer() instanceof OperationExpert) {
                        roles.getCurrentPlayer().buildStation(null, null);
                        enableOE(bs);
                        OtherActions.this.setTitle(roles.getCurrentPlayer().getTitle());
                        return;
                    }
                    if (getSelectedCards().size() != 1) {
                        throw new RuntimeException("only one can be selected");
                    }
                    PlayerCard c = getSelectedCards().get(0);
                    if (c.getCity().equals(roles.getCurrentPlayer().getCity())) {
                        bs.setEnabled(false);
                        drop.setEnabled(false);
                        invent.setEnabled(false);
                        roles.getCurrentPlayer().buildStation(c,deck);
                        OtherActions.this.setTitle(roles.getCurrentPlayer().getTitle());
                        for (OtherPlayerView other : others) {
                            other.diableAll();
                        }
                        resetCheckBoxes();
                    } else {
                        throw new RuntimeException("only current city may be selected");
                    }
                }
            });
            final Button cureDisease = addButon(CD);
            tuneCureButton(roles, cureDisease);
            cureDisease.setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View e) {
                            final List<Cubes> cubes = roles.getCurrentPlayer().getCity().getCubes();
                            Set<j2a.Color> colors = new HashSet<j2a.Color>();
                            for (Cubes cube : cubes) {
                                colors.add(cube.getColor());
                            }
                            if (colors.size() == 1) {
                                roles.getCurrentPlayer().setActionCounter();
                                OtherActions.this.setTitle(roles.getCurrentPlayer().getTitle());
                                j2a.Color cc = cubes.get(0).getColor();
                                if (Drugs.self.isCuredForCubesRemoval(cc)) {
                                    int inLength = cubes.size();
                                    for (int i = 0; i < inLength; i++) {
                                        cubes.remove(0);
                                    }
                                    Drugs.self.checkFixed(cc);
                                    tuneCureButton(roles, cureDisease);
                                } else {
                                    cubes.remove(0);
                                    Drugs.self.checkFixed(cc);
                                    tuneCureButton(roles, cureDisease);
                                }
                            } else {
                                PopupMenu jpp = new PopupMenu(OtherActions.this, cureDisease);
                                for (final j2a.Color color : colors) {

                                    int localCount = 0;
                                    for (Cubes cube : cubes) {
                                        if (cube.getColor().equals(color)) {
                                            localCount++;

                                        }
                                    }

                                    String hexColor = String.format("#%06X", (0xFFFFFF & (Integer)color.getOriginal()));
                                    MenuItem jpps = jpp.getMenu().add(Html.fromHtml("<font color='"+hexColor+"'>"+"_-_-_-_ ( " + localCount + " ) _-_-_-_"+"</font>"));
                                    jpps.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                                        @Override
                                        public boolean onMenuItemClick(MenuItem e) {
                                            int targetColor =  (Integer)color.getOriginal();
                                            roles.getCurrentPlayer().setActionCounter();
                                            OtherActions.this.setTitle(roles.getCurrentPlayer().getTitle());
                                            for (int i = 0; i < cubes.size(); i++) {
                                                Cubes cube = cubes.get(i);
                                                if (cube.getColor().equals(new j2a.android.Color(targetColor))) {
                                                    cubes.remove(i);
                                                    i--;
                                                    if (Drugs.self.isCuredForCubesRemoval(new j2a.android.Color(targetColor))) {
                                                        continue;
                                                    } else {
                                                        break;
                                                    }
                                                }
                                            }
                                            Drugs.self.checkFixed(new j2a.android.Color(targetColor));
                                            tuneCureButton(roles, cureDisease);
                                            return true;
                                        }
                                    });
                                }
                                jpp.show();
                            }
                        }
                    }
            );

            invent = addButon("Invent Cure");
            invent.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        if (!thisPlayer.getCity().haveStation()) {
                            return;
                        }
                        List<PlayerCard> l = getSelectedCards();
                        j2a.Color c = l.get(0).getCity().getColor();
                        for (PlayerCard l1 : l) {
                            if (!l1.getCity().getColor().equals(c)) {
                                return;
                            }
                        }
                        for (PlayerCard l1 : l) {
                            roles.getCurrentPlayer().getCardsInHand().remove(l1);
                            deck.returnCard(l1);
                        }
                        roles.getCurrentPlayer().setActionCounter();
                        OtherActions.this.setTitle(roles.getCurrentPlayer().getTitle());
                        Drugs.self.cure(c);
                        resetCheckBoxes();
                    }
                }
            });
            addCards();
            drop = addDropCardsButon();
            drop.setOnClickListener(createDropListener());
            diableAll();

        }
    }


    private static class CheckboxWithCard extends CheckBox {

        private static final ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        public final PlayerCard card;

        CheckboxWithCard(Context c, PlayerCard pc) {
            super(c);
            this.card = pc;
            this.setLayoutParams(l);
            if (pc.getCity() != null) {
                this.setText(pc.getCity().getName());
                this.setBackgroundColor((Integer) (pc.getCity().getColor().getOriginal()));
                this.setTextColor(getContrastColor((Integer) (pc.getCity().getColor().getOriginal())));
            }else{
                this.setText("null city");
            }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        game.board.drawBoard();
    }

    public final void tuneCureButton(final Roles roles, final Button cureDisease) {
        if (roles.getCurrentPlayer().getCity().getCubes().size() <= 0) {
            cureDisease.setEnabled(false);
            cureDisease.setText(CD + " (" + roles.getCurrentPlayer().getCity().getCubes().size() + ")");
        } else {
            cureDisease.setEnabled(true);
            cureDisease.setText(CD + " (" + roles.getCurrentPlayer().getCity().getCubes().size() + ")");
        }
        this.setTitle(roles.getCurrentPlayer().getTitle());
    }


}
