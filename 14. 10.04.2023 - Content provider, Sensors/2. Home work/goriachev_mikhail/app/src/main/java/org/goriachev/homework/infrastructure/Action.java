package org.goriachev.homework.infrastructure;

import java.io.Serializable;

public interface Action<T> extends Serializable {
    T run();
}
