package com.example.vothanhtrung_shop;

import android.text.Html;
import android.widget.TextView;

public class compnentlistdetail {
    private TextView textView;
    public compnentlistdetail(TextView textView){
        this.textView=textView;
    }
    public void Oncreated() {
        String htmlText = "<ul>" +
                "<li>Noddle</li>" +
                "<li>Salad</li>" +
                "<li>Burger</li>" +
                "<li>Herbal Pan Cake</li>" +
                "<li>Momos</li>" +
                "</ul>";
        textView.setText(Html.fromHtml(htmlText));
    }
}
