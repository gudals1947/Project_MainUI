package com.example.leedongho.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Created by 김 형민 on 2018-08-28.
 */

public class Memberjoin extends AppCompatActivity {
    private static final int RC_SIGN_IN = 10;
    private static final String TAG = "김형민";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean choice = false;
    int idex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.memberjoin);
        getSupportActionBar().hide();

        // Configure Google Sign In

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        SignInButton googlebutton = (SignInButton) findViewById(R.id.loginbutton1);
        googlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_password);

        Button emailLogin = (Button) findViewById(R.id.bt_emailloging);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginId(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        TextView signin = (TextView) findViewById(R.id.Singin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginEmail.class);
                startActivity(intent);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Log.v(TAG, "12" + user.getEmail());
                    FirebaseFirestore.getInstance().collection("user").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                String isAttendance = documentChange.getDocument().getData().get("choice").toString();
                                if (isAttendance.equals("true") && idex == 0) {
                                    Intent intent = new Intent(getApplicationContext(), MainUI.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                            }
                        }
                    });

                } else {

                }

            }
        };
        mAuth = FirebaseAuth.getInstance();

    }

    private void loginId(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                        }
                    }
                });
    }



    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Memberjoin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());


        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
//                            try {
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                Intent intent = new Intent(getApplicationContext(), Login2.class);
//                                intent.putExtra("이름", mAuth.getInstance().getCurrentUser().getDisplayName());
//                                intent.putExtra("이메일", mAuth.getInstance().getCurrentUser().getEmail());
//                                intent.putExtra("회원가입 방식", "페이스북");
//                                startActivity(intent);
//                            } catch (Exception e) {
//
//                            }
                            try {
                                FirebaseFirestore.getInstance().collection("user").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    boolean choice = true;

                                    @Override
                                    public void onSuccess(QuerySnapshot documentSnapshots) {
                                        for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                            Log.d(TAG, "onSuccess:success");

                                            String isAttendance = documentChange.getDocument().getData().get("choice").toString();
                                            String isAttendance1 = documentChange.getDocument().getData().get("method").toString();


                                            if (isAttendance.equals("true") && isAttendance1.equals("페이스북")) {
                                                Log.d(TAG, "onSuccess:success");
                                                Intent intent = new Intent(getApplicationContext(), MainUI.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                                choice = false;
                                                break;
                                            } else {
                                                choice = true;
                                            }
                                        }
                                        if (choice == true) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent intent = new Intent(getApplicationContext(), Login2.class);
                                            intent.putExtra("이름", mAuth.getInstance().getCurrentUser().getDisplayName());
                                            intent.putExtra("이메일", mAuth.getInstance().getCurrentUser().getEmail());
                                            intent.putExtra("회원가입 방식", "페이스북");
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } catch (Exception e) {

                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Memberjoin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        idex = 1;
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredentialGoogle:success");
//                            try {
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                Intent intent = new Intent(getApplicationContext(), Login2.class);
//                                intent.putExtra("이름", mAuth.getInstance().getCurrentUser().getDisplayName());
//                                intent.putExtra("이메일", mAuth.getInstance().getCurrentUser().getEmail());
//                                intent.putExtra("회원가입 방식", "구글");
//                                startActivity(intent);
//                            } catch (Exception e) {
//
//                            }
                            FirebaseFirestore.getInstance().collection("user").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                boolean choice = true;

                                @Override
                                public void onSuccess(QuerySnapshot documentSnapshots) {
                                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                                        Log.d(TAG, "onSuccessGoogle:success");
                                        String isAttendance = documentChange.getDocument().getData().get("choice").toString();
                                        String isAttendance1 = documentChange.getDocument().getData().get("method").toString();


                                        if (isAttendance.equals("true") && isAttendance1.equals("구글")) {
                                            Intent intent = new Intent(getApplicationContext(), MainUI.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                            choice = false;
                                            break;
                                        } else {
                                            choice = true;
                                        }
                                    }
                                    if (choice == true) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(getApplicationContext(), Login2.class);
                                        intent.putExtra("이름", mAuth.getInstance().getCurrentUser().getDisplayName());
                                        intent.putExtra("이메일", mAuth.getInstance().getCurrentUser().getEmail());
                                        intent.putExtra("회원가입 방식", "구글");
                                        startActivity(intent);
                                    }
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Memberjoin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}