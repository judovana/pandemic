package pandemic.game.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;


import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import j2a.BitmapImage;
import pandemic.game.board.Board;
import pandemic.game.board.OtherActionsProvider;
import pandemic.game.board.parts.Deck;
import pandemic.game.roles.Roles;

public class GameActivity extends Activity implements Observer {

    ImageView drawPane;
    Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        drawPane = (ImageView) findViewById(R.id.imageView);
        //board = new Board(new Roles(args), new OtherActionsProvider() {
        try {
            board = new Board(new Roles(new String[]{"scientist"}), new OtherActionsProvider() {

                @Override
                public void provide(Roles r, Deck d) {
                    //new OtherActions(r, d);
                }
            });

            board.addObserver(GameActivity.this);
            board.notifyObservers();
        }catch (IOException ex){
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
