package com.pinheng.youcai.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.pinheng.youcai.OpenException;

import java.io.IOException;

@FunctionalInterface
public interface OpenLogic<T> {


    T executeLogic(JsonNode data) throws IOException, OpenException;

}
