package me.shakealert.logic.parser.api;

import me.shakealert.model.Shake;

import java.util.Collection;

public interface IParser<T> {
  Collection<Shake> parse(T source);
}
