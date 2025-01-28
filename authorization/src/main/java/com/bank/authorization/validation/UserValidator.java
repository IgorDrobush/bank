package com.bank.authorization.service.validation;

import com.bank.authorization.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validate(User user) throws UserValidationException {
        if (!user.getRole().equals("ROLE_ADMIN") && !user.getRole().equals("ROLE_USER")) {
            throw new UserValidationException(
                    "поле \"role\" может принимать только два значения: \"ROLE_ADMIN\" или \"ROLE_USER\""
            );
        }

        if (user.getProfileId() < 1 || user.getProfileId() > 9223372036854775806L) {
            throw new UserValidationException(
                    "поле \"profile_id\" может принимать значения от 1 до 9223372036854775807"
            );
        }

        if (user.getPassword().length() < 6 || user.getPassword().length() > 500) {
            throw new UserValidationException(
                    "пароль может состоять из любых символов количеством от 6 до 500"
            );
        }
    }
}
