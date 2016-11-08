package eu.sim642.idea.zalgofy;

import com.intellij.analysis.AnalysisScope;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.refactoring.RefactoringFactory;
import com.intellij.util.IncorrectOperationException;

public class ZalgofyProjectAction extends AnAction {

    private ZalgoOperator zalgoOperator = new ZalgoOperator();

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        AnalysisScope analysisScope = new AnalysisScope(project);

        analysisScope.accept(new ZalgofyVisitor(project));
    }

    private class ZalgofyVisitor extends PsiRecursiveElementVisitor {
        private Project project;
        private RefactoringFactory refactoringFactory;

        public ZalgofyVisitor(Project project) {
            this.project = project;
            refactoringFactory = RefactoringFactory.getInstance(this.project);
        }

        @Override
        public void visitElement(PsiElement element) {
            if (element instanceof PsiNamedElement && !(element instanceof PsiFile)) {
                try {
                    String newName = "$" + zalgoOperator.apply(((PsiNamedElement) element).getName());

                    if (element instanceof PsiCheckedRenameElement) {
                        ((PsiCheckedRenameElement) element).checkSetName(newName);
                    }

                    refactoringFactory.createRename(element, newName).run();
                }
                catch (IncorrectOperationException e) {

                }
            }

            super.visitElement(element);
        }
    }
}
