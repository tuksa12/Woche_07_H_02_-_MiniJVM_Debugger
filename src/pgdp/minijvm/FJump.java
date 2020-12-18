package pgdp.minijvm;

public class FJump extends Instruction {

	private int targetAddress;

	public FJump(int targetAddress) {
		this.targetAddress = targetAddress;
	}

	@Override
	public void execute(Simulator simulator) {
		int top = simulator.getStack().pop();
		if (top == 0) {
			simulator.setProgramCounter(targetAddress);
		}
	}

}
