package com.example.denis.mytvshow.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ferreira.denis.mytvshow.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    List<AuthUI.IdpConfig> providers;
    private static final int RC_SIGN_IN = 1;
    public static final String ANONYMOUS = "anonymous";
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUsername = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        providers = new ArrayList<>();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    onSignedInInitialize();
                }else {
                    onSignOutCleanUp();
                    providers.clear();
                    providers.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
                    providers.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setTheme(R.style.LoginTheme)
                                    .setAvailableProviders(providers)
                                    .build(), RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if(requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, getResources().getString(R.string.action_sign_in_short), Toast.LENGTH_SHORT).show();
                //Open main activity
                Intent intent = new Intent(this, SerieListActivity.class);
                startActivity(intent);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, getResources().getString(R.string.error_sign_in_short), Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialize() {
        Intent intent = new Intent(this, SerieListActivity.class);
        startActivity(intent);
        finish();
    }

    private void onSignOutCleanUp() {
        mUsername = ANONYMOUS;
    }
}
