package com.springbootrest.controller;

/**
 * Created by KalbandeSwapnil on 8/30/2018.
 */
public class Constants {

    private Constants(){}

    //Response Attributes
    public static final String RESULT = "Result";
    public static final String TOTAL_COUNT = "TotalCount";
    public static final String PAGE_NO = "Page Number";
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "pageSize";
    public static final int DEFAULT_PAGE_SIZE = 100;


    // consumes and produces
    public static final String APPLICATION_JSON = "application/json; charset=utf-8";

    //Resource
    public static final String API_USERS = "/users";

    //Actions API URI
    public static final String API_GET_ALL = "/getAll";
    public static final String API_ADD_USER = "/addUser";
    public static final String API_DELETE_USER = "/deleteUser";
    public static final String API_SEARCH_USER = "/search";
    public static final String API_UPDATE_USER = "/updateUser";

    //User Attributes
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";










}
