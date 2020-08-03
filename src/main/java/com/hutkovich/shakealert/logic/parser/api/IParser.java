package com.hutkovich.shakealert.logic.parser.api;

import com.hutkovich.shakealert.model.Shake;

import java.util.Collection;

public interface IParser<T> {
  Collection<Shake> parse(T source);
}
