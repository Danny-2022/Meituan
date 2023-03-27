package com.example.meituan.Tool;

import static com.example.meituan.Tool.Utils.showToast;

import android.content.Context;

public class CheckPass {
    private Context context;
    private static String LETTER_DIGIT_REGEX = "^[a-z0-9A-Z]+$";
    public CheckPass(Context context){
        this.context = context;
    }
    public boolean checkPassword(String password){
        if (password.length()<8){
            showToast(context,"the length of password < 8");
            return false;
        }else if (isLetterandDigit(password)){
            return true;
        }else {
            showToast(context,"Password should contain numbers and letters");
            return false;
        }
    }
    public  boolean isLetterandDigit(String str) {
        String regex1 = ".*[a-zA-z].*";
        boolean result1 = str.matches(regex1);
        String regex2 = ".*[0-9].*";
        boolean result2 = str.matches(regex2);
        return result1&&result2;
    }

}
