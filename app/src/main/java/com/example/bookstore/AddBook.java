package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.bookstore.databinding.ActivityAddBookBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
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
    String imageName;

    private int pDate, pMonth, pYear;
    private int price;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddBookBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.addBookUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();
            }
        });
        binding.addBookSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

//        price slider listener

        binding.addBookPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                    textView.setText("£" + progress);

                    //Get the thumb bound and get its left value
                    price= seekBar.getThumb().getBounds().left;
                    binding.addBookPriceHeader.setText("Retail Price: £" + price);



                }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });
//        Date picker set up
        binding.addBookPublicationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal =  Calendar.getInstance();
                pDate = Cal.get(Calendar.DATE);
                pMonth = Cal.get(Calendar.MONTH);
                pYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBook.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        String dateTxt = date+"/"+month+"/"+year;
                        binding.addBookPublicationDate.setText(dateTxt);
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
        StorageReference imageStoreRef = storageRef.child("images/"+user.getUid()+"/"+imageName);
        UploadTask uploadTask = imageStoreRef.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                setImageDownloadUri();

            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(AddBook.this, "Failed to upload image!", Toast.LENGTH_SHORT ).show();
            }
        });
        }else {
            Toast.makeText(AddBook.this, "Please complete the form!", Toast.LENGTH_SHORT).show();
        }
    }
    private void setImageDownloadUri(){
        Task downloadTask = storageRef.child("images/"+user.getUid()+"/"+imageName).getDownloadUrl();
        downloadTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imgDownload = String.valueOf(uri);
                uploadAllData(imgDownload);
                Log.d("debug2", imgDownload);

            }
        });
        downloadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddBook.this, "Failed to get image!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Uploading all information of a new book to firebase
    private void uploadAllData(String imgLink) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String title = binding.addBookTitle.getText().toString();
        String author = binding.addBookAuthor.getText().toString();
        Date publicationDate = new Date(binding.addBookPublicationDate.getText().toString());
        String ISBN = binding.addBookISBN.getText().toString();
        String description = binding.addBookDescription.getText().toString();


        if(TextUtils.isEmpty(title)|| TextUtils.isEmpty(author)|| TextUtils.isEmpty(ISBN)|| TextUtils.isEmpty(description)|| price<=0){
            binding.addBookSubmit.setError("true");
            binding.addBookMainErr.setText("Please complete the form!");
        }else if (!validateDate(publicationDate)){
            binding.addBookSubmit.setError("true");
            binding.addBookMainErr.setText("Please enter a valid date!");
        } else {
            Map<String, Object> book = new HashMap<>();
            book.put("title", title);
            book.put("author", author);
            book.put("publicationDate", publicationDate);
            book.put("ISBN", ISBN);
            book.put("description", description);
            book.put("imageURL", imgLink);
            book.put("price", price);
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

//    on selection of image from files, add imageUri to the element
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data ){
        super .onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && data!=null && data.getData()!=null && data.getScheme().equals("content")){

            imageUri = data.getData();
            binding.addBookUpload.setImageURI(imageUri);
            imageName = getFilename(imageUri, AddBook.this);

        }

    }

    @SuppressLint("Range")
    String getFilename(Uri uri, Context context){
        String res = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if(uri.getScheme().equals("content")){
        try{
            if(cursor!=null && cursor.moveToFirst()){
                res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        }finally {
            cursor.close();
        }
        if(res==null){
            res = uri.getPath();
            int requiredSection= res.lastIndexOf('/');
            if(requiredSection!=-1){
                res = res.substring(requiredSection+1);
            }
        }
        }
        return res;

    }

//    validate date format
    public boolean validateDate(Date date){
        Matcher matcher;
        final String DATE_PATTERN =
                "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

        matcher = Pattern.compile(DATE_PATTERN).matcher(String.valueOf(date));
        Log.d("debug2", String.valueOf(matcher.find())+" value of matcher");
        if(matcher.find()){
                String day = matcher.group(1);
                String month = matcher.group(2);
            Log.d("debug2", String.valueOf(matcher.group(1))+" value of matcher group");
                int year = Integer.parseInt(matcher.group(3));
                if (day.equals("31") && (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") ||month.equals("06") ||
                                month.equals("09"))
                ){
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                }
                else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        return !day.equals("30") && !day.equals("31");
                    }
                    else{
                        return !day.equals("29") && !day.equals("30") && !day.equals("31");
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
}