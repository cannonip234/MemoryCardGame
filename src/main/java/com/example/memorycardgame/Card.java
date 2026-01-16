package com.example.memorycardgame;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.beans.property.SimpleBooleanProperty;

public class Card extends StackPane {
    private final int id;
    private boolean flipped = false;
    private boolean matched = false;


    private static final int CARD_SIZE = 100;
    private final Region cardBack = new Region();
    private final ImageView cardFront;

    private final Label debugLabel;

    private final AnimationManager animation;

    public Card(int id, AnimationManager animation) {
        this.id = id;
        this.animation = animation;

        // setup mat sau
        cardBack.setPrefSize(CARD_SIZE, CARD_SIZE);
        cardBack.setMinSize(CARD_SIZE, CARD_SIZE);
        cardBack.getStyleClass().add("card-back");

        // Front card
        cardFront = new ImageView();
        debugLabel = new Label(String.valueOf(id));

        try {
            Image image = new Image(getClass().getResource("card_" + id + ".png").toExternalForm());
            cardFront.setImage(image);
            cardFront.setFitWidth(CARD_SIZE - 20); // Giảm kích thước ảnh so với thẻ
            cardFront.setFitHeight(CARD_SIZE - 20);
        } catch (Exception e) {
            // Nếu không tìm thấy hình ảnh, sử dụng Label số cũ làm dự phòng
            debugLabel.setStyle("-fx-font-size: 36; -fx-font-weight: bold; -fx-text-fill: black;");
        }

        // Back card
        getChildren().addAll(cardBack);

        setMinSize(CARD_SIZE, CARD_SIZE);
        setMaxSize(CARD_SIZE, CARD_SIZE);
        setAlignment(Pos.CENTER);
        setPrefSize(CARD_SIZE, CARD_SIZE);

        // matched
        this.disableProperty().bind(this.matchedProperty());
    }

    public javafx.beans.property.BooleanProperty matchedProperty() {
        return new SimpleBooleanProperty(matched);
    }

    public int getCardId() { return id; }

    public void flip() {
        flipped = !flipped;



        if (flipped) {
            getChildren().remove(cardBack);
            if (cardFront.getImage() != null) {
                getChildren().add(cardFront);
            } else {
                getChildren().add(debugLabel);
            }

        } else {
            getChildren().clear();
            getChildren().add(cardBack);
        }
    }

    public boolean isFlipped() { return flipped; }
    public boolean isMatched() { return matched; }

    public void setMatched(boolean matched) {
        this.matched = matched;
        if (matched) {
            this.getStyleClass().add("card-matched");
        }
    }
}