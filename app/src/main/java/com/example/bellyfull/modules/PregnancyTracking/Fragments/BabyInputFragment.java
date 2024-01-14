package com.example.bellyfull.modules.PregnancyTracking.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.bellyfull.Constant.preference_constant;
import com.example.bellyfull.R;
import com.example.bellyfull.data.firebase.repository.dbBabyInfoRepositoryImpl;
import com.example.bellyfull.utils.hideKeyboardUtils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class BabyInputFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;
    private String currentPhotoPath;
    private String photoName;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String downloadUrl;
    EditText ETFetalLength;
    EditText ETFetalWeight;
    EditText ETHeadCircumference;
    EditText ETGrowthNotes;
    dbBabyInfoRepositoryImpl impl;
    static final String TAG = "BabyInputFragment";

    public BabyInputFragment() {
        super(R.layout.fragment_baby_input);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        impl = new dbBabyInfoRepositoryImpl(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button BtnDownload = view.findViewById(R.id.btnDownload);
        ImageView IVFetalLengthInc = view.findViewById(R.id.IVFetalLengthInc);
        ImageView IVFetalLengthDec = view.findViewById(R.id.IVFetalLengthDec);
        ImageView IVFetalWeightInc = view.findViewById(R.id.IVFetalWeightInc);
        ImageView IVFetalWeightDec = view.findViewById(R.id.IVFetalWeightDec);
        ImageView IVHeadCircumferenceInc = view.findViewById(R.id.IVHeadCircumferenceInc);
        ImageView IVHeadCircumferenceDec = view.findViewById(R.id.IVHeadCircumferenceDec);
        ImageView cameraIcon = view.findViewById(R.id.cameraIcon);
        ETFetalLength = getView().findViewById(R.id.ETFetalLength);
        ETFetalWeight = getView().findViewById(R.id.ETFetalWeight);
        ETHeadCircumference = getView().findViewById(R.id.ETHeadCircumference);
        ETGrowthNotes = getView().findViewById(R.id.ETGrowthNotes);

        IVFetalLengthInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fetalLength = ETFetalLength.getText().toString();
                System.out.println(fetalLength);
                Double currentValue = 0.0;
                if (!fetalLength.matches("")) {
                    currentValue = Double.parseDouble(fetalLength);
                    currentValue += 0.5;
                } else {
                    currentValue = 0.5;
                }
                ETFetalLength.setText(currentValue.toString());
                ETFetalLength.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVFetalLengthDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fetalLength = ETFetalLength.getText().toString();
                Double currentValue = 0.0;
                if (!fetalLength.matches("") && !fetalLength.matches("0.0")) {
                    currentValue = Double.parseDouble(fetalLength);
                    currentValue -= 0.5;
                } else {
                    return;
                }
                ETFetalLength.setText(currentValue.toString());
                ETFetalLength.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVFetalWeightInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fetalWeight = ETFetalWeight.getText().toString();
                Double currentValue = 0.0;
                if (!fetalWeight.matches("")) {
                    currentValue = Double.parseDouble(fetalWeight);
                    currentValue += 0.5;
                } else {
                    currentValue = 0.5;
                }
                ETFetalWeight.setText(currentValue.toString());
                ETFetalWeight.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVFetalWeightDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fetalWeight = ETFetalWeight.getText().toString();
                Double currentValue = 0.0;
                if (!fetalWeight.matches("") && !fetalWeight.matches("0.0")) {
                    currentValue = Double.parseDouble(fetalWeight);
                    currentValue -= 0.5;
                } else {
                    return;
                }
                ETFetalWeight.setText(currentValue.toString());
                ETFetalWeight.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVHeadCircumferenceInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headCircumference = ETHeadCircumference.getText().toString();
                Double currentValue = 0.0;
                if (!headCircumference.matches("")) {
                    currentValue = Double.parseDouble(headCircumference);
                    currentValue += 0.5;
                } else {
                    currentValue = 0.5;
                }
                ETHeadCircumference.setText(currentValue.toString());
                ETHeadCircumference.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        IVHeadCircumferenceDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headCircumference = ETHeadCircumference.getText().toString();
                Double currentValue = 0.0;
                if (!headCircumference.matches("") && !headCircumference.matches("0.0")) {
                    currentValue = Double.parseDouble(headCircumference);
                    currentValue -= 0.5;
                } else {
                    return;
                }
                ETHeadCircumference.setText(currentValue.toString());
                ETHeadCircumference.clearFocus();
                hideKeyboardUtils.hideKeyboard(getActivity(), v);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = BabyInputFragmentDirections.actionBabyInputFragmentToHomeFragment();
                Navigation.findNavController(view).navigate(action);
            }

        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences(preference_constant.pUserInfo, Context.MODE_PRIVATE);
                String userId = preferences.getString(preference_constant.pUserId, "");
                String babyInfoId = UUID.randomUUID().toString();
                impl.createBabyInfo(userId, babyInfoId);

                String updatedFetalLength = ETFetalLength.getText().toString();
                String updatedFetalWeight = ETFetalWeight.getText().toString();
                String updatedHeadCircumference = ETHeadCircumference.getText().toString();
                String updatedGrowthNotes = ETGrowthNotes.getText().toString();

                if (!updatedFetalLength.matches("")) {
                    impl.setBabyInfoFetalLength(babyInfoId, Double.parseDouble(updatedFetalLength));
                    ETFetalLength.getText().clear();
                }
                if (!updatedFetalWeight.matches("")) {
                    impl.setBabyInfoFetalWeight(babyInfoId, Double.parseDouble(updatedFetalWeight));
                    ETFetalWeight.getText().clear();
                }
                if (!updatedHeadCircumference.matches("")) {
                    impl.setBabyInfoHeadCircumference(babyInfoId, Double.parseDouble(updatedHeadCircumference));
                    ETHeadCircumference.getText().clear();
                }
                if (!updatedGrowthNotes.matches("")) {
                    impl.setBabyInfoGrowthNotes(babyInfoId, updatedGrowthNotes);
                    ETGrowthNotes.getText().clear();
                }
                // if photo was taken, save photo downloadurl to database
                if (downloadUrl != null) {
                    impl.setPhotoUrl(babyInfoId, downloadUrl);
                }

                ETGrowthNotes.clearFocus();
                Toast.makeText(getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();

                // After updating the baby information, navigate to BabyVisualizationFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();


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

        BtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadUrl != null) {
                    downloadPhoto();
                } else {
                    Toast.makeText(getContext(), "Waiting upload to complete.", Toast.LENGTH_SHORT).show();
                }
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
                Log.e(TAG, "Error creating image file", ex);
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
        photoName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(photoName,  /* prefix */
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
                photoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    downloadUrl = uri.toString();
                    Log.d(TAG, "Photo uploaded successfully with downloadURL: " + downloadUrl);
                }).addOnFailureListener(exception -> {
                    // Handle the failure to get download URL
                    Log.e(TAG, "Error getting download URL", exception);
                });
            }).addOnFailureListener(exception -> {
                // Photo upload failed
                // You can handle failure actions here
                Log.e(TAG, "Error uploading photo", exception);
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
                // show download button
                Button btnDownload = requireView().findViewById(R.id.btnDownload);
                btnDownload.getLayoutParams().height = 120;
                btnDownload.setVisibility(View.VISIBLE);
            }
        }
    }

    private void downloadPhoto(){
        Toast.makeText(getContext(), "Downloading photo...", Toast.LENGTH_SHORT).show();
        StorageReference photoRef = storage.getReferenceFromUrl(downloadUrl);
        File publicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(publicDirectory, photoName + ".jpg");
        MediaScannerConnection mediaScanner = new MediaScannerConnection(getContext(), null);
        mediaScanner.connect();
        photoRef.getFile(imageFile).addOnSuccessListener(taskSnapshot -> {
            mediaScanner.scanFile(imageFile.toString(), null);
            Toast.makeText(getContext(), "Photo downloaded to gallery", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(exception -> {
            Log.e(TAG, "Error downloading photo", exception);
        });
    }
}