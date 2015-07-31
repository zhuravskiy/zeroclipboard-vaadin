package org.vaadin.addons.zeroclipboardvaadin.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.VLink;
import com.vaadin.shared.ui.Connect;
import org.vaadin.addons.zeroclipboardvaadin.ZeroClipboard;
import org.vaadin.addons.zeroclipboardvaadin.shared.ZeroClipboardState;

/**
 * @author zhuravskiy.vs@gmail.com
 */
@Connect(ZeroClipboard.class)
public class ZeroClipboardConnector extends AbstractExtensionConnector {
    private Widget w;
    @Override
    protected void extend(final ServerConnector serverConnector) {
        if(serverConnector instanceof ComponentConnector) {
            w = ((ComponentConnector) serverConnector).getWidget();
            if (w instanceof HasClickHandlers) {
                int handlersCount = ensureHandlerManager().getHandlerCount(ClickEvent.getType());
                //remove handlers
                for (int i = 0; i < handlersCount; i++) {
                    ClickHandler h = ensureHandlerManager().getHandler(ClickEvent.getType(), i);
                    ensureHandlerManager().removeHandler(ClickEvent.getType(), h);
                }
                ((HasClickHandlers) w).addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        if (!getState().enabled) {
                            Event e = DOM.eventGetCurrentEvent();
                            e.preventDefault();
                        }
                    }
                });
            }
            bindEvent(w.getElement());
        } else if(serverConnector instanceof AbstractExtensionConnector) {
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    bindExtension((AbstractExtensionConnector) serverConnector);
                }
            });
        }
    }

    @Override
    public ZeroClipboardState getState() {
        return (ZeroClipboardState)super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent event) {
        super.onStateChanged(event);
    }

    @Override
    public void onUnregister() {
        super.onUnregister();
    }

    public String [] getFormats() {
        return getState().formats == null ? new String[]{} : getState().formats;
    }

    public String [] getData() {
        return getState().data == null ? new String[]{} : getState().data;
    }

    public String getItemCaption() {
        return getState().itemCaption == null ? "" : getState().itemCaption;
    }

    private native void bindExtension(AbstractExtensionConnector c) /*-{
        var findProperty;
        findProperty = function (el, cName, sN, lvl, path) {
            path = path || "root";
            var props = Object.getOwnPropertyNames(el);
            for (var i = 0; i < props.length; i++) {
                var p = props[i];
                if (lvl === 0 && el[p] !== undefined && el[p] !== null && el[p].constructor && el[p].constructor.name === cName && el[p].className === sN) {
                    console.log(path);
                    return el[p]
                } else if (el[p] !== undefined && el[p] !== null && lvl > 0) {
                    var mbEl = findProperty(el[p], cName, sN, lvl - 1, path + "." + p);
                    if(mbEl !== undefined && el[p] !== null)
                        return mbEl
                }
            }
            return undefined
        };
        var self = this;
        var itemCaption = self.@org.vaadin.addons.zeroclipboardvaadin.client.ZeroClipboardConnector::getItemCaption()();
        var el = findProperty(c, "HTMLDivElement", "v-context-menu-container", 2);
        var items = $wnd.jQuery(el).find("div.v-context-menu-item-basic:contains('" + itemCaption + "')");
        console.log("items founded " + items.length + " " + items);
        items.on("copy", "*", function(e) {
            e.clipboardData.clearData();
            var formats = self.@org.vaadin.addons.zeroclipboardvaadin.client.ZeroClipboardConnector::getFormats()();
            var data = self.@org.vaadin.addons.zeroclipboardvaadin.client.ZeroClipboardConnector::getData()();
            for(var i = 0; i < formats.length; i++) {
                e.clipboardData.setData(formats[i], data[i])
            }
            e.preventDefault()
        });
    }-*/;

    private native void bindEvent(Element el) /*-{
        if($wnd.jQuery === undefined)
            return;
        var self = this;
        $wnd.jQuery(el).on("copy", "*", function(e) {
            e.clipboardData.clearData();
            var formats = self.@org.vaadin.addons.zeroclipboardvaadin.client.ZeroClipboardConnector::getFormats()();
            var data = self.@org.vaadin.addons.zeroclipboardvaadin.client.ZeroClipboardConnector::getData()();
            for(var i = 0; i < formats.length; i++) {
                e.clipboardData.setData(formats[i], data[i])
            }
            e.preventDefault()
        });
    }-*/;

}
