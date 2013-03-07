

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import edu.bit.dlde.eventserver.adapter.EventAdapter;
import edu.bit.dlde.eventserver.model.Request;
import edu.bit.dlde.eventserver.model.Response;

public class TimeHandler extends EventAdapter {
    public TimeHandler() {
    }

    public void onWrite(Request request, Response response) throws Exception {
        String command = new String(request.getDataInput());
        String time = null;
        Date date = new Date();

        if (command.equals("GB")) {
            DateFormat cnDate = DateFormat.getDateTimeInstance(DateFormat.FULL,
                DateFormat.FULL, Locale.CHINA);
            time = cnDate.format(date);
        }
        else {
            DateFormat enDate = DateFormat.getDateTimeInstance(DateFormat.FULL,
                DateFormat.FULL, Locale.US);
            time = enDate.format(date);
        }

        response.send(time.getBytes());
    }
}
