package four.ast;

import java.util.ArrayList;
import java.util.List;

public class SyntaxNode {
	protected List<SyntaxNode> children;
	
	public SyntaxNode() {
		this.children = new ArrayList<SyntaxNode>();
	}
	
	public void addChild(SyntaxNode child) {
		this.children.add(child);
	}
}
