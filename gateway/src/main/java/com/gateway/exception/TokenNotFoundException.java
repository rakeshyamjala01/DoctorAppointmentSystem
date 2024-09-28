package com.gateway.exception;

public class TokenNotFoundException extends Exception{
    public TokenNotFoundException(){

    }
    public TokenNotFoundException(String e,Exception ex) throws Exception{
        throw new Exception (e,ex);
    }
    public TokenNotFoundException(String e) throws Exception{
        throw  new Exception(e);
    }
}
