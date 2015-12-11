package nyc.c4q.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nyc.c4q.android.R;

public class LoginActivity extends Activity {

  private EditText emailField;
  private EditText passwordField;
  private Button loginButton;
  private final AuthenticationManager manager;

  public LoginActivity() {
    // TODO - fix this
    manager = null;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    // TODO - load view hierarchy in R.layout.activity_login
      emailField= (EditText) findViewById(R.id.email);
    // TODO - get references to views, and other setup
      passwordField= (EditText) findViewById(R.id.password);
    // TODO - call checkCredentials via OnClickListener
    
    loginButton= (Button) findViewById(R.id.login);
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkCredentials(emailField.getText().toString(),passwordField.getText().toString());
      }
    });
  }

  private void checkCredentials(String email, String password) {
    if(manager.validateLogin(email, password)) {
      // TODO - go to EmailListActivity
      Intent intent= new Intent(this, EmailListActivity.class);
      startActivity(intent);
    }
    else {
      // TODO launch alert dialog on failed login

      // check strings.xml for dialog
    }
  }
}
