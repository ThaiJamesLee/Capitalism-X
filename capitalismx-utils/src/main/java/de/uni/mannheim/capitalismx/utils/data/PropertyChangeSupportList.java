package de.uni.mannheim.capitalismx.utils.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Create a custom list that can fire event properties when adding or removing elements.
 * @author duly
 *
 * @since 1.0.0
 * @param <T> The generic must be Serializable.
 */
public class PropertyChangeSupportList<T extends Serializable> implements Serializable {

    /**
     * The original list.
     */
    private List<T> list;

    /**
     * The state of the list before it was changed.
     */
    private List<T> oldList;

    /**
     * The event name when removing an object from the list.
     */
    private String removePropertyName;

    /**
     * The event name when adding an object to the list.
     */
    private String addPropertyName;

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public PropertyChangeSupportList() {
        list = new ArrayList<>();
        oldList = new ArrayList<>();
    }

    /**
     * The change listener will receive the fired events.
     * @param listener Add a PropertyChangeListener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * When removing an element of the list, this list will fire an event.
     * @return Returns the name of the remove event.
     */
    public String getRemovePropertyName() {
        return removePropertyName;
    }

    /**
     * Set the remove event name.
     * @param removePropertyName Name of the event, when removing an element.
     */
    public void setRemovePropertyName(String removePropertyName) {
        this.removePropertyName = removePropertyName;
    }

    /**
     * When adding an element to the list, this list will fire an event.
     * @return Returns the name of the add event.
     */
    public String getAddPropertyName() {
        return addPropertyName;
    }

    public List<T> getList() {
        return list;
    }

    /**
     *
     * @param addPropertyName The name of the event that is triggered when calling the add() function.
     */
    public void setAddPropertyName(String addPropertyName) {
        this.addPropertyName = addPropertyName;
    }

    /**
     *
     * @param t the new element to add.
     */
    public void add(T t) {
        copyList(oldList, list);
        list.add(t);
        propertyChangeSupport.firePropertyChange(addPropertyName, oldList, list);
    }

    /**
     *
     * @param t the element to remove.
     */
    public void remove(T t) {
        copyList(oldList, list);
        list.remove(t);
        propertyChangeSupport.firePropertyChange(removePropertyName, oldList, list);
    }

    /**
     *  Replaces the current list with a new list.
     * @param newList the new list to replace.
     */
    public void setList(List<T> newList) {
        copyList(oldList, list);
        list = newList;
        propertyChangeSupport.firePropertyChange(removePropertyName, oldList, list);
    }

    private void copyList(List<T> dst, List<T> src) {
        dst.addAll(src);
    }

    /**
     *
     * @return Returns the size of the list.
     */
    public int size() {
        return list.size();
    }

}
