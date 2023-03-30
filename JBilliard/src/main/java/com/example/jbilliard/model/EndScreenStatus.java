package com.example.jbilliard.model;

import java.io.Serializable;
import java.util.Observable;


//status for the EndScreen

public class EndScreenStatus extends Observable implements Serializable {

	private String gameOverMessage;
	
	public void gameOverSuccess(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
		gameOverMessage = "Player "+ " successfully"
				+ "pocketed all the balls. Congratulations,"
				+ "you win!";

    	setChanged();
    	notifyObservers(); 
	}
	
	public void gameOverFailBlackBall(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
		gameOverMessage = "Oops! Player " +
				" prematurely pocketed the black ball. "
				+ "Unfortunately, you lost!";

    	setChanged();
    	notifyObservers();
	}

	public void gameOverFailCueBall(int currPlayerInd) {
		int playerNum = currPlayerInd + 1;
		gameOverMessage = "Oops! Player " +
				" prematurely pocketed the cue ball. "
				+ "Unfortunately, you lost!";

		setChanged();
		notifyObservers();
	}

	public void gameOverFailNegativePoint(int currPlayerInd, int score) {
		int playerNum = currPlayerInd + 1;
		gameOverMessage = "Oops! Player " +
				" got negative score. "
				+ "Unfortunately, the game is finished!"
				+ "\n[SCORE]: " + score;

		setChanged();
		notifyObservers();
	}
	
	public String getGameOverMessage() { return gameOverMessage; }
}
