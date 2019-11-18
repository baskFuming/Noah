package com.xxx.mining;


import com.xxx.mining.base.App;

public class TokenErrException extends RuntimeException {
    public TokenErrException() {
        super(App.getContext().getResources().getString(R.string.token_overdue_str));
    }
}
