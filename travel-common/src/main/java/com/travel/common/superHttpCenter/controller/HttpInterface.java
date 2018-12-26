package com.travel.common.superHttpCenter.controller;

public interface HttpInterface<T> {
    T  SUCCESS(Object ... args);
    T  FAIL(Object ... args);
    T  ERROR(Object ... args);
}
