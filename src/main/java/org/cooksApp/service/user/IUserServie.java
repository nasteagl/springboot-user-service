package org.cooksApp.service.user;

import org.cooksApp.model.User;

public interface IUserServie {

    User registerUser(User user);

    String findByUsername(String username);
}
