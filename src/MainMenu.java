import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * <p>JavaFX application that starts the main menu.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/6/2020
 */
public class MainMenu extends Application {

    /**
     * <p>Main method that launches the program.</p>
     *
     * @param args  main method arguments are ignored
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * <p>Setups the JavaFX display.</p>
     *
     * @param primaryStage  the primary stage
     * @since 1.0
     */
    @Override
    public void start(Stage primaryStage) {
        // Set title
        primaryStage.setTitle("Main Menu");
        primaryStage.setResizable(false);

        // Make layout for main menu
        VBox mainMenuLayout = new VBox();
        mainMenuLayout.setPrefSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width * 0.4, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height * 0.6);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.setBackground(new Background(new BackgroundFill(Color.rgb(227, 193, 111), CornerRadii.EMPTY, Insets.EMPTY)));

        // Add label
        Label titleLabel = new Label("Chess Program");
        titleLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40.0));
        titleLabel.setAlignment(Pos.CENTER);

        // Add play game buttons
        Button[] playButtons = new Button[3];

        // Swing European chess button
        playButtons[0] = new Button("Play Swing European Chess");
        playButtons[0].setOnAction(e -> SwingChessBoard.main(new String[0]));

        // JavaFX European chess button
        playButtons[1] = new Button("Play JavaFX European Chess");
        playButtons[1].setOnAction(e -> Platform.runLater(() -> {
            System.out.println("Hello");
            try {
                JavaFXChessBoard.parentArguments = new String[] {"chess"};

                Application application = JavaFXChessBoard.class.newInstance();
                Stage anotherStage = new Stage();
                anotherStage.initModality(Modality.APPLICATION_MODAL);

                application.start(anotherStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }));

        // Xiangqi button
        playButtons[2] = new Button("Play JavaFX Xiangqi");
        playButtons[2].setOnAction(e -> Platform.runLater(() -> {
            System.out.println("Hello");
            try {
                JavaFXChessBoard.parentArguments = new String[] {"xiangqi"};

                Application application = JavaFXChessBoard.class.newInstance();
                Stage anotherStage = new Stage();
                anotherStage.initModality(Modality.APPLICATION_MODAL);

                application.start(anotherStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }));

        // Iterates through each button to set the style of each button
        for (Button b : playButtons) {
            b.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25.0));
            b.setAlignment(Pos.CENTER);
            b.setStyle("-fx-background-color: rgba(200, 200, 200, 0.7);");
            b.setOnMouseEntered(e -> b.setStyle("-fx-background-color: rgba(255, 0, 0, 0.3); -fx-cursor: hand;"));
            b.setOnMouseExited(e -> b.setStyle("-fx-background-color: rgba(200, 200, 200, 0.7); -fx-cursor: pointer;"));
            VBox.setMargin(b, new Insets(20));
        }

        // Add buttons and  label
        mainMenuLayout.getChildren().add(titleLabel);
        mainMenuLayout.getChildren().addAll(playButtons);
        VBox.setMargin(titleLabel, new Insets(50));

        // Make new main menu scene
        Scene mainMenu = new Scene(mainMenuLayout);

        // Add to primaryStage and display
        primaryStage.setScene(mainMenu);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
