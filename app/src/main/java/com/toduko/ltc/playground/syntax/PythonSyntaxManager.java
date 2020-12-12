package com.toduko.ltc.playground.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;
import com.toduko.ltc.R;

import java.util.regex.Pattern;

public class PythonSyntaxManager {

    //Language Keywords
    private static final Pattern PATTERN_KEYWORDS = Pattern.compile("\\b(False|await|else|import|pass|None|break|except|in|raise" +
            "|True|class|finally|is|return|and|continue|for|lambda" +
            "|try|as|def|from|nonlocal|while|assert|del|global|not" +
            "|with|async|elif|if|or|yield|print)\\b");

    //Brackets and Colons
    private static final Pattern PATTERN_BUILTINS = Pattern.compile("[,:;[->]{}()]");

    //Data
    private static final Pattern PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b");
    private static final Pattern PATTERN_CHAR = Pattern.compile("'[a-zA-Z]'");
    private static final Pattern PATTERN_STRING = Pattern.compile("\".*\"|\'.*\'|\"\"\".*\"\"\"");
    private static final Pattern PATTERN_HEX = Pattern.compile("0x[0-9a-fA-F]+");
    private static final Pattern PATTERN_TODO_COMMENT = Pattern.compile("#TODO[^\n]*");
    private static final Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\.[a-zA-Z0-9_]+");
    private static final Pattern PATTERN_OPERATION =Pattern.compile( ":|==|>|<|!=|>=|<=|->|=|>|<|%|-|-=|%=|\\+|\\-|\\-=|\\+=|\\^|\\&|\\|::|\\?|\\*");
    private static final Pattern PATTERN_HASH_COMMENT = Pattern.compile("#(?!TODO )[^\\n]*");

    public static void applyTheme(Context context, CodeView codeView) {
        codeView.resetSyntaxPatternList();
        //View Background
        codeView.setBackgroundColor(codeView.getResources().getColor(R.color.backgroundWhite));

        //Syntax Colors
        codeView.addSyntaxPattern(PATTERN_HEX, context.getResources().getColor(R.color.lightPurple));
        codeView.addSyntaxPattern(PATTERN_CHAR, context.getResources().getColor(R.color.monokia_pro_orange));
        codeView.addSyntaxPattern(PATTERN_STRING, context.getResources().getColor(R.color.lightGreen));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, context.getResources().getColor(R.color.lightPurple));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, context.getResources().getColor(R.color.pink));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, context.getResources().getColor(R.color.lightBlack));
        codeView.addSyntaxPattern(PATTERN_HASH_COMMENT, context.getResources().getColor(R.color.grey));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, context.getResources().getColor(R.color.skyBlue));
        codeView.addSyntaxPattern(PATTERN_OPERATION, context.getResources().getColor(R.color.pink));
        //Default Color
        codeView.setTextColor( context.getResources().getColor(R.color.black));

        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, context.getResources().getColor(R.color.gold));

        codeView.reHighlightSyntax();
    }
}
