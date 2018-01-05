package com.yline.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 测试类的 接口
 *
 * @author yline 2017/5/9 -- 15:48
 * @version 1.0.0
 */
public interface ITestCallback {
    void testStart(View view, Bundle savedInstanceState);

    Button addButton(String content, View.OnClickListener listener);

    EditText addEditText(String hintContent);

    EditText addEditText(String hintContent, String content);

    EditText addEditNumber(String hintContent);

    EditText addEditNumber(String hintContent, String content);

    ImageView addImageView(int width, int height);

    TextView addTextView(String initContent);
}
