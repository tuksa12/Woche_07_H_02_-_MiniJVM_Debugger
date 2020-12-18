package pgdp.minijvm;

public class Load extends Instruction {

	private int stackAddress;

	public Load(int stackAddress) {
		this.stackAddress = stackAddress;
	}

	@Override
	public void execute(Simulator simulator) {
		Stack stack = simulator.getStack();
		int loadedValue = stack.getValueAtIndex(stackAddress);
		stack.push(loadedValue);
	}
}
