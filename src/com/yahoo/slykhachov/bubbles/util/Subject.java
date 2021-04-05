package com.yahoo.slykhachov.bubbles.util;

public interface Subject {
	void registerObserver(Observer obsesrver);
	void removeObserver(Observer observer);
	void notifyObservers();
}
