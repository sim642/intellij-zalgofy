package eu.sim642.idea.zalgofy;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

public abstract class TypedZalgofier<T> implements Zalgofier {

    protected abstract void zalgofy(Project project, T element, boolean interactive);

    @Override
    public void zalgofy(Project project, PsiElement element, boolean interactive) {
        zalgofy(project, (T) element, interactive);
    }
}
