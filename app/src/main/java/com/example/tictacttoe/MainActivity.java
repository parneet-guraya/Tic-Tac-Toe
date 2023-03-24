package com.example.tictacttoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tictacttoe.databinding.ActivityMainBinding;
import com.example.tictacttoe.logic.Game;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding mainBinding;
    private Game game;
    private final ImageView[][] boardImage = new ImageView[3][3];
    private static final String BUTTON_STATE_ACTIVE = "BUTTON_STATE_ACTIVE";
    private static final String BUTTON_STATE_INACTIVE = "BUTTON_STATE_INACTIVE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        game = new Game();
        storeViews();
        setOnTileClickListener();

        mainBinding.currentPlayerTextView.setText(String.format("%s's turn", game.getCurrentPlayer()));


        mainBinding.restartButton.setOnClickListener(this);
        changeButtonState(BUTTON_STATE_INACTIVE);
    }

    private void storeViews() {
        boardImage[0][0] = mainBinding.row0Col0;
        boardImage[0][1] = mainBinding.row0Col1;
        boardImage[0][2] = mainBinding.row0Col2;

        boardImage[1][0] = mainBinding.row1Col0;
        boardImage[1][1] = mainBinding.row1Col1;
        boardImage[1][2] = mainBinding.row1Col2;

        boardImage[2][0] = mainBinding.row2Col0;
        boardImage[2][1] = mainBinding.row2Col1;
        boardImage[2][2] = mainBinding.row2Col2;

    }

    private void restartUIState() {
        clearTileImage();
        mainBinding.currentPlayerTextView.setText(String.format("%s's turn", game.getCurrentPlayer()));
    }

    private void setOnTileClickListener() {
        for (int i = 0; i < boardImage.length; i++) {
            for (int j = 0; j < boardImage.length; j++) {
                boardImage[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {

        // TODO: Learn about  'instanecof' keyword
        if (view instanceof ImageView) {

            if (!game.anyWinner && !game.anyDraw) {
                setMarkOnBoard((ImageView) view);
            }
            if (game.anyWinner) {
                showWinner();
                changeButtonState(BUTTON_STATE_ACTIVE);

            } else if (game.anyDraw) {
                showDraw();
                changeButtonState(BUTTON_STATE_ACTIVE);
            }

        }

        if (view instanceof Button) {
            restartGame();
            changeButtonState(BUTTON_STATE_INACTIVE);
        }
    }

    private void changeButtonState(String ButtonState) {
        if (ButtonState.equals(BUTTON_STATE_ACTIVE)) {
            mainBinding.restartButton.setClickable(true);
            // TODO: Should pass resolved color instead of resource id here: getResources().getColor(R.color.purple_700
            mainBinding.restartButton.setBackgroundColor(getResources().getColor(R.color.purple_700));
        } else if (ButtonState.equals(BUTTON_STATE_INACTIVE)) {
            mainBinding.restartButton.setClickable(false);
            mainBinding.restartButton.setBackgroundColor(getResources().getColor(R.color.grey));
        }
    }

    private void restartGame() {
        game.restart();
        restartUIState();
    }

    private void clearTileImage() {
        for (int i = 0; i < boardImage.length; i++) {
            for (int j = 0; j < boardImage.length; j++) {
                boardImage[i][j].setImageDrawable(null);
            }
        }
    }

    private void setMarkOnBoard(ImageView view) {
        for (int i = 0; i < boardImage.length; i++) {
            for (int j = 0; j < boardImage.length; j++) {
                ImageView tile = boardImage[i][j];
                if (view == tile) {
                    setMarkOnBoard(i, j, tile);
                    return;
                }
            }
        }
    }

    private void setMarkOnBoard(int row, int column, ImageView tile) {

        if (game.isValidMove(row, column)) {
            int imageRestId = game.setMarkImage(row, column);
            tile.setImageResource(imageRestId);
            mainBinding.currentPlayerTextView.setText(String.format("%s's turn", game.getCurrentPlayer()));
        } else {
            Snackbar.make(tile, "Already Occupied!", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private void showWinner() {
        String winnerText = String.format("%s's Winner", game.getCurrentPlayer());
        Snackbar.make(mainBinding.currentPlayerTextView, winnerText, Snackbar.LENGTH_SHORT)
                .show();
        mainBinding.currentPlayerTextView.setText(winnerText);
    }

    private void showDraw() {
        String text = "It's a Draw!!";
        Snackbar.make(mainBinding.currentPlayerTextView, text, Snackbar.LENGTH_SHORT)
                .show();
        mainBinding.currentPlayerTextView.setText(text);
    }
}