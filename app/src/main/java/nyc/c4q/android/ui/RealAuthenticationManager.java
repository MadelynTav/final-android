package nyc.c4q.android.ui;

public class RealAuthenticationManager implements AuthenticationManager {
  public boolean validateLogin(String email, String password) {
    if (email.equals("c4q") && password.equals("c4q")){
      return true;
    }
    // TODO - implement authentication logic
    // valid credentials are email: "c4q", password: "c4q"
    return false;
  }
}
