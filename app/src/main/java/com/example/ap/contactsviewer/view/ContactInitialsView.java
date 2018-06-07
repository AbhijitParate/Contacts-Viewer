package com.example.ap.contactsviewer.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.Gravity;

import com.example.ap.contactsviewer.R;

public class ContactInitialsView extends android.support.v7.widget.AppCompatTextView {
    public ContactInitialsView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ContactInitialsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ContactInitialsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setMaxLines(1);
        setMaxEms(2);
        setGravity(Gravity.CENTER);
        setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_circle, context.getTheme()));
        setTextColor(ResourcesCompat.getColor(getResources(), android.R.color.white, context.getTheme()));
        setTypeface(Typeface.DEFAULT_BOLD, defStyleAttr);
    }

    public void setText(String text) {
        super.setText(text, BufferType.NORMAL);
    }
}
