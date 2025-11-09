package com.example.pdftoolkit;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button selectImageButton;
    private Button convertToPdfButton;
    private Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        imageView = findViewById(R.id.imageView);
        selectImageButton = findViewById(R.id.selectImageButton);
        convertToPdfButton = findViewById(R.id.convertToPdfButton);

        // Handle permissions for Android 13+
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
        }

        // Set up image selection button
        selectImageButton.setOnClickListener(v -> openImagePicker());

        // Set up PDF conversion button
        convertToPdfButton.setOnClickListener(v -> {
            if (selectedImageBitmap != null) {
                convertImageToPdf(selectedImageBitmap);
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private final ActivityResultContracts.RequestPermission requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (!isGranted) {
                    Toast.makeText(this, "Permission denied. Cannot access media.", Toast.LENGTH_SHORT).show();
                }
            });

    private void openImagePicker() {
        // Launch image picker intent
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerActivityResultLauncher.launch(intent);
    }

    private final ActivityResultContracts.StartActivityForResult imagePickerActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    try {
                        // Get the selected image's URI
                        Uri selectedImageUri = result.getData().getData();
                        selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        imageView.setImageBitmap(selectedImageBitmap);

                        // Enable the convert to PDF button
                        convertToPdfButton.setEnabled(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error selecting image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void convertImageToPdf(Bitmap bitmap) {
        Document document = new Document();

        // Set the path to save the PDF in the Downloads folder
        String fileName = "ConvertedImage.pdf";
        File downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File pdfFile = new File(downloadFolder, fileName);

        try {
            // Create a FileOutputStream for the PDF
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            PdfWriter.getInstance(document, fileOutputStream);

            // Open the document
            document.open();

            // Convert Bitmap to Image for iTextPDF
            Image pdfImage = Image.getInstance(getImageBytes(bitmap));
            pdfImage.setAlignment(Image.ALIGN_CENTER);
            pdfImage.scaleToFit(500, 500);  // Scale the image to fit in the PDF

            // Add the image to the document
            document.add(pdfImage);
            document.close();

            // Show success message
            Toast.makeText(this, "PDF saved to Downloads", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error converting image to PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
