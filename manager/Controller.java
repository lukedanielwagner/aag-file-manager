package manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class Controller {

    @FXML
    private final ObservableList<String> damageTypes = FXCollections.observableArrayList(

            "Water",
            "Fire",
            "Wind"

    );

    private ArrayList<String> imgFileTypes = new ArrayList<>();

    private ArrayList<File> fileQueue = new ArrayList<>();
    private ArrayList<File> pictureQueue = new ArrayList<>();

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

    private final File rootDir = new File("E:/Computer Science/Files");

    final private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM.dd.yyyy");

    @FXML
    void handleCreateButton() throws IOException {

        if(Objects.equals(lastName.getText(), "") || dateOfLoss.getValue() == null || Objects.equals(aagNumber.getText(), "")) {

            status.setText("Please fill in the required fields denoted by '*'.");

        } else {

            if(fileQueue.size() == 0 && pictureQueue.size() == 0) {

                Alert noFilesAlert = new Alert(Alert.AlertType.CONFIRMATION, "No files or pictures have been added. Is " +
                        "this correct? You will have to add them manually later.", ButtonType.OK, ButtonType.CANCEL);
                noFilesAlert.showAndWait();

                if(noFilesAlert.getResult() == ButtonType.OK) {

                    createFilesystem();
                    moveFiles(fileQueue, pictureQueue);

                } else if(noFilesAlert.getResult() == ButtonType.CANCEL) {



                }

            } else {

                createFilesystem();
                moveFiles(fileQueue, pictureQueue);

            }

        }

    }

    void createFilesystem() throws IOException {

        if (rootDir.exists() && rootDir.isDirectory()) {

            status.setText("Root directory located, creating filesystem...");
            createClientDir(rootDir);

        } else if(!rootDir.canWrite()){

            status.setText("Root directory could not be accessed. Contact Admin.");

        } else {

            status.setText("Something went wrong. Contact Admin.");

        }

    }

    void createClientDir(File root) {

        String formattedDate = dateFormat.format(dateOfLoss.getValue());

        File claimDir = new File(root.getPath() + "/" +
                lastName.getText() + " " + firstName.getText() + " " + formattedDate + " AAG " + aagNumber.getText());

        try {

            claimDir.mkdir();
            status.setText("Filesystem created successfully.");

        } catch (Exception e) {

            status.setText("Something went wrong. " + e);

        }

        flushQueue();

    }

    @FXML
    void populateDamageTypes(MouseEvent mouseEvent) {

        damageType.setItems(damageTypes);

    }

    @FXML
    void clearForm(ActionEvent actionEvent) {
        System.out.println(pictureQueue);


        firstName.setText(null);
        lastName.setText(null);
        dateOfLoss.setValue(null);
        damageType.setValue(null);
        aagNumber.setText(null);
        status.setText(null);
        flushQueue();

    }

    @FXML
    void chooseFiles() {

        FileChooser fileChooser = new FileChooser();

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(new Stage());

        if(selectedFiles != null) {

            sortFiles(selectedFiles);

        }


    }

    @FXML
    void handleDragOver(DragEvent dragEvent) {

        if(dragEvent.getDragboard().hasFiles()) {

            dragEvent.acceptTransferModes(TransferMode.MOVE);

        }

        dragEvent.consume();

    }

    @FXML
    void handleDragDrop(DragEvent dragEvent) throws FileNotFoundException {

        Dragboard dragboard = dragEvent.getDragboard();
        List<File> droppedFiles = dragboard.getFiles();

        if(dragboard.hasFiles()) {

            sortFiles(droppedFiles);
            System.out.print(pictureQueue + "\n" + fileQueue);

        }

    }

    private void sortFiles(List<File> files) {

        imgFileTypes.add(".png");
        imgFileTypes.add(".jpeg");
        imgFileTypes.add(".gif");
        imgFileTypes.add(".tiff");

        for(File file : files) {

            if( file != null) {

                String fileName = file.getName();
                String fileExt = fileName.substring(fileName.lastIndexOf("."));


                if(imgFileTypes.contains(fileExt)) {

                    pictureQueue.add(file);

                } else {

                    fileQueue.add(file);

                }

            }

        }
    }

    public void moveFiles(ArrayList<File> files, ArrayList<File> pictures) throws IOException {

        for(File file : files) {

            Files.move(Paths.get(file.getPath()), Paths.get(new File(rootDir + "/").getPath()), StandardCopyOption.REPLACE_EXISTING);

        }

        if(!pictureQueue.isEmpty()) {

            File pictureDir = new File(rootDir + "/Pictures");

            for(File file : pictures) {

                Files.move(Paths.get(file.getPath()), Paths.get(new File(rootDir + "/Pictures").getPath()), StandardCopyOption.REPLACE_EXISTING);

            }

        }



    }

    @FXML
    void exit(ActionEvent actionEvent) {

        System.exit(0);

    }

    void flushQueue() {

        fileQueue.clear();
        pictureQueue.clear();

    }

}
