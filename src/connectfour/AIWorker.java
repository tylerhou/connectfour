package connectfour;

import javax.swing.SwingWorker;

public abstract class AIWorker extends SwingWorker<Void,BoardLogic> {
	
	public void update(BoardLogic logic) {
		publish(logic);
	}

}
