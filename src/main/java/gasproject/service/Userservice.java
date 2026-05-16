package gasproject.service;

import gasproject.DTO.LoginRequest;
import gasproject.DTO.RegisterRequest;
import gasproject.entities.UserEntity;
import gasproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class Userservice {

    private final UserRepository userRepository;


    public String register(RegisterRequest registerRequest) {
       UserEntity userEntity= new UserEntity();
       userEntity.setName(registerRequest.name());
       userEntity.setEmail(registerRequest.email());
       userEntity.setPassword(registerRequest.password());
       userEntity.setAddress(registerRequest.address());
       userEntity.setMobileNumber(registerRequest.mobileNumber());
       int customerId = generateUniqueCustomerId();
       userEntity.setCustomerId(String.valueOf(customerId));
       userRepository.save(userEntity);
       return "Register Successfully!";
    }

    public  String login(LoginRequest loginRequest){
          UserEntity users=userRepository.findByMobileNumber(loginRequest.mobileNumber());
        if(users==null){
            throw new RuntimeException("User not found");
        }
        return "login successful";
    }


    public UserEntity getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found!"));
    }


    public void deleteUser(Long id){

        userRepository.deleteById(id);

    }
    private  int generateUniqueCustomerId(){
        Random random = new Random();
        int customerId;
        do {
            customerId=100000+random.nextInt(99999);
        }
        while (userRepository.existsByCustomerId(String.valueOf(customerId)));
        return customerId;

    }

}