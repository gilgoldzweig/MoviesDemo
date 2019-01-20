package com.example.gilgoldzweig.moviedemo.utils;

import android.support.annotation.NonNull;

public interface GenericOnItemClickedListener<T> {

    void onItemClicked(@NonNull T element);
}
