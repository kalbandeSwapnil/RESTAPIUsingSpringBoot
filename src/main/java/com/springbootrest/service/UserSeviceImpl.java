package com.springbootrest.service;

import com.springbootrest.controller.Constants;
import com.springbootrest.model.User;
import com.springbootrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserSeviceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public  Page<User> getAll(Pageable pageable) {

        return  userRepository.findAll(null,pageable);
    }

    @Override
    public Page<User> searchUsers(String id, String username, String firstName, String lastName,Pageable pageable) {

        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                if (id != null) predicates.add( builder.equal( root.get( "id" ), id ) );
                if (username != null) predicates.add( builder.equal( root.get( Constants.USERNAME ), username ) );
                if (firstName != null) predicates.add( builder.equal( root.get( Constants.FIRST_NAME ), firstName ) );
                if (lastName != null) predicates.add( builder.equal( root.get( Constants.LAST_NAME ), lastName ) );
                return builder.and( predicates.toArray( new Predicate[predicates.size()] ) );
            }
        };

        return userRepository.findAll( specification ,pageable);
    }

    @Override
    public void saveAll(List<User> users) {
        userRepository.saveAll( users );
    }

    @Override
    public User save(User user) {
       return userRepository.save( user );
    }

    @Override
    public boolean delete(int userId) {
        boolean success;

        if (userRepository.existsById( userId )) {
            userRepository.deleteById( userId );
            success = true;
        } else {
            success = false;
        }
        return success;
    }


    @Override
    public boolean update(User user) {

        boolean success;
        if (userRepository.existsById( user.getId() )) {
            userRepository.save( user );
            success = true;
        } else {
            success = false;
        }
        return success;
    }

}
