package org.example.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import org.example.ai.RuleBasedAI;
import org.example.entities.AIPlayer;
import org.example.entities.AbstractPlayer;
import org.example.entities.Board;
import org.example.entities.HumanPlayer;
import org.example.enums.BoardStatus;
import org.example.enums.EndCondition;
import org.example.enums.FirstMove;

// we implement the onClickListener - so this means there
//will be an onClick method defined for ALL the views later
//in the onClick method

/**
 * TODO:
 *  - Storing stete to handle rotation shifts
 *  - Implementing settings menu for letting the user select and change settings
 *  - Create settings for indicating AI players
 *  - Create settings for specifying winning conditions
 */
public class MainActivity extends Activity implements OnClickListener {

	private boolean gameEnded = false;
	private EndCondition endCondition = EndCondition.FULL_BOARD;

	private int[] fieldIds = { R.id.felt1, R.id.felt2, R.id.felt3, R.id.felt4, R.id.felt5,
			R.id.felt6, R.id.felt7, R.id.felt8, R.id.felt9};
    private AbstractPlayer activePlayer;
	private int turnCounter = 0; // To keep track of how many pieces have been placed.

    private AbstractPlayer playerOne;
    private AbstractPlayer playerTwo;

	private Board board;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		View table = findViewById(R.id.table);
		table.setOnClickListener(this);

		board = new Board();
		// Set the status for all board fields to EMPTY
		for(int i : fieldIds) {
			//we add clicklisteners, this, to all our fields
			View field = findViewById(i);
			field.setOnClickListener(this);

			board.setSquareStatus(i, BoardStatus.EMPTY);
		}

		// Player one
//		playerOne = new AIPlayer(fieldIds, board);
        playerOne = new HumanPlayer();
//        playerOne.setAiAlgorithm(new RuleBasedAI());
//		playerOne.setAiAlgorithm(new MinmaxAI());
		playerOne.setSeed(BoardStatus.CROSS);
//        ((AIPlayer)playerOne).centerFirstMove = FirstMove.EDGE;

        // Player two
		playerTwo = new AIPlayer(fieldIds, board);
//		playerTwo = new HumanPlayer();
//		playerTwo.setAiAlgorithm(new MinmaxAI());
        playerTwo.setAiAlgorithm(new RuleBasedAI());
		playerTwo.setOppenent(playerOne);
		playerTwo.setSeed(BoardStatus.NOUGHT);
//        ((AIPlayer)playerTwo).centerFirstMove = FirstMove.EDGE;

		playerOne.setOppenent(playerTwo);

		// Active player will always be player one from the start of the game
		activePlayer = playerOne;

		// Adding a delay to make sure the game board is shown before the first move is done.
        // This is primarily if player one is an AI player.
        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextMove();
            }
        }, 500);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    /**
     * This method will handle the click event when a human player is taking her turn.
     * @param view
     */
	@Override
	public void onClick(View view) {
		// TODO Here you need to get the ID of the view 
		// being pressed and then if the view is pressed
		// you need to first put a "X", then next time 
		// put a "O" and also make sure that you cannot
		// put a "O" or a "X" if there is already something.
        ImageView image = (ImageView) view;
        performMove(image.getId());
	}

    /**
     * Method for performing the actual move on the board and displaying the visuals.
     * The method will check for game overs and if the move is an illegal move.
     * @param squareId
     */
	public void performMove(int squareId) {
        Context context = getApplicationContext();

        if (gameOver()) {
            gameEnded = true;
            Toast gameEndedToast = Toast.makeText(context, R.string.no_more_moves, Toast.LENGTH_LONG);
            gameEndedToast.setGravity(Gravity.BOTTOM, 0, 0);
            gameEndedToast.show();
        } else {
            System.out.println("field " + squareId + " pressed");
            if (board.checkSquareStatus(squareId).equals(BoardStatus.EMPTY)) {
                setImage(squareId);

                turnCounter++;
                // We need to figure out how the game ended.
                // Both check the number of round played
                // and if any player have 3 pieces in a row.
                if (turnCounter >= 5 && board.checkForWinCondition(activePlayer.getSeed())) {
                    // Somebody won! End the game!
                    gameEnded = true;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Game Over!");
                    sb.append(System.getProperty("line.separator"));
                    sb.append(activePlayer.getSeed() + " won");
                    Toast gameEndedToast = Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG);
                    gameEndedToast.setGravity(Gravity.CENTER, 0, 0);
                    gameEndedToast.show();
                } else if (gameOver()) {
                    gameEnded = true;
                    Toast gameEndedToast = Toast.makeText(context, R.string.no_more_moves, Toast.LENGTH_LONG);
                    gameEndedToast.setGravity(Gravity.BOTTOM, 0, 0);
                    gameEndedToast.show();
                }
                activePlayer = activePlayer == playerTwo ? playerOne : playerTwo;
            } else {
                if (activePlayer.getClass().equals(HumanPlayer.class)) {
                    Toast tryAgain = Toast.makeText(context, R.string.illegal_move, Toast.LENGTH_SHORT);
                    tryAgain.setGravity(Gravity.CENTER, 0, 0);
                    tryAgain.show();
                }
            }
        }

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
           @Override
            public void run() {
               if (!gameOver()) {
                   nextMove();
               }
           }
        }, 500);
    }

    /**
     * Eventhandler for clicking the button to start a new game
     * @param view
     */
	public void onNewGameClicked(View view) {
		gameEnded = false;
		activePlayer = playerOne;
		turnCounter = 0;
		// Set the status for all board fields to EMPTY
		for(int i : fieldIds) {
			ImageView image = findViewById(i);
			image.setImageResource(R.drawable.blank);

			board.setSquareStatus(i, BoardStatus.EMPTY);
		}

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextMove();
            }
        }, 500);
	}

    /**
     * Method that will call the AI player to ask for the next move, or if the active player is a
     * human player, the game will await the move.
     */
    private void nextMove() {
        // If the active player is an AI, then ask it to make a takeTurn!
        if (activePlayer.getClass().equals(AIPlayer.class)) {
            activePlayer.takeTurn(this);
        }
    }

    /**
     * This method will place the correct image on the square specified by the parameter.
     * @param squareId
     */
	private void setImage(int squareId) {
	    ImageView image = findViewById(squareId);
	    if (activePlayer.getSeed().equals(BoardStatus.CROSS)) {
            image.setImageResource(R.drawable.kryds);
        } else {
            image.setImageResource(R.drawable.bolle);
        }
        board.setSquareStatus(image.getId(), activePlayer.getSeed());
	}

    /**
     * Method for validating whether a game over state has been achieved.
     * @return
     */
	private boolean gameOver() {
	    boolean retValue = false;

	    switch(endCondition) {
            case FULL_BOARD:
                retValue = board.getAllEmptySquares().size() == 0 || gameEnded;
                break;
            case INFINITY:
                retValue = gameEnded;
                break;
            case SIX_PIECE:
                retValue = turnCounter >= 6 || gameEnded;
                break;
        }

        return retValue;
    }
}
