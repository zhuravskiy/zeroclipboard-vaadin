package org.vaadin.addons.zeroclipboardvaadin;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.*;
import com.vaadin.ui.UI;
import org.vaadin.addons.zeroclipboardvaadin.shared.ZeroClipboardState;

import java.io.IOException;
import java.util.List;

/**
 * @author zhuravskiy.vs@gmail.com
 */
@JavaScript("jquery.zeroclipboard.min.js")
public class ZeroClipboard extends AbstractExtension {

    static {
        VaadinSession.getCurrent().getCommunicationManager().
                registerDependency("ZeroClipboard.swf", ZeroClipboard.class);
    }

    protected AbstractClientConnector extend;

    public ZeroClipboard(AbstractClientConnector extend) {

        extend(extend);
        this.extend = extend;
    }

    @Override
    public ZeroClipboardState getState() {
        return (ZeroClipboardState) super.getState();
    }

    public void clear() {
        getState().formats = null;
        getState().data = null;
    }

    /**
     * Example of usage
     * <pre>
     * List data = new LinkedList();
     * data.add (new String [] {"text/plain", "Some bold text"});
     * data.add (new String [] {"text/html", "Some <b>bold</b> text"});
     * zc.setData(data);
     * </pre>
     * @param data
     */
    public void setData(List<String[]> data) {
        getState().formats = new String[data.size()];
        getState().data = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            getState().formats[i] = data.get(i)[0];
            getState().data[i] = data.get(i)[1];
        }
    }

}
