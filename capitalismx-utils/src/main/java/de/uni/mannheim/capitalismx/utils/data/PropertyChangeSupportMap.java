package de.uni.mannheim.capitalismx.utils.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Create a custom map that can fire event properties when adding or removing elements.
 * @author sdupper
 * @param <K> The generic must be Serializable.
 * @param <V> The generic must be Serializable.
 */
public class PropertyChangeSupportMap<K extends Serializable, V extends Serializable> implements Serializable {

    private Map<K,V> map;
    private Map<K,V> oldMap;

    private String removePropertyName;
    private String addPropertyName;

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public PropertyChangeSupportMap() {
        map = new ConcurrentSkipListMap<>();
        oldMap = new ConcurrentSkipListMap<>();
    }

    /**
     * The change listener will receive the fired events.
     * @param listener Add a PropertyChangeListener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * When removing an element of the map, this map will fire an event.
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
     * When adding an element to the map, this map will fire an event.
     * @return Returns the name of the add event.
     */
    public String getAddPropertyName() {
        return addPropertyName;
    }

    public Map<K,V> getMap() {
        return map;
    }

    /**
     *
     * @param addPropertyName
     */
    public void setAddPropertyName(String addPropertyName) {
        this.addPropertyName = addPropertyName;
    }

    /**
     *
     * @param k the key of the new element to add.
     * @param v the value of the new element to add.
     */
    public void put(K k, V v) {
        copyMap(oldMap, map);
        map.put(k,v);
        propertyChangeSupport.firePropertyChange(addPropertyName, oldMap, map);
    }

    /**
     * The event returns with getNewValue they Key {@link K}.
     * @param k the key of the new element to add.
     * @param v the value of the new element to add.
     */
    public void putOne(K k, V v) {
        copyMap(oldMap, map);
        map.put(k,v);
        propertyChangeSupport.firePropertyChange(addPropertyName, oldMap, k);
    }

    /**
     * The event returns with getNewValue they Key {@link K}.
     * @param k the key of the element to remove.
     */
    public void removeOne(K k) {
        copyMap(oldMap, map);
        map.remove(k);
        propertyChangeSupport.firePropertyChange(removePropertyName, oldMap, k);
    }

    /**
     *
     * @param k the key of the element to remove.
     */
    public void remove(K k) {
        copyMap(oldMap, map);
        map.remove(k);
        propertyChangeSupport.firePropertyChange(removePropertyName, oldMap, map);
    }

    /**
     *  Replaces the current map with a new map.
     * @param newMap the new map to replace.
     */
    public void setMap(Map<K,V> newMap) {
        copyMap(oldMap, map);
        map = newMap;
        propertyChangeSupport.firePropertyChange(removePropertyName, oldMap, map);
    }

    private void copyMap(Map<K,V> dst, Map<K,V> src) {
        dst.putAll(src);
    }

    public int size() {
        return map.size();
    }

}
