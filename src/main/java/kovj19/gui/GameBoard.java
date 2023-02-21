package kovj19.gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import kovj19.logic.GameWorld;
import kovj19.util.Observer;

public class GameBoard implements Observer {
    private GameWorld gameWorld;
    private HBox hBox = new HBox();
    private AnchorPane anchorPane = new AnchorPane();
    private ImageView playerPosition = new ImageView(new Image(GameBoard.class.getResourceAsStream
            ("/cat.png"),100 , 100, false, false));

    public GameBoard(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        init();
    }

    private void init() {
        ImageView gameBoardImageView = new ImageView(new Image(GameBoard.class.getResourceAsStream
                ("/gameBoard.png"),800.0 , 325.0, false, false));
        anchorPane.getChildren().addAll(gameBoardImageView, playerPosition);
        hBox.getChildren().add(anchorPane);
        hBox.setAlignment(Pos.CENTER);
        setPlayerPosition();
    }

    public HBox getHBox(){
        return hBox;
    }

    @Override
    public void update() {
        setPlayerPosition();
    }

    private void setPlayerPosition() {
        AnchorPane.setTopAnchor(playerPosition, gameWorld.getCurrentArea().getPosTop());
        AnchorPane.setLeftAnchor(playerPosition, gameWorld.getCurrentArea().getPosLeft());
    }

    public void newGame(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        update();
    }
}
