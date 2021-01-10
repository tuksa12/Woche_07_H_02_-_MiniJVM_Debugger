package pgdp.minijvm;

public class Simulator {

	private Instruction[] code;
	private int programCounter = 0;

	private Stack stack;
	private boolean halted;

	/**
	 * Erstellt einen Simulator mit der Stackgröße {@code stackSize} und dem
	 * MiniJava-Code {@code code}.
	 *
	 * @param stackSize
	 * @param code
	 */
	public Simulator(int stackSize, Instruction[] code) {
		stack = new Stack(stackSize);
		this.code = code;
	}

	public boolean executeNextInstruction() {
		if (halted) {
			return false;
		}
		Instruction instr = code[programCounter];
		programCounter++;
		instr.execute(this);
		return !halted;
	}

	/**
	 * Liefert den Stack des Simulators.
	 */
	public Stack getStack() {
		return stack;
	}

	/**
	 * Setzt den Programmzähler des Simulators auf den übergebenen Wert.
	 *
	 * @param programCounter Der neue Wert des Programmzählers.
	 */
	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}

	/**
	 * Liefert den Wert des Programmzählers des Simulators.
	 */
	public int getProgramCounter() {
		return programCounter;
	}

	/**
	 * Setzt das {@code halted}-Attribut
	 *
	 * @param halted Der neue Wert des Attribus.
	 */
	public void setHalted(boolean halted) {
		this.halted = halted;
	}

	/**
	 * Liefert den Wert des {@code halted}-Attributs.
	 */
	public boolean isHalted() {
		return halted;
	}

	@Override
	public String toString() {
		return String.format("Halted: %b%nProgram counter: %d%n%s%n", halted, programCounter, stack);
	}

	public Simulator createCopy(){
		Simulator copy = this;
		return copy;
	}
}
