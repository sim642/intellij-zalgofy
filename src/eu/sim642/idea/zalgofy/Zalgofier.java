package eu.sim642.idea.zalgofy;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

public interface Zalgofier {
    void zalgofy(Project project, PsiElement element, boolean interactive);
}
