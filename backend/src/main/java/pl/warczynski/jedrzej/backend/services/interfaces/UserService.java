package pl.warczynski.jedrzej.backend.services.interfaces;

import pl.warczynski.jedrzej.backend.dto.RegistrationDto;
import pl.warczynski.jedrzej.backend.models.User;

public interface UserService {

    User registerNewUser(RegistrationDto registrationDto);
}
