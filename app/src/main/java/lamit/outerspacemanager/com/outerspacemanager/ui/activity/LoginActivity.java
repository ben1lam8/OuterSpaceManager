package lamit.outerspacemanager.com.outerspacemanager.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import lamit.outerspacemanager.com.outerspacemanager.model.Credentials;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.LoginViewModel;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LoginViewModel vm;

    @BindView(R.id.login_login_textview)    TextView loginTextView;
    @BindView(R.id.login_login_edittext)    EditText loginEditText;
    @BindView(R.id.login_password_textview) TextView passwordTextView;
    @BindView(R.id.login_password_edittext) EditText passwordEditText;
    @BindView(R.id.login_validate_button)   Button loginButton;
    @BindView(R.id.login_new_button)        Button signupButton;

    // INTENTS
    public final static int CREATE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("Created");

        // Inject dependencies
        this.configureDagger();
        Timber.d("Dependencies injected");

        // Inflate layout and bind views
        setContentView(R.layout.activity_login);
        Timber.d("Layout inflated");
        ButterKnife.bind(this);
        Timber.d("Views binded");

        // Configure VM
        this.configureViewModel();
        Timber.d("ViewModel ready");

        // Now listen...
        Timber.d("Listening to data mutations and UI events...");
    }

    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    private void configureViewModel(){
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        this.vm.init();

        // Observe data mutations...
        this.vm.getConnectedUser().observe(
                this,
                connectedUser -> {
                    if (connectedUser != null) {
                        Timber.d("Last connected user is %s", connectedUser.getUsername());

                        launchGame();
                    }
                }
        );
    }

    private void launchGame(){
        Timber.d("Will launch %s", MainActivity.class);
        Intent toMainIntent = new Intent(this, MainActivity.class);
        startActivity(toMainIntent);
    }

    @OnClick(R.id.login_validate_button)
    public void connect(Button validateButton) {
        Timber.d("Click on %s", validateButton.getText());

        Credentials credentials = new Credentials();
        credentials.setUsername(loginEditText.getText().toString());
        credentials.setPassword(passwordEditText.getText().toString());
        Timber.d("Submitted credentials : %s", credentials);

        this.vm.connectUser(credentials);
    }

    @OnClick(R.id.login_new_button)
    public void create(Button createButton) {
        Timber.d("Click on %s", createButton.getText());

        Intent toSignupIntent = new Intent(this, SignupActivity.class);
        Timber.d("Will launch %s for result", SignupActivity.class);

        startActivityForResult(toSignupIntent, CREATE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent createUserIntent){
        if(resultCode == RESULT_CANCELED){
            Timber.d("Signing up abortion");
            Toast.makeText(this, R.string.login_cancel_creation, Toast.LENGTH_SHORT).show();
        }else {
            Timber.d("Signing up completed");
            if (requestCode == CREATE_REQUEST) {

                Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_confirm_user_created, Toast.LENGTH_LONG);
                toast.show();

                Credentials credentials = (Credentials) createUserIntent.getSerializableExtra(SignupActivity.CREDENTIALS_EXTRA);
                this.vm.connectUser(credentials);
            }
        }
    }
}
