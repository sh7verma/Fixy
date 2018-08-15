package com.app.fixy.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.app.fixy.R;
import com.app.fixy.utils.Connection_Detector;
import com.app.fixy.utils.Encode;
import com.app.fixy.utils.LoadingDialog;
import com.app.fixy.utils.MarshMallowPermission;
import com.app.fixy.utils.Utils;
import com.google.gson.Gson;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public MarshMallowPermission mPermission;
    protected int mWidth, mHeight;
    protected Context mContext;
    protected String errorInternet;
    protected String platformStatus = "2";
    protected String errorAPI;
    protected String errorAccessToken;
    protected String terminateAccount;
//    protected Db db;
    Utils utils;
    Gson mGson = new Gson();
    private Snackbar mSnackbar;
    Encode encode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(this);
        setContentView(getContentView());
        utils = new Utils(BaseActivity.this);
        mContext = getContext();
        ButterKnife.bind(this);
//        db = new Db(this);
        encode=new Encode();
        getDefaults();
        onCreateStuff();
        mPermission = new MarshMallowPermission(this);
        errorInternet = getResources().getString(R.string.internet);
        errorAPI = getResources().getString(R.string.error);
        errorAccessToken = getResources().getString(R.string.invalid_access_token);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onPosted();
        initUI();
        initListener();
        onStarted();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    protected abstract int getContentView();

    protected void showProgress() {
        LoadingDialog.getLoader().showLoader(BaseActivity.this);
    }

    protected void hideProgress() {
        LoadingDialog.getLoader().dismissLoader();
    }

    //onCreate
    protected abstract void onCreateStuff();

    //onStart
    protected void onStarted() {

    }

    //onPostCreate
    protected void onPosted() {

    }

    protected abstract void initUI();

    protected abstract void initListener();

    protected void getDefaults() {
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        mWidth = display.widthPixels;
        mHeight = display.heightPixels;
        Log.e("Height = ", mHeight + " width " + mWidth);
        utils.setInt("width", mWidth);
        utils.setInt("height", mHeight);
    }

    protected void showAlert(View view, String message) {
        mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    protected abstract Context getContext();

//    protected void moveToSplash() {
//        Toast.makeText(mContext, "Someone else login on another device with your credentials", Toast.LENGTH_LONG).show();
//        db.deleteAllTables();
//        utils.clear_shf();
//        Intent inSplash = new Intent(mContext, AfterWalkthroughActivity.class);
//        inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(inSplash);
//        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    protected void showSnackBar(View view, String message) {
        mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackbar.getView().setBackgroundColor(Color.RED);
        mSnackbar.show();
    }

    public boolean connectedToInternet() {
        if ((new Connection_Detector(mContext)).isConnectingToInternet())
            return true;
        else
            return false;
    }

//    public void launchActivity(Class<?> clss ,Manifest manifest) {
//        if (ContextCompat.checkSelfPermission(this, manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
//        } else {
//            Intent intent = new Intent(this, clss);
//            startActivityForResult(intent, 2);
//        }
//    }


    protected void showInternetAlert(View view) {
        mSnackbar = Snackbar.make(view, "Internet connection not available!", Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }


    public static void hideKeyboard(Activity mContext) {
        // Check if no view has focus:
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboardDialog(Activity mContext) {
                // Check if no view has focus:
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
