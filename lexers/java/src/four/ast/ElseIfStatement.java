package four.ast;

public class ElseIfStatement extends StatementNode {
	public ElseIfStatement(ExpressionNode predicate, CompoundStatement body) {
		this.addChild(predicate);
		this.addChild(body);
	}
	
	public ExpressionNode getPredicate() {
		return (ExpressionNode) this.children.get(0);
	}
	
	public CompoundStatement getBody() {
		return (CompoundStatement) this.children.get(1);
	}
}
