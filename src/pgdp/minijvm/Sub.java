package pgdp.minijvm;

public class Sub extends Instruction {

	@Override
	public void execute(Simulator simulator) {
		Stack stack = simulator.getStack();
		int first = stack.pop();
		int second = stack.pop();
		stack.push(second - first);
	}

}
