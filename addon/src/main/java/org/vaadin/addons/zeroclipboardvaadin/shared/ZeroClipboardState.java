package org.vaadin.addons.zeroclipboardvaadin.shared;

import com.vaadin.shared.communication.SharedState;

/**
 * Example of usage:
 * <pre>
 *  ZeroClipboardState state = new ZeroClipboardState()
 *  state.formats = {"text/plain", "text/html"}
 *  state.data = {"http://juravskiy.ru", "<a href='http://juravskiy.ru'>juravskiy.ru</a>"}
 *  //..
 * </pre>
 * @author zhuravskiy.vs@gmail.com
 */
public class ZeroClipboardState extends SharedState {
    /**
     * Formats of clipboard, supported values:
     * <ul>
     *     <li>text/plain</li>
     *     <li>text/html</li>
     *     <li>application/rtf</li>
     *     <li>you custom format, see https://github.com/zeroclipboard/zeroclipboard/issues/152</li>
     * </ul>
     * @see ZeroClipboardState#data
     */
    public String [] formats;

    /**
     * Data of clipboard for each format in {@link ZeroClipboardState#formats},
     * examples:
     * <ul>
     *     <li>format: text/plain, data must be = 'simple text'</li>
     *     <li>format: text/html, data can contains html tags: '<b>Foo</b> bar' </li>
     *     <li>format: application/rtf, data can contains rtf markup '{\\rtf1\\ansi\n{\\b Hello}}'</li>
     *     <li>you custom format, see https://github.com/zeroclipboard/zeroclipboard/issues/152,
     *     you can set custom byte array with hex value, and data can looks like '0xDEADBEEF'</li>
     * </ul>
     */
    public String [] data;

    public String itemCaption;
}
