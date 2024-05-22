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

import com.example.myapplication.databinding.FragmentBlankBinding;
import com.example.myapplication.databinding.FragmentPhotoBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class BlankFragment extends Fragment {


    FragmentBlankBinding binding;
    private static final String MAIN_ACTIVITY = "MainActivity";
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBlankBinding.inflate(inflater, null, false);

        binding.str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlert.alertDialogCreateRoom(getContext(), getLayoutInflater());
            }
        });
        binding.stre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlert.alertDialogEnterRoom(getContext(), getLayoutInflater());
            }
        });
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
    }


}