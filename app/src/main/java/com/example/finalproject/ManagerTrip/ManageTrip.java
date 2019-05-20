package com.example.finalproject.ManagerTrip;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.finalproject.Navigation.NavigationDrawerTrips;
import com.example.finalproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ManageTrip extends FragmentActivity
        implements CustomDatePickerFragment.OnDataPassStart,
        CustomDatePickerFragmentOnlyForENDDate.OnDataPassEnd {

    private static final int PERMISION_CODE_TAKE = 1000;
    private static final int PERMISION_CODE_SELECT = 999;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int IMAGE_SELECTED_CODE = 1002;
    public static final String TITLE = "Title";
    public static final String DATE_START = "Start date";
    public static final String DATE_END = "End date";
    public static final String NAME = "Name";
    public static final String DESTINATION = "Destination";
    public static final String SEAR_SIDE = "Sea side";
    public static final String MOUNTAIN = "Mountain";
    public static final String CITY_BREAK = "City break";


    private String mStartDateReceived, mEndDateReceived;
    private EditText mTripName, mDestination;
    private CheckBox mCityBreak, mSeaSide, mMountain;
    private RatingBar mRating;
    private SeekBar mSeekBar;
    private Button mButtonSelectPhoto, mButtonTakePhoto;
    private ImageView mImageView;
    private Uri imageUri;

    private FirebaseFirestore mFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_trip);

        initView();
        setStartDateOnClickListener();
        setEndDateOnClickListener();
        takePhotoOnClickListener();
        selectPhotoOnClickListener();

        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void initView() {
        mTripName = findViewById(R.id.tripName);
        mDestination = findViewById(R.id.destination);
        mCityBreak = findViewById(R.id.checkBoxCityBreak);
        mSeaSide = findViewById(R.id.checkBoxSeaSide);
        mMountain = findViewById(R.id.checkBoxMountains);
        mRating = findViewById(R.id.ratingBar);
        mSeekBar = findViewById(R.id.seekBar);
        mButtonSelectPhoto = findViewById(R.id.button_select_photo);
        mButtonTakePhoto = findViewById(R.id.button_take_photo);
        mImageView = findViewById(R.id.imageView_previewImage);
    }

    @Override
    public void onDataPassStart(String data) {
        this.mStartDateReceived = data;
    }

    @Override
    public void onDataPassEnd(String data) {
        this.mEndDateReceived = data;
    }

    private void setStartDateOnClickListener() {
        findViewById(R.id.buttonStartDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new CustomDatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
                //mStartDateText.setText(mDateToSetInTextView);
            }
        });
    }

    private void setEndDateOnClickListener() {
        findViewById(R.id.buttonEndDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new CustomDatePickerFragmentOnlyForENDDate();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });
    }

    private void takePhotoOnClickListener() {
        mButtonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        String[] permisions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permisions, PERMISION_CODE_TAKE);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });
    }

    private void openCamera() {
        //File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //String pictureName = getPictureName();
        //File imageFile = new File(pictureFolder,pictureName);
        //imageUri.fromFile(imageFile);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    private void selectPhotoOnClickListener() {
        mButtonSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permisions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permisions, PERMISION_CODE_SELECT);
                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();
                }
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_SELECTED_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISION_CODE_TAKE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_LONG).show();
                }
            }
            case PERMISION_CODE_SELECT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IMAGE_CAPTURE_CODE) {
            if (resultCode == RESULT_OK) {
                mImageView.setImageURI(imageUri);
            }
        } else {
            if (resultCode == RESULT_OK) {
                mImageView.setImageURI(data.getData());
            }
        }
    }

    public void onClickSave(View view) {

        /*if (tripName != null && destination != null && mStartDateReceived != null && mEndDateReceived != null) {
            Log.d("Trip Name", tripName);
            Log.d("Destination", destination);
            Log.d("City Break", String.valueOf(cityBreak));
            Log.d("Sea Side", String.valueOf(seaSide));
            Log.d("Mountain", String.valueOf(mountians));
            Log.d("Start date", mStartDateReceived);
            Log.d("End date", mEndDateReceived);
            Log.d("Rating", String.valueOf(rating));
        } else {
            Log.d("Please ", "complete all fileds.");
        }
        Intent sendIntent = new Intent(ManageTrip.this, RecyclerViewActivity.class);
        sendIntent.putExtra(TITLE, tripName);
        sendIntent.putExtra(DESTIANTION, destination);
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, tripName);
        bundle.putString(DESTIANTION, destination);
        startActivity(sendIntent, bundle);*/

        String tripName = mTripName.getText().toString();
        String destination = mDestination.getText().toString();
        boolean cityBreak = mCityBreak.isChecked();
        boolean seaSide = mSeaSide.isChecked();
        boolean mountians = mMountain.isChecked();
        int rating = mRating.getNumStars();

        Map<String, String> tripsMap = new HashMap<>();
        tripsMap.put(NAME, tripName);
        tripsMap.put(DESTINATION, destination);
        if(seaSide){
            tripsMap.put(SEAR_SIDE,"True");
        }
        if(mountians){
            tripsMap.put(MOUNTAIN,"True");
        }
        if(cityBreak){
            tripsMap.put(CITY_BREAK,"True");
        }
        tripsMap.put(DATE_START,mStartDateReceived);
        tripsMap.put(DATE_END,mEndDateReceived);
        tripsMap.put("Rating",String.valueOf(rating));

        CollectionReference tripsColection = mFirebaseFirestore.collection("trips");
        tripsColection.add(tripsMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ManageTrip.this,
                                "Trip added to FireStore", Toast.LENGTH_LONG).show();
                        Log.d("FireSotre", "on succes Listener");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error = e.getMessage();
                Toast.makeText(ManageTrip.this,
                        "Error: " + error, Toast.LENGTH_LONG).show();
                Log.d("FireSotre", "on faliure Listener");
            }
        });

        Intent intent = new Intent(ManageTrip.this, NavigationDrawerTrips.class);
        startActivity(intent);
    }

}



