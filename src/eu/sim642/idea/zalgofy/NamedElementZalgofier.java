package eu.sim642.idea.zalgofy;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiNamedElement;
import com.intellij.refactoring.RefactoringFactory;
import com.intellij.refactoring.RenameRefactoring;

import java.util.function.UnaryOperator;

public class NamedElementZalgofier extends TypedZalgofier<PsiNamedElement> {

    private final UnaryOperator<String> operator = new ZalgoOperator();

    @Override
    protected void zalgofy(Project project, PsiNamedElement element, boolean interactive) {
        RefactoringFactory refactoringFactory = RefactoringFactory.getInstance(project);
        String name = element.getName();
        String newName = operator.apply(name);
        RenameRefactoring renameRefactoring = refactoringFactory.createRename(element, newName, interactive, interactive);
        if (!interactive) {
            renameRefactoring.setInteractive(null);
            renameRefactoring.setPreviewUsages(false);
        }
        renameRefactoring.run();
    }
}
