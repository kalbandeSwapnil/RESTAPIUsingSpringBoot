package com.springbootrest.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springbootrest.model.User;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>,JpaSpecificationExecutor<User>  {

	public User findByUsername(String username);
}
