package com.example.perpus_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuBuku extends AppCompatActivity {

    ExtendedFloatingActionButton tambah_buku;
    EditText edit_judul, edit_pengarang, edit_penerbit, edit_tahun_terbit, edit_desc;
    DatabaseReference bukuDBRef;
    FirebaseDatabase mFirebaseInstance;
    DatabaseReference mDatabaseReference;
    ArrayList<Buku> listBuku;
    ListAdapter mAdapter;
    ListView mListView;
    SearchView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_buku);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        tambah_buku = (ExtendedFloatingActionButton)findViewById(R.id.tambah_buku);
        mListView = findViewById(R.id.list_buku);
        search = findViewById(R.id.search);

        FirebaseApp.initializeApp(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseInstance.getReference("buku");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBuku = new ArrayList<>();

                for (DataSnapshot mDataSnapshot: snapshot.getChildren()){
                    Buku mBuku = mDataSnapshot.getValue(Buku.class);
                    mBuku.setKode(mDataSnapshot.getKey());
                    listBuku.add(mBuku);
                }

                mAdapter = new ListAdapter(MenuBuku.this, listBuku);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuBuku.this,
                        error.getDetails() + " " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        tambah_buku = findViewById(R.id.tambah_buku);
        tambah_buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTambahBuku();
            }
        });

        SearchView searchView = (SearchView ) MenuItemCompat.getActionView(search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!TextUtils.isEmpty(s.trim()))}{


            }
             else{
                 User
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Buku> listBuku; = new ArrayList<>();
                for(Buku buku
                mAdapter = new ListAdapter(MenuBuku.this, listBuku);
                mListView.setAdapter(mAdapter);


            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MenuBuku.this, "Data diklik bun!", Toast.LENGTH_LONG).show();
                Log.d("diklik", "Data diklik bun!");
                onDataClick(listBuku.get(position), position);
            }
        });
    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void onDataClick(final Buku buku, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogUpdateBuku(buku);
            }
        });

        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                hapusDataBuku(buku);
            }
        });

        builder.setNeutralButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dialogTambahBuku() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Buku");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_buku, null);

        edit_judul = (EditText)view.findViewById(R.id.edit_judul_buku);
        edit_pengarang = (EditText)view.findViewById(R.id.edit_pengarang);
        edit_penerbit = (EditText)view.findViewById(R.id.edit_penerbit);
        edit_tahun_terbit = (EditText)view.findViewById(R.id.edit_tahun);
        edit_desc = (EditText)view.findViewById(R.id.edit_desc);

        builder.setView(view);

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String regJudul = edit_judul.getText().toString();
                String regPengarang = edit_pengarang.getText().toString();
                String regPenerbit = edit_penerbit.getText().toString();
                String regTahun = edit_tahun_terbit.getText().toString();
                String regDesc = edit_desc.getText().toString();
                String peminjam = "none";

                if (!regJudul.isEmpty() && !regPengarang.isEmpty() && !regPenerbit.isEmpty() && !regTahun.isEmpty()
                        && !regDesc.isEmpty()) {
                    submitDataBuku(new Buku(regJudul,regPengarang,regPenerbit,regTahun,regDesc,peminjam));
                } else {
                    Toast.makeText(MenuBuku.this, "Data harus di isi bun!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void dialogUpdateBuku(final Buku buku) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Buku");
        View view = getLayoutInflater().inflate(R.layout.layout_edit_buku, null);

        edit_judul = (EditText)view.findViewById(R.id.edit_judul_buku);
        edit_pengarang = (EditText)view.findViewById(R.id.edit_pengarang);
        edit_penerbit = (EditText)view.findViewById(R.id.edit_penerbit);
        edit_tahun_terbit = (EditText)view.findViewById(R.id.edit_tahun);
        edit_desc = (EditText)view.findViewById(R.id.edit_desc);

        builder.setView(view);

        if (buku != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    buku.setJudul(edit_judul.getText().toString());
                    buku.setPengarang(edit_pengarang.getText().toString());
                    buku.setPenerbit(edit_penerbit.getText().toString());
                    buku.setTahunterbit(edit_tahun_terbit.getText().toString());
                    buku.setDeskripsi(edit_desc.getText().toString());
                    updateDataBuku(buku);
                }
            });
        }

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();

    }

    private void submitDataBuku(Buku buku) {
        mDatabaseReference.push()
                .setValue(buku).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(MenuBuku.this, "Data buku berhasil di simpan bun!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateDataBuku(Buku buku) {
        mDatabaseReference.child(buku.getKode())
                .setValue(buku).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(MenuBuku.this, "Data berhasil di update bun!", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void hapusDataBuku(Buku buku) {
        if (mDatabaseReference != null) {
            mDatabaseReference.child("buku").child(buku.getKode())
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(MenuBuku.this, "Data berhasil di hapus bun!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}