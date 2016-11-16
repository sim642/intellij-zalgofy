package eu.sim642.idea.zalgofy;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiComment;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;

public class CommentZalgofier extends TypedZalgofier<PsiComment> {

    private final CommentImplZalgofier commentImplZalgofier = new CommentImplZalgofier();

    @Override
    protected void zalgofy(Project project, PsiComment element, boolean interactive) {
        if (element instanceof PsiCommentImpl)
            commentImplZalgofier.zalgofy(project, element, interactive);
    }
}
