package com.example.lov.gui.fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.lov.DB.DataBaseHandler;
import com.example.lov.R;
import com.example.lov.model.User;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    private ImageView profilePicImageView;
    private Button editPrfoilePicButton;
    private DataBaseHandler dataBaseHandler;
    private EditText editEmail;
    private EditText editPassword;
    private Button applyButton;
    boolean change = false;

    private User user;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        dataBaseHandler = new DataBaseHandler(getContext());
        profilePicImageView = rootView.findViewById(R.id.user_profile_edit_image_view);
        editPrfoilePicButton = rootView.findViewById(R.id.user_profile_image_edit_button);
        editEmail = rootView.findViewById(R.id.user_email_edit);
        editPassword = rootView.findViewById(R.id.user_password_edit);
        applyButton = rootView.findViewById(R.id.apply_edit_button);

        editPrfoilePicButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);
        this.user = getUser();

        editTextSet();
        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.user_profile_image_edit_button: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                    break;
                }
            }
            case R.id.apply_edit_button: {
                String mail = editEmail.getText().toString();
                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(mail);
                if (!mail.equals(user.getEmail()) && matcher.matches()) {
                    if (!dataBaseHandler.checkEmail(mail))
                        Toast.makeText(getContext(), "Email already taken", Toast.LENGTH_SHORT).show();
                    user.setEmail(mail);
                    change = true;
                } else {
                    if (!matcher.matches())
                        Toast.makeText(getContext(), "This isnt a real email", Toast.LENGTH_SHORT).show();
                }

                String pass = editPassword.getText().toString();

                if (pass.length() >= 6 && pass.length() <= 15) {
                    try {
                        user.setPassword(SHA1(pass));
                        change = true;
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Something went wrong pls try again ", Toast.LENGTH_SHORT).show();
                    }
                } else if (!pass.isEmpty()) {
                    Toast.makeText(getContext(), "Password regex is incorrect", Toast.LENGTH_SHORT).show();
                }
                if(change) {
                    if (dataBaseHandler.updateUser(user))
                        Toast.makeText(getContext(), "Data updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Something went wrong try again", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        pickImageFromGallery();
                } else {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //////////////////PO WYBRANIU OBRAZKA PRZEZ USERA (MOZE CALY UPDATE USERA)

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Uri uri = null;
            try {
                uri = data.getData();
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), "Something went wrong try again", Toast.LENGTH_SHORT).show();
            }

            try {
                profilePicImageView.setImageURI(uri);
                String avatarSource = uri.toString();
                user.setAvatar(avatarSource);
                change=true;

            } catch (NullPointerException e) {
                Toast.makeText(getContext(), "Something went wrong try again", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private User getUser() {
        return dataBaseHandler.getActiveUser();
    }

    //TODO nie wiem czemu nie dziala

    private void editTextSet() {
        editEmail.setText(user.getEmail());
      //  if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      //      pickImageFromGallery();
     //   } else {
     //      Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
     //   }
     //   if (ContextCompat.checkSelfPermission(thisActivity, Manifest.permission.WRITE_CALENDAR)
     //           != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
      //  Picasso.get().load(user.getAvatar()).into(profilePicImageView);
        //Picasso.get().load("https://ssl-gfx.filmweb.pl/ph/91/13/299113/160507.1.jpg").into(profilePicImageView);
           //     Picasso.get().load(user.getAvatar()).into(profilePicImageView);
             //   profilePicImageView.setImageURI(Uri.parse(user.getAvatar()));

   //     Uri uri = Uri.parse("android.resource://your.package.here/drawable/image_name");
    //    InputStream stream = getContentResolver().openInputStream(uri);

        String[] permisions={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this.getContext(),permisions[0])==PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(this.getContext(),permisions[1])==PackageManager.PERMISSION_GRANTED)
            profilePicImageView.setImageURI(Uri.parse(user.getAvatar()));


          //  Picasso.get().load(user.getAvatar()).resize(200,200).into(profilePicImageView);
        }

//    public static final Uri getUriToDrawable(@NonNull Context context,
//                                             @AnyRes int drawableId) {
//        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
//                "://" + context.getResources().getResourcePackageName(drawableId)
//                + '/' + context.getResources().getResourceTypeName(drawableId)
//                + '/' + context.getResources().getResourceEntryName(drawableId) );
//        return imageUri;
//    }


    private static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : sha1hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
