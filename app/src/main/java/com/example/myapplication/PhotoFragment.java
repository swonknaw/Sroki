package com.example.myapplication;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.util.Consumer;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentPhotoBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class PhotoFragment extends Fragment {

    private static final String TAG = "PhotoFragment";

    private static final int PERMISSION_REQUEST_CODE = 10;

    private ImageCapture imageCapture;
    FragmentPhotoBinding binding;
    private static final String MAIN_ACTIVITY = "MainActivity";
   View view;
   OnButtonAlertListener onButtonAlertListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotoBinding.inflate(inflater, null, false);
        binding.btnPhoto.setOnClickListener(v -> takePhoto());
        binding.strelkaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonAlertListener.closeAlert();
            }
        });
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.view=view;
        if (allPermissionGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE
            );
        }


    }

    private boolean allPermissionGranted() {
        return ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void takePhoto() {
        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "img.png");

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.
                OutputFileOptions.Builder(file)
                .build();

        imageCapture.takePicture(outputFileOptions,
                ContextCompat.getMainExecutor(getContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Log.i(MAIN_ACTIVITY, file.getAbsolutePath());

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.i(MAIN_ACTIVITY, exception.getMessage());
                    }
                });
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> processCameraProviderListenableFuture =
                ProcessCameraProvider.getInstance(getContext());

        processCameraProviderListenableFuture.addListener(() -> {
                    try {
                        ProcessCameraProvider processCameraProvider = processCameraProviderListenableFuture.get();
                        Preview preview = new Preview.Builder().build();
                        PreviewView previewView = view.findViewById(R.id.pvCamera);
                        preview.setSurfaceProvider(previewView.getSurfaceProvider());

                        CameraSelector cameraSelector = new CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build();

                        imageCapture = new ImageCapture.Builder().build();

                        Camera camera = processCameraProvider.bindToLifecycle(this,
                                cameraSelector,
                                preview,
                                imageCapture
                        );

                    } catch (ExecutionException | InterruptedException e) {
                        Log.e(MAIN_ACTIVITY, e.getMessage());
                    }
                },
                ContextCompat.getMainExecutor(getContext())
        );

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (allPermissionGranted()) {
                startCamera();
            }
        }
    }
    public void setOnCloseFragment(OnButtonAlertListener onButtonAlertListener){
        this.onButtonAlertListener=onButtonAlertListener;
    }
}