package eu.sim642.idea.zalgofy;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIdentifier;
import com.intellij.refactoring.RefactoringActionHandler;
import com.intellij.refactoring.actions.BaseRefactoringAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZalgofyAction extends BaseRefactoringAction {

    private final IdentifierZalgofier identifierZalgofier = new IdentifierZalgofier();
    private final CommentZalgofier commentZalgofier = new CommentZalgofier();

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
                for (PsiElement element : elements) {
                    if (element instanceof PsiIdentifier)
                        identifierZalgofier.zalgofy(project, element, true);
                    else if (element instanceof PsiComment)
                        commentZalgofier.zalgofy(project, element, true);
                }
            }
        };
    }
}
