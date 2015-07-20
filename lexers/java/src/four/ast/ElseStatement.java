package four.ast;

public class ElseStatement extends StatementNode {
	public ElseStatement(CompoundStatement body) {
		this.addChild(body);
	}

	public CompoundStatement getBody() {
		return (CompoundStatement) this.children.get(0);
	}
}
