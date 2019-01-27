package com.std.redditarticles.presentation.views;

import android.content.Context;

public interface ILoginView {

    Context getContext();

    void showLoginError();

    void showPasswordError();

}
