package eu.sim642.idea.zalgofy;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.refactoring.RefactoringActionHandler;
import com.intellij.refactoring.RefactoringFactory;
import com.intellij.refactoring.actions.BaseRefactoringAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZalgofyAction extends BaseRefactoringAction {

    private final ZalgoOperator zalgoOperator = new ZalgoOperator();

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
                invoke(project, BaseRefactoringAction.getPsiElementArray(dataContext), dataContext);
            }

            @Override
            public void invoke(@NotNull Project project, @NotNull PsiElement[] elements, DataContext dataContext) {
                RefactoringFactory refactoringFactory = RefactoringFactory.getInstance(project);
                for (PsiElement element : elements) {
                    String name = ((PsiNamedElement) element).getName();
                    String newName = "$" + zalgoOperator.apply(name);
                    refactoringFactory.createRename(element, newName).run();
                }
            }
        };
    }
}
