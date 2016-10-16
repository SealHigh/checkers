package view;

import controller.CheckersController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;

/**
 * Created by Martin on 2016-10-14.
 */
public class CheckersMenu extends MenuBar {

    public CheckersMenu(int menuBarHeight,int menuBarWidth, CheckersController controller){

        setPrefHeight(menuBarHeight);
        setPrefWidth(menuBarWidth);
        Menu menu = new Menu("Menu");
        Menu subMenuNew = new Menu("New Game");
        MenuItem itmNew1 = new MenuItem("Player vs Player");
        MenuItem itmNew2 = new MenuItem("Player vs AI");
        MenuItem itmNew3 = new MenuItem("Save");
        MenuItem itmNew4 = new MenuItem("Load");
        itmNew1.setOnAction(event -> controller.onNewPvP());
        itmNew2.setOnAction(event -> controller.onNewPvAI());
        itmNew3.setOnAction(event -> controller.onSave());
        itmNew4.setOnAction(event -> controller.onLoad());


        subMenuNew.getItems().addAll(itmNew1, itmNew2);
        menu.getItems().addAll(subMenuNew, itmNew3,itmNew4);

        getMenus().add(menu);
    }

}
