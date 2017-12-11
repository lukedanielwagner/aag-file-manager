package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Controller {

    @FXML
    private final ObservableList<String> damageTypes = FXCollections.observableArrayList(

            "Water",
            "Fire",
            "Wind"

    );

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker dateOfLoss;

    @FXML
    private ComboBox damageType = new ComboBox();

    @FXML
    private TextField aagNumber;

    @FXML
    private TextField status;

    final private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM.dd.yyyy");

    public void exit(ActionEvent actionEvent) {

        System.exit(0);

    }

    @FXML
    private void createFilesystem(ActionEvent actionEvent) throws IOException {

        if(lastName.getText() == null || dateOfLoss.getValue() == null || aagNumber.getText() == null) {

            status.setText("Please fill in the required fields denotated by '*'.");

        } else {

            final File rootDir = new File("E:/Computer Science/Files");

            if (rootDir.exists() && rootDir.isDirectory()) {

                status.setText("Root directory located, creating filesystem...");
                createClientDir(rootDir);

            } else if(!rootDir.canWrite()){

                status.setText("Root directory could not be accessed. Contact Admin.");

            } else {

                status.setText("Something went wrong. Contact Admin.");

            }

            }

        }

    private void createClientDir(File root) {

        String formattedDate = dateFormat.format(dateOfLoss.getValue());

        File claimDir = new File(root.getPath() + "/" +
                lastName.getText() + " " + formattedDate + " AAG " + aagNumber.getText());

        try {

            claimDir.mkdir();
            status.setText("Filesystem created successfully.");

        } catch (Exception e) {

            status.setText("Something went wrong. " + e);

        }

    }

    public void populateDamageTypes(MouseEvent mouseEvent) {

        damageType.setItems(damageTypes);

    }

    public void clearForm(ActionEvent actionEvent) {

        firstName.setText(null);
        lastName.setText(null);
        dateOfLoss.setValue(null);
        damageType.setValue(null);
        aagNumber.setText(null);
        status.setText(null);

    }

}
