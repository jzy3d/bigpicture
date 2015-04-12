package org.jzy3d.io;



public class KeyVal<K,V> {
    public K key;
    public V val;
    
    public interface ValueGenerator<K1,V1>{
        public V1 value();
        public V1 value(K1 key);
    }
    
    public KeyVal(K key, V val) {
        this.key = key;
        this.val = val;
    }
    
    public KeyVal(K key, ValueGenerator<K,V> setter) {
        this.key = key;
        this.val = (V)setter.value(key);
    }

    public K getKey() {
        return key;
    }
    public void setKey(K key) {
        this.key = key;
    }
    public V getVal() {
        return val;
    }
    public void setVal(V val) {
        this.val = val;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((val == null) ? 0 : val.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeyVal other = (KeyVal) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (val == null) {
            if (other.val != null)
                return false;
        } else if (!val.equals(other.val))
            return false;
        return true;
    }
    
    
}
