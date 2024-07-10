package com.challengeliteralura.challengeliteralura.mapper;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> classe);

}
