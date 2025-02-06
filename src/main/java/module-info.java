module edu.ntnu.idi.bidata {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.ntnu.idi.bidata to javafx.fxml;
    exports edu.ntnu.idi.bidata;
}