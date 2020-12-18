package pgdp.minijvm;

public class Add extends Instruction {

	@Override
	public void execute(Simulator simulator) {
		Stack stack = simulator.getStack();
		int first = stack.pop();
		int second = stack.pop();
		stack.push(first + second);
	}
}
