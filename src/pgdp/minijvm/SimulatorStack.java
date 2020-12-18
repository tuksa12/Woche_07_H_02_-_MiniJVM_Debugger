package pgdp.minijvm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/*
 * Modifizieren Sie diese Klasse nicht.
 */
public class SimulatorStack {

	private final Deque<Simulator> deque = new ArrayDeque<>();

	public boolean isEmpty() {
		return deque.isEmpty();
	}

	public int size() {
		return deque.size();
	}

	/**
	 * Legt den übergebenen Simulator auf den Stack
	 */
	public void push(Simulator s) {
		Objects.requireNonNull(s, "Übergebener Simulator darf nicht null sein");
		deque.addLast(s);
	}

	/**
	 * Entfernt den obersten Simulator von dem Stack. Gibt <code>null</code> zurück,
	 * falls der Stack leer ist.
	 */
	public Simulator pop() {
		if (isEmpty()) {
			return null;
		}
		return deque.removeLast();
	}

	/**
	 * Gibt den obersten Simulator von dem Stack zurück, ohne ihn von dem Stack zu
	 * entfernen. Gibt <code>null</code> zurück, falls der Stack leer ist.
	 */
	public Simulator peek() {
		return deque.peekLast();
	}

	/**
	 * Entfernt alle Simulatoren aus dem Stack. Der SimulatorStack ist danach leer.
	 */
	public void clear() {
		deque.clear();
	}

	/**
	 * Legt alle Simulatoren von dem übergebenen Stack ebenfalls auf diesen Stack.
	 * <p>
	 * War dieser SimulatorStack leer, enthält er danach dieselben Simulatoren in
	 * der gleichen Reihenfolge wie <code>other</code>
	 */
	public void addAll(SimulatorStack other) {
		Objects.requireNonNull(other, "Übergebener SimulatorStack darf nicht null sein");
		deque.addAll(other.deque);
	}
}
