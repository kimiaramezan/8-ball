package com.example.jbilliard.model;

import java.io.Serializable;
import java.util.Observable;


//the status of the pool game after each turn.

public class PoolGameStatus extends Observable implements Serializable {
	private String player1PointsStatus = "0";
	private String player2PointsStatus = "0";
    private String player1BallColorStatus = "N/A";
    private String player2BallColorStatus = "N/A";
    private String turnStatus;
    private String lastTurnStatus;
    private String cueBallStatus;
	private int movesLeft;

    public PoolGameStatus() {
    	turnStatus = " your turn!";
    	lastTurnStatus = "Click and drag the cue stick to take a shot"
    			+ " at the cue ball.";
    }

	public void updateMovesLeft(int newMovesLeft) {
		movesLeft = newMovesLeft;
	}

    public void setPoints(int player1Points, int player2Points) {
    	this.player1PointsStatus = player1Points + "";
    	this.player2PointsStatus = player2Points + "";
    	setChanged();
    	notifyObservers();
    }
    
    public void setBallColors(int currPlayerInd, int player1ID) {
		player1BallColorStatus = "POCKET ANY BALL";
		player2BallColorStatus = "POCKET ANY BALL";
		setChanged();
    	notifyObservers();
    }
    
    public void setTurnStatus(int currPlayerInd, boolean streak,
    		boolean canPocketBall) {
    	
    	int playerNum = currPlayerInd + 1;
   
    	String streakText = "";
    	if (streak) {
    		streakText = " again";
    	}
    	
    	String BallPrompt = "";
    	if (canPocketBall) BallPrompt = " You may now pocket the "
    			+ "black ball to win.";
    	
    	turnStatus = "Player " + playerNum + ", "
    			+ "your turn" + streakText + "!" + BallPrompt;
    	
    	setChanged();
    	notifyObservers();
    }
    
    public void setLastTurnStatusPlayerFailed(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	lastTurnStatus = "Player " + " did not "
    			+ "pocket a ball.";
    	setChanged();
    	notifyObservers();
    }
    
	public void setLastTurnStatusPlayerIllegalBreak(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	lastTurnStatus = "Player " + " did not "
    			+ "get four bumper collisions "
    			+ "- Illegal break.";
    	
    	setChanged();
    	notifyObservers();
    }

    public void setLastTurnStatusPlayerSucceeded(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	lastTurnStatus = "Player " +" has pocketed "
    			+ "a ball.";
    	
    	setChanged();
    	notifyObservers();
    }
    
    public void setLastTurnStatusPocketedOther(int currPlayerInd) {
    	int playerNum = currPlayerInd + 1;
    	int otherPlayerNum = (currPlayerInd+1)%2 + 1;
    	lastTurnStatus = "Player " +" pocketed ";
    	setChanged();
    	notifyObservers();
    }
    
    public void unsetCueBallStatus() {
    	cueBallStatus = "";
    	setChanged();
    	notifyObservers();
    }
    
    public String getPlayer1PointsStatus() { return player1PointsStatus; }
    public String getPlayer1BallColorStatus() { return player1BallColorStatus; }
    public String getTurnStatus() { return turnStatus; }
    public String getLastTurnStatus() { return lastTurnStatus; }
    public String getCueBallStatus() { return cueBallStatus; }
	public int getMovesLeft() {
		return movesLeft;
	}
}
