package de.uni.mannheim.capitalismx.utils.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Create a custom list that can fire event properties when adding or removing elements.
 * @author duly
 * @param <T> The generic must be Serializable.
 */
public class PropertyChangeSupportList<T extends Serializable> implements Serializable {

    private List<T> list;
    private List<T> oldList;

    private String removePropertyName;
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

    public void setAddPropertyName(String addPropertyName) {
        this.addPropertyName = addPropertyName;
    }

    public void add(T t) {
        copyList(oldList, list);
        list.add(t);
        propertyChangeSupport.firePropertyChange(addPropertyName, oldList, list);
    }

    public void remove(T t) {
        copyList(oldList, list);
        list.remove(t);
        propertyChangeSupport.firePropertyChange(removePropertyName, oldList, list);
    }

    private void copyList(List<T> dst, List<T> src) {
        dst.addAll(src);
    }

    public int size() {
        return list.size();
    }

}
