import controller.CheckersController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.CheckersBoard;
import view.CheckersMenu;
import view.CheckersView;

/**
 * Created by Martin on 2016-10-13.
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        CheckersBoard board = new CheckersBoard();
        CheckersView view = new CheckersView(board);
        Scene scene = new Scene (view);

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
