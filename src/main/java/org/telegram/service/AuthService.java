package org.telegram.service;

import org.telegram.entity.User;

import static org.telegram.db.DataBase.*;

public class AuthService {
    public void service(){
        while (true){
            System.out.println("""
                    0 exit
                    1 sign up
                    2 sign in
                    """);
            switch (intscan.nextInt()){
                case 0 -> {return;}
                case 1 -> {
                    User user = new User();

                    signUp(user);
                }
                case 2 -> signIn();
            }
        }
    }

    private void signUp(User user) {

    }
}
