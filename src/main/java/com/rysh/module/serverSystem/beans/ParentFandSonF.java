package com.rysh.module.serverSystem.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParentFandSonF {
    private String id;
    private String parentFunctionName;
    private List<Function> functions=new ArrayList<>();
    private Function  function;

    public String getParentFunctionName() {
        return parentFunctionName;
    }

    public void setParentFunctionName(String parentFunctionName) {
        this.parentFunctionName = parentFunctionName;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentFandSonF that = (ParentFandSonF) o;
        return Objects.equals(parentFunctionName, that.parentFunctionName) &&
                Objects.equals(functions, that.functions) &&
                Objects.equals(function, that.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentFunctionName, functions, function);
    }
}
