package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CheckersBoard;
import view.CheckersView;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Martin on 2016-10-13.
 */
public class CheckersController {

    private CheckersBoard board;
    private CheckersView view;

    public CheckersController(CheckersBoard board, CheckersView view){
        this.view = view;
        this.board = board;
    }

    public void onNewPvP(){
        board.resetBoard(false);
        view.initView(this);
    }

    public void onNewPvAI(){
        board.resetBoard(true);
        view.initView(this);
    }

    public void onTilePressed(int x, int y){
        System.out.println(x + " "+ y);
        board.getMove(board.tileFromPos(x,y));
    }

    public void onPause(){
        board.setPaused(true);
    }

    public void onUnpause(){
        board.setPaused(false);
    }

    public void onAbout(){
        Alert alert= new Alert(Alert.AlertType.INFORMATION,
                "Created by Martin Renström\n\n" +
                        "In two days, two rather long days.\n" +
                        "And the AI is still garbage,\n" +
                        "if you lose to it you should feel bad.");
        alert.setHeaderText("About");
        alert.showAndWait();
    }

    public void onRules(){
        Alert alert= new Alert(Alert.AlertType.INFORMATION,
                "Checkers is a board game played on dark tiles only\n\n" +
                "1. A piece can only move diagonally forward unless it's a queen\n" +
                "2. A piece can also destroy an enemy piece by stepping over it\n" +
                        "3. A piece turns into a queen upon reaching opposite side\n" +
                        "4. If you captured a piece and can capture another you may do so\n" +
                        "5. If you dont want to capture another simply press the piece again");
        alert.setHeaderText("Rules");
        alert.showAndWait();
    }


    public void onLoad(){
        SaveManager sm = new SaveManager();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open save");
        Stage test = (Stage) view.getScene().getWindow();
        File file = fileChooser.showOpenDialog(test);
        if (file != null) {
            try {
                this.board = sm.deSerializeFromFile(file.getAbsolutePath());
                view.setBoard(this.board);
                view.initView(this);
                System.out.println("load");
            } catch (IOException e) {
                Alert alert= new Alert(Alert.AlertType.ERROR, "Could not load file, wrong format or damaged file!");
                alert.showAndWait();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Open command cancelled by user.");
        }
    }

    public void onSave(){
        SaveManager sm = new SaveManager();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create save");
        Stage test = (Stage) view.getScene().getWindow();
        File file = fileChooser.showSaveDialog(test);

        if (file != null) {
            try {

                sm.serializeToFile(file.getAbsolutePath(), this.board);
            } catch (IOException e) {
                Alert alert= new Alert(Alert.AlertType.ERROR, "Could not save file, wrong format! Use .txt or .ser");
                alert.showAndWait();
            }
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }
}
