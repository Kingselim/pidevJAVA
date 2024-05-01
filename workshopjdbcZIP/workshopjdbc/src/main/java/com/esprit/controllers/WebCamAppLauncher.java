package com.esprit.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import com.github.sarxos.webcam.Webcam;

import com.github.sarxos.webcam.util.ImageUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;


/**
 * This example demonstrates how to use Webcam Capture API in a JavaFX application.
 *
 * @author Rakesh Bhatt (rakeshbhatt10)
 */
public class WebCamAppLauncher extends Application {
    public String finalidwebcam;
//    int finalid;
//    public void setData(String number) {
//        System.out.println(number);
//          int finalid = Integer.parseInt(number);
//
//        System.out.println(finalid);
//
//    }

    public String finalexternalVariable; // Variable externe

    public void setData(String number) {
        //System.out.println(number); // Imprime la valeur de number
        //finalexternalVariable = number; // Assigner la valeur de number à la variable externe
    }

    public String getExternalVariable() {
        return finalexternalVariable; // Renvoyer la valeur de la variable externe
    }
    public void setExternalVariable(String data)
    {
        finalexternalVariable = data;
    }







    private class WebCamInfo {

        private String webCamName;
        private int webCamIndex;

        public String getWebCamName() {
            return webCamName;
        }

        public void setWebCamName(String webCamName) {
            this.webCamName = webCamName;
        }

        public int getWebCamIndex() {
            return webCamIndex;
        }

        public void setWebCamIndex(int webCamIndex) {
            this.webCamIndex = webCamIndex;
        }

        @Override
        public String toString() {
            return webCamName;
        }
    }

    private FlowPane bottomCameraControlPane;
    private FlowPane topPane;
    private BorderPane root;
    private String cameraListPromptText = "Choose Camera";
    private ImageView imgWebCamCapturedImage;
    private Webcam webCam = null;
    private boolean stopCamera = false;
    private BufferedImage grabbedImage;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
    private BorderPane webCamPane;
    private Button btnCamreaStop;
    private Button btnCamreaStart;
    private Button btnCameraDispose;
    private Button btnCameraCapture;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Connecting Camera Device Using Webcam Capture API");

        root = new BorderPane();
        topPane = new FlowPane();
        topPane.setAlignment(Pos.CENTER);
        topPane.setHgap(20);
        topPane.setOrientation(Orientation.HORIZONTAL);
        topPane.setPrefHeight(40);
        root.setTop(topPane);
        webCamPane = new BorderPane();
        webCamPane.setStyle("-fx-background-color: #ccc;");
        imgWebCamCapturedImage = new ImageView();
        webCamPane.setCenter(imgWebCamCapturedImage);
        root.setCenter(webCamPane);
        createTopPanel();
        bottomCameraControlPane = new FlowPane();
        bottomCameraControlPane.setOrientation(Orientation.HORIZONTAL);
        bottomCameraControlPane.setAlignment(Pos.CENTER);
        bottomCameraControlPane.setHgap(20);
        bottomCameraControlPane.setVgap(10);
        bottomCameraControlPane.setPrefHeight(40);
        bottomCameraControlPane.setDisable(true);
        createCameraControls();
        root.setBottom(bottomCameraControlPane);

