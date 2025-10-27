import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.*;
import java.util.List;
import java.util.zip.*;

public class CompressorApp extends Application {

    private TextArea logArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Compressor & Decompressor");

        Button selectFilesBtn = new Button("Select Files to Compress");
        Button compressBtn = new Button("Compress Selected");
        Button selectZipBtn = new Button("Select ZIP to Decompress");
        Button decompressBtn = new Button("Decompress Selected");

        logArea = new TextArea();
        logArea.setPrefHeight(200);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        HBox hBox1 = new HBox(10, selectFilesBtn, compressBtn);
        HBox hBox2 = new HBox(10, selectZipBtn, decompressBtn);
        root.getChildren().addAll(hBox1, hBox2, logArea);

        Scene scene = new Scene(root, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        final List<File>[] filesToCompress = new List[]{null};
        final File[] zipToDecompress = new File[1];

        selectFilesBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            filesToCompress[0] = fileChooser.showOpenMultipleDialog(primaryStage);
            if (filesToCompress[0] != null) {
                log("Selected " + filesToCompress[0].size() + " files for compression.");
            }
        });

        compressBtn.setOnAction(e -> {
            if (filesToCompress[0] == null || filesToCompress[0].isEmpty()) {
                alert("No files selected!");
                return;
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("compressed.zip");
            File output = fileChooser.showSaveDialog(primaryStage);
            if (output != null) {
                new Thread(() -> compressFiles(filesToCompress[0], output)).start();
            }
        });

        selectZipBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            zipToDecompress[0] = fileChooser.showOpenDialog(primaryStage);
            if (zipToDecompress[0] != null) {
                log("Selected ZIP: " + zipToDecompress[0].getName());
            }
        });

        decompressBtn.setOnAction(e -> {
            if (zipToDecompress[0] == null) {
                alert("No ZIP file selected!");
                return;
            }
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File destDir = directoryChooser.showDialog(primaryStage);
            if (destDir != null) {
                new Thread(() -> decompressFile(zipToDecompress[0], destDir)).start();
            }
        });
    }

    private void compressFiles(List<File> files, File output) {
        try (FileOutputStream fos = new FileOutputStream(output);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (File file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();
                    log("Compressed: " + file.getName());
                }
            }
            log("Compression Completed: " + output.getAbsolutePath());
        } catch (IOException ex) {
            alert("Error: " + ex.getMessage());
        }
    }

    private void decompressFile(File zipFile, File destDir) {
        try (FileInputStream fis = new FileInputStream(zipFile);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File outFile = new File(destDir, entry.getName());
                try (FileOutputStream fos = new FileOutputStream(outFile)) {
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                log("Decompressed: " + entry.getName());
                zis.closeEntry();
            }
            log("Decompression Completed: " + destDir.getAbsolutePath());
        } catch (IOException ex) {
            alert("Error: " + ex.getMessage());
        }
    }

    private void log(String message) {
        javafx.application.Platform.runLater(() -> logArea.appendText(message + "\n"));
    }

    private void alert(String message) {
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
            alert.showAndWait();
        });
    }
}
