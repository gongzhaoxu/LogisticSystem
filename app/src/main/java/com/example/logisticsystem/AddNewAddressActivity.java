package com.example.logisticsystem;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lljjcoder.style.citypickerview.CityPickerView;

public class AddNewAddressActivity extends Activity {

    //申明对象
    CityPickerView mPicker=new CityPickerView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPicker.init(this);
    }

}
