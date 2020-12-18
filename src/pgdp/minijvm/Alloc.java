package pgdp.minijvm;

public class Alloc extends Instruction {

	private int count;

	public Alloc(int count) {
		this.count = count;
	}

	@Override
	public void execute(Simulator simulator) {
		Stack stack = simulator.getStack();
		stack.alloc(count);
	}
}
