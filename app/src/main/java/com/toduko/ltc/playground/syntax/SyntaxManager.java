package com.toduko.ltc.playground.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;

public class SyntaxManager {

    public static void applyTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case Python:
                PythonSyntaxManager.applyTheme(context, codeView);
                break;
            case JavaScript:
                JavaScriptSyntaxManager.applyTheme(context, codeView);
                break;
        }
    }
}
