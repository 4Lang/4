package four.ast;

public class BinaryOperation extends ExpressionNode {
	private BinaryOperationType type;
	
	public BinaryOperation(BinaryOperationType type, ExpressionNode left, ExpressionNode right) {
		this.type = type;
		this.addChild(left);
		this.addChild(right);
	}
	
	public ExpressionNode getLeft() {
		return (ExpressionNode) this.children.get(0);
	}

	public ExpressionNode getRight() {
		return (ExpressionNode) this.children.get(1);
	}
	
	public BinaryOperationType getType() {
		return this.type;
	}
}
