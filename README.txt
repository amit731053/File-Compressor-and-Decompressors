# File Compressor & Decompressor

## Project Description
File Compressor & Decompressor is a desktop JavaFX application built in Java 17.  
It allows users to **compress multiple files into a ZIP** and **decompress ZIP files** easily.  
The application shows log messages to confirm each operation.

## Features
- Select multiple files for compression
- Compress files into a ZIP archive
- Select a ZIP file for decompression
- Decompress files to a chosen folder
- Log area shows operation details:
  - Original size
  - Compressed size
  - Time taken

## Tools & Technologies
- **Java 17 (Temurin)**  
- **JavaFX 17**  
- **IntelliJ IDEA**  
- **java.util.zip** package

# How to Run
## Option 1: Run via IntelliJ
1. Open the project in IntelliJ.
2. Run the main class: `CompressorApp.main()`.
3. The application window will open with buttons:
   - Select Files → Compress
   - Select ZIP → Decompress

## Option 2: Run via JAR
1. Open Command Prompt and navigate to the JAR folder:
example : C:\Users\LENOVO\OneDrive\Desktop\FileCompressor\out\artifacts\FileCompressor_jar
2. Run the JAR with JavaFX modules:
example : java --module-path "C:\Users\LENOVO\Downloads\openjfx-17.0.2_windows-x64_bin-sdk\javafx-sdk-17.0.2\lib" --add-modules javafx.controls,javafx.fxml -jar FileCompressor.jar


# Demo Video
- Record a 1–2 minute screen video showing:
1. Selecting files and compressing
2. Selecting a ZIP file and decompressing
3. Logs showing completion for each action

# Submission Contents
- `CompressorApp.java` → Source code  
- `FileCompressor.jar` → Executable JAR  
- `README.txt` → This file  
- `DemoVideo.mp4` → Demo recording  

# Author
Amit Kumar Vashishtha

