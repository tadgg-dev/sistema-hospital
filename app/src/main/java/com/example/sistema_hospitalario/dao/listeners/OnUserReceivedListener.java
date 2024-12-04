package com.example.sistema_hospitalario.dao.listeners;

import com.example.sistema_hospitalario.dto.UserDTO;

public interface OnUserReceivedListener {
    void onUserReceived(UserDTO user);
    void onError(Exception e);
}
