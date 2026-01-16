module com.example.memorycardgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // DÒNG BỔ SUNG: Cần thiết để sử dụng các lớp Media và MediaPlayer
    requires javafx.media;

    // Bạn cũng nên mở module sang javafx.graphics nếu bạn đã sử dụng nó cho các tài nguyên (assets)
    opens com.example.memorycardgame to javafx.fxml, javafx.graphics;
    exports com.example.memorycardgame;
}