package com.example.entity.response;

/**
 * Response entity
 */
public class ResponseEntity<S, V> {

    private S status;
    private V value;

    public ResponseEntity(S status, V value) {
        this.status = status;
        this.value = value;
    }

    public S getStatus() {
        return status;
    }

    public void setStatus(S status) {
        this.status = status;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
