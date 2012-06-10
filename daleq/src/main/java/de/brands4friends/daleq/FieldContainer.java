package de.brands4friends.daleq;

import com.google.common.base.Optional;

public interface FieldContainer {
    String getName();

    Optional<String> getValue();
}
