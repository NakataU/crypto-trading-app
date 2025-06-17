package org.example.services;


import org.example.exceptions.EntityNotActive;
import org.example.exceptions.EntityNotFound;
import org.example.views.in.UserInView;
import org.example.views.out.UserOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService  {

    public Page<UserOutView> getAll(Pageable pageable);

    public Optional<UserOutView> getById(Long id);

    public UserOutView addOne(UserInView user);

    public UserOutView updateOne(Long id, UserInView user) throws EntityNotFound;

    public void deleteOne(Long id) throws EntityNotActive, EntityNotFound;

    public UserOutView resetUser(Long id) throws EntityNotActive, EntityNotFound;
}
