package eu.sim642.idea.zalgofy;

import com.intellij.codeInspection.CleanupLocalInspectionTool;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import com.siyeh.ig.BaseInspection;
import com.siyeh.ig.BaseInspectionVisitor;
import com.siyeh.ig.InspectionGadgetsFix;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZalgoInspection extends BaseInspection implements CleanupLocalInspectionTool {

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Zalgofy";
    }

    @NotNull
    @Override
    protected String buildErrorString(Object... infos) {
        PsiElement element = (PsiElement) infos[0];
        if (element instanceof PsiIdentifier) {
            String name = (String) infos[1];
            return String.format("Identifer is not enough zalgo: %s", name);
        }
        else if (element instanceof PsiComment) {
            String text = element.getText();
            return String.format("Comment is not enough zalgo: %s", text);
        }
        else
            return "Element is not enough zalgo";
    }

    @Override
    public BaseInspectionVisitor buildVisitor() {
        return new BaseInspectionVisitor() {
            @Override
            public void visitIdentifier(PsiIdentifier identifier) {
                if (identifier.getParent() instanceof PsiNamedElement) {
                    PsiNamedElement psiNamedElement = (PsiNamedElement) identifier.getParent();
                    String name = psiNamedElement.getName();
                    registerError(identifier, identifier, name);
                }

                super.visitIdentifier(identifier);
            }

            @Override
            public void visitComment(PsiComment comment) {
                if (comment instanceof PsiCommentImpl)
                    registerError(comment, comment);

                super.visitComment(comment);
            }
        };
    }

    @Nullable
    @Override
    protected InspectionGadgetsFix buildFix(Object... infos) {
        return new ZalgofyFix();
    }
}
