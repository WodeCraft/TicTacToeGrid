package org.example.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import org.example.enums.BoardStatus;

import java.util.HashMap;
import java.util.Map;

// we implement the onClickListener - so this means there
//will be an onClick method defined for ALL the views later
//in the onClick method
public class MainActivity extends Activity implements OnClickListener {

	private boolean gameEnded = false;
	private int[] fieldIds = { R.id.felt1, R.id.felt2, R.id.felt3, R.id.felt4, R.id.felt5,
			R.id.felt6, R.id.felt7, R.id.felt8, R.id.felt9};
	private Map<Integer, int[]> winConditions = new HashMap<>();
	private int turn = 0; // To keep track of which player has the next turn. 0=Cross
	private int turnCounter = 0; // To keep track of how many pieces have been placed.
	private Map<Integer, BoardStatus> board = new HashMap<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		View table = findViewById(R.id.table);
		table.setOnClickListener(this);

		// Set the status for all board fields to Empty
		for(int i : fieldIds) {
			//we add clicklisteners, this, to all our fields
			View field = findViewById(i);
			field.setOnClickListener(this);

			board.put(i, BoardStatus.Empty);
		}

		winConditions.put(R.id.felt1, new int[] {1, 4, 7});
		winConditions.put(R.id.felt2, new int[] {1, 5});
		winConditions.put(R.id.felt3, new int[] {1, 6, 8});
		winConditions.put(R.id.felt4, new int[] {2, 4});
		winConditions.put(R.id.felt5, new int[] {2, 5, 7, 8});
		winConditions.put(R.id.felt6, new int[] {2, 6});
		winConditions.put(R.id.felt7, new int[] {3, 4, 8});
		winConditions.put(R.id.felt8, new int[] {3, 5});
		winConditions.put(R.id.felt9, new int[] {3, 6, 7});
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

	@Override
	public void onClick(View view) {
		// TODO Here you need to get the ID of the view 
		// being pressed and then if the view is pressed
		// you need to first put a "X", then next time 
		// put a "O" and also make sure that you cannot
		// put a "O" or a "X" if there is already something.
		Context context = getApplicationContext();
		if (turnCounter >= 6 || gameEnded) {
			Toast gameEnded = Toast.makeText(context, R.string.no_more_moves, Toast.LENGTH_LONG);
			gameEnded.setGravity(Gravity.BOTTOM, 0, 0);
			gameEnded.show();
		} else {
			ImageView image = (ImageView) view;
			int imageId = image.getId();

			System.out.println("field " + imageId + " pressed");
			if (board.get(imageId).equals(BoardStatus.Empty)) {
				setImage(image);

				turn = turn == 1 ? 0 : 1;
				turnCounter++;
			} else {
				Toast tryAgain = Toast.makeText(context, R.string.illegal_move, Toast.LENGTH_SHORT);
				tryAgain.setGravity(Gravity.CENTER, 0, 0);
				tryAgain.show();
			}

			// We need to figure out how the game ended.
			// Both check the number of round played
			// and if any player have 3 pieces in a row.
			if (turnCounter >= 5 && checkWincondition(imageId)) {
				// Somebody won! End the game!
				gameEnded = true;
				StringBuilder sb = new StringBuilder();
				sb.append("Game Over!");
				sb.append(System.getProperty("line.separator"));
				sb.append(turn == 1 ? "Cross won": "Nought won");
				Toast gameEndedToast = Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG);
				gameEndedToast.setGravity(Gravity.CENTER, 0, 0);
				gameEndedToast.show();
			} else if (turnCounter >= 6) {
				gameEnded = true;
				Toast gameEndedToast = Toast.makeText(context, R.string.no_more_moves, Toast.LENGTH_LONG);
				gameEndedToast.setGravity(Gravity.BOTTOM, 0, 0);
				gameEndedToast.show();
			}
		}
	}

	public void onNewGameClicked(View view) {
		gameEnded = false;
		turn = 0;
		turnCounter = 0;
		// Set the status for all board fields to Empty
		for(int i : fieldIds) {
			ImageView image = findViewById(i);
			image.setImageResource(R.drawable.blank);

			board.put(i, BoardStatus.Empty);
		}
	}

	private boolean checkWincondition(int fieldId) {

		/**
		 * Win1: felt1 + felt2 + felt3
		 * Win2: felt4 + felt5 + felt6
		 * Win3: felt7 + felt8 + felt9
		 *
		 * Win4: felt1 + felt4 + felt7
		 * Win5: felt2 + felt5 + felt8
		 * Win6: felt3 + felt6 + felt9
		 *
		 * Win7: felt1 + felt5 + felt9
		 *
		 * Win8: felt3 + felt5 + felt7
		 */
		boolean winner = false;

		for(int condition : winConditions.get(fieldId)) {
			switch (condition) {
				case 1:
					winner = checkRow(board.get(R.id.felt1), board.get(R.id.felt2), board.get(R.id.felt3));
					break;
				case 2:
					winner = checkRow(board.get(R.id.felt4), board.get(R.id.felt5), board.get(R.id.felt6));
					break;
				case 3:
					winner = checkRow(board.get(R.id.felt7), board.get(R.id.felt8), board.get(R.id.felt9));
					break;
				case 4:
					winner = checkRow(board.get(R.id.felt1), board.get(R.id.felt4), board.get(R.id.felt7));
					break;
				case 5:
					winner = checkRow(board.get(R.id.felt2), board.get(R.id.felt5), board.get(R.id.felt8));
					break;
				case 6:
					winner = checkRow(board.get(R.id.felt3), board.get(R.id.felt6), board.get(R.id.felt9));
					break;
				case 7:
					winner = checkRow(board.get(R.id.felt1), board.get(R.id.felt5), board.get(R.id.felt9));
					break;
				case 8:
					winner = checkRow(board.get(R.id.felt3), board.get(R.id.felt5), board.get(R.id.felt7));
					break;
			}
			if (winner) {
				return true;
			}
		}

		return false;
/*
		return checkRow(board.get(R.id.felt1), board.get(R.id.felt2), board.get(R.id.felt3))
				&& checkRow(board.get(R.id.felt4), board.get(R.id.felt5), board.get(R.id.felt6))
				&& checkRow(board.get(R.id.felt7), board.get(R.id.felt8), board.get(R.id.felt9))
				&& checkRow(board.get(R.id.felt1), board.get(R.id.felt4), board.get(R.id.felt7))
				&& checkRow(board.get(R.id.felt2), board.get(R.id.felt5), board.get(R.id.felt8))
				&& checkRow(board.get(R.id.felt3), board.get(R.id.felt6), board.get(R.id.felt9))
				&& checkRow(board.get(R.id.felt1), board.get(R.id.felt5), board.get(R.id.felt9))
				&& checkRow(board.get(R.id.felt3), board.get(R.id.felt5), board.get(R.id.felt7));
*/

	}

	private boolean checkRow(BoardStatus field1, BoardStatus field2, BoardStatus field3) {
		return field1.equals(field2) && field1.equals(field3) && !field1.equals(BoardStatus.Empty);
	}

	private void setImage(ImageView image) {
		if (turn == 0) {
			image.setImageResource(R.drawable.kryds);
			board.put(image.getId(), BoardStatus.Cross);
		} else {
			image.setImageResource(R.drawable.bolle);
			board.put(image.getId(), BoardStatus.Nought);
		}
	}
}
