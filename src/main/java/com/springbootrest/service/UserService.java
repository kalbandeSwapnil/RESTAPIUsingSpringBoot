package com.springbootrest.service;

import java.util.List;

import com.springbootrest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	public  Page<User> getAll(Pageable pageable);

	public void saveAll(List<User> users);

	public User save(User user);

	public boolean delete(int id);

	public boolean update(User user);

	public Page<User> searchUsers(String id, String username, String firstname, String lastname,Pageable pageable);
}
