import com.sun.istack.internal.NotNull;
import javafx.beans.value.ObservableValueBase;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import table.ScoreTable;

import java.util.function.Function;

public class MainFXController {
    public static int MAX_SCORE_NAME = 6;

    public TableView tableView;
    public TableColumn player1;
    public TableColumn scoreName;
    public TableColumn player2;
    public Button reRollButton;

    @FXML
    private Text scoreLabel;

    @FXML
    private TableColumn<ScoreTable, String> scoreColumn;

    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;
    @FXML
    private ImageView dice3;
    @FXML
    private ImageView dice4;
    @FXML
    private ImageView dice5;

    private GameEngineFX gfx;

    private Image[] img = new Image[6];

    private static <S, T> TableColumn<S, T> column(String title, Function<S, T> property) {
        TableColumn<S, T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> new ObservableValueBase<T>() {
            @Override
            public T getValue() {
                return property.apply(cellData.getValue());
            }
        });
        return col;
    }

    public void initialize() {
        System.out.println("Application started");
        this.gfx = new GameEngineFX();

        img[0] = new Image("resources/dieWhite1.png");
        img[1] = new Image("resources/dieWhite2.png");
        img[2] = new Image("resources/dieWhite3.png");
        img[3] = new Image("resources/dieWhite4.png");
        img[4] = new Image("resources/dieWhite5.png");
        img[5] = new Image("resources/dieWhite6.png");

        updateDices();
        updateThrowLeft();
        addScore(this.gfx.scoreboard.getMaxScore(), this.gfx.scoreboard.getScore());

        /*
        Inspired from https://stackoverflow.com/questions/12499269/javafx-tableview-detect-a-doubleclick-on-a-cell
        */

        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        TableCell cell = new TableCell<ScoreTable, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                setText(empty ? null : getString());
                                setGraphic(null);
                            }

                            private String getString() {
                                return getItem() == null ? "" : getItem();
                            }
                        };

                        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (event.getClickCount() > 1) {
                                    TableCell c = (TableCell) event.getSource();
                                    System.out.println("Info " + c.getTableRow().getIndex());
                                    if (c.getTableRow().getIndex() < MAX_SCORE_NAME) {
                                        gfx.rou.scoreSelectCheck(c.getTableRow().getIndex());
                                        gfx.scoreboard.maxScore(gfx.rou.dices);
                                        addScore(gfx.scoreboard.getMaxScore(), gfx.scoreboard.getScore());

                                        gfx.reset();
                                        reRollButton.setDisable(false);
                                        updateThrowLeft();
                                        gfx.rou.rollDices();
                                        updateDices();
                                    } else {
                                        System.out.println("Click on bad cell!");
                                    }
                                }
                            }
                        });
                        return cell;
                    }
                };

        player1.setCellFactory(cellFactory);
        player1.setSortable(false);
        player2.setSortable(false);
        scoreName.setSortable(false);
    }

    private void updateDices() {
        dice1.setImage(this.img[this.gfx.rou.dices[0].value() - 1]);
        dice2.setImage(this.img[this.gfx.rou.dices[1].value() - 1]);
        dice3.setImage(this.img[this.gfx.rou.dices[2].value() - 1]);
        dice4.setImage(this.img[this.gfx.rou.dices[3].value() - 1]);
        dice5.setImage(this.img[this.gfx.rou.dices[4].value() - 1]);
    }

    private void updateThrowLeft() {
        scoreLabel.setText("Throw left: " + this.gfx.rou.throwLeft);
    }

    //tableView.setEditable(false);

    @FXML
    public void clickItem(MouseEvent event) {
        if (event.getClickCount() == 2) //Checking double click
        {
            System.out.println(tableView.getSelectionModel().getSelectedItem());
            System.out.println(tableView.getSelectionModel().getSelectedItem());
            System.out.println(tableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    protected void reRollEvent(ActionEvent event) {

        this.gfx.rou.throwLeft--;
        this.updateThrowLeft();

        this.gfx.rou.rollDices();
        this.updateDices();

        this.gfx.scoreboard.maxScore(this.gfx.rou.dices);
        addScore(this.gfx.scoreboard.getMaxScore(), this.gfx.scoreboard.getScore());
        if (this.gfx.rou.throwLeft == 0) {
            this.reRollButton.setDisable(true);
        }
    }

    private void addScore(int[] score, int[] score2) {
        tableView.getItems().clear();
        for (int i = 0; i < 6; i++) {
            tableView.getItems().add(new ScoreTable(Score.lower(i + 1), Integer.toString(score[i]), score2[i] == -1 ? "" : Integer.toString(score2[i])));
        }
    }

    public void diceClick(@NotNull MouseEvent mouseEvent) {
        System.out.println("Dice clicked");
        int id = 0;
        switch (mouseEvent.getPickResult().getIntersectedNode().getId()) {
            case "dice1":
                id = 1;
                break;
            case "dice2":
                id = 2;
                break;
            case "dice3":
                id = 3;
                break;
            case "dice4":
                id = 4;
                break;
            case "dice5":
                id = 5;
                break;
        }

        this.gfx.rou.toggleKeeperSolo(id);
        System.out.println("Toggled");
    }
}