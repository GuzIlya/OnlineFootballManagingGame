package service.services;

import service.forms.LoginForm;
import service.transfer.TokenDto;

public interface LoginService {
    TokenDto login(LoginForm loginForm);
}
