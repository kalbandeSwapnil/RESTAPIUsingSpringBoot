package com.springbootrest.repository;

import com.springbootrest.model.AdminUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by KalbandeSwapnil on 8/31/2018.
 */
public interface AdminUserRespository extends CrudRepository<AdminUser, Integer> {
    public AdminUser findByUsername(String username);
}
