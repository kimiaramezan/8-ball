package com.example.jbilliard.model;

import java.io.Serializable;


//ball types and points

public class Player implements Serializable {
	 
	int ballType;
	int points = 0;
	public Player() {
	    ballType = -1;
	}
	public boolean canPocketBlackBall() {
		return (points == 47);
	}
	public void addPoint(int amount) {
		points += amount;
	}
	public void setBallType(int ballType) { this.ballType = ballType; }
	public int getBallType() { return ballType; }
	public int getPoints() { return points; }
	
}

