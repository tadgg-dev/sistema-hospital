package com.example.sistema_hospitalario.dao;

import com.example.sistema_hospitalario.dao.listeners.OnUserReceivedListener;
import com.example.sistema_hospitalario.dao.listeners.OnUsersReceivedListener;
import com.example.sistema_hospitalario.dto.UserDTO;

public interface UserDAO {
    void createUser(UserDTO user);
    void updateUser(String email, UserDTO user);
    void deleteUser(String email);
    void getUser(String email, OnUserReceivedListener listener);
    void getAllUsers(OnUsersReceivedListener listener);
}
