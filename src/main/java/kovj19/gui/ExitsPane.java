package kovj19.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import kovj19.logic.Area;
import kovj19.logic.Game;
import kovj19.logic.GameWorld;
import kovj19.util.Observer;


public class ExitsPane implements Observer {
    GameWorld gameWorld;
    ListView<String> listView = new ListView<>();
    ObservableList<String> exits = FXCollections.observableArrayList();
    VBox vBox = new VBox();

    public ExitsPane(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        init();
    }

    public VBox getVBox() {
        return vBox;
    }

    private void init() {
        loadCurrentAreaExits();
        listView.setItems(exits);
        listView.setPrefWidth(100);
        vBox.setPrefWidth(100);
        Label label = new Label("Východy:");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vBox.getChildren().addAll(label, listView);
        vBox.setAlignment(Pos.CENTER);
    }

    private void loadCurrentAreaExits() {
        exits.clear();
        Area currentArea = gameWorld.getCurrentArea();
        for (Area area : currentArea.getExits()) {
            exits.add(area.getName());
        }
    }

    @Override
    public void update() {
        loadCurrentAreaExits();
    }

    public void newGame(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        update();
    }
}
