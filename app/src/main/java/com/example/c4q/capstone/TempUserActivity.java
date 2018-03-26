package com.example.c4q.capstone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.c4q.capstone.database.publicuserdata.PublicUser;
import com.example.c4q.capstone.database.publicuserdata.UserIcon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.c4q.capstone.utils.Constants.PUBLIC_USER;
import static com.example.c4q.capstone.utils.Constants.USER_ICON;

/**
 * Created by melg on 3/19/18.
 */

public class TempUserActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 2;
    private CircleImageView profilePic;
    private TextView personName;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String currentUserId;
    private DatabaseReference rootRef, userRef, iconRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_user_profile);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        currentUserId = firebaseUser.getUid();

        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(PUBLIC_USER).child(currentUserId);
        iconRef = rootRef.child(USER_ICON).child(currentUserId);

        profilePic = findViewById(R.id.circle_imageview);
        personName = findViewById(R.id.user_name);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        currentUserProfileData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER) {

            try {
                final Uri uri = data.getData();

                UserIcon test = new UserIcon("hello");

                FirebaseDatabase.getInstance().getReference().child(USER_ICON).child(currentUserId).setValue(test, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {

                            String key = databaseReference.getKey();

                            StorageReference storage = FirebaseStorage.getInstance()
                                    .getReference(USER_ICON)
                                    .child(currentUserId)
                                    .child(key)
                                    .child(uri.getLastPathSegment());

                            storage.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        UserIcon test = new UserIcon(task.getResult().getMetadata().getDownloadUrl().toString());

                                        FirebaseDatabase.getInstance().getReference().child(USER_ICON).child(currentUserId).setValue(test);
                                    }
                                }
                            });
                        }
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

    }

    public void currentUserProfileData() {
        iconRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserIcon userIcon = dataSnapshot.getValue(UserIcon.class);
                try {

                    String userIconUrl = userIcon.getIcon_url();
                    Glide.with(TempUserActivity.this).load(userIconUrl).into(profilePic);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                    profilePic.setImageResource(R.drawable.default_avatar);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PublicUser publicUser = dataSnapshot.getValue(PublicUser.class);
                StringBuilder sb = new StringBuilder();
                sb.append(publicUser.getFirst_name());
                sb.append(" ");
                sb.append(publicUser.getLast_name());
                personName.setText(sb.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
