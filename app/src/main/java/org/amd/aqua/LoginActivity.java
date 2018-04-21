package org.amd.aqua;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import org.amd.aqua.fragment.DatePickerDialogFragment;
import org.amd.aqua.fragment.FragmentActionListener;
import org.amd.aqua.model.User;
import org.amd.aqua.task.AquaApiTask;
import org.amd.aqua.util.PreferenceUtil;
import org.amd.aqua.util.StringUtil;
import org.amd.aqua.util.ViewUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements FragmentActionListener {

    private UserLoginTask mAuthTask = null;

    private static final String OWNER_ID = "20184761";

    // UI references.
    private AutoCompleteTextView mMemberIdView;
    private AutoCompleteTextView mBirthdayView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Set up the login form.
        mMemberIdView = (AutoCompleteTextView) findViewById(R.id.memberIdText);
        mBirthdayView = (AutoCompleteTextView) findViewById(R.id.birthdayText);
        mBirthdayView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mBirthdayView.clearFocus();
                DatePickerDialogFragment datePicker = DatePickerDialogFragment.newInstance(new Date());
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public void onAction(Fragment fragment, int actionId){

    }

    public void onSelect(Fragment fragment, Object item){
        if (fragment instanceof DatePickerDialogFragment) {
            List<Integer> results = (List<Integer>) item;
            Integer year = results.get(0);
            Integer month = results.get(1);
            Integer day = results.get(2);

            mBirthdayView.setText( year + "年" + month + "月" + day + "日");
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mMemberIdView.setError(null);

        // Store values at the time of the login attempt.
        String memberId = mMemberIdView.getText().toString();
        String birthday = mBirthdayView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(memberId)) {
            mMemberIdView.setError(getString(R.string.error_field_required));
            focusView = mMemberIdView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(this, memberId, birthday);
            mAuthTask.execute("memberinfo?ANKOWNERID=" + OWNER_ID);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AquaApiTask {

        private String memberId = null;

        private String birthday = null;

        UserLoginTask(Activity activity, String memberId, String birthday) {
            super(activity);
            this.memberId = memberId;
            this.birthday = birthday;
        }

        @Override
        protected void onPostExecute(String response) {
            try {
                boolean loginSuccess = false;
                JSONObject json = new JSONObject(response);
                JSONArray members = json.getJSONArray("DataModel");
                for (int i = 0; i < members.length(); i++) {
                    JSONObject obj = members.getJSONObject(i);

                    final String memberId = obj.getString("ANKMEMBERID");
                    final String shopId = obj.getString("ANKSHOPID");
                    final String shopName = obj.getString("KNJSHOPNAME");
                    final String userName = obj.getString("KNJUSERNAME");
                    final String sex = obj.getString("ANKSEX");
                    final String birthDay = obj.getString("DTMBD");
                    final String numPoint = obj.getString("NUMPOINT");
                    final String address = "東京都港区北青山3-5-6";

                    if (StringUtil.equals(this.memberId, memberId)) {
                        loginSuccess = true;
                        final StringBuilder builder = new StringBuilder();
                        builder.append("店舗：" + shopName + "\n");
                        builder.append("お名前：" + userName + "\n");
//                        builder.append("性別：" + sex + "\n");
//                        builder.append("生年月日：" + birthDay);

                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("下記会員としてログインします")
                                .setMessage(builder.toString())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PreferenceUtil.saveStringPref(LoginActivity.this, User.KEY_OWNER_ID, OWNER_ID);
                                        PreferenceUtil.saveStringPref(LoginActivity.this, User.KEY_MEMBER_ID, memberId);
                                        PreferenceUtil.saveStringPref(LoginActivity.this, User.KEY_SHOP_ID, shopId);
                                        PreferenceUtil.saveStringPref(LoginActivity.this, User.KEY_SHOP_NAME, shopName);
                                        PreferenceUtil.saveStringPref(LoginActivity.this, User.KEY_USER_NAME, userName);
                                        PreferenceUtil.saveStringPref(LoginActivity.this, User.KEY_SEX, sex);
                                        PreferenceUtil.saveStringPref(LoginActivity.this, User.KEY_BIRTHDAY, birthDay);
                                        PreferenceUtil.saveStringPref(LoginActivity.this, User.KEY_NUM_POINT, numPoint);
                                        PreferenceUtil.saveStringPref(LoginActivity.this, User.KEY_ADDRESS, address);

                                        Intent menuIntent = new Intent(LoginActivity.this, MenuActivity.class);
                                        startActivity(menuIntent);
                                        finish();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        onCancelled();
                                    }
                                })
                                .show();
                    }
                }

                if(! loginSuccess){
                    onCancelled();
                    ViewUtil.showLongToast( LoginActivity.this, R.string.login_failed);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

