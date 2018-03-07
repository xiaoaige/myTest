package com.wja.myapplication.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wja.myapplication.presenter.BasePresenter;

/**
 * Created by hanke on 2018/2/9.
 */

public class BaseFragment<T extends BasePresenter> extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
