package com.proverbio.android.spring.util;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.proverbio.android.spring.R;

/**
 * @author Juan Pablo Proverbio
 */
public class Validator
{
    private Validator()
    {
        super();
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText)
    {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (TextUtils.isEmpty(text))
        {
            editText.setError(editText.getContext().getString(R.string.required_label));
            return false;
        }

        return true;
    }

    public static boolean hasText(TextView textView)
    {
        String text = textView.getText().toString().trim();
        textView.setError(null);

        if (TextUtils.isEmpty(text))
        {
            textView.setError(textView.getContext().getString(R.string.required_label));
            return false;
        }

        return true;
    }
}
