package pgdp.minijvm;

public class Jump extends Instruction {

	private int targetAddress;

	public Jump(int targetAddress) {
		this.targetAddress = targetAddress;
	}

	@Override
	public void execute(Simulator simulator) {
		simulator.setProgramCounter(targetAddress);
	}
}
