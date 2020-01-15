package de.uni.mannheim.capitalismx.utils.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
        list = new CopyOnWriteArrayList<>();
        oldList = new CopyOnWriteArrayList<>();
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
    public synchronized void add(T t) {
        copyList(oldList, list);
        list.add(t);
        propertyChangeSupport.firePropertyChange(addPropertyName, oldList, list);
    }

    /**
     *
     * @param t the element to remove.
     */
    public synchronized void remove(T t) {
        copyList(oldList, list);
        //list.remove(t);
        propertyChangeSupport.firePropertyChange(removePropertyName, oldList, list);
    }

    /**
     *  Replaces the current list with a new list.
     * @param newList the new list to replace.
     */
    public synchronized void setList(List<T> newList) {
        copyList(oldList, list);
        list = newList;
        propertyChangeSupport.firePropertyChange(removePropertyName, oldList, list);
    }

    /**
     *
     * @param index The index on the list.
     * @return Returns the object from the specified index.
     */
    public T get(int index) {
        return list.get(index);
    }

    /**
     * Create a copy from the src to the dst list.
     * This does not copy the reference, but create completely new objects.
     *
     * @param dst The destination to copy to.
     * @param src The source to copy from.
     */
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
