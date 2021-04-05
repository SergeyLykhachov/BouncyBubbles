package com.yahoo.slykhachov.bubbles.util;

import java.util.List;
import com.yahoo.slykhachov.bubbles.model.BubbleModel;

public interface Observer {
	void update(List<BubbleModel> list);
}
