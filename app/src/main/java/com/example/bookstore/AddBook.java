package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bookstore.databinding.ActivityAddBookBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBook extends AppCompatActivity {
    ActivityAddBookBinding binding;

    StorageReference storageRef ;
    Uri imageUri;

    private int pDate, pMonth, pYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addbookUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();
            }
        });
        binding.addbookSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

//        Date picker set up
        binding.addbookPublicationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal =  Calendar.getInstance();
                pDate = Cal.get(Calendar.DATE);
                pMonth = Cal.get(Calendar.MONTH);
                pYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBook.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        binding.addbookPublicationDate.setText(date+"/"+month+"/"+year);
                    }
                }, pYear, pMonth, pDate);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

    }

//    upload image to firebase and get the download url
    private void uploadImage() {
        if(imageUri!=null){
        storageRef = FirebaseStorage.getInstance().getReference();

        storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                setImageDownloadUri();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(AddBook.this, "Failed to upload image!", Toast.LENGTH_SHORT ).show();
            }
        });}else {
            Toast.makeText(AddBook.this, "Please complete the form!", Toast.LENGTH_SHORT).show();
        }
    }
    private void setImageDownloadUri(){

        storageRef.child(String.valueOf(imageUri)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imgDownload = String.valueOf(uri);
                uploadAllData(imgDownload);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddBook.this, "Failed to add book!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Uploading all information of a new book to firebase
    private void uploadAllData(String imgLink) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String title = binding.addbookTitle.getText().toString();
        String author = binding.addbookAuthor.getText().toString();
        Date publicationDate = new Date(binding.addbookPublicationDate.getText().toString());
        String ISBN = binding.addbookISBN.getText().toString();
        String description = binding.addbookDescription.getText().toString();
        if(TextUtils.isEmpty(title)|| TextUtils.isEmpty(author)|| TextUtils.isEmpty(ISBN)|| TextUtils.isEmpty(description)){
            binding.addbookSubmit.setError("Please complete the form!");
        }else if (!validateDate(publicationDate)){
            binding.addbookSubmit.setError("Please enter a valid date!");
        } else {
            Map<String, Object> book = new HashMap<>();
            book.put("title", title);
            book.put("author", author);
            book.put("publicationDate", publicationDate);
            book.put("ISBN", ISBN);
            book.put("description", description);
            db.collection("books").add(book).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Intent intent = new Intent(AddBook.this, Home.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddBook.this, "Failed to add book!", Toast.LENGTH_SHORT).show();
                }
            });
        }





    }



//    Selecting image from files
    private void addImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

//    on selection of image from files, add imageuri to the element
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data ){
        super .onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && data!=null && data.getData()!=null){
            imageUri = data.getData();
            binding.addbookUpload.setImageURI(imageUri);
        }

    }

//    validate date format
    public boolean validateDate(Date date){
        Matcher matcher;
        final String DATE_PATTERN =
                "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

        matcher = Pattern.compile(DATE_PATTERN).matcher(String.valueOf(date));

        if(matcher.matches()){
            matcher.reset();
            if(matcher.find()){
                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));
                if (day.equals("31") &&
                        (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                }
                else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        if(day.equals("30") || day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                    else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                }
                else{
                    return true;
                }
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}