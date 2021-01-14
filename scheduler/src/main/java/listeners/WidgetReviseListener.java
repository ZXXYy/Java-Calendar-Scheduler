package listeners;

import java.util.EventListener;
import events.WidgetReviseEvent;

public interface WidgetReviseListener extends EventListener{
	// Event dispatch methods
    void widgetRevise(WidgetReviseEvent e);
}
