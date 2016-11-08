package eu.sim642.idea.zalgofy;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.refactoring.RefactoringFactory;

public class ZalgofyFileAction extends AnAction {

    private ZalgoOperator zalgoOperator = new ZalgoOperator();

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        Document document = editor.getDocument();

        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        psiFile.acceptChildren(new ZalgofyVisitor(project));
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
            if (element instanceof PsiNamedElement) {
                String newName = "$" + zalgoOperator.apply(((PsiNamedElement) element).getName());
                refactoringFactory.createRename(element, newName).run();
            }

            super.visitElement(element);
        }
    }
}
