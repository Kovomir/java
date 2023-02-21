package kovj19.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import kovj19.logic.Game;
import kovj19.logic.GameWorld;
import kovj19.logic.Inventory;
import kovj19.util.Observer;
import java.io.InputStream;
import java.util.Set;

public class InventoryPane implements Observer {
    private Inventory inventory;
    private VBox vBox = new VBox();
    private FlowPane inventoryPane = new FlowPane();


    public InventoryPane(Game game) {
        inventory = game.getWorld().getInventory();
        init();
    }

    private void init() {
        vBox.setPrefWidth(100);
        Label label = new Label("Inventář:");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vBox.getChildren().addAll(label, inventoryPane);
        vBox.setAlignment(Pos.TOP_CENTER);
    }

    public VBox getVBox() {
        return vBox;
    }

    private void loadItemImages() {
        inventoryPane.getChildren().clear();
        String itemName;
        Set<String> items = inventory.getItemNames();
        for (String item : items) {
            itemName = "/" + item + ".png";
            InputStream inputStream = InventoryPane.class.getResourceAsStream(itemName);
            Image image = new Image(inputStream, 100, 100, false, false);
            ImageView imageView = new ImageView(image);
            inventoryPane.getChildren().add(imageView);
        }
    }

    @Override
    public void update() {
        loadItemImages();
    }

    public void newGame(Inventory inventory) {
        this.inventory = inventory;
        update();
    }
}
