package eu.sim642.idea.zalgofy;

import com.intellij.codeInspection.CleanupLocalInspectionTool;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiNamedElement;
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
        String name = (String) infos[0];
        return String.format("Identifer is not enough zalgo: %s", name);
    }

    @Override
    public BaseInspectionVisitor buildVisitor() {
        return new BaseInspectionVisitor() {
            @Override
            public void visitIdentifier(PsiIdentifier identifier) {
                if (identifier.getParent() instanceof PsiNamedElement) {
                    PsiNamedElement psiNamedElement = (PsiNamedElement) identifier.getParent();
                    String name = psiNamedElement.getName();
                    registerError(identifier, name);
                }

                super.visitIdentifier(identifier);
            }
        };
    }

    @Nullable
    @Override
    protected InspectionGadgetsFix buildFix(Object... infos) {
        return new ZalgofyFix();
    }
}
