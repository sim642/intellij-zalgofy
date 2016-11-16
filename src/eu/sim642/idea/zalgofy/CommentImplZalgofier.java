package eu.sim642.idea.zalgofy;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import com.intellij.psi.tree.IElementType;

import java.util.function.UnaryOperator;

public class CommentImplZalgofier extends TypedZalgofier<PsiCommentImpl> {

    private final UnaryOperator<String> operator = new ZalgoOperator();

    @Override
    protected void zalgofy(Project project, PsiCommentImpl element, boolean interactive) {
        IElementType commentType = element.getTokenType();

        String text = element.getText();
        String innerText;
        if (commentType.equals(JavaTokenType.C_STYLE_COMMENT))
            innerText = text.substring(2, text.length() - 4);
        else if (commentType.equals(JavaTokenType.END_OF_LINE_COMMENT))
            innerText = text.substring(2);
        else
            return; // unknown comment type

        String newInnerText = operator.apply(innerText);
        String newText;
        if (commentType.equals(JavaTokenType.C_STYLE_COMMENT))
            newText = "/*" + newInnerText + "*/";
        else if (commentType.equals(JavaTokenType.END_OF_LINE_COMMENT))
            newText = "//" + newInnerText;
        else
            return; // unknown comment type

        WriteCommandAction.runWriteCommandAction(project, () -> {
            element.updateText(newText);
        });
    }
}
