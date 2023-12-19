package edu.apartmentbooking.service;

import edu.apartmentbooking.model.User;
import edu.apartmentbooking.model.dto.ResetPasswordDTO;
import edu.apartmentbooking.model.dto.UserDTO;
import edu.apartmentbooking.model.dto.UserRegistrationDTO;

import java.util.List;

public interface UserService {

    User saveUser(UserRegistrationDTO registrationDTO);

    // For registration
    User findUserByUsername(String username);

    UserDTO findUserDTOByUsername(String username);

    UserDTO findUserById(Long id);

    List<UserDTO> findAllUsers();

    void updateUser(UserDTO userDTO);

    void updateLoggedInUser(UserDTO userDTO);

    void deleteUserById(Long id);

    User resetPassword(ResetPasswordDTO resetPasswordDTO);

}
