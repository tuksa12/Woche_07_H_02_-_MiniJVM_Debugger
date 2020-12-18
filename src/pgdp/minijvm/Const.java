package pgdp.minijvm;

public class Const extends Instruction {

	private int constant;

	public Const(int constant) {
		this.constant = constant;
	}

	@Override
	public void execute(Simulator simulator) {
		Stack stack = simulator.getStack();
		stack.push(constant);
	}

}
