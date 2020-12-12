package com.toduko.ltc.playground.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;
import com.toduko.ltc.R;

import java.util.regex.Pattern;

public class JavaScriptSyntaxManager {

    //Language Keywords
    private static final Pattern PATTERN_KEYWORDS = Pattern.compile("\\b(abstract|arguments|await|boolean" +
            "|break|byte|case|catch|char|class|const|continue|debugger|default|delete|do|double" +
            "|else|enum|eval|export|extends|false|final|finally|float|for|function|goto|if" +
            "|implements|import|in|instanceof|int|interface|let|long|native|new|null|package" +
            "|private|protected|public|return|short|static|super|switch|synchronized|this|throw" +
            "|throws|transient|true|try|typeof|var|void|volatile|while|with|yield)\\b");

    //Brackets and Colons
    private static final Pattern PATTERN_BUILTINS = Pattern.compile("[,:;[->]{}()]");

    //Data
    private static final Pattern PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b");
    private static final Pattern PATTERN_CHAR = Pattern.compile("'[a-zA-Z]'");
    private static final Pattern PATTERN_STRING = Pattern.compile("\".*\"");
    private static final Pattern PATTERN_HEX = Pattern.compile("0x[0-9a-fA-F]+");
    private static final Pattern PATTERN_TODO_COMMENT = Pattern.compile("//TODO[^\n]*");
    private static final Pattern PATTERN_HASH_COMMENT = Pattern.compile("//(?!TODO )[^\\n]*");
    private static final Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\.[a-zA-Z0-9_]+");
    private static final Pattern PATTERN_OPERATION =Pattern.compile( "\\+|-|\\*|\\*\\*|/|%|\\+\\+|--" + //Arithmetic Operators
                                                                    "|=|\\+=|-=|\\*=|/=|%=|\\*\\*=" +   //Assignment Operators
                                                                    "|==|===|!=|!==|>|<|>=|<=|\\?" +    //Comparison Operators
                                                                    "|&&|\\|\\||!" +                    //Logical Operators
                                                                    "|&|\\||~|^|<<|>>|>>>");            //Bitwise Operators


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
