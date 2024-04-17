package com.example.vothanhtrung_shop;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class componetfood {
    private EditText editText;
    private TextView textView;

    // Constructor nhận EditText và TextView
    public componetfood(EditText editText, TextView textView) {
        this.editText = editText;
        this.textView = textView;
        setupTextWatcher();
    }

    // Phương thức thiết lập TextWatcher
    private void setupTextWatcher() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Gọi phương thức để tạo danh sách từ văn bản nhập vào EditText
                createListFromText(s.toString());
            }
        });
    }

    // Phương thức tạo danh sách từ văn bản nhập vào EditText
    private void createListFromText(String text) {
        // Chia văn bản thành các dòng
        String[] lines = text.split("\n");

        // Tạo HTML cho danh sách
        StringBuilder htmlText = new StringBuilder("<ul>");
        for (String line : lines) {
            htmlText.append("<li>").append(line).append("</li>");
        }
        htmlText.append("</ul>");

        // Hiển thị HTML trong TextView
        textView.setText(Html.fromHtml(htmlText.toString()));
    }
}
