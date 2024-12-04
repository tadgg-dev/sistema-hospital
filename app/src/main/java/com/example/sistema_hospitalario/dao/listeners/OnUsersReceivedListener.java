package com.example.sistema_hospitalario.dao.listeners;

import com.example.sistema_hospitalario.dto.UserDTO;

import java.util.List;

public interface OnUsersReceivedListener {
    void onUsersReceived(List<UserDTO> users);
    void onError(Exception e);
}
