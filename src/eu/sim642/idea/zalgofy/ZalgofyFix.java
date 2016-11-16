package eu.sim642.idea.zalgofy;

import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.siyeh.ig.InspectionGadgetsFix;
import org.jetbrains.annotations.NotNull;

public class ZalgofyFix extends InspectionGadgetsFix {

    private final IdentifierZalgofier identifierZalgofier = new IdentifierZalgofier();
    private final CommentZalgofier commentZalgofier = new CommentZalgofier();

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
        PsiElement element = descriptor.getPsiElement();
        if (element instanceof PsiIdentifier) {
            final PsiElement elementToRename = element.getParent();

            if (elementToRename instanceof PsiMethod && ((PsiMethod) elementToRename).isConstructor()) // exclude constructors
                return;

            identifierZalgofier.zalgofy(project, element, false);
        }
        else if (element instanceof PsiComment)
            commentZalgofier.zalgofy(project, element, false);
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