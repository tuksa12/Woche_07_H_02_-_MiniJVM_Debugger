package pgdp.minijvm;

import java.util.Arrays;

/*
 * Modifizieren Sie diese Klasse nicht.
 */
public final class Stack {

	private int[] buffer;
	private int stackPointer;

	private Stack(int[] buffer, int stackPointer) {
		this.buffer = buffer;
		this.stackPointer = stackPointer;
	}

	/**
	 * Erstellt einen Stack mit der Kapazität {@code size}
	 *
	 * @param size Die Kapazität des zu erzeugenden Stacks.
	 */
	public Stack(int size) {
		buffer = new int[size];
		stackPointer = -1;
	}

	/**
	 * Liefert den Wert am Index des Stackpointers zurück und dekrementiert diesen.
	 */
	public int pop() {
		return buffer[stackPointer--];
	}

	/**
	 * Legt den Wert {@code value} auf den Stack. Der Stackpointer wird dabei
	 * inkrementiert.
	 *
	 * @param value Der auf den Stack zu legende Wert.
	 */
	public void push(int value) {
		buffer[++stackPointer] = value;
	}

	/**
	 * Addiert zum Stackpointer den Wert {@code count}. Dies entspricht einer
	 * Reservierung von Platz im Stack für {@code count} Variablen.
	 *
	 * @param count Die Anzahl an Variablen für die Platz reserviert werden soll.
	 */
	public void alloc(int count) {
		stackPointer += count;
	}

	/**
	 * Liefert den Wert am Index {@code index} des Stacks zurück.
	 */
	public int getValueAtIndex(int index) {
		return buffer[index];
	}

	/**
	 * Setzt den Wert am Index {@code index} des Stacks auf den Wert {@code value}.
	 */
	public void setValueAtIndex(int index, int value) {
		buffer[index] = value;
	}

	/**
	 * Liefert den stackPointer
	 */
	public int getStackPointer() {
		return stackPointer;
	}

	/**
	 * Setzt den Stackpointer auf den übergebenen Wert,
	 */
	public void setStackPointer(int stackPointer) {
		this.stackPointer = stackPointer;
	}

	/**
	 * Liefert eine String-Darstellung des Stacks.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Stack:");
		builder.append("[");
		for (int i = 0; i < buffer.length; i++) {
			builder.append(buffer[i]);
			if (i < buffer.length - 1)
				builder.append(", ");
		}
		builder.append("]");
		builder.append(System.lineSeparator());
		builder.append("Stackpointer: ");
		builder.append(stackPointer);
		return builder.toString();
	}

	/**
	 * Erzeugt eine tiefe Kopie des Stacks. Nach dem die Kopie erzeugt wurde haben
	 * Operationen auf der Kopie oder diesem Stack keine Auswirkungen auf den
	 * jeweils anderen Stack.
	 */
	public Stack createCopy() {
		return new Stack(Arrays.copyOf(buffer, buffer.length), stackPointer);
	}
}
