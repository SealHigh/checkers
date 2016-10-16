package controller;

import javafx.scene.control.Alert;
import model.CheckersBoard;
import view.CheckersView;
import view.TileView;

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

    public void onLoad(){
        SaveManager sm = new SaveManager();
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
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

        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
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
}
