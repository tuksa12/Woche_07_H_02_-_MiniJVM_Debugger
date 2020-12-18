package pgdp.minijvm;

public class Store extends Instruction {

	private int stackAddress;

	public Store(int stackAddress) {
		this.stackAddress = stackAddress;
	}

	@Override
	public void execute(Simulator simulator) {
		Stack stack = simulator.getStack();
		int value = stack.pop();
		stack.setValueAtIndex(stackAddress, value);
	}
}
