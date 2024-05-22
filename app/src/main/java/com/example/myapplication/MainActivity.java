package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.InputSrokBinding;
import com.example.myapplication.repository.OnALertClose;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.security.Permission;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private MainViewModel viewModel;
    List<Product> productList = new ArrayList<>();
    final int NOTIFICATION_PERMISSION_CODE = 123;
    PhotoFragment photoFragment;
    FragmentManager manager;
    BlankFragment fragment;
    FirebaseAuth mAuth;

    RecyclerAdapter adapter;
    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View customLoadingView = getLayoutInflater().inflate(R.layout.splash_screen, null);
        setContentView(customLoadingView);
        //здесь добавление/получение юзера и  проверка на то есть ли юзер в комнате
        //если нет, то
         manager = getSupportFragmentManager();
         CustomAlert customAlert = new CustomAlert();
         fb = FirebaseFirestore.getInstance();
         mAuth = FirebaseAuth.getInstance();
         fragment= new BlankFragment();
         customAlert.setOnCloseFragment(new OnButtonAlertListener() {
             @Override
             public void closeAlert() {
                 manager.beginTransaction().remove(fragment).commit();
                 binding.mainlayout.setVisibility(View.VISIBLE);
                 binding.frame.setVisibility(View.GONE);
                 binding.all.setText("asdasd");
             }
         });
        customAlert.setOnALertClose(new OnALertClose() {
            @Override
            public void closeAlert() {
                viewModel.addProduct();
            }
        });

         photoFragment = new PhotoFragment();
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
            }
        }
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getProductList().observe(this, productList -> {
            setContentView(binding.getRoot());
            if (productList != null && !productList.isEmpty()) {
                setupRecyclerView(productList);

            }
        });
        binding.all.setOnClickListener(v -> viewModel.getProductList().observe(MainActivity.this, productList -> {
            if (productList != null && !productList.isEmpty()) {
                setupRecyclerView(productList);
            }
        }));
        binding.fresh.setOnClickListener(v -> viewModel.getFreshnessProduct(1).observe(MainActivity.this, products -> {
            if(products!=null&& !products.isEmpty()){
                setupRecyclerView(products);
            }
        }));
        binding.deadline.setOnClickListener(v -> viewModel.getFreshnessProduct(2).observe(MainActivity.this, products -> {
            if(products!=null&& !products.isEmpty()){
                setupRecyclerView(products);
            }
        }));
        binding.failed.setOnClickListener(v -> viewModel.getFreshnessProduct(3).observe(MainActivity.this, products -> {
            if(products!=null&& !products.isEmpty()){
                setupRecyclerView(products);
            }
        }));
        photoFragment.setOnCloseFragment(new OnButtonAlertListener() {
            @Override
            public void closeAlert() {
                binding.horizontalScrollView.setVisibility(View.VISIBLE);
                binding.btnPlus.setVisibility(View.VISIBLE);
                binding.recycler.setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(photoFragment).commit();
                binding.frame.setVisibility(View.GONE);
            }
        });
        binding.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnCamera.setVisibility(View.VISIBLE);
                binding.btnInput.setVisibility(View.VISIBLE);

            }
        });
        binding.btnCamera.setOnClickListener(v -> {
            binding.btnCamera.setVisibility(View.GONE);
            binding.btnInput.setVisibility(View.GONE);
            FragmentManager fragmentManager= getSupportFragmentManager();
            binding.frame.setVisibility(View.VISIBLE);
            binding.horizontalScrollView.setVisibility(View.GONE);
            binding.btnPlus.setVisibility(View.GONE);
            binding.recycler.setVisibility(View.GONE);
            fragmentManager.beginTransaction().replace(R.id.frame,photoFragment).commit();
        });
        binding.btnInput.setOnClickListener(v -> {
                    binding.btnCamera.setVisibility(View.GONE);
                    binding.btnInput.setVisibility(View.GONE);
                    CustomAlert.alertDialogDate(MainActivity.this, getLayoutInflater());
                });
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.START);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String id = productList.get(position).getId();
                FirestoreGetId firestoreGetId = new FirestoreGetId(fb);

                firestoreGetId.getId(mAuth.getCurrentUser().getUid(), userId -> {
                    fb.collection("Users")
                            .document(userId)
                            .collection("Products")
                            .whereEqualTo("id", productList.get(position).getId())
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        document.getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                adapter.deleteItem(position);
                                            }
                                        });

                                    }

                                }
                            });
                });


            }
        });
        helper.attachToRecyclerView(binding.recycler);



    }



    private void setupRecyclerView(List<Product> productList) {
        adapter = new RecyclerAdapter(this);
        adapter.setItems(productList);
        this.productList = productList;
        adapter.setItemClickListener(diaryEntry -> adapter.openFragment(this, diaryEntry, getLayoutInflater()));
        binding.recycler.setAdapter(adapter);
        binding.recycler.setClipToPadding(false);
    }
    public LocalDate convertStringToDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yy");
        return LocalDate.parse(dateStr, formatter);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        manager.beginTransaction().replace(R.id.frame,fragment).commit();
//        binding.mainlayout.setVisibility(View.GONE);
//        binding.frame.setVisibility(View.VISIBLE);
//    }
}
