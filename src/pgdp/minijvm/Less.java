package pgdp.minijvm;

public class Less extends Instruction {

	@Override
	public void execute(Simulator simulator) {
		Stack stack = simulator.getStack();
		int first = stack.pop();
		int second = stack.pop();
		int result;
		if (second < first) {
			result = 1;
		} else {
			result = 0;
		}
		stack.push(result);
	}

}
