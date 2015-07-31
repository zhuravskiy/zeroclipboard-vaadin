package org.vaadin.addons.zeroclipboardvaadin.demo;


import com.vaadin.annotations.JavaScript;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ClientConnector;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import org.vaadin.addons.zeroclipboardvaadin.ZeroClipboard;
import org.vaadin.peter.contextmenu.ContextMenu;
import org.vaadin.peter.contextmenu.client.ContextMenuState;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Date;

@JavaScript({
        "//code.jquery.com/jquery-2.1.3.min.js"
})
public class ZeroClipboardVaadinDemo extends com.vaadin.ui.UI {

    @Override
    protected void init(com.vaadin.server.VaadinRequest request) {

        Button btn = new Button("Click, Me");
        ZeroClipboard zeroClipboard = new ZeroClipboard(btn);
       zeroClipboard.setData(Arrays.asList(
                new String[]{"text/plain", "Simple text, current server date time is " + new Date()},
                new String[]{"text/html", "<b>Simple</b> text, current server date time is " + new Date()}
        ));
        btn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Notification.show("btn clicked");
            }
        });

        Button btn2 = new Button("Click, Me vaadin click disabled");
        zeroClipboard = new ZeroClipboard(btn2);
        zeroClipboard.getState().enabled = false;
        zeroClipboard.setData(Arrays.asList(
                new String[]{"text/plain", "2Simple text, current server date time is " + new Date()},
                new String[]{"text/html", "<b>2Simple</b> text, current server date time is " + new Date()}
        ));
        btn2.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Notification.show("btn clicked");
            }
        });

        Link lnk = new Link("Link for copy", new ExternalResource("http://ya.ru"));
        zeroClipboard = new ZeroClipboard(lnk);
        zeroClipboard.setData(Arrays.asList(
                new String[]{"text/plain", "Link for copy " + new Date()},
                new String[]{"text/html", "<b>Link</b> for copy " + new Date()}
        ));

        Link lnk2 = new Link("Link for copy, vaadin click disabled, has menu", new ExternalResource("http://ya.ru"));
        zeroClipboard = new ZeroClipboard(lnk2);
        zeroClipboard.getState().enabled = false;
        zeroClipboard.setData(Arrays.asList(
                new String[]{"text/plain", "Link for copy " + new Date()},
                new String[]{"text/html", "<b>Link</b> for copy " + new Date()}
        ));

        final VerticalLayout vl = new VerticalLayout(
                btn,
                btn2,
                lnk,
                lnk2
        );
        vl.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                ContextMenu menu = new ContextMenu();
                ContextMenu.ContextMenuItem item = menu.addItem("Test copy");
                item.addItemClickListener(new ContextMenu.ContextMenuItemClickListener() {
                    @Override
                    public void contextMenuItemClicked(ContextMenu.ContextMenuItemClickEvent contextMenuItemClickEvent) {
                        Notification.show("menu item clicked");
                    }
                });
                menu.addItem("Test nothing");
                ZeroClipboard zc = new ZeroClipboard(menu);
                zc.setData(Arrays.asList(
                        new String[]{"text/plain", "Control, #2"},
                        new String[]{"text/html", "<a href='https://localhost/client/#!2.2'>Control, #2</a>"}
                ));
                zc.getState().itemCaption = "Test copy";
                menu.setParent(vl);
                menu.open(event.getClientX(), event.getClientY());
            }
        });
        setContent(vl);
    }

}