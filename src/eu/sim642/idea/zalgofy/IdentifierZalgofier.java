package eu.sim642.idea.zalgofy;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiNamedElement;

public class IdentifierZalgofier extends TypedZalgofier<PsiIdentifier> {

    private final NamedElementZalgofier namedElementZalgofier = new NamedElementZalgofier();

    @Override
    protected void zalgofy(Project project, PsiIdentifier element, boolean interactive) {
        PsiElement parent = element.getParent();
        if (parent instanceof PsiNamedElement) {
            namedElementZalgofier.zalgofy(project, parent, interactive);
        }
    }
}
