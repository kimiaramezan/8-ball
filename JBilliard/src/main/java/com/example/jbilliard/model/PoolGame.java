package com.example.jbilliard.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.example.jbilliard.GameManager;
import javafx.animation.AnimationTimer;


//class that runs the game play. Brings together a PoolBoard and Players Updates status of the game at each step of play.

public class PoolGame implements Observer, Serializable {
	private final GameManager gameManager = GameManager.getInstance();
	private final PoolBoard poolBoard = new PoolBoard();
	private final Player[] players = {new Player(), new Player()};
	private final PoolGameStatus poolGameStatus = new PoolGameStatus();
	private final EndScreenStatus endScreenStatus = new EndScreenStatus();
	private AnimationTimer timer;
	private int currPlayerInd = 0;
	private boolean sidesAreSet = false;
	private int numberOfMoves = 10;
	private final boolean isOnePlayer = true;

	public PoolGame() {
		poolBoard.getCueStick().addObserver(this);
		setAnimationTimer();
	}

	private void setAnimationTimer() {
		timer = new AnimationTimer() {
			@Override
			public void handle(long timestamp) {
				poolBoard.update();
				if (poolBoard.stable()) { 
					this.stop();
					updateStatus(poolBoard.pocketedBalls());
					poolBoard.resetPocketedBalls();
					poolBoard.getCueStick().setCanMove(true);
					poolBoard.getCueStick().setCanReset(true);
				}
			}
		};
	}

	private void setSides(int ballId) {
		players[currPlayerInd].setBallType(ballId);
		players[(currPlayerInd+1)%2].setBallType((ballId + 1)%2);
		poolGameStatus.setBallColors(currPlayerInd, ballId);
		sidesAreSet = true;
	}

	private boolean pocketedOther(ArrayList<Ball> pocketedBalls) {
		int playerBallType = players[currPlayerInd].getBallType();
		for (Ball b: pocketedBalls) {
			if (playerBallType != -1 && playerBallType != b.getId()
					&& (b.getId() == 0 || b.getId() == 1)) {
				return true;
			}
		}
		return false;
	}

	private boolean pocketedCueBall(ArrayList<Ball> pocketedBalls) {
		for (Ball b: pocketedBalls) {
			if ( b.getId() == 2) return true;
		}
		return false;
	}

	private boolean pocketedBlackBall(ArrayList<Ball> pocketedBalls) {
		for (Ball b: pocketedBalls) {
			if ( b.getId() == 3) return true;
		}
		return false;
	}

	private void switchPlayer() {
		currPlayerInd = (currPlayerInd + 1) % 2;
		poolGameStatus.setTurnStatus(currPlayerInd, false,
				players[currPlayerInd].canPocketBlackBall());
	}

	private void continuePlayer() {
		poolGameStatus.setTurnStatus(currPlayerInd, true,
				players[currPlayerInd].canPocketBlackBall());
	}

	private void updatePoints(ArrayList<Ball> pocketedBalls) {
		for (Ball pocketedBall : pocketedBalls) {
			int ballId = pocketedBall.getId();
			if (ballId != 3) {
				if (!sidesAreSet) {
					setSides(ballId);
				}

				players[currPlayerInd].addPoint(pocketedBall.getPoint());
			}
		}

		poolGameStatus.setPoints(players[0].getPoints(), 
				players[1].getPoints());
	}

	private void updateStatus(ArrayList<Ball> pocketedBalls) {
		int size = pocketedBalls.size();
		if (poolBoard.getCollisionCount() < 4 && size == 0) {
			poolBoard.rackBalls(poolBoard.getBalls());
			poolGameStatus.setLastTurnStatusPlayerIllegalBreak(currPlayerInd);
			if (!isOnePlayer) switchPlayer();
		}

		else if (size == 0) {
			poolGameStatus.setLastTurnStatusPlayerFailed(currPlayerInd);
			if (!isOnePlayer) switchPlayer();
		}
		else {
			updatePoints(pocketedBalls);
			if (pocketedBlackBall(pocketedBalls)) {
				if (players[currPlayerInd].canPocketBlackBall()) {
					endScreenStatus.gameOverSuccess(currPlayerInd);
					gameManager.initEndScreen();
				}
				else {
					endScreenStatus.gameOverFailBlackBall(currPlayerInd);
					gameManager.initEndScreen();
				}
			}
			else if (pocketedCueBall(pocketedBalls)) {
				endScreenStatus.gameOverFailCueBall(currPlayerInd);
				gameManager.initEndScreen();
			}
			else if (pocketedOther(pocketedBalls)) {
				poolGameStatus.setLastTurnStatusPocketedOther(currPlayerInd);
				if (!isOnePlayer) switchPlayer();
			}
			else{
				poolGameStatus.setLastTurnStatusPlayerSucceeded(currPlayerInd);
				continuePlayer();
			}
		}

		if (numberOfMoves <= 0) {
			players[currPlayerInd].addPoint(- 4);
		}
		if (players[currPlayerInd].points < 0) {
			endScreenStatus.gameOverFailNegativePoint(currPlayerInd, players[currPlayerInd].points);
			gameManager.initEndScreen();
		}
		numberOfMoves--;
		poolGameStatus.updateMovesLeft(numberOfMoves);
	}

	public void update(Observable o, Object arg) {
		if (o == poolBoard.getCueStick()) {
			if (poolBoard.getCueStick().hasHit()) {
				poolGameStatus.unsetCueBallStatus();
				timer.start();
			}
		}
	}

	public PoolBoard getPoolBoard() { return poolBoard; }
	public PoolGameStatus getPoolGameStatus() { return poolGameStatus; }
	public EndScreenStatus getEndScreenStatus() { return endScreenStatus; }
	
}