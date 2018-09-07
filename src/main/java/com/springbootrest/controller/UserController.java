package com.springbootrest.controller;

import com.springbootrest.model.User;
import com.springbootrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.springbootrest.controller.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(API_USERS)
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = API_GET_ALL, produces = APPLICATION_JSON)
    @ResponseBody
    public Map<String, Object> getAllUsers(@RequestParam(value = PAGE, required = false) String page,
                                           @RequestParam(value = PAGE_SIZE, required = false) String size) {

        Page<User> users = userService.getAll( getPagination( page, size ) );
        return getResponse( users );
    }

    @PostMapping(value = API_ADD_USER, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addUser(@RequestBody Map<String, String> requestBody) {
        User user = new User();
        user.setUsername( requestBody.get( USERNAME ) );
        user.setFirstName( requestBody.get( FIRST_NAME ) );
        user.setLastName( requestBody.get( LAST_NAME ) );
        userService.save( user );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @DeleteMapping(value = API_DELETE_USER)
    @ResponseBody
    public ResponseEntity<Object> deleteUser(@RequestParam(ID) int id) {
        boolean success = userService.delete( id );
        if (success)
            return new ResponseEntity<>( HttpStatus.OK );
        else
            return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }

    @GetMapping(value = API_SEARCH_USER, produces = APPLICATION_JSON)
    @ResponseBody
    public Map<String, Object> searchUser(@RequestParam(value = USERNAME, required = false) String username,
                                          @RequestParam(value = FIRST_NAME, required = false) String firstName,
                                          @RequestParam(value = LAST_NAME, required = false) String lastName,
                                          @RequestParam(value = ID, required = false) String id,
                                          @RequestParam(value = PAGE, required = false) String page,
                                          @RequestParam(value = PAGE_SIZE, required = false) String size) {

        Page<User> users = userService.searchUsers( id, username, firstName, lastName, getPagination( page, size ) );
        return getResponse( users );
    }

    private Map<String, Object> getResponse(Page<User> users) {
        Map<String, Object> result = new HashMap<>();
        if(users!= null) {
            result.put( Constants.RESULT, users.getContent() );
            result.put( Constants.TOTAL_COUNT, users.getTotalElements() );
            result.put( Constants.PAGE_NO, users.getNumber() );
            result.put( Constants.PAGE_SIZE, users.getContent().size() );
        }
        return result;
    }

    private Pageable getPagination(String page, String pageSize) {
        int pageNo;
        int size;
        try {
            pageNo = (page != null) ? Integer.parseInt( page ) : 0;
            size = (pageSize != null) ? Integer.parseInt( pageSize ) : DEFAULT_PAGE_SIZE;
        } catch (NumberFormatException E) {
            pageNo = 0;
            size = DEFAULT_PAGE_SIZE;
        }
        return PageRequest.of( pageNo, size );
    }


    @PostMapping(value = API_UPDATE_USER, consumes = APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity<Object> updateUser(@RequestBody Map<String, String> requestBody) {

        User user = new User();
        user.setId( Integer.parseInt( requestBody.get( ID ) ) );
        user.setUsername( requestBody.get( USERNAME ) );
        user.setFirstName( requestBody.get( FIRST_NAME ) );
        user.setLastName( requestBody.get( LAST_NAME ) );
        boolean success = userService.update( user );
        if (success)
            return new ResponseEntity<>( HttpStatus.OK );
        else
            return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }

}
