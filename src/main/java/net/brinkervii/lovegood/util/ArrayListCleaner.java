package net.brinkervii.lovegood.util;

import java.util.ArrayList;

public class ArrayListCleaner<T> {
	private final ArrayList<T> target;
	private final CleanCondition<T> condition;

	public ArrayListCleaner(ArrayList<T> target, CleanCondition<T> condition) {
		this.target = target;
		this.condition = condition;
	}

	public boolean clean() {
		ArrayList<T> rubbish = new ArrayList<>();
		for (T thing : target) {
			if (condition.shouldClean(thing)) {
				rubbish.add(thing);
			}
		}

		boolean didRemoveSomething = rubbish.size() > 0;
		boolean successfulRemoval = true;
		for (T thing : rubbish) {
			successfulRemoval &= target.remove(thing);
		}

		rubbish.clear();
		return didRemoveSomething && successfulRemoval;
	}

	public interface CleanCondition<T> {
		boolean shouldClean(T thing);
	}
}
