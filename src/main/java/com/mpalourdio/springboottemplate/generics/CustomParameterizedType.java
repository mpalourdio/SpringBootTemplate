package com.mpalourdio.springboottemplate.generics;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Shamelessly copy/pasted from SO
 *
 * @see <a href="http://stackoverflow.com/a/41629503">http://stackoverflow.com/a/41629503</a>
 */
public class CustomParameterizedType implements ParameterizedType {

    private final ParameterizedType delegate;
    private final Type[] actualTypeArguments;

    public CustomParameterizedType(ParameterizedType delegate, Type[] actualTypeArguments) {
        this.delegate = delegate;
        this.actualTypeArguments = actualTypeArguments;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    @Override
    public Type getRawType() {
        return delegate.getRawType();
    }

    @Override
    public Type getOwnerType() {
        return delegate.getOwnerType();
    }
}
