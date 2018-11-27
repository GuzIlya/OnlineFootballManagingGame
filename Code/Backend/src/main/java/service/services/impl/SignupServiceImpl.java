package service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import service.forms.UserForm;
import service.models.Role;
import service.models.State;
import service.models.User;
import service.repositories.UsersRepository;
import service.services.SignupService;

@Service
public class SignupServiceImpl implements SignupService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Environment env;

    @Override
    public void signUp(UserForm userForm) {
        if(!usersRepository.findOneByLogin(userForm.getLogin()).isPresent()) {
            String hashPassword = passwordEncoder.encode(userForm.getPassword());

            User user = User.builder()
                    .hashPassword(hashPassword)
                    .login(userForm.getLogin())
                    .balance(Integer.parseInt(env.getProperty("my.startbalance")))
                    .points(0)
                    .role(Role.USER)
                    .state(State.ACTIVE)
                    .build();

            usersRepository.save(user);
        } else throw new IllegalArgumentException("Login already used");
    }
}
