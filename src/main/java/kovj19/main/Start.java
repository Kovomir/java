package kovj19.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import kovj19.gui.ExitsPane;
import kovj19.gui.GameBoard;
import kovj19.gui.InventoryPane;
import kovj19.logic.Game;
import kovj19.logic.GameWorld;
import kovj19.logic.Inventory;
import kovj19.textUI.TextUI;

/**
 * Spouštěcí třída aplikace.
 */
public class Start extends Application
{
    private Game game = new Game();
    private GameWorld gameWorld = game.getWorld();
    private Inventory inventory = gameWorld.getInventory();
    private VBox gameVBox = new VBox();
    private TextArea console;
    private TextField commandField;
    private Label commandLabel;
    private BorderPane gameBorderPane;
    private MenuBar menuBar;
    private HBox commandFieldHBox;
    private ExitsPane exitsPane;
    private GameBoard gameBoard;
    private InventoryPane inventoryPane;

    public static void main(String[] args)
    {
        if (args.length == 0) {
            launch(args);
        } else {
            if (args[0].equals("-text")) {
                Game game = new Game();
                TextUI textUi = new TextUI(game);
                textUi.play();
                System.exit(0);
            } else if (args[0].equals("-gui")) {
                launch(args);
            }
            else {
                System.out.println("Hra byla spuštěna s nepodporovanými parametry!");
                System.out.println("\nAplikaci je možné spustit s následujícími parametry: '-text' nebo '-gui'");
                System.out.println("<bez parametrů>: Spustí hru s GUI.");
            }
        }
    }

    @Override
    public void start(Stage primaryStage){
        setUpPrimaryStage(primaryStage);
        setUpConsole();
        setUpCommandField();
        exitsPane = new ExitsPane(gameWorld);
        gameWorld.registerObserver(exitsPane);
        inventoryPane = new InventoryPane(game);
        inventory.registerObserver(inventoryPane);
        gameBoard = new GameBoard(gameWorld);
        gameWorld.registerObserver(gameBoard);
        setUpGameBorderPane();
        setUpMenuBar();

        gameVBox.getChildren().addAll(menuBar, gameBorderPane);
        commandField.requestFocus();
    }

    private void setUpPrimaryStage(Stage primaryStage) {
        Scene scene = new Scene(gameVBox, 800.0, 525.0);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hladový kocour");
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

    private void setUpGameBorderPane() {
        gameBorderPane = new BorderPane();
        gameBorderPane.setTop(gameBoard.getHBox());
        gameBorderPane.setRight(exitsPane.getVBox());
        gameBorderPane.setCenter(console);
        gameBorderPane.setBottom(commandFieldHBox);
        gameBorderPane.setLeft(inventoryPane.getVBox());
    }

    private void setUpConsole() {
        console = new TextArea();
        console.setText(game.getPrologue());
        console.setEditable(false);
    }

   private void setUpCommandField(){
       commandLabel = new Label("Zadej příkaz: ");
       commandLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

       commandField = new TextField();
       commandField.setOnAction(event -> {
           String command = commandField.getText();
           console.appendText("\n" + command + "\n");
           commandField.setText("");
           String commandResult = game.processAction(command);
           console.appendText("\n" + commandResult + "\n");
           if (game.isGameOver()) {
               commandField.setEditable(false);
               console.appendText("\n" + game.getEpilogue());
           }
       });
       commandFieldHBox = new HBox();
       commandFieldHBox.getChildren().addAll(commandLabel, commandField);
       commandFieldHBox.setAlignment(Pos.CENTER);
   }

   private void setUpMenuBar() {
       Menu gameMenu = new Menu("Hra");
       MenuItem newGame = new MenuItem("Nová hra");
       MenuItem quit = new MenuItem("Konec");
       SeparatorMenuItem separatorGameMenu = new SeparatorMenuItem();
       gameMenu.getItems().addAll(newGame, separatorGameMenu, quit);

       quit.setOnAction(event -> System.exit(0));

       newGame.setOnAction(event -> {
           game = new Game();
           console.setText(game.getPrologue());
           gameWorld = game.getWorld();
           gameWorld.registerObserver(gameBoard);
           gameWorld.registerObserver(exitsPane);
           inventory = gameWorld.getInventory();
           inventory.registerObserver(inventoryPane);
           gameBoard.newGame(gameWorld);
           exitsPane.newGame(gameWorld);
           inventoryPane.newGame(inventory);
           commandField.setEditable(true);
           commandField.requestFocus();
       });

       Menu hintMenu = new Menu("Nápověda");
       MenuItem aboutGame = new MenuItem("O aplikaci");
       MenuItem howToPlay = new MenuItem("Jak hrát");
       SeparatorMenuItem separatorHintMenu = new SeparatorMenuItem();
       hintMenu.getItems().addAll(aboutGame, separatorHintMenu, howToPlay);

       aboutGame.setOnAction(event -> {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("O aplikaci");
           alert.setHeaderText("Hladový kocour");
           alert.setContentText("verze ZS 2021");
           alert.showAndWait();
       });

       howToPlay.setOnAction(event -> {
           Stage stage = new Stage();
           stage.setTitle("Nápověda k aplikaci");
           WebView webView = new WebView();
           webView.getEngine().load(Start.class.getResource("/napoveda.html").toExternalForm());
           stage.setScene(new Scene(webView, 600, 600));
           stage.show();
       });

       menuBar = new MenuBar();
       menuBar.getMenus().addAll(gameMenu, hintMenu);
   }
}
