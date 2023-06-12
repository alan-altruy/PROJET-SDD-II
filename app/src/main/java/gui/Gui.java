package gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import windowing.Windowing;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The Gui class represents the GUI of the application. It is responsible for displaying the windowing pane,
 * the windowing size, the windowing chosen size, the lines, the sliders, the text fields, the buttons, the
 * menu items, and the toggle buttons.
 */
public final class Gui {
    public AnchorPane mainPane;
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Double[]> segments = new ArrayList<>();
    @FXML
    private Rectangle windowingChosenSize, windowingSize;
    @FXML
    private Menu infoSize;
    private Windowing windowing;
    @FXML
    private Pane windowPane, linesPane;
    @FXML
    private TextField minX, maxX, minY, maxY;
    @FXML
    private Button computeWindow, zoomIn, zoomOut;
    @FXML
    private Slider sliderMinX, sliderMaxX, sliderMinY, sliderMaxY;
    @FXML
    private ToggleButton focusToggleButton;
    private double lineStrokeWidth = 1.5;

    /**
     * It sets up the GUI
     *
     * @param primaryStage The stage that the windowing GUI will be displayed on.
     * @param root         the root node of the scene graph
     */
    public void init(Stage primaryStage, Parent root) {
        this.windowing = Windowing.getInstance();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(665);
        primaryStage.setMinWidth(825);
        primaryStage.setTitle("Windowing");
        try {
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(Gui.class.
                    getClassLoader().getResourceAsStream("java-fx/icon.png"))));
        } catch (Exception e) {
            System.out.println("Icon not found");
        }
        addEventHandlers(mainPane);
        addEventHandlers(sliderMinX, minX, sliderMaxX);
        addEventHandlers(sliderMaxX, maxX, sliderMinX);
        addEventHandlers(sliderMinY, minY, sliderMaxY);
        addEventHandlers(sliderMaxY, maxY, sliderMinY);
        format(minX);
        format(maxX);
        format(minY);
        format(maxY);
        computeWindow.setMouseTransparent(true);
        focusToggleButton.setMouseTransparent(true);
        primaryStage.show();
    }

    /**
     * It adds event handlers to the scene's width and height properties, so that when the window is
     * resized, the windowing size is updated accordingly
     *
     * @param pane The pane that the windowing pane is in.
     */
    public void addEventHandlers(AnchorPane pane) {
        pane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double windowingWidth = windowingSize.getWidth();
            double scale = (windowing.getMaxX() - windowing.getMinX()) / (windowing.getMaxY() - windowing.getMinY());
            windowingSize.setWidth(Math.min((pane.getHeight() - 20) * scale, (double) newValue - 20));
            windowingSize.setHeight(windowingSize.getWidth() / scale);
            eraseLines();
            focusToggleButton.setSelected(false);
            setChosenSize();
            updateLines(windowingSize.getWidth() / windowingWidth);
        });
        pane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double windowingHeight = windowingSize.getHeight();
            double scale = (windowing.getMaxX() - windowing.getMinX()) / (windowing.getMaxY() - windowing.getMinY());
            windowingSize.setHeight(Math.min((double) newValue - 20, (pane.getWidth() - 20) / scale));
            windowingSize.setWidth(windowingSize.getHeight() * scale);
            eraseLines();
            focusToggleButton.setSelected(false);
            setChosenSize();
            updateLines(windowingSize.getHeight() / windowingHeight);
        });
    }

    /**
     * It adds a listener to the slider's value property, which is a double, and when the value
     * changes, it updates the text field with the new value, and also updates the min or max value of
     * the slave slider
     *
     * @param slider      the slider that will be used to change the value of the text field
     * @param txtField    The text field that will be updated with the slider's value.
     * @param sliderSlave the slider that will be affected by the change of the slider value.
     */
    public void addEventHandlers(Slider slider, TextField txtField, Slider sliderSlave) {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double scale = (windowing.getMaxX() - windowing.getMinX()) / 200;
            int value = ((Double) (newValue.doubleValue() / scale)).intValue() * (int) scale;
            txtField.setText("");
            txtField.setText(String.valueOf(value));
            if (slider.equals(sliderMinX) || slider.equals(sliderMinY))
                sliderSlave.setMin(value + 1);
            else
                sliderSlave.setMax(value + 1);
            verifyWindowingChosenSize();
        });
    }

    /**
     * It opens a file chooser, reads the file, and sets the window size
     */
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        String response = null;
        if (file != null) {
            try {
                response = windowing.readFile(file);
            } catch (Exception e) {
                errorWindow("Error reading file");
            }
            if (response != null) {
                eraseLines();
                infoSize.setText(response);
                this.lineStrokeWidth = Math.min(2.0, (windowingSize.getWidth() / (windowing.getMaxX() - windowing.getMinX())
                        * 2.5) / (windowing.getSegmentsSize() / 600));
                initWindowSize();
                setWindowingChosenSize();
            } else errorWindow("Error reading file");
        } else errorWindow("No file selected");

    }

    /**
     * It sets the window size
     */
    private void initWindowSize() {
        double scale = (windowing.getMaxX() - windowing.getMinX()) / (windowing.getMaxY() - windowing.getMinY());
        windowingSize.setWidth(Math.min(mainPane.getHeight() * scale, mainPane.getWidth()) - 20);
        windowingSize.setHeight(windowingSize.getWidth() / scale);

        computeWindow.setMouseTransparent(false);
        focusToggleButton.setMouseTransparent(false);
        sliderMinX.setMin(windowing.getMinX());
        sliderMinX.setMax(windowing.getMaxX());
        sliderMinX.setValue(windowing.getMinX());
        sliderMaxX.setMin(windowing.getMinX());
        sliderMaxX.setMax(windowing.getMaxX());
        sliderMaxX.setValue(windowing.getMaxX());
        sliderMinY.setMin(windowing.getMinY());
        sliderMinY.setMax(windowing.getMaxY());
        sliderMinY.setValue(windowing.getMinY());
        sliderMaxY.setMin(windowing.getMinY());
        sliderMaxY.setMax(windowing.getMaxY());
        sliderMaxY.setValue(windowing.getMaxY());

        minX.setText("");
        minX.setText(String.valueOf(windowing.getMinX()));
        maxX.setText("");
        maxX.setText(String.valueOf(windowing.getMaxX()));
        minY.setText("");
        minY.setText(String.valueOf(windowing.getMinY()));
        maxY.setText("");
        maxY.setText(String.valueOf(windowing.getMaxY()));
    }

    /**
     * The function i s used to format a textfield
     *
     * @param txtField The TextField that you want to format.
     */
    private void format(TextField txtField) {
        txtField.setTextFormatter(new TextFormatter<>(filter -> {
            filter.setText(reviseDouble(filter.getText(), txtField));
            return filter;
        }));
    }

    /**
     * If the text in the text field is a valid double, return the added text, otherwise return an
     * empty string
     *
     * @param addedText The text that was added to the TextField.
     * @param textField The TextField that the user is typing in.
     * @return The method returns a string.
     */
    private String reviseDouble(String addedText, TextField textField) {
        addedText = addedText.replaceAll(",", ".");
        String amountText = textField.getText() + addedText;

        return amountText.matches("-?([0-9]{1,10})?(\\.[0-9]{0,14})?") ? addedText : "";
    }

    /**
     * Checking if the windowing chosen size is valid, and if it is, it requests the lines to be drawn.
     */
    @FXML
    private void setWindowingChosenSize() {
        if (verifyWindowingChosenSize())
            infoSize.setText(windowing.requestLines());
        else errorWindow("Invalid windowing size");
    }

    /**
     * It checks if the user inputted values are valid, and if they are, it sets the window size to the
     * user inputted values
     *
     * @return A boolean value.
     */
    private boolean verifyWindowingChosenSize() {
        try {
            if (windowing.choseSize(Double.parseDouble(minX.getText()), Double.parseDouble(maxX.getText()),
                    Double.parseDouble(minY.getText()), Double.parseDouble(maxY.getText()))) {
                setChosenSize();
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * This allows focus on the selected windowing area
     */
    @FXML
    private void focusOnWindowingChosenSize() {
        if (focusToggleButton.isSelected()) {
            if (lines.size() == 0) drawLines(segments);
            double scale = Math.min(windowPane.getWidth() / windowingChosenSize.getWidth(),
                    windowPane.getHeight() / windowingChosenSize.getHeight());
            scaleWindowPane(scale);
            zoomIn.setVisible(true);
            zoomOut.setVisible(true);
        } else deactivateFocus();
    }

    /**
     * This function scales a window pane and adjusts its position based on the new scale.
     *
     * @param scale The scale parameter is a double value that represents the scaling factor to be
     *              applied to the windowPane. It determines how much the windowPane should be scaled up or down.
     */
    private void scaleWindowPane(double scale) {
        double x = windowingChosenSize.getX() * scale;
        double y = windowingChosenSize.getY() * scale;
        double width = windowingChosenSize.getWidth() * scale;
        double height = windowingChosenSize.getHeight() * scale;
        windowPane.setScaleX(scale);
        windowPane.setScaleY(scale);
        windowPane.setTranslateX(-x + (mainPane.getWidth() - width - 20) / 2 + (windowPane.getWidth() * (scale - 1) / 2));
        windowPane.setTranslateY(-y + (mainPane.getHeight() - height - 20) / 2 + (windowPane.getHeight() * (scale - 1) / 2));

        windowingChosenSize.setStrokeWidth(3 / scale);
        windowingSize.setStrokeWidth(3 / scale);
    }


    /**
     * This function zooms in on a window pane if the focus toggle button is selected.
     */
    @FXML
    private void zoomIn() {
        if (focusToggleButton.isSelected()) {
            double scale = windowPane.getScaleX() * 1.35;
            scaleWindowPane(scale);
        }
    }


    /**
     * This function zooms out the window pane by reducing its scale by 1.35 if the focus toggle button
     * is selected.
     */
    @FXML
    private void zoomOut() {
        if (focusToggleButton.isSelected()) {
            double scale = windowPane.getScaleX() / 1.35;
            scaleWindowPane(scale);
        }
    }

    /**
     * This removes focus from the selected windowing area
     */
    public void deactivateFocus() {
        windowPane.setTranslateX(0);
        windowPane.setTranslateY(0);
        windowPane.setScaleX(1);
        windowPane.setScaleY(1);
        windowingSize.setStrokeWidth(3);
        windowingChosenSize.setStrokeWidth(3);
        zoomIn.setVisible(false);
        zoomOut.setVisible(false);
    }

    /**
     * The function sets the size of the chosen windowing area
     */
    private void setChosenSize() {
        double scale = windowingSize.getWidth() / (windowing.getMaxX() - windowing.getMinX());
        windowingChosenSize.setX((windowing.getChosenMinX() - windowing.getMinX()) * scale);
        windowingChosenSize.setWidth((windowing.getChosenMaxX() - windowing.getChosenMinX()) * scale);
        windowingChosenSize.setHeight((windowing.getChosenMaxY() - windowing.getChosenMinY()) * scale);
        windowingChosenSize.setY(-(windowing.getChosenMaxY() - windowing.getMaxY()) * scale);
        focusOnWindowingChosenSize();
    }

    /**
     * This function draws the lines on the window pane.
     *
     * @param segments The segments parameter is an ArrayList of Double arrays that represent the
     *                 segments that are to be drawn on the window pane.
     */
    public void drawLines(ArrayList<Double[]> segments) {
        this.segments = segments;
        eraseLines();
        if (focusToggleButton.isSelected()) {
            for (Double[] segment : segments) {
                drawLineForFocus(segment[0], segment[1], segment[2], segment[3]);
            }
        } else updateLines(1);

    }

    /**
     * Draws a line on a graphics window using Cartesian coordinates.
     *
     * @param x1 the x-coordinate of the starting point of the line
     * @param y1 the y-coordinate of the starting point of the line
     * @param x2 the x-coordinate of the ending point of the line
     * @param y2 the y-coordinate of the ending point of the line
     */
    public void drawLineForFocus(double x1, double y1, double x2, double y2) {
        double scale = windowingSize.getWidth() / (windowing.getMaxX() - windowing.getMinX());
        Line line = new Line();
        line.setStartX((x1 - windowing.getMinX()) * scale);
        line.setStartY(windowingSize.getHeight() - (y1 - windowing.getMinY()) * scale);
        line.setEndX((x2 - windowing.getMinX()) * scale);
        line.setEndY(windowingSize.getHeight() - (y2 - windowing.getMinY()) * scale);
        line.setStrokeWidth(this.lineStrokeWidth);
        lines.add(line);
        linesPane.getChildren().add(line);
    }

    /**
     * It clears the linesPane and the lines ArrayList
     */
    public void eraseLines() {
        windowPane.getChildren().remove(linesPane);
        linesPane = new Pane();
        windowPane.getChildren().add(linesPane);
        this.lines = new ArrayList<>();
    }

    /**
     * For each line in the list of lines, multiply the start and end x and y coordinates by the scale,
     * and multiply the stroke width by the scale.
     *
     * @param scale The scale to multiply the lines by.
     */
    public void updateLines(double scale) {
        this.lineStrokeWidth = lineStrokeWidth * scale;
        if (focusToggleButton.isSelected()) {
            for (Line line : lines) {
                line.setStartX(line.getStartX() * scale);
                line.setStartY(line.getStartY() * scale);
                line.setEndX(line.getEndX() * scale);
                line.setEndY(line.getEndY() * scale);
                line.setStrokeWidth(lineStrokeWidth);
            }
        } else {
            eraseLines();
            scale = windowingSize.getWidth() / (windowing.getMaxX() - windowing.getMinX());
            Canvas canvas = new Canvas(windowingSize.getWidth(), windowingSize.getHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setLineWidth(lineStrokeWidth);
            for (Double[] seg : segments) {
                gc.strokeLine((seg[0] - windowing.getMinX()) * scale,
                        windowingSize.getHeight() - (seg[1] - windowing.getMinY()) * scale,
                        (seg[2] - windowing.getMinX()) * scale,
                        windowingSize.getHeight() - (seg[3] - windowing.getMinY()) * scale);
            }
            linesPane.getChildren().add(canvas);
        }
    }

    /**
     * For each line in the list of lines, set the stroke width to the current stroke width times the
     * scale.
     */
    public void changeLineSize() {
        if (focusToggleButton.isSelected()) {
            for (Line line : lines) {
                line.setStrokeWidth(lineStrokeWidth);
            }
        } else {
            updateLines(1);
        }

    }


    /**
     * This function doubles the line stroke width and calls another function to apply the change.
     */
    @FXML
    private void increaseLineSize() {
        this.lineStrokeWidth = lineStrokeWidth * 2;
        changeLineSize();
    }


    /**
     * This function decreases the line stroke width by half and calls another function to update the
     * line size.
     */
    @FXML
    private void decreaseLineSize() {
        this.lineStrokeWidth = lineStrokeWidth / 2;
        changeLineSize();
    }

    /**
     * The function creates a pop-up window displaying an error message with an "OK" button to close
     * the window.
     *
     * @param txt The error message that will be displayed in the error window.
     */
    private void errorWindow(String txt) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Error");

        Label label = new Label(txt);
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> stage.close());

        VBox vbox = new VBox(label, okButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        Scene scene = new Scene(vbox);
        stage.setWidth(300);
        stage.setHeight(150);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
