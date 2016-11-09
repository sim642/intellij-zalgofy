package eu.sim642.idea.zalgofy;

import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.refactoring.RefactoringFactory;
import com.intellij.refactoring.RenameRefactoring;
import com.siyeh.ig.InspectionGadgetsFix;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public class ZalgofyFix extends InspectionGadgetsFix {

    private final UnaryOperator<String> operator = new ZalgoIdentifierOperator();

    @NotNull
    @Override
    public String getFamilyName() {
        return "Zalgofy";
    }

    @Override
    @NotNull
    public String getName() {
        return "Zalgofy";
    }

    @Override
    public void doFix(final Project project, final ProblemDescriptor descriptor) {
        final PsiElement nameIdentifier = descriptor.getPsiElement();
        final PsiElement elementToRename = nameIdentifier.getParent();

        final RefactoringFactory factory = RefactoringFactory.getInstance(project);
        final RenameRefactoring renameRefactoring =
                factory.createRename(elementToRename, operator.apply(((PsiNamedElement) elementToRename).getName()), false, false);
        renameRefactoring.setInteractive(null);
        renameRefactoring.setPreviewUsages(false);
        renameRefactoring.run();
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }

    @Override
    protected boolean prepareForWriting() {
        return false;
    }
}