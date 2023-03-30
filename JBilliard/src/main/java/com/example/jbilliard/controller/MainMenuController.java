package com.example.jbilliard.controller;

import com.example.jbilliard.GameManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MainMenuController {
	@FXML Button btn_Go;
	
	@FXML
	public void handleButtonAction() {
		GameManager gm = GameManager.getInstance((btn_Go.getScene()));
		gm.initPoolScreen();
	}
}
