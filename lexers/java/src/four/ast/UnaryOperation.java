package four.ast;

public class UnaryOperation extends ExpressionNode {
	private UnaryOperationType type;
	
	public UnaryOperation(UnaryOperationType type, ExpressionNode operand) {
		this.type = type;
		this.addChild(operand);
	}
	
	public ExpressionNode getOperand() {
		return (ExpressionNode) this.children.get(0);
	}

	public UnaryOperationType getType() {
		return this.type;
	}
}
