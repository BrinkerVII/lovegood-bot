package net.brinkervii.lovegood.util;

import java.util.ArrayList;

public class ArrayListCleaner<T> {
	private final ArrayList<T> target;
	private final CleanCondition<T> condition;

	public ArrayListCleaner(ArrayList<T> target, CleanCondition<T> condition) {
		this.target = target;
		this.condition = condition;
	}

	public ArrayList<T> clean() {
		ArrayList<T> rubbish = new ArrayList<>();
		for (T thing : target) {
			if (condition.shouldClean(thing)) {
				rubbish.add(thing);
			}
		}

		ArrayList<T> removedClashes = new ArrayList<>();
		for (T thing : rubbish) {
			if (target.remove(thing)) {
				removedClashes.add(thing);
			}
		}

		rubbish.clear();
		return removedClashes;
	}

	public interface CleanCondition<T> {
		boolean shouldClean(T thing);
	}
}
