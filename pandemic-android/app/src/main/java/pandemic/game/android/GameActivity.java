package pandemic.game.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import j2a.android.BitmapImage;
import pandemic.game.board.Board;
import pandemic.game.board.OtherActionsProvider;
import pandemic.game.board.parts.Deck;
import pandemic.game.roles.Roles;

public class GameActivity extends Activity implements Observer {

    ImageView drawPane;
    //the intent is recreat during each 90rotation. this is saving state
    static Board board;
    int lastX;
    int lastY;
    int lastAct;
    int clicks;
    //functions for recalculate coordinations

    private int real(int coord, int current, int orig) {
        return (int) real((double) coord, (double) current, (double) orig);
    }

    private double real(double coord, double current, double orig) {
        return (orig / current) * coord;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        drawPane = (ImageView) findViewById(R.id.imageView);
        drawPane.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                board.move(
                        real(lastX, drawPane.getWidth(), board.getOrigWidth()),
                        real(lastY, drawPane.getHeight(), board.getOrigHeight()));
                lastAct = event.getAction();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("move");
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("up");
                        break;
                }
                return false;
            }
        });
        drawPane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(250);
                            clicks = 0;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
                switch (lastAct) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("move");
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("up");
                        break;
                }
                if (clicks < 2) {
                    if (lastAct != MotionEvent.ACTION_MOVE) {
                        try {
                            board.mainClick(
                                    real(lastX, drawPane.getWidth(), board.getOrigWidth()),
                                    real(lastY, drawPane.getHeight(), board.getOrigHeight()));
                        }catch (Exception ex){
                            //null city can strike
                            ex.printStackTrace();
                        }
                    }
                } else {
                    //lonng touch with the movement hack is not working on all devices. supporting double click for second action.
                    try {
                        board.second(
                                real(lastX, drawPane.getWidth(), board.getOrigWidth()),
                                real(lastY, drawPane.getHeight(), board.getOrigHeight()));
                    }catch (Exception ex){
                    //null city can strike
                    ex.printStackTrace();
                }
                }
            }
        });
        drawPane.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switch (lastAct) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("move");
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("up");
                        break;
                }
                if (lastAct != MotionEvent.ACTION_MOVE) {
                    try {
                        board.second(
                                real(lastX, drawPane.getWidth(), board.getOrigWidth()),
                                real(lastY, drawPane.getHeight(), board.getOrigHeight()));
                    }catch (Exception ex){
                        //null city can strike
                        ex.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
        try {
            if (board == null) {
                Roles roles = null;
                Bundle extras = getIntent().getExtras();
                if (extras == null) {
                    roles = new Roles(new String[]{});
                } else {
                    List<String> foundRoles = new ArrayList<String>(7);
                    for (String s : Roles.knownRoles) {
                        boolean is = extras.getBoolean(s);
                        if (is) {
                            foundRoles.add(s);
                        }
                    }
                    roles = new Roles(foundRoles.toArray(new String[foundRoles.size()]));
                }
                board = new Board(roles, new OtherActionsProvider() {

                    @Override
                    public void provide(Roles r, Deck d) {
                        Intent register = new Intent(GameActivity.this, OtherActions.class);
                        OtherActions.roles = r;
                        OtherActions.deck = d;
                        OtherActions.game = GameActivity.this;
                        GameActivity.this.startActivity(register);
                    }
                },extras.getBoolean("random"),extras.getInt("epidemies"), extras.getBoolean("symetric"),extras.getInt("longing"));
            }
            board.addObserver(GameActivity.this);
            board.notifyObservers();
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameActivity.this);
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

    @Override
    public void update(Observable o, Object o1) {
        if (drawPane != null) {
            drawPane.setImageBitmap((Bitmap) (((BitmapImage) o1).getOrigianl()));
        }
    }


}
