package com.springbootrest.controller;

import com.springbootrest.model.User;
import com.springbootrest.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by kalbas on 8/30/2018.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addUser() throws Exception {

        //AddUser
        User user = new User( "testUsername", "testFirstName", "testLastName" );
        Mockito.when( userService.save( Mockito.any( User.class ) ) ).thenReturn( user );

        String expectedJSON = "{\"id\":0,\"username\":\"testUsername\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                Constants.API_USERS + Constants.API_ADD_USER ).accept( MediaType.APPLICATION_JSON )
                .content( expectedJSON ).contentType( MediaType.APPLICATION_JSON );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals( HttpStatus.OK.value(), response.getStatus() );
    }

    @Test
    public void getAllUsers() throws Exception {

        //GetAll method
        List<User> users = new ArrayList<User>();
        users.add( new User( "testUsername", "testFirstName", "testLastName" ) );
        users.add( new User( "testUsername", "testFirstName", "testLastName" ) );
        users.add( new User( "testUsername", "testFirstName", "testLastName" ) );
        users.add( new User( "testUsername", "testFirstName", "testLastName" ) );
        users.add( new User( "testUsername", "testFirstName", "testLastName" ) );
        Mockito.when( userService.getAll( PageRequest.of( 0, 100 ) ) ).thenReturn( new PageImpl<User>( users ) );
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                Constants.API_USERS + Constants.API_GET_ALL ).accept( MediaType.APPLICATION_JSON );
        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();
        String expectedJsonAsString = "{\"TotalCount\":5,\"pageSize\":5,\"Page Number\":0,\"Result\":[{\"id\":0,\"username\":\"testUsername\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\"},{\"id\":0,\"username\":\"testUsername\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\"},{\"id\":0,\"username\":\"testUsername\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\"},{\"id\":0,\"username\":\"testUsername\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\"},{\"id\":0,\"username\":\"testUsername\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\"}]}\n";
        JSONAssert.assertEquals( expectedJsonAsString, result.getResponse()
                .getContentAsString(), false );

    }

    @Test
    public void deleteUser() throws Exception {
        Mockito.when( userService.delete( 1 ) ).thenReturn( true );
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                Constants.API_USERS + Constants.API_DELETE_USER + "?id=1" );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals( HttpStatus.OK.value(), response.getStatus() );
    }

    @Test
    public void deleteUserNegative() throws Exception {
        Mockito.when( userService.delete( 2 ) ).thenReturn( false );
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
                Constants.API_USERS + Constants.API_DELETE_USER + "?id=1" );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals( HttpStatus.NO_CONTENT.value(), response.getStatus() );
    }

    @Test
    public void searchUser() throws Exception {

        List<User> users = new ArrayList<User>();
        users.add( new User( "testusername", "testFirstName", "testLastName" ) );
        Mockito.when( userService.searchUsers( "0", "testusername", "testFirstName", "testLastName", PageRequest.of( 0, 100 ) ) ).thenReturn( new PageImpl<User>( users ) );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                Constants.API_USERS + Constants.API_SEARCH_USER + "?username=testusername&id=0&lastName=testLastName&firstName=testFirstName" ).accept( MediaType.APPLICATION_JSON );
        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();
        String expectedJSON = "{\"TotalCount\":1,\"pageSize\":1,\"Page Number\":0,\"Result\":[{\"id\":0,\"username\":\"testusername\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\"}]}";
        JSONAssert.assertEquals( expectedJSON, result.getResponse()
                .getContentAsString(), false );
    }

    @Test
    public void searchUserNegative() throws Exception {

        List<User> users = new ArrayList<User>();
        Mockito.when( userService.searchUsers( "1", "testusername", "testFirstName", "testLastName", PageRequest.of( 0, 100 ) ) ).thenReturn( new PageImpl<User>( users ) );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                Constants.API_USERS + Constants.API_SEARCH_USER + "?username=testusername&id=0&lastName=testLastName&firstName=testFirstName" ).accept( MediaType.APPLICATION_JSON );
        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();
        String expectedJSON = "{}";
        JSONAssert.assertEquals( expectedJSON, result.getResponse()
                .getContentAsString(), false );
    }

    @Test
    public void updateUser() throws Exception {
        Mockito.when( userService.update( Mockito.any( User.class ) ) ).thenReturn( true );
        String expectedJSON = "{\"id\":0,\"username\":\"testUsername\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                Constants.API_USERS + Constants.API_UPDATE_USER ).accept( MediaType.APPLICATION_JSON )
                .content( expectedJSON ).contentType( MediaType.APPLICATION_JSON );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals( HttpStatus.OK.value(), response.getStatus() );
    }

    @Test
    public void updateUserNegative() throws Exception {

        Mockito.when( userService.update( Mockito.any( User.class ) ) ).thenReturn( false );
        String expectedJSON = "{\"id\":0,\"username\":\"testUsername\",\"firstName\":\"testFirstName\",\"lastName\":\"testLastName\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                Constants.API_USERS + Constants.API_UPDATE_USER ).accept( MediaType.APPLICATION_JSON )
                .content( expectedJSON ).contentType( MediaType.APPLICATION_JSON );

        MvcResult result = mockMvc.perform( requestBuilder ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals( HttpStatus.NO_CONTENT.value(), response.getStatus() );
    }

}