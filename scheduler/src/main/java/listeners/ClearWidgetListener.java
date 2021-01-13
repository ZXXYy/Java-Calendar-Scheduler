package listeners;
import java.util.EventListener;

import events.ClearWidgetEvent;
import scheduler.EventWidget;



public interface ClearWidgetListener  extends EventListener{
	void clearWidget(EventWidget eventWidget);
}