        primaryStage.setScene(new Scene(root));
        primaryStage.setHeight(700);
        primaryStage.setWidth(600);
        primaryStage.centerOnScreen();
        primaryStage.show();

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                setImageViewSize();
            }
        });

    }

    protected void setImageViewSize() {

        double height = webCamPane.getHeight();
        double width = webCamPane.getWidth();

        imgWebCamCapturedImage.setFitHeight(height);
        imgWebCamCapturedImage.setFitWidth(width);
        imgWebCamCapturedImage.prefHeight(height);
        imgWebCamCapturedImage.prefWidth(width);
        imgWebCamCapturedImage.setPreserveRatio(true);

    }

    private void createTopPanel() {

        int webCamCounter = 0;
        Label lbInfoLabel = new Label("Select Your WebCam Camera");
        ObservableList<WebCamInfo> options = FXCollections.observableArrayList();

        topPane.getChildren().add(lbInfoLabel);

        for (Webcam webcam : Webcam.getWebcams()) {
            WebCamInfo webCamInfo = new WebCamInfo();
            webCamInfo.setWebCamIndex(webCamCounter);
            webCamInfo.setWebCamName(webcam.getName());
            options.add(webCamInfo);
            webCamCounter++;
        }

        ComboBox<WebCamInfo> cameraOptions = new ComboBox<WebCamInfo>();
        cameraOptions.setItems(options);
        cameraOptions.setPromptText(cameraListPromptText);
        cameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {

            @Override
            public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
                if (arg2 != null) {
                    System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
                    initializeWebCam(arg2.getWebCamIndex());
                }
            }
        });
        topPane.getChildren().add(cameraOptions);
    }

    protected void initializeWebCam(final int webCamIndex) {

        Task<Void> webCamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                if (webCam != null) {
                    disposeWebCamCamera();
                }

                webCam = Webcam.getWebcams().get(webCamIndex);
                webCam.open();
                return null;
            }
        };

        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();

        bottomCameraControlPane.setDisable(false);
        btnCamreaStart.setDisable(true);

        String val = getExternalVariable();
        System.out.println(val);
    }

    protected void startWebCamStream() {

        stopCamera = false;

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                final AtomicReference<WritableImage> ref = new AtomicReference<>();
                BufferedImage img = null;

                while (!stopCamera) {
                    try {
                        if ((img = webCam.getImage()) != null) {

                            ref.set(SwingFXUtils.toFXImage(img, ref.get()));
                            img.flush();

                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    imageProperty.set(ref.get());
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);

    }

    private void createCameraControls() {

        btnCamreaStop = new Button();
        btnCamreaStop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                stopWebCamCamera();
            }
        });
        btnCamreaStop.setText("Stop Camera");
        btnCamreaStart = new Button();
        btnCamreaStart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                startWebCamCamera();
            }
        });
        btnCamreaStart.setText("Start Camera");
        btnCameraDispose = new Button();
        btnCameraDispose.setText("Dispose Camera");
        btnCameraDispose.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                disposeWebCamCamera();
            }
        });
        btnCameraCapture= new Button();
        btnCameraCapture.setText("Capture");
        btnCameraCapture.setOnAction(event ->  {

            // ce peuti bout de code est utilise pour prendre une capture de la webcam
            BufferedImage image = webCam.getImage();
//            try {
//
//                ImageIO.write(image, ImageUtils.FORMAT_JPG, new File("C:\\Users\\21650\\Desktop\\3A40\\semaistre 2\\pidevJava\\workshopjdbc\\src\\main\\resources\\images\\selfie"+finalidwebcam+".jpg"));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            startWebCamStream();
            webCam.close();
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close(); // Close the current stage
            // Load and show the new interface
            System.out.println("finalid ===2222222==========="+finalexternalVariable);

            try {
                // Chargement de la vue AffcherCompte.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
                Parent root = loader.load();

                AjouterUser controller = loader.getController();
                controller.setData("selfie.jpg");
                controller.imageAjouter=image;
                // Création de la scène
                Scene scene = new Scene(root);
                // Création de la nouvelle fenêtre pour afficher la vue AffcherCompte.fxml
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Page de Modification");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, if any
            }
            // fin du peutit bout de code
        });
        bottomCameraControlPane.getChildren().add(btnCamreaStart);
        bottomCameraControlPane.getChildren().add(btnCamreaStop);
        bottomCameraControlPane.getChildren().add(btnCameraDispose);
        bottomCameraControlPane.getChildren().add(btnCameraCapture);
    }

    protected void disposeWebCamCamera() {
        stopCamera = true;
        webCam.close();
        btnCamreaStart.setDisable(true);
        btnCamreaStop.setDisable(true);

    }

    protected void startWebCamCamera() {
        stopCamera = false;
        startWebCamStream();
        btnCamreaStop.setDisable(false);
        btnCamreaStart.setDisable(true);
    }

    protected void stopWebCamCamera() {
        stopCamera = true;
        btnCamreaStart.setDisable(false);
        btnCamreaStop.setDisable(true);

    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}