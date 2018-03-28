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
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import lamit.outerspacemanager.com.outerspacemanager.model.Credentials;
import lamit.outerspacemanager.com.outerspacemanager.R;
import lamit.outerspacemanager.com.outerspacemanager.viewmodel.SignupViewModel;
import timber.log.Timber;

public class SignupActivity extends AppCompatActivity implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private SignupViewModel vm;

    @BindView(R.id.signup_login_textview)       TextView loginTextView;
    @BindView(R.id.signup_login_edittext)       EditText loginEditText;
    @BindView(R.id.signup_password_textview)    TextView passwordTextView;
    @BindView(R.id.signup_password_edittext)    EditText passwordEditText;
    @BindView(R.id.signup_email_textview)       TextView emailTextView;
    @BindView(R.id.signup_email_edittext)       EditText emailEditText;
    @BindView(R.id.signup_validate_button)      Button createButton;

    public static final String CREDENTIALS_EXTRA = "credentials_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("Created");

        // Inject dependencies
        this.configureDagger();
        Timber.d("Dependencies injected");

        // Inflate layout and bind views
        setContentView(R.layout.activity_signup);
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
        this.vm = ViewModelProviders.of(this, viewModelFactory).get(SignupViewModel.class);
        this.vm.init();

        // Observe data mutations...
    }

    @OnClick(R.id.signup_validate_button)
    public void validateUserCreation(Button createButton){
        Timber.d("Click on %s", createButton.getText());

        Credentials credentials = new Credentials();
        credentials.setUsername(loginEditText.getText().toString());
        credentials.setPassword(passwordEditText.getText().toString());
        credentials.setEmail(emailEditText.getText().toString());
        Timber.d("Submitted credentials : %s", credentials);

        this.vm.createUser(credentials);

        Intent backToLoginIntent = new Intent();
        backToLoginIntent.putExtra(CREDENTIALS_EXTRA, credentials);
        setResult(RESULT_OK, backToLoginIntent);

        finish();
    }
}
