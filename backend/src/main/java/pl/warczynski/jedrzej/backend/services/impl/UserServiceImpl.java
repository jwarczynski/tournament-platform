//package pl.warczynski.jedrzej.backend.services.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import pl.warczynski.jedrzej.backend.common.AccountStatus;
//import pl.warczynski.jedrzej.backend.dao.UserDao;
//import pl.warczynski.jedrzej.backend.dto.RegistrationDto;
//import pl.warczynski.jedrzej.backend.models.User;
//import pl.warczynski.jedrzej.backend.services.interfaces.UserService;
//
//@Service
//public class UserServiceImpl implements UserService {
//
////    private final UserDao userDao;
//    @Autowired
//    private MongoTemplate mongoTemplate;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public UserServiceImpl(/*UserDao userDao*/PasswordEncoder passwordEncoder) {
////        this.userDao = userDao;
//        this.passwordEncoder = passwordEncoder;
//    }
//    @Override
//    public User registerNewUser(RegistrationDto registrationDto) {
//        String password = registrationDto.password();
//        String salt = BCrypt.gensalt();
//        String hashedPassword = passwordEncoder.encode(salt + password);
//
//        User newUser = new User(
//                registrationDto.email(),
//                hashedPassword,
//                salt,
//                registrationDto.firstName(),
//                registrationDto.surname(),
//                AccountStatus.INACTIVE.getStaus()
//        );
//        mongoTemplate.getCollection("users");
//        return null;
////        return userDao.save(newUser);
//    }
//}
