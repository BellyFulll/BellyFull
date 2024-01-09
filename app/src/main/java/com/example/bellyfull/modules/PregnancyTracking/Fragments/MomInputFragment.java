package com.example.bellyfull.modules.PregnancyTracking.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.repository.dbMomInfoRepositoryImpl;
import com.example.bellyfull.modules.General.InputDialog;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MomInputFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;
    private String currentPhotoPath;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String downloadUrl;

    private EditText ETDigestiveSymptomsInput;
    private EditText ETPhysicalDiscomfortsInput;
    private EditText ETMentalAndEmotionalHealthInput;
    private EditText ETBreastAndBodyChangesInput;
    private EditText ETUrinaryAndReproductiveHealthInput;
    private EditText ETGastrointestinalSymptomsInput;

    private dbMomInfoRepositoryImpl impl;

    public MomInputFragment() {
        super(R.layout.fragment_mom_input);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        impl = new dbMomInfoRepositoryImpl(getContext());
        Button BtnMomInputUpdate = view.findViewById(R.id.BtnMomInputUpdate);
        Button BtnMomInputCancel = view.findViewById(R.id.BtnMomInputCancel);
        Button BtnDigestiveSymptoms = view.findViewById(R.id.BtnDigestiveSymptoms);
        Button BtnPhysicalDiscomforts = view.findViewById(R.id.BtnPhysicalDiscomforts);
        Button BtnMaterialAndEmotionalHealth = view.findViewById(R.id.BtnMentalAndEmotionalHealth);
        Button BtnBreastAndBodyChanges = view.findViewById(R.id.BtnBreastAndBodyChanges);
        Button BtnUrinaryAndReproductiveHealth = view.findViewById(R.id.BtnUrinaryAndReproductiveHealth);
        Button BtnGastrointestinalSymptoms = view.findViewById(R.id.BtnGastrointestinalSymptoms);
        ImageView cameraIcon = view.findViewById(R.id.cameraIcon);
        EditText ETFoodDiary = view.findViewById(R.id.ETFoodDiary);
        EditText ETSleepPattern = view.findViewById(R.id.ETSleepPatterns);
        ETDigestiveSymptomsInput = view.findViewById(R.id.ETDigestiveSymptomsInput);
        ETPhysicalDiscomfortsInput = view.findViewById(R.id.ETPhysicalDiscomfortsInput);
        ETMentalAndEmotionalHealthInput = view.findViewById(R.id.ETMentalAndEmotionalHealthInput);
        ETBreastAndBodyChangesInput = view.findViewById(R.id.ETBreastAndBodyChangesInput);
        ETUrinaryAndReproductiveHealthInput = view.findViewById(R.id.ETUrinaryAndReproductiveHealthInput);
        ETGastrointestinalSymptomsInput = view.findViewById(R.id.ETGastrointestinalSymptomsInput);

        BtnDigestiveSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(ETDigestiveSymptomsInput);
            }
        });

        BtnPhysicalDiscomforts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(ETPhysicalDiscomfortsInput);
            }
        });

        BtnMaterialAndEmotionalHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(ETMentalAndEmotionalHealthInput);
            }
        });

        BtnBreastAndBodyChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(ETBreastAndBodyChangesInput);
            }
        });

        BtnUrinaryAndReproductiveHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(ETUrinaryAndReproductiveHealthInput);
            }
        });

        BtnGastrointestinalSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(ETGastrointestinalSymptomsInput);
            }
        });

        // Set an OnClickListener to open the camera app when the camera icon is clicked
        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check camera permission
                if (!checkCameraPermission(requireContext())) {
                    requestCameraPermission(requireContext());
                } else {
                    dispatchTakePictureIntent();
                }
            }
        });

        BtnMomInputUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String digestiveSymptoms = ETDigestiveSymptomsInput.getText().toString();
                String physicalDiscomforts = ETPhysicalDiscomfortsInput.getText().toString();
                String mentalAndEmotionalHealth = ETMentalAndEmotionalHealthInput.getText().toString();
                String breastAndBodyChanges = ETBreastAndBodyChangesInput.getText().toString();
                String urinaryAndReproductiveHealth = ETUrinaryAndReproductiveHealthInput.getText().toString();
                String gastrointestinalSymptoms = ETGastrointestinalSymptomsInput.getText().toString();
                String foodDiary = ETFoodDiary.getText().toString();
                String sleepPattern = ETSleepPattern.getText().toString();

                SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
                String userId = preferences.getString(preference_constant.pUserId, "");
                String babyInfoId = UUID.randomUUID().toString();
                impl.createMomInfo(userId, babyInfoId);

                if (!digestiveSymptoms.matches("")) {
                    impl.setDigestiveSymptomsCondition(babyInfoId, digestiveSymptoms);
                }

                if (!physicalDiscomforts.matches("")) {
                    impl.setPhysicalDiscomfortsCondition(babyInfoId, physicalDiscomforts);
                }

                if (!mentalAndEmotionalHealth.matches("")) {
                    impl.setMentalAndEmotionalHealthCondition(babyInfoId, mentalAndEmotionalHealth);
                }

                if (!breastAndBodyChanges.matches("")) {
                    impl.setBreastAndBodyChanges(babyInfoId, breastAndBodyChanges);
                }

                if (!urinaryAndReproductiveHealth.matches("")) {
                    impl.setUrinaryAndReproductiveHealth(babyInfoId, urinaryAndReproductiveHealth);
                }

                if (!gastrointestinalSymptoms.matches("")) {
                    impl.setGastrointestinalSymptoms(babyInfoId, gastrointestinalSymptoms);
                }

                if (!foodDiary.matches("")) {
                    impl.setFoodDiary(babyInfoId, foodDiary);
                }

                if (!sleepPattern.matches("")) {
                    impl.setSleepPatterns(babyInfoId, sleepPattern);
                }

                NavDirections action = MomInputFragmentDirections.actionMomInputFragmentToHomeFragment();
                Toast.makeText(getContext(), "Mom Info successfully added", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(action);
            }
        });

        BtnMomInputCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = MomInputFragmentDirections.actionMomInputFragmentToHomeFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Handle errors while creating the file
                Log.e("MomInputFragment", "Error creating image file", ex);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(), "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);
        // Save a file path for use with ACTION_VIEW intents
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public static boolean checkCameraPermission(Context context) {
        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    private void uploadPhotoToFirebase(Uri photoUri) {
        if (photoUri != null) {
            StorageReference photoRef = storageReference.child("images/" + photoUri.getLastPathSegment());
            UploadTask uploadTask = photoRef.putFile(photoUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Photo upload success
                // You can handle success actions here
                downloadUrl = photoRef.getDownloadUrl().toString();
                Log.d("MomInputFragment", "Photo uploaded successfully");
            }).addOnFailureListener(exception -> {
                // Photo upload failed
                // You can handle failure actions here
                Log.e("MomInputFragment", "Error uploading photo", exception);
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Photo was successfully taken
            if (currentPhotoPath != null) {
                File photoFile = new File(currentPhotoPath);
                Uri photoUri = Uri.fromFile(photoFile);

                // Upload the photo to Firebase Storage
                uploadPhotoToFirebase(photoUri);
                // change image view to show the photo
                ImageView imageUpload = requireView().findViewById(R.id.imageUpload);
                imageUpload.setImageURI(photoUri);
                // set layout height to wrap content
                imageUpload.getLayoutParams().height = 450;
            }
        }
    }

    public void openDialog(EditText editText) {
        InputDialog exampleDialog = new InputDialog(editText);
        exampleDialog.show(getActivity().getSupportFragmentManager(), "input dialog");
    }

}