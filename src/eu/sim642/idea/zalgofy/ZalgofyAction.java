package eu.sim642.idea.zalgofy;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.refactoring.RefactoringActionHandler;
import com.intellij.refactoring.RefactoringFactory;
import com.intellij.refactoring.actions.BaseRefactoringAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

public class ZalgofyAction extends BaseRefactoringAction {

    private final UnaryOperator<String> operator = new ZalgoOperator();

    @Override
    protected boolean isAvailableInEditorOnly() {
        return false;
    }

    @Override
    protected boolean isEnabledOnElements(@NotNull PsiElement[] elements) {
        return false;
    }

    @Nullable
    @Override
    protected RefactoringActionHandler getHandler(@NotNull DataContext dataContext) {
        return new RefactoringActionHandler() {
            @Override
            public void invoke(@NotNull Project project, Editor editor, PsiFile file, DataContext dataContext) {
                invoke(project, new PsiElement[]{BaseRefactoringAction.getElementAtCaret(editor, file)}, dataContext);
            }

            @Override
            public void invoke(@NotNull Project project, @NotNull PsiElement[] elements, DataContext dataContext) {
                RefactoringFactory refactoringFactory = RefactoringFactory.getInstance(project);
                for (PsiElement element : elements) {
                    if (element instanceof PsiNamedElement) {
                        String name = ((PsiNamedElement) element).getName();
                        String newName = operator.apply(name);
                        refactoringFactory.createRename(element, newName).run();
                    }
                    else if (element instanceof PsiCommentImpl) {
                        IElementType tokenType = ((PsiCommentImpl) element).getTokenType();

                        String text = element.getText();
                        String innerText;
                        if (tokenType.equals(JavaTokenType.C_STYLE_COMMENT))
                            innerText = text.substring(2, text.length() - 4);
                        else if (tokenType.equals(JavaTokenType.END_OF_LINE_COMMENT))
                            innerText = text.substring(2);
                        else
                            continue; // unknown comment type

                        String newInnerText = operator.apply(innerText);
                        String newText;
                        if (tokenType.equals(JavaTokenType.C_STYLE_COMMENT))
                            newText = "/*" + newInnerText + "*/";
                        else if (tokenType.equals(JavaTokenType.END_OF_LINE_COMMENT))
                            newText = "//" + newInnerText;
                        else
                            continue; // unknown comment type

                        WriteCommandAction.runWriteCommandAction(project, () -> {
                            ((PsiCommentImpl) element).updateText(newText);
                        });
                    }
                }
            }
        };
    }
}
