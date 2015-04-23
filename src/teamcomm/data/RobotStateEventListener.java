package teamcomm.data;

import java.util.EventListener;

/**
 *
 * @author Felix Thielke
 */
public interface RobotStateEventListener extends EventListener {

    public void robotStateChanged(RobotStateEvent e);
}