package com.springbootrest.service;

import com.springbootrest.model.AdminUser;
import com.springbootrest.repository.AdminUserRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kalbas on 8/31/2018.
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private static final Logger logger = LoggerFactory.getLogger( AdminUserServiceImpl.class );
    @Autowired
    private AdminUserRespository adminUserRespository;

    @Override
    public boolean authenticateUser(String username, String password) {
        AdminUser adminUser = adminUserRespository.findByUsername( username );
        logger.info( "Authenticated to user : " + username);
        return (adminUser != null && adminUser.getPassword().equals( password )) ? true : false;
    }
}
